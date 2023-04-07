package com.sankuai.avatar.client.ops.impl;

import com.google.common.collect.ImmutableMap;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.core.HttpResult;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.client.ops.model.*;
import com.sankuai.avatar.client.ops.request.SrvQueryRequest;
import com.sankuai.avatar.client.ops.response.*;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * WIKI：<a href="https://docs.sankuai.com/mt/sa/opsweb/master/">OPS接口文档</a>
 * WIKI：<a href="https://km.sankuai.com/page/1360418392">ops-avatar 代理型接口迁移</a>
 * @author xk
 */
@Service
@Slf4j
public class OpsHttpClientImpl extends BaseHttpClient implements OpsHttpClient {

    @MdpConfig("OPS_DOMAIN:http://ops.sre.test.sankuai.com")
    private String opsDomain;

    private static final int OPS_READ_TIMEOUT = 20;

    private static final int OPS_RETRY_COUNT = 2;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        // OPS系统返回判断：若error为空，代表请求正常
        httpEventListenerList.add(CommonListener.isBlank("$.error"));
        return httpEventListenerList;
    }

    @Override
    public String baseUrl() {
        return opsDomain;
    }

    @Override
    public int readTimeout() {
        return OPS_READ_TIMEOUT;
    }

    @Override
    public int retryCount() {
        return OPS_RETRY_COUNT;
    }

    @Override
    public String octoAuth() {
        return "com.sankuai.sre.ops.api";
    }

    @Override
    @RaptorTransaction
    public OpsAppkey getAppkeyInfo(String appkey) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v0.2/appkeys/{appkey}";
        Map<String, String> pathParams = ImmutableMap.of("appkey", appkey);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams).build();
        return httpClient.get(request).toBean(OpsAppkeyResponse.class).getAppkey();
    }

    @Override
    @RaptorTransaction
    public OpsSrv getSrvInfoByAppkey(String appkey) {
        String urlPath = "/api/v0.2/appkeys/{appkey}/srvs";
        Map<String, String> pathParams = ImmutableMap.of("appkey", appkey);
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath))
                .pathParams(pathParams)
                .retryCount(2)
                .readTimeout(3)
                .build();
        HttpResult httpResult = httpClient.get(httpRequest);
        List<OpsSrv> srvs = httpResult.toBean(OpsSrvsResponse.class).getSrvs();
        return CollectionUtils.isNotEmpty(srvs) ? srvs.get(0) : null;
    }

    @Override
    @RaptorTransaction
    public OpsSrv getSrvInfoByHost(String host) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v0.2/hosts/{host}/srvs";
        Map<String, String> pathParams = ImmutableMap.of("host", host);
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath))
                .pathParams(pathParams)
                .retryCount(1)
                .readTimeout(3)
                .build();
        HttpResult httpResult = httpClient.get(httpRequest);
        List<OpsSrv> srvs = httpResult.toBean(OpsSrvsResponse.class).getSrvs();
        return CollectionUtils.isNotEmpty(srvs) ? srvs.get(0) : null;
    }

    @Override
    @RaptorTransaction
    public OpsTree getSrvTreeByKey(String srv) {
        String urlPath = "/api/v0.2/srvs/{srv}";
        Map<String, String> pathParams = ImmutableMap.of("srv", srv);
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath))
                .pathParams(pathParams)
                .retryCount(2)
                .readTimeout(3)
                .build();
        return httpClient.get(httpRequest).toBean(OpsTree.class);
    }

    @Override
    @RaptorTransaction
    public OpsAvatarSrvsResponse getSrvsByQueryRequest(SrvQueryRequest srvQueryRequest) {
        String urlPath = "/api/v0.2/avatar/srvs";
        Map<String, String> urlParams = srvQueryRequest.toMap();
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath))
                .urlParams(urlParams)
                .build();
        HttpResult httpResult = httpClient.get(httpRequest);
        return httpResult.toBean(OpsAvatarSrvsResponse.class);
    }

    @Override
    @RaptorTransaction
    public OpsSrvDetail getSrvDetailByKey(String srv) {
        String urlPath = "/api/v0.2/avatar/srvs/{srv}";
        Map<String, String> pathParams = ImmutableMap.of("srv", srv);
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath))
                .pathParams(pathParams)
                .retryCount(2)
                .readTimeout(3)
                .build();
        return httpClient.get(httpRequest).toBean(OpsSrvDetail.class);
    }

    @Override
    @RaptorTransaction
    public List<String> getSrvSubscribers(String srv) {
        String urlPath = "/api/v0.2/srvs/{srv}/subscribers";
        Map<String, String> pathParams = ImmutableMap.of("srv", srv);
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath))
                .pathParams(pathParams)
                .retryCount(2)
                .readTimeout(3)
                .build();
        return httpClient.get(httpRequest).toBean(OpsSubscribersResponse.class).getSubscribers();
    }

    @Override
    @RaptorTransaction
    public OpsSrv getSrvInfoBySrvKey(String srv) {
        return getSrvDetailByKey(srv).getSrv();
    }

    @Override
    @RaptorTransaction
    public List<OpsSrv> getAllAppkeyInfo() {
        String urlPath = "/api/v0.2/srvs";
        HttpResult httpResult = httpClient.get(buildUrl(urlPath), 25, 3);
        return httpResult.toBean(OpsSrvsResponse.class).getSrvs();
    }

    @Override
    @RaptorTransaction
    public OpsHost getHostInfo(String hostname) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v0.2/hosts/{hostname}";
        Map<String, String> pathParams = ImmutableMap.of("hostname", hostname);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams).build();
        return httpClient.get(request).toBean(OpsHostResponse.class).getHost();
    }

    @Override
    @RaptorTransaction
    public List<OpsHost> getHostsBySrv(String srv) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v0.2/srvs/{srv}/hosts";
        Map<String, String> pathParams = ImmutableMap.of("srv", srv);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams).build();
        return httpClient.get(request).toBeanList(OpsHost.class, "$.hosts");
    }

    @Override
    @RaptorTransaction
    public List<OpsHost> getHostsByAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v0.2/appkeys/{appkey}/hosts";
        Map<String, String> pathParams = ImmutableMap.of("appkey", appkey);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams).build();
        return httpClient.get(request).toBean(OpsHostListResponse.class).getHosts();
    }

    @Override
    @RaptorTransaction
    public List<OpsOwt> getOwtList() {
        String url = "/api/v0.2/owts";
        HttpResult httpResult = httpClient.get(buildUrl(url));
        return httpResult.toBeanList(OpsOwt.class, "$.owts");
    }

    @Override
    @RaptorTransaction
    public OpsOwt getOwtByKey(String key) {
        String urlPath = "/api/v0.2/owts/{key}";
        Map<String, String> pathParams = ImmutableMap.of("key", key);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams)
                .retryCount(1).readTimeout(2).build();
        HttpResult httpResult = httpClient.get(request);
        return httpResult.toBean(OpsOwt.class, "$.owt");
    }

    @Override
    @RaptorTransaction
    public List<OpsOrg> getOrgListByKey(String key) {
        String urlPath = "/api/v0.2/teams/servicetree.{key}/orgs";
        Map<String, String> pathParams = ImmutableMap.of("key", key);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams)
                .retryCount(1).readTimeout(2).build();
        HttpResult httpResult = httpClient.get(request);
        return httpResult.toBeanList(OpsOrg.class, "$.orgs");
    }

    @Override
    @RaptorTransaction
    public List<OpsBg> getBgList() {
        String url = "/api/v0.2/bgs";
        HttpResult httpResult = httpClient.get(buildUrl(url));
        return httpResult.toBeanList(OpsBg.class, "$.bgs");
    }

    @Override
    @RaptorTransaction
    public List<String> getBgListByUser(String user) {
        String urlPath = "/api/v0.2/users/{user}/bgs";
        Map<String, String> pathParams = ImmutableMap.of("user", user);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams)
                .retryCount(1).readTimeout(3).build();
        HttpResult httpResult = httpClient.get(request);
        return httpResult.toBeanList(String.class, "$.bgs");
    }

    @Override
    @RaptorTransaction
    public List<OpsPdl> getPdlListByOwtKey(String owtKey) {
        String urlPath = "/api/v0.2/owts/{owtKey}/pdls";
        Map<String, String> pathParams = ImmutableMap.of("owtKey", owtKey);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams)
                .retryCount(1).readTimeout(2).build();
        HttpResult httpResult = httpClient.get(request);
        return httpResult.toBeanList(OpsPdl.class, "$.pdls");
    }

    @Override
    @RaptorTransaction
    public List<OpsPdl> getPdlList() {
        String url = "/api/v0.2/pdls";
        HttpResult httpResult = httpClient.get(buildUrl(url));
        return httpResult.toBeanList(OpsPdl.class, "$.pdls");
    }

    @Override
    @RaptorTransaction
    public String getSrvKeyByAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException {
        OpsSrv opsSrv = getSrvInfoByAppkey(appkey);
        return Objects.nonNull(opsSrv) ? opsSrv.getKey() : "";
    }

    @Override
    @RaptorTransaction
    public OpsPdlResponse getPdlByKey(String key) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v0.2/pdls/{key}";
        Map<String, String> pathParams = ImmutableMap.of("key", key);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams)
                .retryCount(1).readTimeout(2).build();
        HttpResult httpResult = httpClient.get(request);
        return httpResult.toBean(OpsPdlResponse.class);
    }

    @Override
    public Boolean isExistAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException {
        return Objects.nonNull(getSrvInfoByAppkey(appkey));
    }

    @Override
    @RaptorTransaction
    public List<OpsSrv> getSrvListByPdl(String pdlKey) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v0.2/pdls/{pdlKey}/srvs";
        Map<String, String> pathParams = ImmutableMap.of("pdlKey", pdlKey);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams)
                .retryCount(1).readTimeout(2).build();
        HttpResult httpResult = httpClient.get(request);
        return httpResult.toBeanList(OpsSrv.class, "$.srvs");
    }

    @Override
    @RaptorTransaction
    public Boolean updateOpsCapacity(String appkey, OpsCapacity opsCapacity) throws SdkCallException, SdkBusinessErrorException {
        String srv = getSrvKeyByAppkey(appkey);
        if (StringUtils.isEmpty(srv)) {
            return false;
        }
        String urlPath = "/api/v0.2/srvs/{srv}";
        Map<String, String> pathParams = ImmutableMap.of("srv", srv);
        opsCapacity.setUpdateTime(new Date());
        opsCapacity.setUpdateBy("avatar");
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams).body(opsCapacity).build();
        return httpClient.put(request).toBean(OpsResponse.class).isSuccess();
    }

    @Override
    @RaptorTransaction
    public List<OpsSrv> getUserAppkeyInfo(String mis) throws SdkCallException, SdkBusinessErrorException {
        SrvQueryRequest request = SrvQueryRequest.builder()
                .page(1).pageSize(500).user(mis).type("mine").build();
        List<OpsSrvDetail> opsSrvDetails = getSrvsByQueryRequest(request).getSrvs();
        if (CollectionUtils.isNotEmpty(opsSrvDetails)) {
            return opsSrvDetails.stream().map(OpsSrvDetail::getSrv).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    @Override
    @RaptorTransaction
    public String getAppkeyByHost(String host) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v0.2/hosts/{host}/appkeys";
        Map<String, String> pathParams = ImmutableMap.of("host", host);
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath))
                .pathParams(pathParams)
                .retryCount(1)
                .readTimeout(3)
                .build();
        List<String> appkeys = httpClient.get(httpRequest).toBean(OpsHostAppkeyResponse.class).getAppkeys();
        return CollectionUtils.isNotEmpty(appkeys) ? appkeys.get(0) : null;
    }

    private HttpRequest buildFavorAppkeyHttpRequest(String appkey, String mis) {
        String srv = getSrvKeyByAppkey(appkey);
        if (StringUtils.isEmpty(srv)) {
            return null;
        }
        String urlPath = "/api/v0.2/srvs/{srv}/subscribers";
        Map<String, String> pathParams = ImmutableMap.of("srv", srv);
        Map<String, String> userMap = ImmutableMap.of("user", mis);
        return HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams).body(userMap).build();
    }

    @Override
    @RaptorTransaction
    public Boolean favorAppkey(String appkey, String mis) throws SdkCallException, SdkBusinessErrorException {
        HttpRequest request = buildFavorAppkeyHttpRequest(appkey, mis);
        if (request == null) {
            return false;
        }
        return httpClient.post(request).toBean(OpsResponse.class).isSuccess();
    }

    @Override
    @RaptorTransaction
    public Boolean cancelFavorAppkey(String appkey, String mis) throws SdkCallException, SdkBusinessErrorException {
        HttpRequest request = buildFavorAppkeyHttpRequest(appkey, mis);
        if (request == null) {
            return false;
        }
        return httpClient.delete(request).toBean(OpsResponse.class).isSuccess();
    }

    @Override
    @RaptorTransaction
    public List<String> getUserFavorAppkey(SrvQueryRequest request) throws SdkCallException, SdkBusinessErrorException {
        request.setPage(1);
        request.setPageSize(500);
        List<OpsSrvDetail> opsSrvDetails = getSrvsByQueryRequest(request).getSrvs();
        Set<String> set = new HashSet<>();
        if (CollectionUtils.isNotEmpty(opsSrvDetails)) {
            opsSrvDetails.stream().map(OpsSrvDetail::getAppkeys).filter(CollectionUtils::isNotEmpty).forEach(set::addAll);
            return new ArrayList<>(set);
        }
        return Collections.emptyList();
    }

    @Override
    @RaptorTransaction
    public OpsUser getOpsUser(String mis) throws SdkCallException, SdkBusinessErrorException {
        if (StringUtils.isEmpty(mis)) {
            return null;
        }
        String urlPath = "/api/v0.2/users/{user}";
        Map<String, String> pathParams = ImmutableMap.of("user", mis);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams)
                .retryCount(1).readTimeout(2).build();
        HttpResult httpResult = httpClient.get(request);
        return httpResult.toBean(OpsUser.class, "$.user");
    }

    @Override
    public boolean isUserOpsSre(String mis) {
        OpsUser opsUser = getOpsUser(mis);
        if (Objects.nonNull(opsUser) && Objects.nonNull(opsUser.getRoles())) {
            return opsUser.getRoles().getOrDefault("sre", false);
        }
        return false;
    }
}