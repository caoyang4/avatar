package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 请求长度超过限制
 *
 * @author qinwei05
 */

public class RequestLengthExceededException extends RuntimeException {

    /**
     * Constructs an <code>RequestLengthExceededException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public RequestLengthExceededException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>RequestLengthExceededException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public RequestLengthExceededException(String msg, Throwable t) {
        super(msg, t);
    }
}
