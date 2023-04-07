package com.sankuai.avatar.client.rocket.impl;

import com.jayway.jsonpath.TypeRef;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.rocket.RocketHttpClient;
import com.sankuai.avatar.client.rocket.model.HostQueryRequest;
import com.sankuai.avatar.client.rocket.model.RocketHost;
import com.sankuai.avatar.client.rocket.response.RocketHostResponseData;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * WIKI：<a href="https://km.sankuai.com/page/285465741">Rocket接口文档</a>
 * <p>
 * prod: rocket.sankuai.com/instance/api
 * staging: rocket.tbd.st.sankuai.com/instance/api
 * test/dev: rocket.tbd.test.sankuai.com/instance/api
 * @author qinwei05
 */
@Service
@Slf4j
public class RocketHttpClientImpl extends BaseHttpClient implements RocketHttpClient {

    @MdpConfig("ROCKET_DOMAIN:http://rocket.tbd.test.sankuai.com")
    private String rocketDomain;

    private static final int ROCKET_READ_TIMEOUT = 10;

    private static final int ROCKET_RETRY_COUNT = 2;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.isBlank("$.error"));
        return httpEventListenerList;
    }

    @Override
    public String baseUrl() {
        return rocketDomain;
    }

    @Override
    public int readTimeout() {
        return ROCKET_READ_TIMEOUT;
    }

    @Override
    public int retryCount() {
        return ROCKET_RETRY_COUNT;
    }

    @Override
    public String octoAuth() {
        return "com.sankuai.rocket.host.instance";
    }

    @Override
    @RaptorTransaction
    public RocketHostResponseData<RocketHost> getAppkeyHosts(String appkey) throws SdkCallException, SdkBusinessErrorException {
        HostQueryRequest hostQueryRequest = HostQueryRequest.builder().appkey(appkey).build();
        return getAppkeyHostsByQueryRequest(hostQueryRequest);
    }

    @Override
    public RocketHostResponseData<RocketHost> getAppkeyHostsByQueryRequest(HostQueryRequest hostQueryRequest) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/instance/api/v1/hosts_by_appkey";
        Map<String, String> urlParams = hostQueryRequest.toMap();
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).urlParams(urlParams).build();
        return httpClient.get(httpRequest).toBean(new TypeRef<RocketHostResponseData<RocketHost>>(){}, "$.data");
    }
}
