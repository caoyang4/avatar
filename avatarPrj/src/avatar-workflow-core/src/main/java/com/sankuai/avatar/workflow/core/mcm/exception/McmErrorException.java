package com.sankuai.avatar.workflow.core.mcm.exception;

/**
 * MCM 调用异常
 *
 * @author zhaozhifan02
 */
public class McmErrorException extends RuntimeException {
    public McmErrorException() {
    }

    public McmErrorException(String msg) {
        super(msg);
    }

    public McmErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
