package com.sankuai.avatar.common.exception;

/**
 * 资源中心: 采集逻辑错误异常
 *
 * @author qinwei05
 * @date 2023/01/03
 */
public class CollectErrorException extends RuntimeException{

    public CollectErrorException() { super(); }

    /**
     * 错误异常
     *
     * @param message 异常信息
     */
    public CollectErrorException(String message) {
        super(message);
    }
}
