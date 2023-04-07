package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 没有权限
 *
 * @author qinwei05
 */

public class UnAuthorizedException extends RuntimeException {

    /**
     * Constructs an <code>UnAuthorizedException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public UnAuthorizedException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>UnAuthorizedException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public UnAuthorizedException(String msg, Throwable t) {
        super(msg, t);
    }
}
