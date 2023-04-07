package com.sankuai.avatar.common.exception.general;


/**
 * 异常类型: 服务暂停
 *
 * @author qinwei05
 */

public class ServiceNotAvailableException extends RuntimeException {

    /**
     * Constructs an <code>ServiceNotAvailableException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public ServiceNotAvailableException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>ServiceNotAvailableException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public ServiceNotAvailableException(String msg, Throwable t) {
        super(msg, t);
    }
}
