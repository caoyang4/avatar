package com.sankuai.avatar.web.dto.emergency;

import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-30 17:14
 */
@Data
public class OfflineHostDTO {

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
    private List<EmergencyHostDTO> hosts;

}
