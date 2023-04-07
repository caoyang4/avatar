package com.sankuai.avatar.client.workflow.impl;

import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.TypeRef;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.http.core.HttpRequest;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.workflow.AvatarWorkflowHttpClient;
import com.sankuai.avatar.client.workflow.model.AppkeyFlow;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AvatarWorkflowHttpClientImpl extends BaseHttpClient implements AvatarWorkflowHttpClient {

    @MdpConfig("AVATAR_WORKFLOW_DOMAIN:http://mbop.oversea.test.sankuai.com")
    private String avatarWorkflowApiDomain;

    private static final int AVATAR_WORKFLOW_READ_TIMEOUT = 10;

    private static final int AVATAR_WORKFLOW_RETRY_COUNT = 1;

    @Override
    public String baseUrl() {
        return avatarWorkflowApiDomain;
    }

    @Override
    public int readTimeout() {
        return AVATAR_WORKFLOW_READ_TIMEOUT;
    }

    @Override
    public int retryCount() {
        return AVATAR_WORKFLOW_RETRY_COUNT;
    }

    @Override
    public String octoAuth() {
        return "avatar-workflow-web";
    }

    @Override
    public List<HttpEventListener> httpEventListener() {
        List<HttpEventListener> httpEventListenerList = super.httpEventListener();
        httpEventListenerList.add(CommonListener.explainHttpBusinessCode("$.code", 0));
        return httpEventListenerList;
    }

    @Override
    public PageResponse<AppkeyFlow> batchGetAppkeyFlowList(List<String> appkeyList, String state) throws SdkCallException, SdkBusinessErrorException {
        String urlPath = "/api/v1/avatar/workflow/search/flow/list";
        Map<String, String> body = ImmutableMap.of(
                "appkey", String.join(",", appkeyList),
                "pageSize", "200",
                "state", state);
        HttpRequest httpRequest = HttpRequest.builder().baseUrlPath(buildUrl(urlPath)).body(body)
                .retryCount(1).readTimeout(3).build();
        return httpClient.post(httpRequest).toBean(new TypeRef<PageResponse<AppkeyFlow>>() {}, "$.data");
    }
}
