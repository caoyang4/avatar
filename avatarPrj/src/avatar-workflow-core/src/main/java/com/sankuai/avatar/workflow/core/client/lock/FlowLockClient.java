package com.sankuai.avatar.workflow.core.client.lock;

import com.sankuai.avatar.workflow.core.client.lock.request.FlowLockRequest;
import com.sankuai.avatar.workflow.core.client.lock.response.FlowLockResponse;

/**
 * 流程锁客户端，流程元数据锁而非资源锁，仅限制流程发起频率
 *
 * @author zhaozhifan02
 */
public interface FlowLockClient {

    /**
     * 锁资源校验
     *
     * @param request 请求参数
     * @return 锁是否已存在
     */
    FlowLockResponse validate(FlowLockRequest request);

    /**
     * 加锁
     *
     * @param request 请求参数
     * @return 是否加锁成功
     */
    boolean acquire(FlowLockRequest request);

    /**
     * 释放锁
     *
     * @param request 请求参数
     * @return 是否释放成功
     */
    boolean release(FlowLockRequest request);

    /**
     * 获取缓存 key
     *
     * @param request 请求参数
     * @return 缓存 key
     */
    String getCacheKey(FlowLockRequest request);
}
