package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 接口不存在
 *
 * @author qinwei05
 */

public class InterfaceNotFoundException extends RuntimeException {

    /**
     * Constructs an <code>InterfaceNotFoundException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public InterfaceNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>InterfaceNotFoundException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public InterfaceNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
