package com.sankuai.avatar.client.dom.impl;

import com.google.common.collect.ImmutableMap;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.dom.DomHttpClient;
import com.sankuai.avatar.client.dom.model.AppkeyResourceUtil;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WIKI：<a href="https://km.sankuai.com/page/162546252">DOM接口文档</a>
 * @author qinwei05
 */
@Service
@Slf4j
public class DomHttpClientImpl extends BaseHttpClient implements DomHttpClient {

    @MdpConfig("DOM_API_DOMAIN:http://dom-api.sankuai.com")
    private String domApiDomain;

    @MdpConfig("DOM_API_TOKEN:Token 73b96bd4-3483-42e5-9d6c-904c0a538461")
    private String domApiToken;

    private static final int DOM_READ_TIMEOUT = 5;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.explainHttpBusinessCode("$.code", 0));
        return httpEventListenerList;
    }

    @Override
    public String baseUrl() {
        return domApiDomain;
    }

    @Override
    public int readTimeout() {
        return DOM_READ_TIMEOUT;
    }

    @Override
    public Map<String, String> tokenAuth() {
        HashMap<String, String> token = new HashMap<>(1);
        token.put("Authorization", domApiToken);
        return token;
    }

    @Override
    @RaptorTransaction
    public AppkeyResourceUtil getAppkeyResourceUtil(String appkey) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/utilization/optimize_msg";
        String lastWeekDate = LocalDateTime.now().plusDays(-7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ImmutableMap<String,String> urlParams = ImmutableMap.<String, String>builder()
                .put("appkey", appkey)
                .put("start_date", lastWeekDate)
                .put("end_date", lastWeekDate)
                .put("env", "prod")
                .put("period", "week")
                .put("include_white_list", "1")
                .build();
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath))
                .urlParams(urlParams)
                .retryCount(3)
                .readTimeout(3)
                .build();
        return httpClient.get(httpRequest).toBean(AppkeyResourceUtil.class, "$.data");
    }
}
