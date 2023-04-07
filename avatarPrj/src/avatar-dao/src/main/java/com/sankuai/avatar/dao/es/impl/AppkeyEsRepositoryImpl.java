package com.sankuai.avatar.dao.es.impl;

import com.dianping.cat.Cat;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.es.AppkeyEsRepository;
import com.sankuai.avatar.dao.es.exception.EsException;
import com.sankuai.avatar.dao.es.request.AppkeyQueryRequest;
import com.sankuai.avatar.dao.es.request.AppkeyTreeRequest;
import com.sankuai.avatar.dao.es.request.AppkeyUpdateRequest;
import com.sankuai.avatar.dao.es.request.UserAppkeyRequest;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import cn.hutool.core.bean.BeanUtil;
import deps.redis.clients.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * appKey es 数据管理接口实现
 *
 * @author qinwei05
 */
@Slf4j
@Repository
public class AppkeyEsRepositoryImpl implements AppkeyEsRepository {

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public AppkeyEsRepositoryImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * 服务索引名
     */
    private static final String INDEX_NAME = "avatar_appkey";

    @MdpConfig("FLOW_INDEX_PREFIX:avatar_appkey")
    private String indexPrefix;

    @Override
    public PageResponse<AppkeyDO> search(AppkeyQueryRequest queryRequest) throws EsException {
        try {
            SearchResponse searchResponse = restHighLevelClient.search(buildSearchRequest(queryRequest, true), RequestOptions.DEFAULT);
            SearchHits hits;
            if (Objects.isNull(searchResponse) || Objects.isNull(hits = searchResponse.getHits())) {
                return null;
            }
            return toPageResponse(hits, queryRequest.getPage(), queryRequest.getPageSize());
        } catch (Exception e) {
            Cat.logError(e);
            throw new EsException(e.getMessage());
        }
    }

