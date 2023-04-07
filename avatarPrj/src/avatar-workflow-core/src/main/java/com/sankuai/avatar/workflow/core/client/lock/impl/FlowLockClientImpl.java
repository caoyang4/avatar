package com.sankuai.avatar.workflow.core.client.lock.impl;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.workflow.core.client.lock.FlowLockClient;
import com.sankuai.avatar.workflow.core.client.lock.request.FlowLockRequest;
import com.sankuai.avatar.workflow.core.client.lock.response.FlowLockResponse;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import com.sankuai.avatar.dao.workflow.repository.FlowCreateLockRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Component
public class FlowLockClientImpl implements FlowLockClient {

    /**
     * 流程锁定时间，两分钟中内不能重复提交相同流程
     */
    private static final Integer DEFAULT_LOCK_TIME = 180;

    /**
     * 重复流程提交提醒
     */
    private static final String REPEAT_FLOW_WARNING_MSG = "重复提交相同任务,请稍后再试";

    private final FlowCreateLockRepository flowCreateLockRepository;

    public FlowLockClientImpl(FlowCreateLockRepository flowCreateLockRepository) {
        this.flowCreateLockRepository = flowCreateLockRepository;
    }

    @Override
    public FlowLockResponse validate(FlowLockRequest request) {
        FlowContext flowContext = request.getFlowContext();
        String cacheKey = generateResourceMd5(flowContext);
        FlowLockResponse response = FlowLockResponse.builder().locked(false).build();
        if (flowCreateLockRepository.isExists(cacheKey)) {
            response.setLocked(true);
            response.setMessage(REPEAT_FLOW_WARNING_MSG);
        }
        return response;
    }

    @Override
    public boolean acquire(FlowLockRequest request) {
        FlowContext flowContext = request.getFlowContext();
        String cacheKey = generateResourceMd5(flowContext);
        return flowCreateLockRepository.save(cacheKey, getCacheResource(flowContext), DEFAULT_LOCK_TIME);
    }

    @Override
    public boolean release(FlowLockRequest request) {
        FlowContext flowContext = request.getFlowContext();
        String cacheKey = generateResourceMd5(flowContext);
        return flowCreateLockRepository.delete(cacheKey);
    }

    @Override
    public String getCacheKey(FlowLockRequest request) {
        return generateResourceMd5(request.getFlowContext());
    }

    /**
     * 生成资源MD5值
     *
     * @param flowContext 流程上下文
     * @return 资源MD5值
     */
    private String generateResourceMd5(FlowContext flowContext) {
        String cacheResource = getCacheResource(flowContext);
        return DigestUtils.md5Hex(cacheResource).toUpperCase();
    }

    /**
     * 获取缓存资源值
     *
     * @param flowContext 流程上下文
     * @return 资源值
     */
    private String getCacheResource(FlowContext flowContext) {
        String createUser = flowContext.getCreateUser();
        FlowInput flowInput = flowContext.getFlowInput();
        String templateName = flowContext.getTemplateName();
        String inputJsonStr = JsonUtil.bean2Json(flowInput);
        return createUser + templateName + inputJsonStr;
    }
}
