package com.sankuai.avatar.web.exception;

public class AuthDeniedException extends RuntimeException{
    public AuthDeniedException() {

    }
    public AuthDeniedException(String message) {
        super(message);
    }
}
