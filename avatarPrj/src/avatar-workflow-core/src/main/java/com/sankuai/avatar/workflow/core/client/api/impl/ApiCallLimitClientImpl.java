package com.sankuai.avatar.workflow.core.client.api.impl;

import com.google.common.reflect.TypeToken;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.workflow.core.client.api.ApiCallLimitClient;
import com.sankuai.avatar.workflow.core.client.api.ApiCallLimitStrategy;
import com.sankuai.avatar.workflow.core.client.api.request.ApiCallLimitRequest;
import com.sankuai.avatar.workflow.core.client.api.response.ApiCallLimitResponse;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.dao.workflow.repository.FlowCreateLockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Component
public class ApiCallLimitClientImpl implements ApiCallLimitClient {

    /**
     * API 调用默认递增值
     */
    private static final Long DEFAULT_INCREASE_VALUE = 1L;

    /**
     * API 限流提醒
     */
    private static final String API_LIMIT_WARNING_MSG = "API调用过于频繁，请稍后再试";

    /**
     * API 用户限流策略
     */
    @MdpConfig("API_USER_LIMIT_STRATEGY:{}")
    private String apiUserLimitStrategy;

    private final FlowCreateLockRepository flowCreateLockRepository;

    public ApiCallLimitClientImpl(FlowCreateLockRepository flowCreateLockRepository) {
        this.flowCreateLockRepository = flowCreateLockRepository;
    }

    @Override
    public ApiCallLimitResponse validate(ApiCallLimitRequest request) {
        FlowContext flowContext = request.getFlowContext();
        String flowTemplateName = flowContext.getTemplateName();
        FlowResource flowResource = flowContext.getResource();
        if (flowTemplateName == null || flowResource == null) {
            return null;
        }
        String createUser = flowContext.getCreateUser();
        Map<String, ApiCallLimitStrategy> limitStrategyMap = getApiCallLimitStrategy();
        if (limitStrategyMap == null) {
            return null;
        }
        ApiCallLimitStrategy limitStrategy = limitStrategyMap.get(createUser);
        int limitFrequency = limitStrategy.getFrequency();
        String cacheKey = generateApiUserCacheKey(flowContext);

        ApiCallLimitResponse response = ApiCallLimitResponse.builder().locked(false).build();
        Integer qps = flowCreateLockRepository.get(cacheKey, Integer.class);
        if (qps != null && qps > limitFrequency) {
            response.setLocked(true);
            response.setMessage(API_LIMIT_WARNING_MSG);
        }
        return response;
    }

    @Override
    public boolean acquire(ApiCallLimitRequest request) {
        FlowContext flowContext = request.getFlowContext();
        String createUser = flowContext.getCreateUser();
        String key = generateApiUserCacheKey(flowContext);
        Map<String, ApiCallLimitStrategy> limitStrategyMap = getApiCallLimitStrategy();
        if (limitStrategyMap == null) {
            return false;
        }
        ApiCallLimitStrategy limitStrategy = limitStrategyMap.get(createUser);
        int interval = limitStrategy.getInterval();
        return flowCreateLockRepository.save(key, DEFAULT_INCREASE_VALUE, interval);
    }

    @Override
    public boolean release(ApiCallLimitRequest request) {
        FlowContext flowContext = request.getFlowContext();
        String key = generateApiUserCacheKey(flowContext);
        return flowCreateLockRepository.delete(key);
    }

    @Override
    public boolean increaseApiHit(ApiCallLimitRequest request) {
        FlowContext flowContext = request.getFlowContext();
        String key = generateApiUserCacheKey(flowContext);
        return flowCreateLockRepository.increment(key, DEFAULT_INCREASE_VALUE);
    }

    @Override
    public String getApiUserCacheKey(ApiCallLimitRequest request) {
        return generateApiUserCacheKey(request.getFlowContext());
    }

    /**
     * 生成 api 用户缓存 key
     *
     * @param flowContext 流程上下文
     * @return 缓存 key
     */
    private String generateApiUserCacheKey(FlowContext flowContext) {
        FlowResource flowResource = flowContext.getResource();
        String appKey = flowResource.getAppkey();
        String templateName = flowContext.getTemplateName();
        return String.format("flow_api_call_%s_%s_%s", flowContext.getCreateUser(), appKey, templateName);
    }

    /**
     * 获取限流配置
     *
     * @return Lion限流配置
     */
    private Map<String, ApiCallLimitStrategy> getApiCallLimitStrategy() {
        // API 的限流配置
        Type mapType = new TypeToken<Map<String, ApiCallLimitStrategy>>() {
        }.getType();
        return GsonUtils.deserialization(apiUserLimitStrategy, mapType);
    }
}
