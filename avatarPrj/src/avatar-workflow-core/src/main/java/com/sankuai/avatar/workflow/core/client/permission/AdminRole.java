package com.sankuai.avatar.workflow.core.client.permission;

/**
 * @author zhaozhifan02
 */
public enum AdminRole {
    /**
     * 服务负责人
     */
    RD_ADMIN("rd_admin", "服务负责人"),

    /**
     * SRE
     */
    SRE("sre", "SRE"),

    /**
     * 测试负责人
     */
    EP_ADMIN("ep_admin", "测试负责人"),

    /**
     * 超级管理员
     */
    SUPER_ADMIN("super_admin", "超级管理员");

    AdminRole(String value, String name) {
        this.value = value;
        this.name = name;
    }

    private String value;

    private String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static AdminRole getByValue(String value) {
        for (AdminRole role : values()) {
            boolean equals = value.equals(role.getValue());
            if (equals) {
                return role;
            }
        }
        return null;
    }

}
