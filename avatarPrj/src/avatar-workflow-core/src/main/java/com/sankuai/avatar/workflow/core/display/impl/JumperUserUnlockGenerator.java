package com.sankuai.avatar.workflow.core.display.impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.display.AbstractDisplayGenerator;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 跳板机账户解锁展示信息生成器
 *
 * @author zhaozhifan02
 */
@Slf4j
@Component("user_unlock")
public class JumperUserUnlockGenerator extends AbstractDisplayGenerator  {


    @Override
    protected FlowDisplay doGenerate(FlowContext flowContext) {
        return FlowDisplay.builder().input(getInputDisplay(flowContext)).build();
    }

    @Override
    public List<InputDisplay> getInputDisplay(FlowContext flowContext) {
        return getDefaultInputDisplay(flowContext);
    }
}
