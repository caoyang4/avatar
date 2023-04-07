package com.sankuai.avatar.client.banner.impl;

import com.google.common.collect.ImmutableMap;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.banner.BannerHttpClient;
import com.sankuai.avatar.client.banner.response.ElasticTip;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qinwei05
 * @date 2023/2/8 20:30
 * @version 1.0
 */
@Service
@Slf4j
public class BannerHttpClientImpl extends BaseHttpClient implements BannerHttpClient {

    @MdpConfig("BANNER_API_DOMAIN:http://bannerapi.inf.test.sankuai.com")
    private String bannerApiDomain;

    @MdpConfig("BANNER_API_CLIENT_TOKEN:a8e520b90f8f4611bc32bc05943de325")
    private String bannerApiClientToken;

    @MdpConfig("BANNER_API_CLIENT_ID:avatarNew")
    private String bannerApiClientId;

    private static final int BANNER_READ_TIMEOUT = 3;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.explainHttpBusinessCode("$.code", 0));
        return httpEventListenerList;
    }

    @Override
    public String baseUrl() {
        return bannerApiDomain;
    }

    @Override
    public int readTimeout() {
        return BANNER_READ_TIMEOUT;
    }

    @Override
    public Map<String, String> tokenAuth() {
        HashMap<String, String> token = new HashMap<>(2);
        token.put("auth-token", "a8e520b90f8f4611bc32bc05943de325");
        token.put("operatorId", "avatarNew");
        return token;
    }

    @Override
    public ElasticTip getElasticTips() throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/bannerapi/automatic/active/tips";
        HttpRequest request = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath))
                .retryCount(1)
                .build();
        return httpClient.get(request).toBean(ElasticTip.class, "$.data");
    }

    @Override
    public Boolean getElasticGrayScale(String owt) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/bannerapi/automatic/active/grayscale";
        Map<String, String> urlParams = ImmutableMap.of("owt", owt);
        HttpRequest request = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath))
                .urlParams(urlParams)
                .retryCount(1)
                .build();
        return httpClient.get(request).toBean(Boolean.class, "$.data");
    }
}
