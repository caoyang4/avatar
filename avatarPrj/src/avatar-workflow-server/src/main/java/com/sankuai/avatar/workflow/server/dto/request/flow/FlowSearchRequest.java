package com.sankuai.avatar.workflow.server.dto.request.flow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流程查询请求对象
 *
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlowSearchRequest {

    /**
     * 服务 appKey
     */
    private String appKey;

    /**
     * 模板名称
     */
    private String  template;

    /**
     * 环境
     */
    private String env;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 审核人
     */
    private String approveUser;

    /**
     * 原因
     * 模糊查询
     */
    private String reason;

    /**
     * 创建时间-开始时间
     */
    private String createTimeBegin;

    /**
     * 创建时间-结束时间
     */
    private String createTimeEnd;

    /**
     * 操作对象
     */
    private String fuzzy;
}
