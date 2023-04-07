package com.sankuai.avatar.web.exception;

/**
 * crane 任务异常
 * @author caoyang
 * @create 2022-11-11 17:09
 */
public class CraneTaskException extends RuntimeException{

    public CraneTaskException() { super(); }

    public CraneTaskException(String message) {
        super(message);
    }
}
