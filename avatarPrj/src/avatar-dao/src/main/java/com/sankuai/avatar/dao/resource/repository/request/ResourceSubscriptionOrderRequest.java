package com.sankuai.avatar.dao.resource.repository.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author caoyang
 * @create 2023-02-10 14:51
 */
@Data
@Builder
public class ResourceSubscriptionOrderRequest {

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
     * 区域
     */
    private String region;

    /**
     * 机房
     */
    private String idc;

    /**
     * 结算单元
     */
    private String unit;

    /**
     * 状态
     */
    private String status;

}
