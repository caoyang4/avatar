package com.sankuai.avatar.web.exception;

public class AuthRedirectException extends RuntimeException{
    public AuthRedirectException() {
        super();
    }
    public AuthRedirectException(String message) {
        super(message);
    }
}
