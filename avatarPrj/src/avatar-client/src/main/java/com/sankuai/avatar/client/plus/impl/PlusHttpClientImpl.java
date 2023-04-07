package com.sankuai.avatar.client.plus.impl;

import com.google.common.collect.ImmutableMap;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.plus.PlusHttpClient;
import com.sankuai.avatar.client.plus.model.PlusRelease;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/10/7 16:33
 * @version 1.0
 */

@Service
@Slf4j
public class PlusHttpClientImpl extends BaseHttpClient implements PlusHttpClient {

    @MdpConfig("PLUS_DOMAIN:http://plus.sankuai.com")
    private String plusDomain;

    private static final int PLUS_READ_TIMEOUT = 5;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.explainHttpBusinessCode("$.code", 0));
        return httpEventListenerList;
    }

    @Override
    public String baseUrl() {
        return plusDomain;
    }

    @Override
    protected int readTimeout() {
        return PLUS_READ_TIMEOUT;
    }

    @Override
    @RaptorTransaction
    public List<PlusRelease> getBindPlusByAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v2/appkeys/{appkey}/releases?relation_type=bind";
        Map<String, String> pathParams = ImmutableMap.of("appkey", appkey);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams)
                .retryCount(1).readTimeout(2).build();
        return httpClient.get(request).toBeanList(PlusRelease.class, "$.data");
    }

    @Override
    @RaptorTransaction
    public List<PlusRelease> getAppliedPlusByAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v2/appkeys/{appkey}/releases?relation_type=applied";
        Map<String, String> pathParams = ImmutableMap.of("appkey", appkey);
        HttpRequest request = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).pathParams(pathParams)
                .retryCount(1).readTimeout(2).build();
        return httpClient.get(request).toBeanList(PlusRelease.class, "$.data");
    }
}