    /**
     * bool查询构建器
     * 我的服务页面ES检索Builder
     *
     * @param request 请求
     * @return {@link BoolQueryBuilder}
     */
    private BoolQueryBuilder getBoolQueryBuilder(UserAppkeyRequest request){
        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        // 限制非下线后端服务
        builder.filter(QueryBuilders.termQuery("isOffline", false));

        // 匹配 BACKEND 类型和不存在类型的记录
        builder.filter(QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("type.keyword", "BACKEND"))
                .should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("type"))));

        // 服务名称搜索
        if (StringUtils.isNotBlank(request.getQuery())) {
            String q = request.getQuery().trim();
            builder.filter(QueryBuilders.wildcardQuery("appkey.keyword", String.format("*%s*", q)));
        }
        // 匹配用户管理员
        builder.filter(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("rdAdmin", request.getUser()))
                .should(QueryBuilders.matchPhraseQuery("epAdmin", request.getUser())));
        // 匹配服务等级
        if (StringUtils.isNotEmpty(request.getRank())) {
            builder.filter(QueryBuilders.termQuery("rank.keyword",
                    Objects.equals("core", request.getRank()) ? "核心服务" : "非核心服务"));
        }
        // 匹配服务SET
        if (StringUtils.isNotEmpty(request.getCell())) {
            if (Objects.equals("set", request.getCell())) {
                builder.filter(QueryBuilders.termQuery("isSet", Boolean.TRUE));
            } else if (Objects.equals("liteset", request.getCell())) {
                builder.filter(QueryBuilders.termQuery("isLiteset", Boolean.TRUE));
            }
        }
        // 匹配服务状态
        if (Objects.nonNull(request.getStateful())) {
            builder.filter(QueryBuilders.termQuery("stateful", request.getStateful().booleanValue()));
        }
        // 匹配过滤PAAS服务，这个查询将匹配所有满足以下条件之一的文档：boolField 字段不存在 || boolField 字段存在，但值为 false
        if (Boolean.TRUE.equals(request.getPaas())) {
            builder.filter(QueryBuilders.boolQuery()
                    .should(QueryBuilders.termQuery("paas", Boolean.FALSE))
                    .should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("paas"))));
        }
        // 匹配服务容灾等级
        if (StringUtils.isNotEmpty(request.getCapacity())) {
            String[] capacities = request.getCapacity().split(",");
            builder.filter(QueryBuilders.termsQuery("capacity", capacities));
        }
        return builder;
    }

    @Override
    public PageResponse<AppkeyDO> getOwnAppkey(UserAppkeyRequest request, List<String> topAppkeys) throws EsException {

        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = getBoolQueryBuilder(request);
        searchBuilder.query(boolQueryBuilder);

        Integer page = request.getPage();
        Integer pageSize = request.getPageSize();

        // 自定义置顶排序
        if (CollectionUtils.isNotEmpty(topAppkeys)){
            Map<String, Integer> orderMap = IntStream.range(0, topAppkeys.size())
                    .boxed()
                    .collect(Collectors.toMap(topAppkeys::get, i -> i, (a, b) -> a, LinkedHashMap::new));
            Script script = new Script(
                    ScriptType.INLINE,
                    "painless",
                    "def orderMap = params.orderMap; " +
                            "int order = orderMap.containsKey(doc['appkey.keyword'].value) " +
                            "? orderMap.get(doc['appkey.keyword'].value) " +
                            ": orderMap.size(); " +
                            "return order + 1;",
                    Collections.singletonMap("orderMap", orderMap)
            );
            searchBuilder.sort(SortBuilders
                    .scriptSort(script, ScriptSortBuilder.ScriptSortType.NUMBER)
                    .order(SortOrder.ASC).sortMode(SortMode.MIN));
        }
        // 分页查询
        searchBuilder.from((page - 1) * pageSize);
        searchBuilder.size(pageSize);
        // 返回全部字段
        searchBuilder.fetchSource(true);
        // 默认排序规则：PAAS服务后置 && 创建时间降序 （_last 缺失值排在最后）
        if (!Boolean.TRUE.equals(request.getPaas())){
            searchBuilder.sort(SortBuilders.fieldSort("paas").order(SortOrder.ASC).missing("_last").unmappedType("boolean"));
        }
        searchBuilder.sort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC).missing("_last").unmappedType("date"));
        searchRequest.source(searchBuilder);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            List<AppkeyDO> appkeyDOList = Arrays.stream(searchHits.getHits()).map(SearchHit::getSourceAsString)
                    .map(json -> JsonUtil.json2Bean(json, AppkeyDO.class)).collect(Collectors.toList());
            // 统计总数
            TotalHits totalHits = searchHits.getTotalHits();
            long totalCount = totalHits != null ? totalHits.value : 0;
            // 分页
            PageResponse<AppkeyDO> pageResponse = new PageResponse<>();
            pageResponse.setPage(page);
            pageResponse.setPageSize(pageSize);
            pageResponse.setTotalCount(totalCount);
            pageResponse.setItems(appkeyDOList);
            pageResponse.setTotalPage((totalCount - 1) / pageSize + 1L);
            return pageResponse;
        } catch (Exception e) {
            Cat.logError(e);
            throw new EsException(e.getMessage());
        }
    }

    @Override
    public List<AppkeyDO> getOwnAppkey(UserAppkeyRequest request) throws EsException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // 限制非下线后端服务
        builder.must(QueryBuilders.termQuery("isOffline", false));
        BoolQueryBuilder typeBuilder = QueryBuilders.boolQuery();
        typeBuilder.should(QueryBuilders.matchPhraseQuery("type.keyword", "BACKEND"))
                   .should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("type")));
        builder.must(typeBuilder);
        BoolQueryBuilder adminBuilder = QueryBuilders.boolQuery();
        adminBuilder.should(QueryBuilders.matchPhraseQuery("rdAdmin", request.getUser()))
                    .should(QueryBuilders.matchPhraseQuery("epAdmin", request.getUser()));
        builder.must(adminBuilder);

        if (StringUtils.isNotEmpty(request.getRank())) {
            builder.must(QueryBuilders.termQuery("rank.keyword", Objects.equals("core", request.getRank()) ? "核心服务" : "非核心服务"));
        }
        if (StringUtils.isNotEmpty(request.getCell())) {
            if (Objects.equals("set", request.getCell())){
                builder.must(QueryBuilders.termQuery("isSet", Boolean.TRUE));
            } else if (Objects.equals("liteset", request.getCell())) {
                builder.must(QueryBuilders.termQuery("isLiteset", Boolean.TRUE));
            }
        }
        if (Objects.nonNull(request.getStateful())) {
            builder.must(QueryBuilders.termQuery("stateful", request.getStateful().booleanValue()));
        }
        if (StringUtils.isNotEmpty(request.getCapacity())) {
            BoolQueryBuilder capacityBuilder = QueryBuilders.boolQuery();
            String[] capacities = request.getCapacity().split(",");
            for (String capacity : capacities) {
                capacityBuilder.should(QueryBuilders.matchPhraseQuery("capacity", capacity));
            }
            builder.must(capacityBuilder);
        }
        searchBuilder.size(2000);
        searchBuilder.query(builder);
        searchBuilder.fetchSource(true);
        searchRequest.source(searchBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits;
            if (Objects.isNull(searchResponse) || Objects.isNull(hits = searchResponse.getHits())) {
                return null;
            }
            return Arrays.stream(hits.getHits()).map(SearchHit::getSourceAsString)
                    .map(json -> JsonUtil.json2Bean(json, AppkeyDO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            Cat.logError(e);
            throw new EsException(e.getMessage());
        }
    }

    @Override
    public PageResponse<AppkeyDO> getPageAppkey(AppkeyTreeRequest request) throws EsException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // 限制非下线后端服务
        builder.must(QueryBuilders.termQuery("isOffline", false));
        BoolQueryBuilder typeBuilder = QueryBuilders.boolQuery();
        typeBuilder.should(QueryBuilders.matchPhraseQuery("type.keyword", "BACKEND"))
                   .should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("type")));
        builder.must(typeBuilder);
        // 服务等级
        if (StringUtils.isNotEmpty(request.getRank())) {
            builder.must(QueryBuilders.termQuery("rank.keyword", Objects.equals("core", request.getRank()) ? "核心服务" : "非核心服务"));
        }
        // 服务是否有状态
        if (Objects.nonNull(request.getStateful())) {
            builder.must(QueryBuilders.termQuery("stateful", request.getStateful().booleanValue()));
        }
        // 服务容灾等级
        if (StringUtils.isNotEmpty(request.getCapacity())) {
            BoolQueryBuilder capacityBuilder = QueryBuilders.boolQuery();
            String[] capacities = request.getCapacity().split(",");
            for (String capacity : capacities) {
                capacityBuilder.should(QueryBuilders.matchPhraseQuery("capacity", capacity));
            }
            builder.must(capacityBuilder);
        }
        // 服务名称搜索
        if (Objects.nonNull(request.getAppkey()) && StringUtils.isNotEmpty(request.getAppkey().trim())) {
            String appkey = request.getAppkey().trim();
            if (appkey.contains(".") || appkey.contains("-")) {
                builder.must(QueryBuilders.matchPhraseQuery("appkey", appkey));
            } else {
                builder.must(QueryBuilders.fuzzyQuery("appkey", appkey));
            }
        }
        // bg
        if (StringUtils.isNotEmpty(request.getBg())) {
            builder.must(QueryBuilders.termQuery("businessGroup.keyword", request.getBg()));
        }
        // 部门
        if (StringUtils.isNotEmpty(request.getOwt())) {
            builder.must(QueryBuilders.termQuery("owt.keyword", request.getOwt()));
        }
        // 产品线
        if (StringUtils.isNotEmpty(request.getPdl())) {
            builder.must(QueryBuilders.termQuery("pdl.keyword", request.getPdl()));
        }
        // 服务创建起始时间
        if (Objects.nonNull(request.getStartTime())) {
            builder.must(QueryBuilders.rangeQuery("createTime").gte(DateUtils.dateToString(request.getStartTime(), "yyyy-MM-dd HH:mm:ss")));
        }
        // 服务创建截止时间
        if (Objects.nonNull(request.getEndTime())) {
            builder.must(QueryBuilders.rangeQuery("createTime").lte(DateUtils.dateToString(request.getEndTime(), "yyyy-MM-dd HH:mm:ss")));
        }
        searchBuilder.from((request.getPage() - 1) * request.getPageSize());
        searchBuilder.size(request.getPageSize());
        searchBuilder.query(builder);
        searchBuilder.fetchSource(true);
        searchRequest.source(searchBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits;
            if (Objects.isNull(searchResponse) || Objects.isNull(hits = searchResponse.getHits())) {
                return null;
            }
            return toPageResponse(hits, request.getPage(), request.getPageSize());
        } catch (Exception e) {
            Cat.logError(e);
            throw new EsException(e.getMessage());
        }
    }

    @Override
    public PageResponse<AppkeyDO> query(AppkeyQueryRequest queryRequest) throws EsException {
        try {
            SearchResponse searchResponse = restHighLevelClient.search(buildSearchRequest(queryRequest, false), RequestOptions.DEFAULT);
            SearchHits hits;
            if (Objects.isNull(searchResponse) || Objects.isNull(hits = searchResponse.getHits())) {
                return null;
            }
            return toPageResponse(hits, queryRequest.getPage(), queryRequest.getPageSize());
        } catch (Exception e) {
            Cat.logError(e);
            throw new EsException(e.getMessage());
        }
    }

    private PageResponse<AppkeyDO> toPageResponse(SearchHits hits, int page, int pageSize){
        SearchHit[] searchHits = hits.getHits();
        List<AppkeyDO> doList = Arrays.stream(searchHits).map(SearchHit::getSourceAsString)
                .map(json -> JsonUtil.json2Bean(json, AppkeyDO.class)).collect(Collectors.toList());
        long total = hits.getTotalHits().value;
        PageResponse<AppkeyDO> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(total);
        pageResponse.setTotalPage((total - 1) / pageSize + 1);
        pageResponse.setItems(doList);
        return pageResponse;
    }


    @Override
    public Boolean update(AppkeyUpdateRequest appkeyUpdateRequest) throws EsException {
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.docAsUpsert(true);
            updateRequest.index(INDEX_NAME);
            updateRequest.id(appkeyUpdateRequest.getAppkey());
            Map<String, Object> docMap = formatDocMapDate(BeanUtil.beanToMap(appkeyUpdateRequest, false, true));
            updateRequest.doc(docMap);
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return RestStatus.CREATED.equals(update.status()) || RestStatus.OK.equals(update.status());
        } catch (Exception e) {
            Cat.logError(e);
            throw new EsException(e.getMessage());
        }
    }

    /**
     * ES文档Date信息转字符串存储
     *
     * @param docMap ES文档信息
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    private Map<String, Object> formatDocMapDate(Map<String, Object> docMap){
        String timeFormatType = "yyyy-MM-dd HH:mm:ss";
        if (docMap.get("createTime") != null){
            docMap.put("createTime", DateUtils.dateToString((Date) docMap.get("createTime"), timeFormatType));
        }
        if (docMap.get("updateTime") != null){
            docMap.put("updateTime", DateUtils.dateToString((Date) docMap.get("updateTime"), timeFormatType));
        }
        if (docMap.get("offlineTime") != null){
            docMap.put("offlineTime", DateUtils.dateToString((Date) docMap.get("offlineTime"), timeFormatType));
        }
        if (docMap.get("capacityUpdateAt") != null){
            docMap.put("capacityUpdateAt", DateUtils.dateToString((Date) docMap.get("capacityUpdateAt"), timeFormatType));
        }
        return docMap;
    }

    /**
     * 建立搜索请求
     *
     * @param request  请求
     * @param isPrefix 是否前缀搜索
     * @return {@link SearchRequest}
     */
    private SearchRequest buildSearchRequest(AppkeyQueryRequest request, boolean isPrefix){
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 限制非下线后端服务
        queryBuilder.must(QueryBuilders.termQuery("isOffline", false))
                    .must(QueryBuilders.matchPhraseQuery("type.keyword", "BACKEND"));
        if (StringUtils.isNotEmpty(request.getSrv())) {
            queryBuilder.must(
                    isPrefix
                            ? QueryBuilders.prefixQuery("srv.keyword", request.getSrv())
                            : QueryBuilders.termQuery("srv.keyword", request.getSrv())
            );
        }
        // appkey关键字查询
        // 服务名称搜索
        if (Objects.nonNull(request.getAppkey()) && StringUtils.isNotEmpty(request.getAppkey().trim())) {
            String appkey = request.getAppkey().trim();
            if (appkey.contains(".") || appkey.contains("-")) {
                queryBuilder.must(QueryBuilders.matchPhraseQuery("appkey", appkey));
            } else {
                queryBuilder.must(QueryBuilders.fuzzyQuery("appkey", appkey));
            }
        }
        // 模糊查询
        if (StringUtils.isNotEmpty(request.getDescription())) {
            queryBuilder.must(QueryBuilders.matchPhraseQuery("description", StringUtils.lowerCase(request.getDescription())).slop(10));
        }
        if (StringUtils.isNotEmpty(request.getOwt())) {
            queryBuilder.must(
                    isPrefix
                            ? QueryBuilders.prefixQuery("owt.keyword", request.getOwt())
                            : QueryBuilders.termQuery("owt.keyword", request.getOwt())
            );
        }
        if (StringUtils.isNotEmpty(request.getPdl())) {
            queryBuilder.must(
                    isPrefix
                            ? QueryBuilders.prefixQuery("pdl.keyword", request.getPdl())
                            : QueryBuilders.termQuery("pdl.keyword", request.getPdl())
            );
        }
        if (StringUtils.isNotEmpty(request.getApplicationName())) {
            queryBuilder.must(QueryBuilders.prefixQuery("applicationName.keyword", request.getApplicationName()));
        }
        // 精确匹配
        if (StringUtils.isNotEmpty(request.getOrgId())) {
            queryBuilder.must(QueryBuilders.termQuery("orgId.keyword", request.getOrgId()));
        }
        if (StringUtils.isNotEmpty(request.getOrgName())) {
            queryBuilder.must(QueryBuilders.prefixQuery("orgName.keyword", request.getOrgName()));
        }
        if (StringUtils.isNotEmpty(request.getServiceType())) {
            queryBuilder.must(QueryBuilders.termQuery("serviceType.keyword", request.getServiceType()));
        }
        if (StringUtils.isNotEmpty(request.getCategories())) {
            queryBuilder.must(QueryBuilders.matchPhraseQuery("categories", StringUtils.lowerCase(request.getCategories())).slop(10));
        }
        if (StringUtils.isNotEmpty(request.getTags())) {
            queryBuilder.must(QueryBuilders.matchPhraseQuery("tags", StringUtils.lowerCase(request.getTags())).slop(10));
        }
        if (StringUtils.isNotEmpty(request.getFrameworks())) {
            queryBuilder.must(QueryBuilders.matchPhraseQuery("frameworks", StringUtils.lowerCase(request.getFrameworks())).slop(10));
        }
        if (StringUtils.isNotEmpty(request.getGitRepository())) {
            queryBuilder.must(QueryBuilders.matchPhraseQuery("gitRepository", StringUtils.lowerCase(request.getGitRepository())).slop(10));
        }
        if (StringUtils.isNotEmpty(request.getType())) {
            queryBuilder.must(QueryBuilders.prefixQuery("type.keyword", request.getType()));
        }
        if (StringUtils.isNotEmpty(request.getBillingUnit())) {
            queryBuilder.must(QueryBuilders.prefixQuery("billingUnit.keyword", request.getBillingUnit()));
        }
        if (StringUtils.isNotEmpty(request.getApplicationId())) {
            queryBuilder.must(QueryBuilders.termQuery("applicationId", request.getApplicationId()));
        }
        if (StringUtils.isNotEmpty(request.getBillingUnitId())) {
            queryBuilder.must(QueryBuilders.termQuery("billingUnitId.keyword", request.getBillingUnitId()));
        }
        if (Objects.nonNull(request.getStateful())) {
            queryBuilder.must(QueryBuilders.termQuery("stateful", request.getStateful()));
        }
        if (Objects.nonNull(request.getCompatibleIpv6())) {
            queryBuilder.must(QueryBuilders.termQuery("compatibleIpv6", request.getCompatibleIpv6()));
        }
        if (Objects.nonNull(request.getIsLiteset())) {
            queryBuilder.must(QueryBuilders.termQuery("isLiteset", request.getIsLiteset()));
        }
        if (Objects.nonNull(request.getIsSet())) {
            queryBuilder.must(QueryBuilders.termQuery("isSet", request.getIsSet()));
        }
        if (StringUtils.isNotEmpty(request.getRdAdmin())) {
            queryBuilder.should(QueryBuilders.matchPhraseQuery("rdAdmin", StringUtils.lowerCase(request.getRdAdmin())));
        }
        if (StringUtils.isNotEmpty(request.getAdmin())) {
            queryBuilder.must(QueryBuilders.matchPhraseQuery("admin", StringUtils.lowerCase(request.getAdmin())));
        }
        if (StringUtils.isNotEmpty(request.getEpAdmin())) {
            queryBuilder.should(QueryBuilders.matchPhraseQuery("epAdmin", StringUtils.lowerCase(request.getEpAdmin())));
        }
        if (StringUtils.isNotEmpty(request.getOpAdmin())) {
            queryBuilder.must(QueryBuilders.matchPhraseQuery("opAdmin", StringUtils.lowerCase(request.getOpAdmin())));
        }

        searchBuilder.size(request.getPageSize());
        searchBuilder.from((request.getPage() - 1) * request.getPageSize());
        searchBuilder.query(queryBuilder);
        searchBuilder.fetchSource(true);
        searchRequest.source(searchBuilder);
        return searchRequest;
    }
}
