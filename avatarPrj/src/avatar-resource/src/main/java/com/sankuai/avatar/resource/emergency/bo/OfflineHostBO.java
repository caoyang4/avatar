package com.sankuai.avatar.resource.emergency.bo;

import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-02 21:22
 */
@Data
public class OfflineHostBO {

    /**
     * 环境
     */
    private String env;

    /**
     * appkey
     */
    private String appkey;

    /**
     * 服务名称
     */
    private String displaySrv;

    /**
     * 原因
     */
    private String reason;

    /**
     * 下线机器信息
     */
    private List<EmergencyHostBO> hosts;
}
