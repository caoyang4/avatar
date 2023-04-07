package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.loader.FlowContextLoader;
import com.sankuai.avatar.workflow.core.context.request.FlowCreateRequest;
import com.sankuai.avatar.workflow.core.engine.exception.FlowException;
import com.sankuai.avatar.workflow.core.engine.listener.PushEvent;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContextLoader;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 流程加载器
 *
 * @author xk
 */
@Component
public class LoaderImpl implements Loader {
    @Autowired
    ProcessContextLoader processContextLoader;

    @Autowired
    FlowContextLoader flowContextLoader;

    @Autowired
    PushEvent pushEvent;

    @Override
    @RaptorTransaction
    public ProcessTemplate flowId(Integer flowId) {
        // 获取flowContext
        FlowContext flowContext = flowContextLoader.id(flowId);
        if (flowContext == null) {
            throw new FlowException(String.format("流程id: %d 不存在", flowId));
        }
        // 获取processContext
        List<ProcessContext> processContextList = processContextLoader.buildByFlowContext(flowContext);
        return loadProcessTemplate(processContextList, flowContext);
    }

    @Override
    @RaptorTransaction
    public ProcessTemplate flowTemplateName(String templateName, FlowCreateRequest flowCreateRequest) {
        // 构建 flowContext
        FlowContext flowContext = flowContextLoader.buildByTemplateName(templateName, flowCreateRequest);
        // 构建 processContext
        List<ProcessContext> processContextList = processContextLoader.buildByFlowContext(flowContext);
        // 构建 ProcessTemplate
        return loadProcessTemplate(processContextList, flowContext);
    }

    /**
     * 组装至processTemplate
     */
    @Override
    public ProcessTemplate loadProcessTemplate(List<ProcessContext> processContextList, FlowContext flowContext) {
        ProcessTemplate processTemplate = new ProcessTemplate(flowContext.getProcessIndex(), processContextList);
        processContextList.forEach(processContext -> {
            processContext.setProcessTemplate(processTemplate);
            processContext.setFlowContext(flowContext);
        });
        flowContext.setCurrentProcessContext(processTemplate.getCurrentProcesses());
        return processTemplate;
    }
}
