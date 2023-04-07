package com.sankuai.avatar.workflow.core.engine.listener;

import com.sankuai.avatar.workflow.core.context.FlowState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 监听器订阅, 被注解的监听器仅推送订阅的事件
 *
 * @author kui.xu
 * @date 2023/03/24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenFlowState {
    FlowState[] value() default {};
}
