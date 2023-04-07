package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.request.FlowCreateRequest;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;

import java.util.List;

/**
 * @author xk
 */
public interface Loader {
    /**
     * 加载已存在流程, 根据流程id从存储读取
     *
     * @param flowId 流id
     * @return {@link ProcessContext}
     */
    ProcessTemplate flowId(Integer flowId);

    /**
     * 新创建流程, 根据名称构建流程上下文和编排模板
     *
     * @param templateName 模版名字
     * @param flowCreateRequest {@link FlowCreateRequest}
     * @return {@link ProcessContext}
     */
    ProcessTemplate flowTemplateName(String templateName, FlowCreateRequest flowCreateRequest);

    /**
     * 加载流程模板
     *
     * @param processContextList 进程上下文列表
     * @param flowContext        流上下文
     * @return {@link ProcessTemplate}
     */
    ProcessTemplate loadProcessTemplate(List<ProcessContext> processContextList, FlowContext flowContext);
}
