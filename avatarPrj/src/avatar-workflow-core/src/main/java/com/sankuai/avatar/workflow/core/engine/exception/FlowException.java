package com.sankuai.avatar.workflow.core.engine.exception;

/**
 * 流程相关失败
 * @author Jie.li.sh
 * @create 2023-02-21
 **/
public class FlowException extends RuntimeException {
    public FlowException(String message) {
        super(String.format("流程操作失败: %s", message));
    }
}
