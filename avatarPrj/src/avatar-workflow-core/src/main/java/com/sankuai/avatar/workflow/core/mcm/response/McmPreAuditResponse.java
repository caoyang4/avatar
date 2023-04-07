package com.sankuai.avatar.workflow.core.mcm.response;

import com.sankuai.mcm.client.sdk.context.handler.AuditChain;
import lombok.Data;

/**
 * MCM审核结果
 *
 * @author zhaozhifan02
 */
@Data
public class McmPreAuditResponse {

    /**
     * 事件ID
     */
    private Integer id;

    /**
     * 是否可忽略
     */
    private Boolean shouldIgnore;

    /**
     * 审核链
     */
    private AuditChain auditChain;

    /**
     * MCM 审核链接
     */
    private String url;
}