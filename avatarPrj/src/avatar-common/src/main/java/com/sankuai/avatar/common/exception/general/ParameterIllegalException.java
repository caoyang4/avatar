package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 参数错误
 *
 * @author qinwei05
 */

public class ParameterIllegalException extends RuntimeException {

    /**
     * Constructs an <code>ParameterIllegalException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public ParameterIllegalException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>ParameterIllegalException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public ParameterIllegalException(String msg, Throwable t) {
        super(msg, t);
    }
}
