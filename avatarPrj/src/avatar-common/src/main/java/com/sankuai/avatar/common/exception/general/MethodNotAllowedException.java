package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 请求的HTTP METHOD不支持，请检查是否选择了正确的POST/GET方式
 *
 * @author qinwei05
 */

public class MethodNotAllowedException extends RuntimeException {

    /**
     * Constructs an <code>MethodNotAllowedException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public MethodNotAllowedException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>MethodNotAllowedException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public MethodNotAllowedException(String msg, Throwable t) {
        super(msg, t);
    }
}
