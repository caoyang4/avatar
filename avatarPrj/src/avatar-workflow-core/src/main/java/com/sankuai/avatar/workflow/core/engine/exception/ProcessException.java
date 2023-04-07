package com.sankuai.avatar.workflow.core.engine.exception;

/**
 * 操作失败
 * @author Jie.li.sh
 * @create 2023-02-21
 **/
public class ProcessException extends RuntimeException {
    public ProcessException(String message) {
        super(String.format("Process执行失败: %s", message));
    }
}
