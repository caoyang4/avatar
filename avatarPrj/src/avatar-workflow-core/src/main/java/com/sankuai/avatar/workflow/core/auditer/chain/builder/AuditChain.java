package com.sankuai.avatar.workflow.core.auditer.chain.builder;

import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.context.FlowContext;

/**
 * 审核链构建接口
 *
 * @author zhaozhifan02
 */
public interface AuditChain {
    /**
     * 构建审核链
     *
     * @param flowContext {@link FlowContext}
     * @return {@link FlowAuditChain}
     */
    FlowAuditChain build(FlowContext flowContext);
}
