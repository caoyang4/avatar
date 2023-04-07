package com.sankuai.avatar.collect.appkey.event;

/**
 * Appkey状态
 *
 * @author qinwei05
 * @date 2022/12/14
 */
public enum AppkeyStatusEnum {

    /**
     * 存在
     */
    ONLINE("ONLINE"),

    /**
     * 已下线
     */
    OFFLINE("OFFLINE"),

    /**
     * 未知类型: 不存在
     */
    UNKNOWN("UNKNOWN");

    private final String value;

    AppkeyStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
