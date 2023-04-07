package com.sankuai.avatar.collect.appkey.event;

/**
 * 收集事件类型
 *
 * @author qinwei05
 * @date 2022/12/14
 */
public enum CollectEventTypeEnum {

    /**
     * 新增
     */
    ADD("ADD"),

    /**
     * 修改
     */
    UPDATE("UPDATE"),

    /**
     * 删除
     */
    DELETE("DELETE");

    private final String value;

    CollectEventTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
