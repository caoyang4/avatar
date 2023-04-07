package com.sankuai.avatar.workflow.core.notify;

import lombok.Getter;

/**
 * 接收人角色
 *
 * @author Jie.li.sh
 */
public enum NotifyReceiverRole {
    /**
     * 发起人
     */
    CREATE_USER("create_user", "发起", 0),
    /**
     * 服务开发负责人
     */
    RD_ADMIN("rd_admin", "负责", 1),

    /**
     *  服务运维负责人
     */
    OP_ADMIN("op_admin", "维护", 2),

    /**
     * 服务测试负责人
     */
    EP_ADMIN("ep_Admin", "测试", 3),

    /**
     * 域名负责人
     */
    DOMAIN_ADMIN("domain_admin", "负责", 4),

    /**
     * 其他：群
     */
    OTHER("other", "其他", 5);

    /**
     * 角色
     */
    @Getter
    private final String value;

    /**
     * 角色名称
     */
    @Getter
    private final String roleName;

    /**
     * 角色优先级，重复user只匹配优先级高的角色
     */
    @Getter
    private final Integer priority;

    NotifyReceiverRole(String value, String roleName, Integer priority) {
        this.value = value;
        this.roleName = roleName;
        this.priority = priority;
    }
}
