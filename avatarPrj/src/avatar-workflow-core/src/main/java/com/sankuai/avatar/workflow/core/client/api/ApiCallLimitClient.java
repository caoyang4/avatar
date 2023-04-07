package com.sankuai.avatar.workflow.core.client.api;

import com.sankuai.avatar.workflow.core.client.api.request.ApiCallLimitRequest;
import com.sankuai.avatar.workflow.core.client.api.response.ApiCallLimitResponse;

/**
 * API 请求限流客户端
 *
 * @author zhaozhifan02
 */
public interface ApiCallLimitClient {

    /**
     * 锁资源校验
     *
     * @param request 请求参数
     * @return 锁是否已存在
     */
    ApiCallLimitResponse validate(ApiCallLimitRequest request);

    /**
     * 加锁
     *
     * @param request 请求参数
     * @return 是否加锁成功
     */
    boolean acquire(ApiCallLimitRequest request);

    /**
     * 释放锁
     *
     * @param request 请求参数
     * @return 是否释放成功
     */
    boolean release(ApiCallLimitRequest request);

    /**
     * API 请求次数递增
     *
     * @param request 请求参数
     * @return 是否递增成功
     */
    boolean increaseApiHit(ApiCallLimitRequest request);

    /**
     * 返回缓存 key
     *
     * @param request 请求参数
     * @return key
     */
    String getApiUserCacheKey(ApiCallLimitRequest request);
}
