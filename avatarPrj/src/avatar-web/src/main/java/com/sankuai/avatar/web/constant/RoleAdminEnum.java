package com.sankuai.avatar.web.constant;

import lombok.Getter;

/**
 * @Author: qinwei05
 * @Date: 2021/11/17 10:39
 */

@Getter
public enum RoleAdminEnum {
    /**
     * RoleAdmin
     */
    OP_ADMIN(0, "SRE"),

    EP_ADMIN(0, "QA"),

    RD_ADMIN(2, "RD");

    private final int code;

    private final String name;

    RoleAdminEnum(int code, String name) {
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
