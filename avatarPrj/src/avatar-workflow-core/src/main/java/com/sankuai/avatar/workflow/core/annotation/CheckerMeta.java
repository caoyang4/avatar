package com.sankuai.avatar.workflow.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhaozhifan02
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CheckerMeta {

    /**
     * 默认参数, 预检的流程名称
     *
     * @return {@link String[]}
     */
    String[] value() default {};

    /**
     * 超时时间
     * @return ms
     */
    int timeout() default 100;

    /**
     * 是否检查API用户。false: 如果API用户则跳过, 默认检查
     * @return true/false
     */
    boolean checkAppkeyUser() default true;

    /**
     * 检查顺序
     */
    int order() default 0;
}
