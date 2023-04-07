package com.sankuai.avatar.workflow.core.display;

import com.dianping.cat.Cat;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 展示信息抽象类
 *
 * @author zhaozhifan02
 */
@Slf4j
public abstract class AbstractDisplayGenerator implements DisplayGenerator {

    @Override
    public FlowDisplay generate(FlowContext flowContext, FlowState state) {
        return doGenerate(flowContext);
    }

    /**
     * 具体的生成逻辑，子类实现
     *
     * @param flowContext 流程上下文
     * @return {@link FlowDisplay}
     */
    protected abstract FlowDisplay doGenerate(FlowContext flowContext);

    /**
     * 根据 FlowInput display 注解生成展示信息
     *
     * @param flowContext 流程上下文
     * @return List<InputDisplay>
     */
    protected List<InputDisplay> getDefaultInputDisplay(FlowContext flowContext) {
        FlowInput flowInput = flowContext.getFlowInput();
        if (flowInput == null) {
            return Collections.emptyList();
        }
        List<InputDisplay> inputDisplayList = new ArrayList<>();
        for (Field field : flowInput.getClass().getDeclaredFields()) {
            String displayName = flowInput.displayName(field.getName());
            if (StringUtils.isBlank(displayName)) {
                continue;
            }
            try {
                field.setAccessible(true);
                if (field.get(flowInput) == null) {
                    continue;
                }
                InputDisplay inputDisplay = InputDisplay.builder()
                        .fieldName(field.getName())
                        .key(displayName)
                        .value(field.get(flowInput).toString()).build();
                inputDisplayList.add(inputDisplay);
            } catch (IllegalAccessException e) {
                Cat.logError(e);
                log.error("Get flowInput displayName catch error", e);
            }
        }
        return inputDisplayList;
    }

}
