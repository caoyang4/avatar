package com.sankuai.avatar.resource.appkey.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.client.banner.BannerHttpClient;
import com.sankuai.avatar.client.banner.response.ElasticTip;
import com.sankuai.avatar.client.dom.DomHttpClient;
import com.sankuai.avatar.client.dom.model.AppkeyResourceUtil;
import com.sankuai.avatar.client.ecs.EcsHttpClient;
import com.sankuai.avatar.client.ecs.model.BillingUnit;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.mgw.MgwHttpClient;
import com.sankuai.avatar.client.mgw.request.MgwVsRequest;
import com.sankuai.avatar.client.mgw.response.MgwVs;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.client.ops.model.OpsSrv;
import com.sankuai.avatar.client.ops.request.SrvQueryRequest;
import com.sankuai.avatar.client.soa.ScHttpClient;
import com.sankuai.avatar.client.soa.model.ScAppkey;
import com.sankuai.avatar.client.soa.model.ScIsoltAppkey;
import com.sankuai.avatar.client.soa.model.ScPageResponse;
import com.sankuai.avatar.client.soa.model.ScV1Appkey;
import com.sankuai.avatar.client.soa.request.IsoltAppkeyRequest;
import com.sankuai.avatar.client.workflow.AvatarWorkflowHttpClient;
import com.sankuai.avatar.client.workflow.model.AppkeyFlow;
import com.sankuai.avatar.common.exception.ResourceNotFoundErrorException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.PageHelperUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.*;
import com.sankuai.avatar.resource.appkey.request.*;
import com.sankuai.avatar.resource.appkey.transfer.AppkeyRequestBOTransfer;
import com.sankuai.avatar.resource.appkey.transfer.AppkeyTransfer;
import com.sankuai.avatar.dao.es.AppkeyEsRepository;
import com.sankuai.avatar.dao.es.exception.EsException;
import com.sankuai.avatar.dao.es.request.AppkeyQueryRequest;
import com.sankuai.avatar.dao.es.request.AppkeyTreeRequest;
import com.sankuai.avatar.dao.es.request.UserAppkeyRequest;
import com.sankuai.avatar.dao.resource.repository.AppkeyRepository;
import com.sankuai.avatar.dao.resource.repository.UserRelationRepository;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import com.sankuai.avatar.dao.resource.repository.model.UserRelationDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyRequest;
import com.sankuai.avatar.dao.resource.repository.request.UserRelationRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2022-12-14 13:43
 */
@Component
public class AppkeyResourceImpl implements AppkeyResource {

    private final AppkeyRepository repository;
    private final UserRelationRepository userRelationRepository;

    private final AppkeyEsRepository esRepository;
    private final OpsHttpClient opsHttpClient;
    private final MgwHttpClient mgwHttpClient;
    private final ScHttpClient scHttpClient;
    private final DomHttpClient domHttpClient;
    private final BannerHttpClient bannerHttpClient;
    private final AvatarWorkflowHttpClient avatarWorkflowHttpClient;
    private final EcsHttpClient ecsHttpClient;

    @Autowired
    public AppkeyResourceImpl(AppkeyRepository repository,
                              UserRelationRepository userRelationRepository, AppkeyEsRepository esRepository,
                              OpsHttpClient opsHttpClient,
                              MgwHttpClient mgwHttpClient,
                              ScHttpClient scHttpClient,
                              DomHttpClient domHttpClient,
                              BannerHttpClient bannerHttpClient,
                              AvatarWorkflowHttpClient avatarWorkflowHttpClient,
                              EcsHttpClient ecsHttpClient) {
        this.repository = repository;
        this.userRelationRepository = userRelationRepository;
        this.esRepository = esRepository;
        this.opsHttpClient = opsHttpClient;
        this.mgwHttpClient = mgwHttpClient;
        this.scHttpClient = scHttpClient;
        this.domHttpClient = domHttpClient;
        this.bannerHttpClient = bannerHttpClient;
        this.avatarWorkflowHttpClient = avatarWorkflowHttpClient;
        this.ecsHttpClient = ecsHttpClient;
    }

