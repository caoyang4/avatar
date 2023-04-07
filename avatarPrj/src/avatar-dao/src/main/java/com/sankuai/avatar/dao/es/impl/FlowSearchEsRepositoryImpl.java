package com.sankuai.avatar.dao.es.impl;

import com.dianping.cat.Cat;
import com.dianping.rhino.annotation.Degrade;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.es.FlowSearchEsRepository;
import com.sankuai.avatar.dao.es.entity.FlowSearchEntity;
import com.sankuai.avatar.dao.es.exception.EsException;
import com.sankuai.avatar.dao.es.request.FlowSearchQueryRequest;
import com.sankuai.avatar.dao.es.request.FlowSearchUpdateRequest;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

/**
 * 流程查询类ES数据管理接口实现
 *
 * @author zhaozhifan02
 */
@Slf4j
@Repository
public class FlowSearchEsRepositoryImpl implements FlowSearchEsRepository {

    /**
     * 全量索引后缀
     */
    private static final String INDEX_SUFFIX = "*";

    /**
     * 索引时间后缀格式
     */
    private static final String DATE_FORMAT = "yyyy-MM";

    @MdpConfig("FLOW_SEARCH_INDEX_PREFIX:avatar_workflow_search")
    private String indexPrefix;

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public FlowSearchEsRepositoryImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public boolean update(FlowSearchUpdateRequest request) {
        if (Objects.isNull(request)) {
            return false;
        }
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.docAsUpsert(true);
        Date startTime = DateUtils.StrToDate(request.getStartTime());
        String dateStr = DateUtil.format(DateUtil.date(startTime), DATE_FORMAT);
        updateRequest.index(String.format("%s_%s", indexPrefix, dateStr));
        updateRequest.id(request.getUuid());
        updateRequest.doc(GsonUtils.serialization(request), XContentType.JSON);
        try {
            UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return RestStatus.CREATED.equals(response.status()) || RestStatus.OK.equals(response.status());
        } catch (IOException e) {
            Cat.logError("Update search flow caught exception: ", e);
            log.error("Update search flow caught exception: ", e);
            return false;
        }
    }

    @Override
    @Degrade(rhinoKey = "pageEsQueryFlow", fallBackMethod = "fallBack")
    public PageResponse<FlowSearchEntity> pageQuery(FlowSearchQueryRequest request, int page, int pageSize) throws EsException {
        SearchSourceBuilder searchSourceBuilder = getSearchSourceBuilder(request, page, pageSize, true);
        String index = String.format("%s_%s", indexPrefix, INDEX_SUFFIX);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            Cat.logError("Search flow caught exception: ", e);
            throw new EsException(e.getMessage());
        }
        if (searchResponse == null) {
            return null;
        }
        SearchHits searchHits = searchResponse.getHits();
        if (searchHits == null) {
            return null;
        }

        // 总数
        TotalHits totalHits = searchHits.getTotalHits();

        // 列表
        List<FlowSearchEntity> flowSearchEntities = Lists.newArrayList();
        SearchHit[] searchHitArr = searchHits.getHits();
        for (SearchHit searchHit : searchHitArr) {
            String json = searchHit.getSourceAsString();
            FlowSearchEntity flowSearchEntity = GsonUtils.deserialization(json, FlowSearchEntity.class);
            flowSearchEntities.add(flowSearchEntity);
        }

        PageResponse<FlowSearchEntity> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(totalHits == null ? 0L : totalHits.value);
        pageResponse.setItems(flowSearchEntities);
        pageResponse.setCurTotalSize(totalHits == null ? 0L : totalHits.value);
        return pageResponse;
    }

    private SearchSourceBuilder getSearchSourceBuilder(FlowSearchQueryRequest request, int page, int pageSize,
                                                       Boolean isFetchSource) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 发起人
        if (Objects.nonNull(request.getId())) {
            queryBuilder.filter(QueryBuilders.termQuery("id", request.getId()));
        }
        if (StringUtils.isNotEmpty(request.getUuid())) {
            queryBuilder.filter(QueryBuilders.termQuery("uuid", request.getUuid()));
        }
        // appkey
        if (Objects.nonNull(request.getAppKey()) && StringUtils.isNotEmpty(request.getAppKey().trim())) {
            String appkey = request.getAppKey().trim();
            queryBuilder.filter(QueryBuilders.wildcardQuery("appkey.keyword", String.format("*%s*", appkey)));
        }

        // 发起人
        if (StringUtils.isNotBlank(request.getCreateUser())) {
            queryBuilder.filter(QueryBuilders.termQuery("create_user.keyword", request.getCreateUser()));
        }
        // 审核人
        if (StringUtils.isNotBlank(request.getApproveUser())) {
            queryBuilder.filter(QueryBuilders.matchPhraseQuery("approve_users", request.getApproveUser()));
        }
        // 流程类型
        if (StringUtils.isNotBlank(request.getTemplate())) {
            queryBuilder.filter(QueryBuilders.termQuery("template_name", request.getTemplate()));
        }

        // 环境
        if (StringUtils.isNotBlank(request.getEnv())) {
            queryBuilder.filter(QueryBuilders.termQuery("env", request.getEnv()));
        }

        // 状态
        if (StringUtils.isNotBlank(request.getStatus())) {
            queryBuilder.filter(QueryBuilders.termQuery("status", request.getStatus().toLowerCase()));
        }

        // 申请时间
        if (request.getCreateTimeBegin() != null && request.getCreateTimeEnd() != null) {
            queryBuilder.filter(QueryBuilders.rangeQuery("start_time")
                    .gte(DateUtils.dateToString(request.getCreateTimeBegin(), "yyyy-MM-dd HH:mm:ss"))
                    .lte(DateUtils.dateToString(request.getCreateTimeEnd(), "yyyy-MM-dd HH:mm:ss")));
        }

        // 原因模糊搜索
        if (StringUtils.isNotBlank(request.getReason())) {
            queryBuilder.filter(QueryBuilders.fuzzyQuery("fuzzy.keyword", StringUtils.lowerCase(request.getReason())));
        }

        // 操作对象模板搜索
        if (StringUtils.isNotBlank(request.getFuzzy())) {
            queryBuilder.filter(QueryBuilders.matchPhraseQuery("fuzzy.keyword", StringUtils.lowerCase(request.getFuzzy())).slop(10));
        }

        Map<String, Integer> orderMap = ImmutableMap.of("BREAK_POINT", 0, "HOLDING", 1,
                "DELAYED", 2, "RUNNING", 3, "GRAY_PUBLISH", 4);
        Script script = new Script(
                ScriptType.INLINE,
                "painless",
                "def orderMap = params.orderMap; " +
                        "int order = orderMap.containsKey(doc['status.keyword'].value) " +
                        "? orderMap.get(doc['status.keyword'].value) " +
                        ": orderMap.size(); " +
                        "return order + 1;",
                Collections.singletonMap("orderMap", orderMap)
        );
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from((page - 1) * pageSize);
        searchSourceBuilder.size(pageSize);
        if (Boolean.FALSE.equals(request.getStateSort())) {
            searchSourceBuilder.sort(new FieldSortBuilder("start_time").order(SortOrder.DESC));
        } else {
            searchSourceBuilder.sort(SortBuilders.scriptSort(script, ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.ASC).sortMode(SortMode.MIN))
                    .sort(new FieldSortBuilder("start_time").order(SortOrder.DESC));
        }
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.fetchSource(isFetchSource);
        return searchSourceBuilder;
    }
}
