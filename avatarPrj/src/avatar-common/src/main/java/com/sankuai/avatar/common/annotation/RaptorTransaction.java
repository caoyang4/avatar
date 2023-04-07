package com.sankuai.avatar.common.annotation;

import java.lang.annotation.*;

/**
 * transaction 打点
 * @author kui.xu
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RaptorTransaction {
    /**
     * cat transaction type 默认为类名
     */
    String type() default "";

    /**
     * cat transaction name 默认为方法名
     */
    String name() default "";

    /**
     * 默认抛异常。若不希望抛异常，可设置为 false，此时发生异常时方法返回 null
     */
    boolean throwException() default true;
}

