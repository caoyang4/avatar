package com.sankuai.avatar.client.octo.impl;

import com.google.common.collect.ImmutableMap;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.core.HttpResult;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.octo.OctoHttpClient;
import com.sankuai.avatar.client.octo.model.OctoProviderGroup;
import com.sankuai.avatar.client.octo.request.OctoNodeStatusQueryRequest;
import com.sankuai.avatar.client.octo.response.OctoNodeStatusResponse;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 * @create 2022-12-13 15:04
 */
@Service
@Slf4j
public class OctoHttpClientImpl extends BaseHttpClient implements OctoHttpClient {

    @MdpConfig("OCTO_PROD_API_DOMAIN:https://octoopenapi.vip.sankuai.com")
    private String octoProdApiDomain;

    @MdpConfig("OCTO_TEST_API_DOMAIN:http://octoopenapi.inf.dev.sankuai.com")
    private String octoTestApiDomain;

    private static final int OCTO_READ_TIMEOUT = 20;

    private static final int OCTO_RETRY_COUNT = 2;

    @Override
    public String baseUrl() {
        return "";
    }

    @Override
    public int readTimeout() {
        return OCTO_READ_TIMEOUT;
    }

    @Override
    public int retryCount() {
        return OCTO_RETRY_COUNT;
    }

    @Override
    public String octoAuth() {
        return "com.sankuai.octo.openapi";
    }

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.explainHttpBusinessCode("$.code", 0));
        return httpEventListenerList;
    }

    /**
     * 根据env切换对应域名
     *
     * @param env env
     * @return {@link String}
     */
    @Override
    public String baseUrlByEnv(EnvEnum env) {
        if (env == EnvEnum.PROD || env == EnvEnum.STAGING) {
            return octoProdApiDomain;
        }else if (env == EnvEnum.TEST || env == EnvEnum.DEV) {
            return octoTestApiDomain;
        } else {
            return "Unknown environment: " + env.getName();
        }
    }

    /**
     * 转换env为对应数字
     *
     * @param env env
     * @return {@link String}
     */
    private String convertEnv(EnvEnum env) {
        Map<String, String> envMap = ImmutableMap.of(
                "dev", "1",
                "test", "2",
                "stage", "3",
                "prod", "4"
        );
        return envMap.getOrDefault(env.getName(), "4");
    }

    @Override
    @RaptorTransaction
    public List<OctoProviderGroup> getOctoProviderGroup(String appkey, EnvEnum env, String type) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/v1/provider/group/info";
        String protocol = "http".equals(type) ? "2" : "1";
        String envNum = convertEnv(env);
        Map<String, String> urlParams = ImmutableMap.of("env", envNum, "appkey", appkey, "type", protocol);
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath, env))
                .urlParams(urlParams)
                .retryCount(1)
                .readTimeout(3)
                .build();
        HttpResult httpResult = httpClient.get(httpRequest);
        return httpResult.toBeanList(OctoProviderGroup.class, "$.data");
    }

    @Override
    @RaptorTransaction
    public OctoNodeStatusResponse getAppkeyOctoNodeStatus(OctoNodeStatusQueryRequest octoNodeStatusQueryRequest, EnvEnum env)
            throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/v1/provider/status";
        String envNum = convertEnv(env);
        Map<String, String> urlParams = octoNodeStatusQueryRequest.toMap();
        urlParams.put("env", envNum);
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath, env))
                .urlParams(urlParams)
                .retryCount(1)
                .readTimeout(3)
                .build();
        HttpResult httpResult = httpClient.get(httpRequest);
        return httpResult.toBean(OctoNodeStatusResponse.class, "$.data");
    }
}
