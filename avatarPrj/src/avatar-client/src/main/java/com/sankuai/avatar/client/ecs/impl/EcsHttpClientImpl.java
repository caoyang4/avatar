package com.sankuai.avatar.client.ecs.impl;

import com.google.common.collect.ImmutableMap;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.ecs.EcsHttpClient;
import com.sankuai.avatar.client.ecs.model.BillingUnit;
import com.sankuai.avatar.client.ecs.model.EcsIdc;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ECS系统接口实现
 * @author qinwei05
 */
@Service
@Slf4j
public class EcsHttpClientImpl extends BaseHttpClient implements EcsHttpClient {

    @MdpConfig("ECS_DOMAIN:http://mbop.oversea.test.sankuai.com")
    private String ecsDomain;

    private static final int ECS_READ_TIMEOUT = 20;

    private static final int ECS_RETRY_COUNT = 1;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.explainHttpBusinessCode("$.code", 0));
        return httpEventListenerList;
    }

    @Override
    public String baseUrl() {
        return ecsDomain;
    }

    @Override
    public int readTimeout() {
        return ECS_READ_TIMEOUT;
    }

    @Override
    public int retryCount() {
        return ECS_RETRY_COUNT;
    }

    @Override
    public String octoAuth() {
        return "avatar-ecs-web";
    }

    @Override
    @RaptorTransaction
    public List<EcsIdc> getIdcList() throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v1/avatar/ecs/idc";
        Map<String, String> urlParams = ImmutableMap.of("sn", "200");
        HttpRequest request = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath)).urlParams(urlParams)
                .retryCount(2).build();
        return httpClient.get(request).toBeanList(EcsIdc.class, "$.data.items[*]");
    }

    @Override
    public BillingUnit getAppkeyUnitList(String appkey) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v1/avatar/ecs/appkey/server_product";
        Map<String, String> urlParams = ImmutableMap.of("appkey", appkey);
        HttpRequest request = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath)).urlParams(urlParams)
                .retryCount(1).readTimeout(2).build();
        return httpClient.get(request).toBean(BillingUnit.class, "$.data");
    }
}
