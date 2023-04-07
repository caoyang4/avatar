package com.sankuai.avatar.web.vo.appkey;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * sc appkey
 *
 * @author qinwei05
 * @date 2022/11/07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsoltAppkeyVO {

    /**
     * 服务名称
     */
    private String appKey;

    /**
     * 描述
     */
    private String description;

    /**
     * 容灾等级
     */
    private Integer capacity;

    /**
     * 主机数
     */
    private Integer host;

    /**
     * 服务Owner
     */
    private Admin admin;

    /**
     * 服务隔离环境标识
     */
    private String tenant;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 服务等级
     */
    private String serviceLevel;

    /**
     * 服务状态
     */
    private Boolean stateful;

    /**
     * 服务Owner负责人
     */
    @Data
    public static class Admin {
        private String mis;
        private String name;
    }
}
