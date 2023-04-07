package com.sankuai.avatar.common.exception;


/**
 * 异常类型: 资源不存在
 *
 * @author qinwei05
 * @date 2022/11/24
 */
public class ResourceNotFoundErrorException extends RuntimeException {

    private Integer code;

    /**
     * Constructs an <code>ResourceNotFoundErrorException</code> with the specified message.
     *
     * @param detailMessage the detail message
     */
    public ResourceNotFoundErrorException(String detailMessage) {
        super(detailMessage);
    }

    public ResourceNotFoundErrorException(Integer code, String detailMessage) {
        super(detailMessage);
        this.code = code;
    }

    /**
     * Constructs an <code>ResourceNotFoundErrorException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public ResourceNotFoundErrorException(String msg, Throwable t) {
        super(msg, t);
    }

    public ResourceNotFoundErrorException(Integer code, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}