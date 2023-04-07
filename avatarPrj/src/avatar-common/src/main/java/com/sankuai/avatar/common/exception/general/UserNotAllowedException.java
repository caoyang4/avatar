package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 不合法的用户
 *
 * @author qinwei05
 */

public class UserNotAllowedException extends RuntimeException {
    /**
     * Constructs an <code>UserNotAllowedException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public UserNotAllowedException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>UserNotAllowedException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public UserNotAllowedException(String msg, Throwable t) {
        super(msg, t);
    }
}
