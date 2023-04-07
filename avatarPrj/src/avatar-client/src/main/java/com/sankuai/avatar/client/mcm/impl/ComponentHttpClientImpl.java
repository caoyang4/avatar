package com.sankuai.avatar.client.mcm.impl;

import com.jayway.jsonpath.TypeRef;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.mcm.ComponentHttpClient;
import com.sankuai.avatar.client.mcm.request.CreateBusyPeriodRequest;
import com.sankuai.avatar.client.mcm.response.ComponentResponse;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Service
public class ComponentHttpClientImpl extends BaseHttpClient implements ComponentHttpClient {

    @MdpConfig("COMPONENT_DOMAIN:http://mcm.tbd.test.sankuai.com")
    private String componentDomain;

    @Override
    protected String baseUrl() {
        return componentDomain;
    }

    @Override
    public String octoAuth() {
        return "com.sankuai.cf.component.server";
    }

    @Override
    @RaptorTransaction
    public boolean createBusyPeriod(CreateBusyPeriodRequest request) {
        String urlPath = "/api/v1/cf/component/busyperiod";
        HttpRequest httpRequest = HttpRequest.builder()
                .baseUrlPath(buildUrl(urlPath)).body(JsonUtil.beanToMap(request))
                .retryCount(2).build();
        return httpClient.post(httpRequest).toBean(new TypeRef<ComponentResponse<Object>>() {
        }).isSuccess();
    }
}
