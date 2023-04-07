package com.sankuai.avatar.client.mcm.request;

import com.sankuai.avatar.client.mcm.model.PeriodConfig;
import lombok.Builder;
import lombok.Data;

/**
 * 创建高峰期解禁记录请求对象
 *
 * @author zhaozhifan02
 */
@Data
@Builder
public class CreateBusyPeriodRequest {
    /**
     * 服务 appkey
     */
    private String configKey;

    /**
     * 高峰期记录维度
     * - 服务：APPKEY
     */
    private String configType;

    /**
     * 高峰期记录是否生效:ONLINE、OFFLINE
     */
    private String status;

    /**
     * 高峰期记录是否覆盖：True、False
     */
    private Boolean covered;

    /**
     * 高峰期记录类型: 解禁OPEN
     */
    private String actionType;

    /**
     * 高峰期时间配置：临时解禁2小时、长期解禁
     */
    private PeriodConfig periodConfig;

    /**
     * 申请人
     */
    private String createUser;

    /**
     * 描述
     */
    private String description;
}
