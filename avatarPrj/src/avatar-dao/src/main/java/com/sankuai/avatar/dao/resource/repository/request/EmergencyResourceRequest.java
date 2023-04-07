package com.sankuai.avatar.dao.resource.repository.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 紧急资源请求对象
 * @author caoyang
 * @create 2022-11-25 23:20
 */
@Data
@Builder
public class EmergencyResourceRequest {

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
    private String operationType;

    /**
     * 大于某结束时间
     */
    private Date endTimeGtn;

    /**
     * 小于某结束时间
     */
    private Date endTimeLtn;
}
