package com.sankuai.avatar.common.exception;

/**
 * @author qinwei05
 */
public class JsonSerializationException extends RuntimeException{

    /**
     * Constructs an <code>JsonSerializationException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public JsonSerializationException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>JsonSerializationException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public JsonSerializationException(String msg, Throwable t) {
        super(msg, t);
    }
}
