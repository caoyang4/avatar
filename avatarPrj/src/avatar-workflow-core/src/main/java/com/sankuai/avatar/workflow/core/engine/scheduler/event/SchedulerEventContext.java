package com.sankuai.avatar.workflow.core.engine.scheduler.event;

import com.sankuai.avatar.common.exception.JsonSerializationException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 调度器事件对象，提交调度器事件处理器
 *
 * @author xk
 */
@Builder
public class SchedulerEventContext {

    /**
     * 事件数据
     */
    @Getter
    @Setter
    private Object eventInput;

    /**
     * 事件类型
     */
    @Getter
    @Setter
    private SchedulerEventEnum schedulerEventEnum;

    /**
     * 获取事件输入数据, 泛型化
     *
     * @param t t
     * @return {@link T}
     * @throws JsonSerializationException json序列化异常
     */
    public <T> T getEventInput(Class<T> t) throws JsonSerializationException {
        return t.isInstance(this.eventInput)? t.cast(this.eventInput): null;
    }

    /**
     * of
     *
     * @param schedulerEventEnum 事件类型
     * @return {@link SchedulerEventContext}
     */
    public static SchedulerEventContext of(SchedulerEventEnum schedulerEventEnum) {
        return SchedulerEventContext.builder().schedulerEventEnum(schedulerEventEnum).build();
    }
}