    @Override
    public List<AppkeyBO> getByAppkeyRandom(Integer count) {
        List<AppkeyDO> randomAppkeyDOList = new ArrayList<>();
        AppkeyRequest request = AppkeyRequest.builder().build();
        List<AppkeyDO> appkeyDOList = repository.query(request);
        int[] boundedRandomValues = ThreadLocalRandom.current().ints(count, 0, appkeyDOList.size()).toArray();
        for (int boundedRandomValue : boundedRandomValues) {
            randomAppkeyDOList.add(appkeyDOList.get(boundedRandomValue));
        }
        return AppkeyTransfer.INSTANCE.toBOList(randomAppkeyDOList);
    }

    @Override
    public AppkeyBO getByAppkey(String appkey) {
        List<AppkeyDO> doList = repository.query(AppkeyRequest.builder().appkey(appkey).build());
        if (CollectionUtils.isNotEmpty(doList)) {
            return AppkeyTransfer.INSTANCE.toBO(doList.get(0));
        }
        return null;
    }

    @Override
    public String getSrvKeyByAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException {
        return opsHttpClient.getSrvKeyByAppkey(appkey);
    }

    @Override
    public OpsSrvBO getAppkeyRelatedSrvInfo(String appkey) throws SdkCallException, SdkBusinessErrorException {
        OpsSrv opsSrv =  opsHttpClient.getSrvInfoByAppkey(appkey);
        return AppkeyTransfer.INSTANCE.toOpsSrvBO(opsSrv);
    }

    @Override
    public OpsSrvBO getAppkeyByOps(String appkey) throws SdkCallException, SdkBusinessErrorException {
        String srv = opsHttpClient.getSrvKeyByAppkey(appkey);
        if (StringUtils.isBlank(srv)) {
            throw new ResourceNotFoundErrorException(404, String.format("Appkey：%s服务树节点信息不存在", appkey));
        }
        OpsSrv opsSrv = opsHttpClient.getSrvInfoBySrvKey(srv);
        // 为空时代表Appkey不存在，主动抛出异常
        if (opsSrv == null) {
            throw new ResourceNotFoundErrorException(404, String.format("Appkey：%s服务树节点信息不存在", appkey));
        }
        return AppkeyTransfer.INSTANCE.toOpsSrvBO(opsSrv);
    }

    @Override
    public ScAppkeyBO getAppkeyBySc(String appkey) throws SdkCallException, SdkBusinessErrorException {
        List<ScAppkey> scAppkeyList = scHttpClient.getAppkeysInfo(Collections.singletonList(appkey));
        // 为空时代表Appkey不存在，主动抛出异常
        if (CollectionUtils.isEmpty(scAppkeyList)) {
            throw new ResourceNotFoundErrorException(404, String.format("Appkey：%s不存在", appkey));
        }
        // V1接口补充其他数据
        ScV1Appkey scAppkey = scHttpClient.getAppkeyInfoByV1(appkey);
        ScAppkeyBO scAppkeyBO = AppkeyTransfer.INSTANCE.toScAppkeyBOV2(scAppkeyList.get(0));
        scAppkeyBO.setTenant(scAppkey.getTenant());
        scAppkeyBO.setIsBoughtExternal(scAppkey.getIsBoughtExternal());
        scAppkeyBO.setFrameworks(scAppkey.getFrameworks());

        scAppkeyBO.setCompatibleIpv6(scAppkey.getCompatibleIpv6());
        scAppkeyBO.setEpAdmin(scAppkey.getEpAdmin());
        scAppkeyBO.setOpAdmin(scAppkey.getOpAdmin());
        scAppkeyBO.setRdAdmin(scAppkey.getRdAdmin());
        return scAppkeyBO;
    }

    @Override
    public List<ScAppkeyBO> batchGetAppkeyBySc(List<String> appkeyList) throws SdkCallException, SdkBusinessErrorException {
        List<ScAppkey> scAppkeyList = scHttpClient.getAppkeysInfo(appkeyList);
        return AppkeyTransfer.INSTANCE.batchToScAppkeyBO(scAppkeyList);
    }

    @Override
    public PageResponse<ScIsoltAppkeyBO> getIsoltAppkeys(IsoltAppkeyRequestBO request) throws SdkCallException, SdkBusinessErrorException {
        IsoltAppkeyRequest isoltAppkeyRequest = AppkeyRequestBOTransfer.INSTANCE.toIsoltAppkeyRequest(request);
        ScPageResponse<ScIsoltAppkey> scPageResponse = scHttpClient.getIsoltAppkeys(isoltAppkeyRequest);
        return AppkeyTransfer.INSTANCE.toScIsoltAppkeyBOPageResponse(scPageResponse);
    }

