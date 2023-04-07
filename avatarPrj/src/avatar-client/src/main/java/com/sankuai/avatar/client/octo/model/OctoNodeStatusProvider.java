package com.sankuai.avatar.client.octo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author qinwei05
 */
@Data
public class OctoNodeStatusProvider {

    /**
     * ip
     */
    private String ip;

    /**
     * 节点端口
     */
    private Integer port;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateTime;
}
