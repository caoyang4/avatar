package com.sankuai.avatar.collect.appkey.annotion;

import com.sankuai.avatar.collect.appkey.event.CollectEventName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qinwei05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OnFire {

    /**
     * 采集事件列表
     *
     * @return {@link CollectEventName[]}
     */
    CollectEventName[] value();
}
