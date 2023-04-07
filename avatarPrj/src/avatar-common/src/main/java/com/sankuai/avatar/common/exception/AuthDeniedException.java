package com.sankuai.avatar.common.exception;

/**
 * @author zhaozhifan02
 */
public class AuthDeniedException extends RuntimeException{
    public AuthDeniedException() {

    }
    public AuthDeniedException(String message) {
        super(message);
    }
}

