package com.sankuai.avatar.workflow.core.input;

import com.dianping.cat.Cat;
import com.sankuai.avatar.workflow.core.annotation.Display;
import com.sankuai.avatar.workflow.core.annotation.FlowMeta;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * 流程输入对象
 *
 * @author Jie.li.sh
 * @create 2022-11-11
 **/
@Data
public abstract class AbstractFlowInput implements FlowInput {

    @Override
    public String flowTemplateName() {
        return this.getClass().getAnnotation(FlowMeta.class).flowTemplateName();
    }

    /**
     * 展示的中文名
     * @param fieldName 字段名
     * @return display name
     */
    @Override
    public String displayName(String fieldName) {
        try {
            Display display = this.getClass().getDeclaredField(fieldName).getAnnotation(Display.class);
            if (display != null && !StringUtils.isBlank(display.name())) {
                return display.name();
            }
        } catch (NoSuchFieldException e) {
            Cat.logError(e);
        }
        return null;
    }
}
