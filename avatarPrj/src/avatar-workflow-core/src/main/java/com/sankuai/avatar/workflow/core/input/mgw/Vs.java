package com.sankuai.avatar.workflow.core.input.mgw;

import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
public class Vs {
    /**
     * VIP
     */
    String vip;

    /**
     * VS 端口
     */
    Integer vport;

    /**
     * 协议 TCP、UDP
     */
    String protocol;
}
