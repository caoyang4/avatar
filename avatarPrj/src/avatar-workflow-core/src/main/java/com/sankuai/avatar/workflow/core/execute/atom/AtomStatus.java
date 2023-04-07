package com.sankuai.avatar.workflow.core.execute.atom;

import lombok.Getter;

/**
 * atom对象状态
 *
 * @author zhaozhifan02
 */
public enum AtomStatus {

    /**
     * 未开始
     */
    NEW(0, "未开始", "NEW"),

    /**
     * 调度
     */
    SCHEDULER(1, "调度", "Scheduler"),

    /**
     * 运行
     */
    RUNNING(2, "运行中", "RUNNING"),

    /**
     * 挂起，用于等待、中断等场景
     */
    PENDING(3, "挂起", "PENDING"),

    /**
     * 成功
     */
    SUCCESS(4, "成功", "SUCCESS"),

    /**
     * 失败
     */
    FAIL(5, "失败", "FAIL");

    @Getter
    private final Integer code;

    @Getter
    private final String name;

    @Getter
    private final String value;

    AtomStatus(Integer code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public static AtomStatus getByStatusValue(String status) {
        for (AtomStatus atomStatus : values()) {
            boolean equals = status.equals(atomStatus.getValue());
            if (equals) {
                return atomStatus;
            }
        }
        return null;
    }
}
