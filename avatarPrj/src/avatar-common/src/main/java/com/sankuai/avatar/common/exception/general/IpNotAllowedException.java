package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: IP限制不能请求该资源
 *
 * @author qinwei05
 */

public class IpNotAllowedException extends RuntimeException {
    /**
     * Constructs an <code>IpNotAllowedException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public IpNotAllowedException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>IpNotAllowedException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public IpNotAllowedException(String msg, Throwable t) {
        super(msg, t);
    }
}