    @Override
    public AppkeyResourceUtilBO getAppkeyResourceUtil(String appkey) throws SdkCallException, SdkBusinessErrorException, ResourceNotFoundErrorException {
        AppkeyResourceUtil appkeyResourceUtil = domHttpClient.getAppkeyResourceUtil(appkey);
        if (appkeyResourceUtil == null || appkeyResourceUtil.getAppkey() == null) {
            throw new ResourceNotFoundErrorException(404, String.format("Appkey：%s不存在，无法获取资源利用率信息", appkey));
        }
        return AppkeyTransfer.INSTANCE.toAppkeyResourceUtilBO(appkeyResourceUtil);
    }

    @Override
    public AppkeyBO getBySrv(String srv) {
        List<AppkeyDO> doList = null;
        try {
            PageResponse<AppkeyDO> query = esRepository.query(AppkeyQueryRequest.builder().srv(srv).build());
            if (Objects.nonNull(query)) {
                doList = query.getItems();
            }
        } catch (EsException ignored) {
        }
        if (CollectionUtils.isEmpty(doList)) {
            doList = repository.query(AppkeyRequest.builder().srv(srv).build());
        }
        if (CollectionUtils.isNotEmpty(doList)) {
            return AppkeyTransfer.INSTANCE.toBO(doList.get(0));
        }
        return null;
    }

    @Override
    public PageResponse<AppkeyBO> queryPage(AppkeyRequestBO requestBO) {
        int page = requestBO.getPage();
        int pageSize = requestBO.getPageSize();
        PageResponse<AppkeyBO> pageResponse = new PageResponse<>();
        Page<AppkeyDO> doPage = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(AppkeyRequestBOTransfer.INSTANCE.toAppkeyRequest(requestBO))
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(doPage.getTotal());
        pageResponse.setTotalPage(doPage.getPages());
        pageResponse.setItems(AppkeyTransfer.INSTANCE.toBOList(doPage));
        return pageResponse;
    }

    @Override
    public PageResponse<AppkeyBO> searchAppkey(AppkeySearchRequestBO searchRequestBO) {
        int page = searchRequestBO.getPage();
        int pageSize = searchRequestBO.getPageSize();
        try {
            AppkeyQueryRequest queryRequest = AppkeyRequestBOTransfer.INSTANCE.toAppkeyQueryRequest(searchRequestBO);
            queryRequest.setPage(searchRequestBO.getPage());
            queryRequest.setPageSize(searchRequestBO.getPageSize());
            PageResponse<AppkeyDO> search = esRepository.search(queryRequest);
            if (Objects.nonNull(search)) {
                PageResponse<AppkeyBO> pageResponse = new PageResponse<>();
                pageResponse.setPage(page);
                pageResponse.setPageSize(pageSize);
                pageResponse.setTotalCount(search.getTotalCount());
                pageResponse.setTotalPage(search.getTotalPage());
                pageResponse.setItems(AppkeyTransfer.INSTANCE.toBOList(search.getItems()));
                return pageResponse;
            }
        } catch (EsException ignored) {}
        return queryPage(AppkeyRequestBOTransfer.INSTANCE.toAppkeyRequest(searchRequestBO));
    }

    @Override
    public String getByHost(String host) {
        try {
            return opsHttpClient.getAppkeyByHost(host);
        } catch (SdkCallException | SdkBusinessErrorException ignored) {
        }
        return null;
    }

    private List<String> getUserTopAppkeys(String mis){
        List<UserRelationDO> relationDOList = userRelationRepository.query(UserRelationRequest.builder()
                .loginName(mis).tag("top").build());
        return relationDOList.stream().map(UserRelationDO::getAppkey).distinct().collect(Collectors.toList());
    }

