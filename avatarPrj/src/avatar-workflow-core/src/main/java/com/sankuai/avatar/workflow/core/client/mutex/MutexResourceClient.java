package com.sankuai.avatar.workflow.core.client.mutex;

import com.sankuai.avatar.workflow.core.client.mutex.request.MutexResourceRequest;
import com.sankuai.avatar.workflow.core.client.mutex.response.MutexResourceResponse;

/**
 * 流程资源互斥客户端
 * IP资源、通用资源、公共资源
 *
 * @author zhaozhifan02
 */
public interface MutexResourceClient {

    /**
     * 资源互斥锁校验，判断资源是否已被锁定
     *
     * @param request 请求参数
     * @return MutexResourceResponse
     */
    MutexResourceResponse validate(MutexResourceRequest request);

    /**
     * 加锁
     *
     * @param request 请求参数
     * @return MutexResourceResponse
     */
    boolean lock(MutexResourceRequest request);

    /**
     * 释放锁
     *
     * @param request 请求参数
     * @return 是否释放成功
     */
    boolean unLock(MutexResourceRequest request);

    /**
     * 是否可跳过资源校验
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean canSkip(MutexResourceRequest request);
}
