package com.sankuai.avatar.workflow.core.notify;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import lombok.AllArgsConstructor;

/**
 * 大象通知内的链接操作
 * @author Jie.li.sh
 * @create 2022-11-30
 **/
@AllArgsConstructor
public class NotifyAction {
    /**
     * 按钮内容
     */
    String content;
    /**
     * 格式：default默认颜色, primary蓝色, danger红色, disable置灰且不可点击
     */
    String style;
    /**
     * 链接
     */
    String url;

    /**
     * 详情的操作按钮
     * @param flowContext 流程
     * @return action
     */
    public static NotifyAction ofFlowDetailAction(FlowContext flowContext) {
        return new NotifyAction("查看详情", "default", flowContext.getUuid());
    }

}
