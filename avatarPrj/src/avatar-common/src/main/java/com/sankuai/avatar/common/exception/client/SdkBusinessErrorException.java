package com.sankuai.avatar.common.exception.client;


/**
 * sdk业务错误异常
 * 异常类型: 系统调用逻辑错误
 *
 * @author qinwei05
 * @date 2022/11/24
 */
public class SdkBusinessErrorException extends RuntimeException {

    private Integer code;

    /**
     * Constructs an <code>SdkBusinessErrorException</code> with the specified message.
     *
     * @param detailMessage the detail message
     */
    public SdkBusinessErrorException(String detailMessage) {
        super(detailMessage);
    }

    public SdkBusinessErrorException(Integer code, String detailMessage) {
        super(detailMessage);
        this.code = code;
    }

    /**
     * Constructs an <code>SdkBusinessErrorException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public SdkBusinessErrorException(String msg, Throwable t) {
        super(msg, t);
    }

    public SdkBusinessErrorException(Integer code, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}