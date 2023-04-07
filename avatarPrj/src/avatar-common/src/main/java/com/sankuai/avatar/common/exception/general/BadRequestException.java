package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 非法请求
 *
 * @author qinwei05
 */

public class BadRequestException extends RuntimeException {

    /**
     * Constructs an <code>BadRequestException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public BadRequestException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>BadRequestException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public BadRequestException(String msg, Throwable t) {
        super(msg, t);
    }
}
