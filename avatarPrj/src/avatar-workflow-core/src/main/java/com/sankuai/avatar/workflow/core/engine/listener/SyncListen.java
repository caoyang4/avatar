package com.sankuai.avatar.workflow.core.engine.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 同步监听器, 被注解的监听器将被同步执行
 *
 * @author xk
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SyncListen {
    String value() default "true";
}
