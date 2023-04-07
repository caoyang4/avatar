package com.sankuai.avatar.common.exception;

/**
 * @author zhaozhifan02
 */
public class SupportErrorException extends RuntimeException{
    public SupportErrorException() { super(); }

    public SupportErrorException(String message) {
        super(message);
    }
}
