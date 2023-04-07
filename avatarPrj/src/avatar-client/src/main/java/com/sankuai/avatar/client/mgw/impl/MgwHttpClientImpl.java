package com.sankuai.avatar.client.mgw.impl;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.mgw.MgwHttpClient;
import com.sankuai.avatar.client.mgw.request.MgwVsRequest;
import com.sankuai.avatar.client.mgw.response.MgwVs;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://km.sankuai.com/page/28014774">MGW ADMIN API v1</a>
 * @author qinwei05
 * @date 2022/12/25 16:30
 */

@Service
@Slf4j
public class MgwHttpClientImpl extends BaseHttpClient implements MgwHttpClient {

    @MdpConfig("MGW_PROD_DOMAIN:http://mip.sankuai.com")
    private String mgwProdDomain;

    @MdpConfig("MGW_TEST_DOMAIN:http://mip.ep.test.sankuai.com")
    private String mgwTestDomain;

    @MdpConfig("MGW_TOKEN:token e71a4680d97cafe941f217c511f0250f")
    private String mgwToken;

    private static final int MGW_READ_TIMEOUT = 5;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.equals("success", "$.status"));
        return httpEventListenerList;
    }

    @Override
    public String baseUrlByEnv(EnvEnum env) {
        if (env == EnvEnum.PROD || env == EnvEnum.STAGING) {
            return mgwProdDomain;
        }else if (env == EnvEnum.TEST || env == EnvEnum.DEV) {
            return mgwTestDomain;
        } else {
            return "Unknown environment: " + env.getName();
        }
    }

    @Override
    public String baseUrl() {
        return "";
    }

    @Override
    protected int readTimeout() {
        return MGW_READ_TIMEOUT;
    }

    @Override
    public Map<String, String> tokenAuth() {
        HashMap<String, String> token = new HashMap<>(1);
        token.put("Authorization", "token e71a4680d97cafe941f217c511f0250f");
        return token;
    }

    @Override
    @RaptorTransaction
    public List<MgwVs> getVsList(MgwVsRequest mgwVsRequest, EnvEnum env) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/v2/mgw/vs/";
        Map<String, String> urlParams = mgwVsRequest.toMap();
        HttpRequest request = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath, env)).urlParams(urlParams)
                .retryCount(2).build();
        return httpClient.get(request).toBeanList(MgwVs.class, "$.data.items[*]");
    }
}
