package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.workflow.core.context.FlowAudit;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.dao.workflow.repository.FlowRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import cn.hutool.core.date.DateTime;
import com.sankuai.avatar.workflow.core.engine.listener.FlowListen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * FlowContext 存储
 *
 * @author xk
 */
@Order(Integer.MIN_VALUE)
@Component
public class SaveFlowListen implements FlowListen {

    @Autowired
    FlowRepository flowRepository;

    /**
     * 更新流程上下文FlowContext上下文到存储
     */
    @Override
    public void receiveEvents(FlowContext flowContext, FlowState flowState) {
        if (flowContext == null || flowState == null) {
            return;
        }
        if (FlowState.NEW.equals(flowState)) {
            return;
        }
        // 更新流程
        FlowEntity flowEntity = FlowEntity.builder()
                .id(flowContext.getId())
                .status(flowState.getEvent())
                .processIndex(flowContext.getProcessIndex())
                .build();

        if (getFlowAuditState().contains(flowState)) {
            FlowAudit flowAudit = flowContext.getFlowAudit();
            if (flowAudit != null) {
                // 兼容V1 更新审核人
                flowEntity.setApproveUsers(String.join(",", flowAudit.getAuditor()));
            }
        }
        if (getFlowCompletedState().contains(flowState)) {
            flowContext.setEndTime(DateTime.now());
            flowEntity.setEndTime(DateTime.now());
        }
        flowRepository.updateFlow(flowEntity);
    }

    /**
     * 流程终态
     *
     * @return List<FlowState>
     */
    private List<FlowState> getFlowCompletedState() {
        return Arrays.asList(FlowState.SHUTDOWN, FlowState.EXECUTE_SUCCESS, FlowState.EXECUTE_FAILED,
                FlowState.PRE_CHECK_REJECTED, FlowState.AUDIT_REJECTED);
    }

    /**
     * 审核动作状态
     *
     * @return List<FlowState>
     */
    private List<FlowState> getFlowAuditState() {
        return Arrays.asList(FlowState.AUDITING, FlowState.AUDIT_ACCEPTED, FlowState.AUDIT_REJECTED);
    }
}
