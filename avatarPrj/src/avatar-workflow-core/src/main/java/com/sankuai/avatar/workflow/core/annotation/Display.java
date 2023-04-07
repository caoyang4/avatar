package com.sankuai.avatar.workflow.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Display {
    /**
     * 中文名称
     * @return name
     */
    String name() default "";

    /**
     * 是否在通知中展示
     *
     * @return false/true
     */
    boolean displayNotify() default false;
}
