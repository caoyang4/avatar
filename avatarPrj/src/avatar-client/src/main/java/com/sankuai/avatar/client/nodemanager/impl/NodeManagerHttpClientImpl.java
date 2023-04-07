package com.sankuai.avatar.client.nodemanager.impl;

import com.jayway.jsonpath.TypeRef;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.nodemanager.NodeManagerHttpClient;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HULK 宿主机生命周期管理系统实现
 * @author qinwei05
 */
@Service
@Slf4j
public class NodeManagerHttpClientImpl extends BaseHttpClient implements NodeManagerHttpClient {

    @MdpConfig("NODEMANAGER_PROD_API_DOMAIN:http://nodemanager.hulk.vip.sankuai.com")
    private String nodemanagerProdApiDomain;

    @MdpConfig("NODEMANAGER_TEST_API_DOMAIN:http://nodemanager.hulk.test.sankuai.com")
    private String nodemanagerTestApiDomain;

    private static final int NODEMANAGER_READ_TIMEOUT = 5;

    private static final int NODEMANAGER_RETRY_COUNT = 1;

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.explainHttpBusinessCode("$.code", 0));
        return httpEventListenerList;
    }

    @Override
    public String baseUrl() {
        return "";
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
            return nodemanagerProdApiDomain;
        }else if (env == EnvEnum.TEST || env == EnvEnum.DEV) {
            return nodemanagerTestApiDomain;
        } else {
            throw new SdkBusinessErrorException("Unknown environment: " + env.getName());
        }
    }

    @Override
    public int readTimeout() {
        return NODEMANAGER_READ_TIMEOUT;
    }

    @Override
    public int retryCount() {
        return NODEMANAGER_RETRY_COUNT;
    }

    @Override
    @RaptorTransaction
    public Map<String, List<String>> getHostsParentFeatures(List<String> parentHostsName, EnvEnum env) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/nodemanager/api/hosts/features";
        HttpRequest request = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath, env))
                .body(parentHostsName).build();
        return httpClient.post(request).toBean(new TypeRef<HashMap<String, List<String>>>() {}, "$.data");
    }
}
