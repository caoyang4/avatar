package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.context.FlowState;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 调度器状态枚举, 状态影响影调度器行为
 *
 * @author xk
 */
@AllArgsConstructor
public enum SchedulerState {

    /**
     * 第一次调度
     */
    NEW("NEW", "新开始"),

    /**
     * 成功继续调度
     */
    SUCCESS("SUCCESS", "执行成功"),

    /**
     * 挂起，用于等待、审核、中断等场景, 终止调度
     */
    PENDING("PENDING", "挂起"),

    /**
     * 失败, 终止调度
     */
    FAIL("FAIL", "失败");

    @Getter
    private final String state;

    @Getter
    private final String name;

    /**
     * 获取调度状态
     *
     * @param flowState 流程状态
     * @return {@link SchedulerState}
     */
    public static SchedulerState getValue(FlowState flowState) {
        switch (flowState) {
            case NEW:
                return SchedulerState.NEW;
            case PRE_CHECK_ACCEPTED:
            case AUDIT_IGNORE:
            case AUDIT_ACCEPTED:
            case EXECUTE_SUCCESS:
                return SchedulerState.SUCCESS;
            default:
                return SchedulerState.PENDING;
        }
    }
}
