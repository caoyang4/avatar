package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 任务过多，后端限流
 *
 * @author qinwei05
 */

public class RequestLimitException extends RuntimeException {

    /**
     * Constructs an <code>RequestLimitException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public RequestLimitException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>RequestLimitException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public RequestLimitException(String msg, Throwable t) {
        super(msg, t);
    }
}
