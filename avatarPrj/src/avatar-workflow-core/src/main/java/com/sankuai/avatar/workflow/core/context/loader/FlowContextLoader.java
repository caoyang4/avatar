package com.sankuai.avatar.workflow.core.context.loader;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.request.FlowCreateRequest;

/**
 * @author xk
 */
public interface FlowContextLoader {

    /**
     * 获取指定 id 的流程上下文
     *
     * @param flowId 流程id
     * @return {@link FlowContext}
     */
    FlowContext id(Integer flowId);

    /**
     * 创建指定模版名称的流程上下文
     *
     * @param templateName 名字
     * @param flowCreateRequest 创建请求
     * @return {@link FlowContext}
     */
    FlowContext buildByTemplateName(String templateName, FlowCreateRequest flowCreateRequest);
}
