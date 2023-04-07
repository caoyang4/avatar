package com.sankuai.avatar.workflow.core.execute.exception;

/**
 * Atom 执行异常
 *
 * @author zhaozhifan02
 */
public class AtomExecuteException extends RuntimeException {
    public AtomExecuteException() {
    }

    public AtomExecuteException(String msg) {
        super(msg);
    }

    public AtomExecuteException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
