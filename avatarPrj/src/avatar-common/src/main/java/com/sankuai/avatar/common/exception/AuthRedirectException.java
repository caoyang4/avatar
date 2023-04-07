package com.sankuai.avatar.common.exception;

/**
 * @author zhaozhifan02
 */
public class AuthRedirectException extends RuntimeException{
    public AuthRedirectException() {
        super();
    }
    public AuthRedirectException(String message) {
        super(message);
    }
}