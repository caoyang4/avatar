package com.sankuai.avatar.client.dayu.impl;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.dayu.DayuHttpClient;
import com.sankuai.avatar.client.dayu.model.DayuGroupTag;
import com.sankuai.avatar.client.dayu.model.GroupTagQueryRequest;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WIKI：<a href="https://km.sankuai.com/page/141081850">Dayu接口文档</a>
 * @author qinwei05
 */
@Service
@Slf4j
public class DayuHttpClientImpl extends BaseHttpClient implements DayuHttpClient {

    @MdpConfig("DAYU_API_DOMAIN:http://dayu.fetc.test.sankuai.com")
    private String dayuApiDomain;

    @MdpConfig("DAYU_CLIENT_ID:avatar")
    private String dayuClientId;

    @MdpConfig("DAYU_CLIENT_SECRET:1017088D4C0B11ECB5BF0A580A247DD6")
    private String dayuClientSecret;

    private static final int DAYU_READ_TIMEOUT = 3;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.explainHttpBusinessCode("$.code", 0));
        return httpEventListenerList;
    }

    @Override
    public String baseUrl() {
        return dayuApiDomain;
    }

    @Override
    public int readTimeout() {
        return DAYU_READ_TIMEOUT;
    }

    @Override
    public Map<String, String> tokenAuth() {
        HashMap<String, String> token = new HashMap<>(2);
        token.put("dayu-token", "1017088D4C0B11ECB5BF0A580A247DD6");
        token.put("username", "avatar");
        return token;
    }

    @Override
    @RaptorTransaction
    public List<DayuGroupTag> getGrouptags(GroupTagQueryRequest groupTagQueryRequest) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v2/external/grouptags/list/";
        Map<String, String> urlParams = groupTagQueryRequest.toMap();
        HttpRequest request = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath)).urlParams(urlParams)
                .retryCount(2).build();
        return httpClient.get(request).toBeanList(DayuGroupTag.class, "$.data.items[*]");
    }
}