    @Override
    public PageResponse<AppkeyBO> getOwnAppkey(AppkeySrvsQueryRequest request) {
        // ES查询【我的服务】
        PageResponse<AppkeyDO> appkeyDOPageResponse = getOwnAppkeyPageResponseByEs(request);
        if (appkeyDOPageResponse == null) {
            // DB查询【我的服务】（ES异常降级策略）
            appkeyDOPageResponse = getOwnAppkeyPageResponseByDB(request);
        }
        // 分页转换
        PageResponse<AppkeyBO> pageResponse = new PageResponse<>();
        pageResponse.setPage(appkeyDOPageResponse.getPage());
        pageResponse.setPageSize(appkeyDOPageResponse.getPageSize());
        pageResponse.setTotalCount(appkeyDOPageResponse.getTotalCount());
        pageResponse.setTotalPage(appkeyDOPageResponse.getTotalPage());
        pageResponse.setItems(AppkeyTransfer.INSTANCE.toBOList(appkeyDOPageResponse.getItems()));
        return pageResponse;
    }

    private PageResponse<AppkeyDO> getOwnAppkeyPageResponseByEs(AppkeySrvsQueryRequest request) {
        PageResponse<AppkeyDO> appkeyDOPageResponse;
        try {
            List<String> topAppkeys = getUserTopAppkeys(request.getUser());
            UserAppkeyRequest userAppkeyRequest = AppkeyRequestBOTransfer.INSTANCE.toUserAppkeyRequest(request);
            appkeyDOPageResponse = esRepository.getOwnAppkey(userAppkeyRequest, topAppkeys);
        } catch (EsException ignored) {
            return null;
        }
        return appkeyDOPageResponse;
    }

    private PageResponse<AppkeyDO> getOwnAppkeyPageResponseByDB(AppkeySrvsQueryRequest request) {
        List<AppkeyDO> appkeyDOList = repository.query(AppkeyRequest.builder().owner(request.getUser()).build());
        if (CollectionUtils.isEmpty(appkeyDOList)){
            return PageHelperUtils.toPageResponse(request.getPage(), request.getPageSize(), appkeyDOList);
        }
        // 字段筛选：非下线状态的后端类型 && 其他自定义筛选条件
        appkeyDOList = appkeyDOList.stream()
                .filter(i -> Boolean.FALSE.equals(i.getIsOffline()) && Objects.equals(i.getType(), "BACKEND"))
                .filter(i -> isMatch(i, request))
                .collect(Collectors.toList());
        // 按照服务置顶排序
        List<String> topAppkeys = getUserTopAppkeys(request.getUser());
        if (CollectionUtils.isNotEmpty(topAppkeys)) {
            appkeyDOList.sort(Comparator.comparingInt(bo -> {
                int index = topAppkeys.indexOf(bo.getAppkey());
                return index >= 0 ? index : topAppkeys.size();
            }));
        }
        return PageHelperUtils.toPageResponse(request.getPage(), request.getPageSize(), appkeyDOList);
    }

    /**
     * 是否匹配条件
     *
     * @param appkey  服务
     * @param request 请求
     * @return boolean
     */
    private static boolean isMatch(AppkeyDO appkey, AppkeySrvsQueryRequest request){
        boolean match = true;
        // 服务容灾等级过滤
        if (StringUtils.isNotBlank(request.getCapacity())) {
            match = Arrays.asList(request.getCapacity().split(",")).contains(String.valueOf(appkey.getCapacity()));
        }
        // 服务名称检索
        if (StringUtils.isNotBlank(request.getQuery())) {
            match &= appkey.getAppkey().contains(request.getQuery().trim().toLowerCase());
        }
        // 服务等级
        if (StringUtils.isNotBlank(request.getRank())) {
            if ("core".equals(request.getRank())){
                match &= Objects.equals(appkey.getRank(), "核心服务");
            } else if ("non-core".equals(request.getRank())) {
                match &= Objects.equals(appkey.getRank(), "非核心服务");
            }
        }
        // 服务SET
        if (StringUtils.isNotBlank(request.getCell())) {
            if (Objects.equals("set", request.getCell())) {
                match &= Boolean.TRUE.equals(appkey.getIsSet());
            } else if (Objects.equals("liteset", request.getCell())) {
                match &= Boolean.TRUE.equals(appkey.getIsLiteset());
            }
        }
        // 服务状态
        if (request.getStateful() != null) {
            match &= Objects.equals(appkey.getStateful(), request.getStateful());
        }
        // 主机业务分组过滤
        if (Boolean.TRUE.equals(request.getPaas())){
            match &= (appkey.getPaas()== null || Objects.equals(appkey.getPaas(), Boolean.FALSE));
        }
        return match;
    }

