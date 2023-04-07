package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 用户请求频次超过上限
 *
 * @author qinwei05
 */

public class UserRequestExceededException extends RuntimeException {
    /**
     * Constructs an <code>UserRequestExceededException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public UserRequestExceededException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>UserRequestExceededException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public UserRequestExceededException(String msg, Throwable t) {
        super(msg, t);
    }
}
