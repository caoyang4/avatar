package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.dao.workflow.repository.FlowAuditNodeRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowAuditRecordRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowAuditNodeEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditNodeAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditNodeUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditRecordAddRequest;
import com.sankuai.avatar.workflow.core.auditer.AuditResult;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainNode;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditorOperation;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.listener.FlowListen;
import com.sankuai.avatar.workflow.core.engine.listener.ListenFlowState;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 审核结果监听器，持久化审核链路，更新审核操作记录
 *
 * @author zhaozhifan02
 */
@Slf4j
@Component
@ListenFlowState({FlowState.AUDITING, FlowState.AUDIT_ACCEPTED, FlowState.AUDIT_REJECTED, FlowState.AUDIT_CANCELED})
public class AuditResultFlowListen implements FlowListen {

    /**
     * 审核人连接符
     */
    private static final String AUDITOR_DELIMITER = ",";

    @Autowired
    private FlowAuditNodeRepository flowAuditNodeRepository;

    @Autowired
    private FlowAuditRecordRepository flowAuditRecordRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveEvents(FlowContext flowContext, FlowState flowState) {
        AuditResult auditResult = flowContext.getCurrentProcessContext().getResponse().getResult(AuditResult.class);
        if (auditResult == null) {
            return;
        }

        // 更新审核节点
        updateFlowAuditNode(flowContext, auditResult);
        // 更新审核操作记录
        updateFlowAuditRecord(auditResult);
    }

    /**
     * 更新流程审核节点
     *
     * @param flowContext 流上下文
     * @param auditResult 审计结果
     */
    private void updateFlowAuditNode(FlowContext flowContext, AuditResult auditResult) {
        List<FlowAuditNodeEntity> entityList = flowAuditNodeRepository.queryAuditNode(flowContext.getId());
        if (CollectionUtils.isEmpty(entityList)) {
            // 不存在则新增
            FlowAuditChain auditChain = auditResult.getAuditChain();
            if (auditChain == null || CollectionUtils.isEmpty(auditChain.getChainNodes())) {
                return;
            }
            for (FlowAuditChainNode chainNode : auditChain.getChainNodes()) {
                FlowAuditNodeAddRequest request = buildAuditNodeAddRequest(flowContext, chainNode);
                request.setAuditType(auditChain.getChainType().getCode());
                boolean status = flowAuditNodeRepository.addAuditNode(request);
                log.info("Add flowAuditNode: {}, result: {}", request, status);
            }
            return;
        }
        // 更新审核节点状态
        FlowAuditNodeUpdateRequest request = buildAuditNodeUpdateRequest(auditResult);
        boolean status = flowAuditNodeRepository.updateAuditNode(request);
        log.info("Update flowAuditNode: {}, result: {}", request, status);
    }

    /**
     * 更新操作记录
     *
     * @param auditResult {@link AuditResult}
     */
    private void updateFlowAuditRecord(AuditResult auditResult) {
        FlowAuditorOperation auditorOperation = auditResult.getOperation();
        if (auditorOperation == null || auditorOperation.getAuditNodeId() == null) {
            return;
        }
        FlowAuditRecordAddRequest request = FlowAuditRecordAddRequest.builder()
                .auditNodeId(auditorOperation.getAuditNodeId())
                .auditOperation(auditorOperation.getOperationType().getCode())
                .auditOperationName(auditorOperation.getOperationType().getValue())
                .auditor(auditorOperation.getAuditor())
                .comment(auditorOperation.getComment())
                .operateTime(new Date())
                .build();
        flowAuditRecordRepository.addAuditRecord(request);
    }

    /**
     * 构建添加审核节点请求
     *
     * @param flowContext 流上下文
     * @param chainNode   链节点
     * @return {@link FlowAuditNodeAddRequest}
     */
    private FlowAuditNodeAddRequest buildAuditNodeAddRequest(FlowContext flowContext, FlowAuditChainNode chainNode) {

        return FlowAuditNodeAddRequest.builder()
                .name(chainNode.getName())
                .seq(chainNode.getSeq())
                .auditor(String.join(AUDITOR_DELIMITER, chainNode.getAuditors()))
                .state(chainNode.getState().getCode())
                .stateName(chainNode.getState().getStateName())
                .flowId(flowContext.getId())
                .auditType(chainNode.getApproveType().getCode())
                .build();
    }

    /**
     * 构建更新审核节点请求
     *
     * @param auditResult {@link FlowAuditChainNode}
     * @return {@link FlowAuditNodeUpdateRequest}
     */
    private FlowAuditNodeUpdateRequest buildAuditNodeUpdateRequest(AuditResult auditResult) {
        FlowAuditorOperation auditorOperation = auditResult.getOperation();
        return FlowAuditNodeUpdateRequest.builder()
                .id(auditorOperation.getAuditNodeId())
                .state(auditResult.getAuditState().getCode())
                .stateName(auditResult.getAuditState().getStateName())
                .build();
    }
}
