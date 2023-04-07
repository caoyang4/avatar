package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 任务超时
 *
 * @author qinwei05
 */

public class TimeoutException extends RuntimeException {
    /**
     * Constructs an <code>TimeoutException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public TimeoutException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>TimeoutException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public TimeoutException(String msg, Throwable t) {
        super(msg, t);
    }
}
