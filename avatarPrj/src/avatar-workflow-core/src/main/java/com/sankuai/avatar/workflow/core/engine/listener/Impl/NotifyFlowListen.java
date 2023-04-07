package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.listener.FlowListen;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessNameLoader;
import com.sankuai.avatar.workflow.core.engine.process.response.ExecuteResult;
import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.notify.NotifyRequest;
import com.sankuai.avatar.workflow.core.notify.handler.FlowTemplateType;
import com.sankuai.avatar.workflow.core.notify.handler.NotifyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *  通知监听器, Process状态变化后需要触发相关用户/群通知
 * @author Jie.li.sh
 * @create 2023-03-17
 **/
@Component
public class NotifyFlowListen implements FlowListen {

    @Autowired
    NotifyHandler flowNotifyHandler;

    @Autowired
    ProcessNameLoader processNameLoader;

    @Override
    public void receiveEvents(FlowContext flowContext, FlowState flowState) {
        // 预检通过 -> 已发起
        if (flowState.equals(FlowState.PRE_CHECK_ACCEPTED)) {
            flowNotifyHandler.handle(FlowTemplateType.START, NotifyRequest.of(flowContext));
            return;
        }
        // 执行失败 -> 失败通知
        if (flowState.equals(FlowState.EXECUTE_FAILED)) {
            flowNotifyHandler.handle(FlowTemplateType.FAILED, NotifyRequest.of(flowContext, buildExecuteFailedMsg(flowContext)));
            return;
        }
        // 执行完成 -> 流程完成
        if (flowState.equals(FlowState.EXECUTE_SUCCESS)) {
            flowNotifyHandler.handle(FlowTemplateType.COMPLETE, NotifyRequest.of(flowContext));
        }
    }

    /**
     * 聚合本次执行失败Atom的异常数据
     *
     * @param flowContext 流上下文
     * @return 失败信息
     */
    private List<String> buildExecuteFailedMsg(FlowContext flowContext) {
        ExecuteResult executeResult = flowContext.getCurrentProcessContext().getResponse().getResult(ExecuteResult.class);

        if (executeResult == null) {
            return null;
        }
        List<String> errorMsgs = new ArrayList<>();
        for (AtomContext atomContext : executeResult.getAtomContextList()) {
            if (atomContext.getAtomResult() == null || atomContext.getAtomResult().getException() == null) {
                continue;
            }
            errorMsgs.add(String.format("%s 执行发生了异常: %s",
                    atomContext.getName(),
                    atomContext.getAtomResult().getException().getMessage())
            );
        }
        return errorMsgs;
    }
}
