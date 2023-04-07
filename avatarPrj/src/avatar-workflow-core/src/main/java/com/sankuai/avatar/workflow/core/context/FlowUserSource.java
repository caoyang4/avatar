package com.sankuai.avatar.workflow.core.context;

/**
 * @author Jie.li.sh
 * @create 2022-11-04
 **/
public enum FlowUserSource {
    /**
     * 正常用户, web管理端使用
     */
    USER(1),
    /**
     * 服务, 通过API调用
     */
    APPKEY(2);

    private final Integer value;

    FlowUserSource(Integer value) {
        this.value = value;
    }
}
