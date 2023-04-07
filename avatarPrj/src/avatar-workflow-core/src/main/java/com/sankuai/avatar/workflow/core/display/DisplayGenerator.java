package com.sankuai.avatar.workflow.core.display;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import com.sankuai.avatar.workflow.core.display.model.TextDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaozhifan02
 */
public interface DisplayGenerator {
    /**
     * 生成展示信息
     *
     * @param flowContext 流程上下文
     * @param state       状态
     * @return {@link FlowDisplay}
     */
    FlowDisplay generate(FlowContext flowContext, FlowState state);

    /**
     * 获取输入展示信息
     *
     * @param flowContext {@link FlowContext}
     * @return List<InputDisplay>
     */
    default List<InputDisplay> getInputDisplay(FlowContext flowContext){
        return new ArrayList<>();
    }

    /**
     * 获取风险提示展示信息
     *
     * @param flowContext {@link FlowContext}
     * @return List<TextDisplay>
     */
    default List<TextDisplay> getTextDisplay(FlowContext flowContext){
        return new ArrayList<>();
    }
}
