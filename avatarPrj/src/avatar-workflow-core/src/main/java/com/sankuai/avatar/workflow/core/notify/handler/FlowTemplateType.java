package com.sankuai.avatar.workflow.core.notify.handler;

/**
 * @author Jie.li.sh
 * @create 2023-03-10
 **/
public enum FlowTemplateType {
    /**
     * 已发起
     */
    START,
    /**
     * 失败
     */
    FAILED,
    /**
     * 完成时
     */
    COMPLETE;

    FlowTemplateType() {
    }
}
