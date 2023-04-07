package com.sankuai.avatar.workflow.core.display.impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.display.AbstractDisplayGenerator;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 跳板机密码重置 display 生成器
 * @author caoyang
 * @create 2023-03-14 16:38
 */
@Component("password_reset")
public class PasswordResetGenerator extends AbstractDisplayGenerator {

    @Override
    protected FlowDisplay doGenerate(FlowContext flowContext) {
        return FlowDisplay.builder().input(getInputDisplay(flowContext)).build();
    }

    @Override
    public List<InputDisplay> getInputDisplay(FlowContext flowContext) {
        return getDefaultInputDisplay(flowContext);
    }

}
