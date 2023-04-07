package com.sankuai.avatar.common.constant;

/**
 * HttpClient 任务执行状态
 */
public enum State {

    /**
     * 请求成功
     */
    SUCCESS,

    /**
     * 执行异常
     */
    EXCEPTION,

    /**
     * 网络超时
     */
    TIMEOUT,

    /**
     * 网络出错
     */
    NETWORK_ERROR
}
