package com.sankuai.avatar.resource.emergency.request;

import com.sankuai.avatar.common.vo.PageRequest;
import com.sankuai.avatar.resource.emergency.constant.OperationType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author caoyang
 * @create 2022-12-02 21:36
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class EmergencyResourceRequestBO extends PageRequest {

    private Integer id;

    /**
     * 流程id
     */
    private Integer flowId;
    /**
     * 流程uuid
     */
    private String flowUuid;

    /**
     * appkey
     */
    private String appkey;

    /**
     * 环境
     */
    private String env;

    /**
     * 流程模板名称
     */
    private String template;

    /**
     * 操作类型: 紧急资源新增、归还
     */
    private OperationType operationType;

    /**
     * 大于某结束时间
     */
    private Date endTimeGtn;

    /**
     * 小于某结束时间
     */
    private Date endTimeLtn;

}
