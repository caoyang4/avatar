package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: IP请求频次超过上限
 *
 * @author qinwei05
 */

public class IpRequestExceededException extends RuntimeException {

    /**
     * Constructs an <code>IpRequestExceededException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public IpRequestExceededException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>IpRequestExceededException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public IpRequestExceededException(String msg, Throwable t) {
        super(msg, t);
    }
}
