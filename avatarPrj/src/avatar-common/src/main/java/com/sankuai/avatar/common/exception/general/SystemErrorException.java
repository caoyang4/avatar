package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 系统内部错误
 *
 * @author qinwei05
 */

public class SystemErrorException extends RuntimeException {

    /**
     * Constructs an <code>SystemErrorException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public SystemErrorException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>SystemErrorException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public SystemErrorException(String msg, Throwable t) {
        super(msg, t);
    }
}
