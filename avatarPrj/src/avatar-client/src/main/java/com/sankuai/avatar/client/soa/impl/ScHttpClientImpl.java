package com.sankuai.avatar.client.soa.impl;

import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.TypeRef;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.soa.ScHttpClient;
import com.sankuai.avatar.client.soa.model.*;
import com.sankuai.avatar.client.soa.request.IsoltAppkeyRequest;
import com.sankuai.avatar.client.soa.request.ApplicationPageRequest;
import com.sankuai.avatar.client.soa.response.ScData;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ScHttpClient的实现类
 * * WIKI：<a href="https://km.sankuai.com/page/395821375">SC接口文档</a>
 *
 * @author zhangxiaoning07
 * @create 2022/11/14
 **/
@Service
public class ScHttpClientImpl extends BaseHttpClient implements ScHttpClient {

    @MdpConfig("SERVICECATALOG_DOMAIN:http://service.fetc.test.sankuai.com")
    private String serviceCatalogDomain;

    private static final int SC_READ_TIMEOUT = 5;

    private static final int SC_RETRY_COUNT = 2;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.explainHttpBusinessCode("$.code", 0));
        return httpEventListenerList;
    }

    @Override
    public String baseUrl() {
        return serviceCatalogDomain;
    }

    @Override
    public int readTimeout() {
        return SC_READ_TIMEOUT;
    }

    @Override
    public int retryCount() {
        return SC_RETRY_COUNT;
    }

    @Override
    public String octoAuth() {
        return "com.sankuai.sc.openservice";
    }

    @Override
    @RaptorTransaction
    public ScOrg getOrg(String orgIds) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/open/api/avatar/v1.1/operationEvaluation/meta";
        HashMap<String, String> params = new HashMap<>();
        params.put("orgIds", orgIds);
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).urlParams(params).build();
        return httpClient.get(httpRequest).toBean(ScOrg.class, "$.data");
    }

    @Override
    @RaptorTransaction
    public List<ScOrgTreeNode> getOrgTreeByUser(String user) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/open/api/avatar/v1.1/org/tree";
        HashMap<String, String> params = new HashMap<>();
        params.put("mis", user);
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).urlParams(params).build();
        return httpClient.get(httpRequest).toBeanList(ScOrgTreeNode.class, "$.data");
    }

    @Override
    @RaptorTransaction
    public ScPageResponse<ScApplication> getApplications(ApplicationPageRequest request) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/open/api/avatar/v1.1/applications";
        Map<String, String> params = request.buildQueryParams();
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).urlParams(params).build();
        return httpClient.get(httpRequest).toBean(new TypeRef<ScPageResponse<ScApplication>>() {
        }, "$.data");
    }

    @Override
    @RaptorTransaction
    public ScPageResponse<ScUserOwnerApplication> getUserOwnerApplications(ApplicationPageRequest request) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/open/api/get/application";
        Map<String, String> params = request.buildQueryParams();
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).urlParams(params).build();
        return httpClient.get(httpRequest).toBean(new TypeRef<ScPageResponse<ScUserOwnerApplication>>() {
        }, "$.data");
    }

    @Override
    public ScPageResponse<ScQueryApplication> queryApplications(ApplicationPageRequest request) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/open/api/v2/application/search";
        Map<String, String> params = request.buildQueryParams();
        if (StringUtils.isNotBlank(params.get("q"))){
            params.put("keyword", params.get("q"));
            params.remove("q");
        }
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).urlParams(params).build();
        return httpClient.get(httpRequest).toBean(new TypeRef<ScPageResponse<ScQueryApplication>>() {
        }, "$.data");
    }

    @Override
    @RaptorTransaction
    public ScApplicationDetail getApplication(String name) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/open/api/avatar/v1/application/{name}";
        Map<String, String> pathParams = ImmutableMap.of("name", name);
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams)
                .readTimeout(15).retryCount(1).build();
        return httpClient.get(httpRequest).toBean(ScApplicationDetail.class, "$.data");
    }

    @Override
    @RaptorTransaction
    public ScV1Appkey getAppkeyInfoByV1(String appKey) {
        String urlPath = "/open/api/avatar/v1.1/appKey/{appKey}";
        Map<String, String> pathParams = ImmutableMap.of("appKey", appKey);
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams).build();
        return httpClient.get(httpRequest).toBean(ScV1Appkey.class, "$.data");
    }

    @Override
    @RaptorTransaction
    public ScPageResponse<ScIsoltAppkey> getIsoltAppkeys(IsoltAppkeyRequest request) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/open/api/avatar/v1.1/isol/appKeys";
        Map<String, String> params = request.toMap();
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).urlParams(params).build();
        return httpClient.get(httpRequest).toBean(new TypeRef<ScPageResponse<ScIsoltAppkey>>() {}, "$.data");
    }

    @Override
    @RaptorTransaction
    public List<ScAppkey> getAppkeysInfo(List<String> appkeys) {
        String urlPath = "/open/api/v2/appkeys/meta";
        Map<String, String> urlParams = ImmutableMap.of("appKeys", String.join(",", appkeys));
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).urlParams(urlParams).build();
        return httpClient.get(httpRequest).toBeanList(ScAppkey.class, "$.data");
    }

    @Override
    @RaptorTransaction
    public List<String> getAllAppkeys() {
        List<String> appKeys = new ArrayList<>();
        int page = 1;
        int pageSize = 100;
        List<String> curPageAppkeys = getAllAppkeysByPage(page, pageSize);
        while (CollectionUtils.isNotEmpty(curPageAppkeys)) {
            page += 1;
            List<String> nextPageAppkeys = getAllAppkeysByPage(page, pageSize);
            appKeys.addAll(curPageAppkeys);
            curPageAppkeys = nextPageAppkeys;
        }
        return appKeys;
    }

    @Override
    @RaptorTransaction
    public List<String> getAllAppkeysByPage(Integer page, Integer pageSize) {
        String urlPath = "/open/api/v2/get/all/appkeys";
        Map<String, String> urlParams = ImmutableMap.<String, String>builder()
                .put("page", String.valueOf(page))
                .put("size", String.valueOf(pageSize))
                .build();
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).urlParams(urlParams).build();
        return httpClient.get(httpRequest).toBean(ScData.class, "$.data").getItems();
    }

    @Override
    @RaptorTransaction
    public List<String> getAllPaasApplications() {
        String urlPath = "/open/api/v2/application/get/all/paas";
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).build();
        return httpClient.get(httpRequest).toBeanList(String.class, "$.data");
    }
}