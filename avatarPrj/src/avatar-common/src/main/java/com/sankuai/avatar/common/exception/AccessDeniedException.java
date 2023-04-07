package com.sankuai.avatar.common.exception;

/**
 * @author zhaozhifan02
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {

    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
