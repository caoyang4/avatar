package com.sankuai.avatar.web.constant;

import lombok.Getter;

/**
 * @Author: qinwei05
 * @Date: 2022/03/28 10:39
 */

@Getter
public enum AppkeyEnvEnum {
    /**
     * status
     */
    PROD(0, "prod"),

    TEST(1, "test");

    private final int code;

    private final String name;

    AppkeyEnvEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }


    public String getName() {
        return name;
    }
}
