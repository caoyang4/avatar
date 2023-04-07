package com.sankuai.avatar.web.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoyang
 * @create 2023-02-13 14:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceSubscriptionOrderPageRequest extends PageRequest {

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
