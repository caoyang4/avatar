package com.sankuai.avatar.collect.appkey.collector.source;

/**
 * 采集来源
 * @author qinwei05
 */
public enum SourceName {
    /**
     * 来源是mafka消息
     */
    MAFKA_MESSAGE("mafka_message"),

    /**
     * 来源是dom
     */
    DOM("dom"),

    /**
     * 来源是dom
     */
    ROCKET("rocket"),

    /**
     * 来源是ops
     */
    OPS("ops"),

    /**
     * 来源是sc
     */
    SC("sc");

    SourceName(String value) {
        this.value = value;
    }

    /**
     * 值
     */
    private final String value;

    public String getValue() {
        return value;
    }
}
