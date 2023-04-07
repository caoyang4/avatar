package com.sankuai.avatar.workflow.core.engine.process.impl;

import com.sankuai.avatar.workflow.core.auditer.AuditHandler;
import com.sankuai.avatar.workflow.core.auditer.AuditResult;
import com.sankuai.avatar.workflow.core.auditer.chain.AuditState;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainNode;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditorOperation;
import com.sankuai.avatar.workflow.core.context.FlowAudit;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 审核执行器, 审核有三种结果, 审核通过、审核拒绝、撤销审核，分别对应三个处理函数
 *
 * @author xk
 */
@Component
public class AuditFlowProcess extends AbstractFlowProcess {

    @Autowired
    private AuditHandler auditHandler;

    @Override
    protected Response doProcess(ProcessContext processContext) {
        // 发起审核、获取审核链、Pending流程
        this.setFlowState(processContext.getFlowContext(), FlowState.AUDIT_LAUNCHED);
        AuditResult auditResult = auditHandler.audit(processContext.getFlowContext());

        if (auditResult.isShouldIgnore()) {
            // 跳过审核
            return Response.of(FlowState.AUDIT_IGNORE, auditResult);
        } else {
            // 待审核
            return Response.of(FlowState.AUDITING, auditResult);
        }
    }

    @Override
    protected Response doEventProcess(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        // 更新审核人
        FlowAuditorOperation auditorOperation = schedulerEventContext.getEventInput(FlowAuditorOperation.class);
        processContext.getFlowContext().getFlowAudit().getAuditor().add(auditorOperation.getAuditor());

        if (schedulerEventContext.getSchedulerEventEnum().equals(SchedulerEventEnum.AUDIT_ACCEPTED)) {
            // 审核通过
            return this.auditAccept(processContext, schedulerEventContext);
        } else if (schedulerEventContext.getSchedulerEventEnum().equals(SchedulerEventEnum.AUDIT_REJECTED)) {
            // 审核拒绝
            return this.auditReject(processContext, schedulerEventContext);
        } else if (schedulerEventContext.getSchedulerEventEnum().equals(SchedulerEventEnum.AUDIT_CANCELED)) {
            // 审核撤销
            return this.auditCancel(processContext, schedulerEventContext);
        }
        return null;
    }

    /**
     * 审核通过
     *
     * @param processContext        处理器上下文
     * @param schedulerEventContext 调度器事件背景
     */
    private Response auditAccept(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        // 是否为最后一个待审核节点
        List<FlowAuditChainNode> auditingNode = getAuditingNode(processContext.getFlowContext());
        if (auditingNode.size() == 1) {
            // 最后一个待审核节点
            return Response.of(FlowState.AUDIT_ACCEPTED, this.buildAuditResult(AuditState.ACCEPTED, processContext, schedulerEventContext));
        } else {
            // 非最后审核节点，则继续审核
            return Response.of(FlowState.AUDITING, this.buildAuditResult(AuditState.ACCEPTED, processContext, schedulerEventContext));
        }
    }

    /**
     * 审核拒绝
     *
     * @param processContext        处理器上下文
     * @param schedulerEventContext 调度器事件背景
     */
    private Response auditReject(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        return Response.of(FlowState.AUDIT_REJECTED, this.buildAuditResult(AuditState.REJECTED, processContext, schedulerEventContext));
    }

    /**
     * 审核撤销
     *
     * @param processContext        处理器上下文
     * @param schedulerEventContext 调度器事件背景
     */
    private Response auditCancel(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        return Response.of(FlowState.AUDIT_CANCELED, this.buildAuditResult(AuditState.CANCELED, processContext, schedulerEventContext));
    }

    /**
     * 获取审核链待审核节点
     *
     * @param flowContext {@link FlowContext}
     * @return List<FlowAuditChainNode>
     */
    private List<FlowAuditChainNode> getAuditingNode(FlowContext flowContext) {
        FlowAudit flowAudit = flowContext.getFlowAudit();
        return flowAudit.getAuditNode().stream()
                .filter(i -> AuditState.AUDITING.equals(i.getState())).collect(Collectors.toList());
    }

    /**
     * 得到流审计链类型
     * 获取审核链,只需设置审核链类型
     *
     * @return {@link FlowAuditChain}
     */
    private FlowAuditChain getFlowAuditChainType(FlowContext flowContext) {
        return FlowAuditChain.builder().chainType(flowContext.getFlowAudit().getAuditChainType()).build();
    }

    /**
     * 构建审核结果
     *
     * @return {@link AuditResult}
     */
    private AuditResult buildAuditResult(AuditState auditState, ProcessContext processContext, SchedulerEventContext eventContext) {
         //事件入参
        FlowAuditorOperation auditorOperation = eventContext.getEventInput(FlowAuditorOperation.class);

         //审核结果
        return AuditResult.builder()
                .auditState(auditState)
                .auditChain(getFlowAuditChainType(processContext.getFlowContext()))
                .operation(auditorOperation).build();
    }
}
