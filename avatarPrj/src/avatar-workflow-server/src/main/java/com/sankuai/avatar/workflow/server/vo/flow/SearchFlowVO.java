package com.sankuai.avatar.workflow.server.vo.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhaozhifan02
 */
@Data
@Builder
public class SearchFlowVO {
    /**
     * 流程Id
     */
    private Integer id;

    /**
     * 流程 UUID
     */
    private String applyId;

    /**
     * 模板名称
     */
    private String applyName;

    /**
     * 服务 appKey
     */
    private String appKey;

    /**
     * 流程状态
     */
    private String status;

    /**
     * 流程审批状态
     */
    private String approveStatus;

    /**
     * 审批类型
     * 或签：0
     * 会签：1
     */
    private Integer approveType;

    /**
     * 审批人
     */
    private String approveUsers;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 待操作人员
     */
    private String operatingUsers;

    /**
     * 已操作人员
     */
    private String operatedUsers;

    /**
     * 环境
     */
    private String env;

    /**
     * 别名
     */
    private String cname;

    /**
     * 开始时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 原因
     */
    private String reason;

    /**
     * 备注
     */
    private String comment;

    /**
     * 关键字
     */
    private List<String> keyword;
}

