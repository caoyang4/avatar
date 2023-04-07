package com.sankuai.avatar.workflow.server.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-02-20 17:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlowPageRequest extends PageRequest {

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

    private String reason;

    private String fuzzy;

    /**
     * 流程发起起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTimeGt;

    /**
     * 流程发起截止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTimeLt;

    /**
     * 是否从 db 数据源查询
     */
    private Boolean dbSource;

    /**
     * 是否状态排序
     */
    private Boolean stateSort;

}