    @Override
    public PageResponse<AppkeyBO> getPageAppkey(AppkeyTreeQueryRequestBO request) {
        AppkeyTreeRequest esRequest = AppkeyRequestBOTransfer.INSTANCE.toTreeRequest(request);
        try {
            PageResponse<AppkeyDO> doPageResponse = esRepository.getPageAppkey(esRequest);
            PageResponse<AppkeyBO> pageResponse = new PageResponse<>();
            if (Objects.nonNull(doPageResponse)) {
                pageResponse.setPage(doPageResponse.getPage());
                pageResponse.setPageSize(doPageResponse.getPageSize());
                pageResponse.setTotalCount(doPageResponse.getTotalCount());
                pageResponse.setTotalPage(doPageResponse.getTotalPage());
                pageResponse.setItems(AppkeyTransfer.INSTANCE.toBOList(doPageResponse.getItems()));
                return pageResponse;
            }
            return pageResponse;
        } catch (EsException ignored) {
        }
        return queryPage(AppkeyRequestBOTransfer.INSTANCE.toAppkeyRequest(request));
    }

    @Override
    public List<String> getByVip(String vip) {
        if (StringUtils.isEmpty(vip)) {
            return Collections.emptyList();
        }
        MgwVsRequest request = MgwVsRequest.builder().vip(vip).build();
        EnvEnum[] envEnums = {EnvEnum.PROD, EnvEnum.TEST};
        for (EnvEnum envEnum : envEnums) {
            try {
                List<MgwVs> vsList = mgwHttpClient.getVsList(request, envEnum);
                if (CollectionUtils.isNotEmpty(vsList)) {
                    List<String> appkeys = vsList.stream().map(MgwVs::getAppkey).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(appkeys)) {
                        return appkeys;
                    }
                }
            } catch (SdkCallException | SdkBusinessErrorException ignored) {
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Boolean favorAppkey(String appkey, String mis) {
        try {
            return opsHttpClient.favorAppkey(appkey, mis);
        } catch (SdkCallException | SdkBusinessErrorException ignored) {
        }
        return false;
    }

    @Override
    public Boolean cancelFavorAppkey(String appkey, String mis) {
        try {
            return opsHttpClient.cancelFavorAppkey(appkey, mis);
        } catch (SdkCallException | SdkBusinessErrorException ignored) {
        }
        return false;
    }

    @Override
    public List<AppkeyBO> getFavorAppkey(AppkeySrvsQueryRequest request) {
        SrvQueryRequest srvQueryRequest = SrvQueryRequest.builder()
                .type("favor").user(request.getUser())
                .rank(request.getRank()).capacity(request.getCapacity()).stateful(request.getStateful()).build();
        List<String> favorAppkey = opsHttpClient.getUserFavorAppkey(srvQueryRequest);
        if (CollectionUtils.isNotEmpty(favorAppkey)) {
            List<AppkeyDO> doList = repository.query(AppkeyRequest.builder().appkeys(favorAppkey).build());
            return AppkeyTransfer.INSTANCE.toBOList(doList);
        }
        return Collections.emptyList();
    }

    @Override
    public ElasticTipBO getElasticTips() throws SdkCallException, SdkBusinessErrorException {
        ElasticTip elasticTip = bannerHttpClient.getElasticTips();
        return AppkeyTransfer.INSTANCE.toElasticTipBO(elasticTip);
    }

    @Override
    public Boolean getElasticGrayScale(String owt) throws SdkCallException, SdkBusinessErrorException {
        return bannerHttpClient.getElasticGrayScale(owt);
    }

    @Override
    public PageResponse<AppkeyFlowBO> batchGetAppkeyRunningAndHoldingFlowList(List<String> appkeyList) throws SdkCallException, SdkBusinessErrorException {
        PageResponse<AppkeyFlow> appkeyFlowPageResponse = avatarWorkflowHttpClient.batchGetAppkeyFlowList(appkeyList, "RUNNING,HOLDING");
        return AppkeyTransfer.INSTANCE.toAppkeyFlowBOPageResponse(appkeyFlowPageResponse);
    }

    @Override
    public BillingUnitBO getAppkeyUnitList(String appkey) throws SdkCallException, SdkBusinessErrorException {
        BillingUnit billingUnit = ecsHttpClient.getAppkeyUnitList(appkey);
        return AppkeyTransfer.INSTANCE.toBillingUnit(billingUnit);
    }
}