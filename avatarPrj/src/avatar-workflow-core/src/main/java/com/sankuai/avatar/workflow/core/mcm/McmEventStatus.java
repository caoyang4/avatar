package com.sankuai.avatar.workflow.core.mcm;

import lombok.Getter;

/**
 * MCM 事件状态
 *
 * @author zhaozhifan02
 */
public enum McmEventStatus {
    /**
     * 待审核
     */
    AUDITING(1, "AUDITING", "待审核"),

    /**
     * 已驳回
     */
    REJECTED(2, "REJECTED", "已驳回"),

    /**
     * 变更待确认
     */
    WARNING(3, "WARNING", "变更待确认"),

    /**
     * 变更终止
     */
    STOPPED(4, "STOPPED", "变更终止"),

    /**
     * 变更中
     */
    RUNNING(5, "RUNNING", "变更中"),

    /**
     * 变更失败
     */
    FAILED(6, "FAILED", "变更失败"),

    /**
     * 变更完成
     */
    SUCCEED(7, "SUCCEED", "变更完成"),

    /**
     * 预检中
     */
    PRECHECKING(8, "PRECHECKING", "预检中"),

    /**
     * 预检完成
     */
    PRECHECKED(9, "PRECHECKED", "预检完成"),

    /**
     * 复检中
     */
    AFTERCHECKING(10, "AFTERCHECKING", "复检中");


    McmEventStatus(Integer id, String status, String statusName) {
        this.id = id;
        this.status = status;
        this.statusName = statusName;
    }

    /**
     * id
     */
    @Getter
    private final Integer id;

    /**
     * 状态
     */
    @Getter
    private final String status;

    /**
     * 状态名称
     */
    @Getter
    private final String statusName;

    /**
     * 根据 status 获取  McmEventStatus
     *
     * @param status 状态
     * @return {@link McmEventStatus}
     */
    public static McmEventStatus getEventStatus(String status) {
        for (McmEventStatus eventStatus : McmEventStatus.values()) {
            if (eventStatus.getStatus().equals(status)) {
                return eventStatus;
            }
        }
        return null;
    }
}
