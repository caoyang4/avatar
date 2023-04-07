package com.sankuai.avatar.collect.appkey.annotion;

import com.sankuai.avatar.collect.appkey.collector.source.SourceName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qinwei05
 * @date 2022/12/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SourceBy {

    /**
     * 事件源
     *
     * @return {@link SourceName}
     */
    SourceName[] value();

}
