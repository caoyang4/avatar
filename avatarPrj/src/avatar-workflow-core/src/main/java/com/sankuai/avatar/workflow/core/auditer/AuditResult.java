package com.sankuai.avatar.workflow.core.auditer;

import com.sankuai.avatar.workflow.core.auditer.chain.AuditState;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditorOperation;
import com.sankuai.avatar.workflow.core.engine.process.response.Result;
import lombok.Builder;
import lombok.Data;

/**
 * 审核执行结果
 *
 * @author zhaozhifan02
 */
@Builder
@Data
public class AuditResult extends Result {

    /**
     * 是否可跳过
     */
    private boolean shouldIgnore;

    /**
     * 当前审核状态
     */
    private AuditState auditState;

    /**
     * 审核链
     */
    private FlowAuditChain auditChain;

    /**
     * 审核动作
     */
    private FlowAuditorOperation operation;
}
