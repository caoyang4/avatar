package com.sankuai.avatar.client.jumper.impl;

import com.google.common.collect.ImmutableMap;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.jumper.JumperHttpClient;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Service
public class JumperHttpClientImpl extends BaseHttpClient implements JumperHttpClient {

    @MdpConfig("JUMPER_DOMAIN:https://jumper-api.vip.sankuai.com")
    private String jumperDomain;

    @MdpConfig("JUMPER_TOKEN:945ZKME&DPJ*WyAd")
    private String jumperToken;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        // code 非200 业务异常
        httpEventListenerList.add(CommonListener.explainHttpBusinessCode("$.code", 200));
        return httpEventListenerList;
    }

    @Override
    @RaptorTransaction
    public void userUnlock(String userName) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/avatar/user/unlock";
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath)).body(ImmutableMap.of("user_name", userName))
                .retryCount(2).build();
        httpClient.post(httpRequest);
    }

    @Override
    public void passwordReset(String userName) {
        String urlPath = "/api/avatar/user/password_reset";
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath)).body(ImmutableMap.of("user_name", userName))
                .retryCount(2).build();
        httpClient.post(httpRequest);
    }

    @Override
    protected String baseUrl() {
        return jumperDomain;
    }

    @Override
    public Map<String, String> httpHeader() {
        HashMap<String, String> httpHeader = new HashMap<>(10);
        httpHeader.put("Content-Type", "application/json;charset=UTF-8");
        httpHeader.put("Authorization", "945ZKME&DPJ*WyAd");
        return httpHeader;
    }
}
