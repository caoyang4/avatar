package com.sankuai.avatar.client.kapiserver.impl;

import com.jayway.jsonpath.TypeRef;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.kapiserver.KApiServerHttpClient;
import com.sankuai.avatar.client.kapiserver.model.HostFeature;
import com.sankuai.avatar.client.kapiserver.model.VmHostDiskFeature;
import com.sankuai.avatar.client.kapiserver.request.VmHostDiskQueryRequest;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HULK2.0集群调度系统 - APIServer模块服务
 * @author qinwei05
 */
@Service
@Slf4j
public class KApiServerHttpClientImpl extends BaseHttpClient implements KApiServerHttpClient {

    @MdpConfig("KAPISERVER_PROD_API_DOMAIN:http://kapiserver.hulk.vip.sankuai.com")
    private String kApiServerProdApiDomain;

    @MdpConfig("KAPISERVER_TEST_API_DOMAIN:http://kapiserver.hulk.test.sankuai.com")
    private String kApiServerTestApiDomain;

    private static final int KAPISERVER_READ_TIMEOUT = 5;

    private static final int KAPISERVER_RETRY_COUNT = 0;

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
            return kApiServerProdApiDomain;
        }else if (env == EnvEnum.TEST || env == EnvEnum.DEV) {
            return kApiServerTestApiDomain;
        } else {
            return "Unknown environment: " + env.getName();
        }
    }

    @Override
    public int readTimeout() {
        return KAPISERVER_READ_TIMEOUT;
    }

    @Override
    public int retryCount() {
        return KAPISERVER_RETRY_COUNT;
    }

    @Override
    @RaptorTransaction
    public Map<String, HostFeature> getHulkHostsFeatures(List<String> hostNameList, EnvEnum env) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/manager/sets/features/v2";
        HttpRequest request = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath, env))
                .readTimeout(2)
                .body(hostNameList)
                .build();
        return httpClient.post(request).toBean(new TypeRef<HashMap<String, HostFeature>>() {}, "$.data");
    }

    @Override
    @RaptorTransaction
    public List<VmHostDiskFeature> getVmHostsDiskFeatures(VmHostDiskQueryRequest request) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/vm/batchquery";
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath, EnvEnum.valueOf(request.getEnv().toUpperCase())))
                .readTimeout(2)
                .body(request).build();
        return httpClient.post(httpRequest).toBeanList(VmHostDiskFeature.class, "$.data");
    }
}
