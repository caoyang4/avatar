package com.sankuai.avatar.workflow.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FlowMeta {
    /**
     * 流程模版名称
     * @return template name
     */
    String flowTemplateName();
}
