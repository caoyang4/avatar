package com.sankuai.avatar.client.http.core;

/**
 * @author qinwei05
 * @date 2022/12/25 19:39
 * @version 1.0
 */
public enum EnvEnum {

    /**
     * 线上正式环境
     */
    PROD(1, "prod"),

    /**
     * 线上预发布环境
     */
    STAGING(2, "staging"),

    /**
     * 线下测试环境
     */
    TEST(3, "test"),

    /**
     * 线下开发环境
     */
    DEV(4, "dev");

    private final Integer code;

    private final String name;

    EnvEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
