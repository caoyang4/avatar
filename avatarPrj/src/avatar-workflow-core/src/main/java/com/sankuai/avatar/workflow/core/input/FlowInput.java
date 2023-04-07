package com.sankuai.avatar.workflow.core.input;

/**
 * 输入对象
 * @author Jie.li.sh
 * @create 2022-11-11
 **/
public interface FlowInput {
    /**
     * 流程模版名
     * @return name
     */
    String flowTemplateName();

    /**
     * 展示的中文名
     *
     * @param fieldName 字段名
     * @return display name
     */
    String displayName(String fieldName);
}
