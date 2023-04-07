package com.sankuai.avatar.dao.workflow.repository.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-02-20 15:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlowQueryRequest extends PageRequest {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 模板Id
     */
    private Integer templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 流程类型
     */
    private String flowType;

    /**
     * 流程状态
     */
    private String status;

    /**
     * 审核状态
     */
    private String approveStatus;

    /**
     * 审核人
     */
    private String approveUsers;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 服务 appKey
     */
    private String appkey;

    /**
     * 环境
     */
    private String env;

    /**
     * 队列任务类型
     * squirrelQueue、dbQueue
     */
    private String type;

    /**
     * 流程发起起始时间
     */
    private Date startTimeGt;

    /**
     * 流程发起截止时间
     */
    private Date startTimeLt;

    /**
     * 是否状态排序
     */
    private Boolean stateSort;
}
