package com.sankuai.avatar.client.mgw.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * mgw集群
 *
 * @author qinwei05
 * @date 2022/12/25 20:02
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MgwCluster {

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 机房编号
     */
    private Integer idc;

    /**
     * 名字
     */
    private String name;

    /**
     * 地区
     */
    private String region;

    /**
     * idc名字
     */
    @JsonProperty("idc_name")
    private String idcName;

    /**
     * createtime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /**
     * updatetime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    /**
     * 会话同步
     */
    @JsonProperty("session_sync")
    private String sessionSync;

    /**
     * id
     */
    private Integer id;

    /**
     * 集群是否支持anycast
     */
    private String anycast;

    /**
     * ipv6
     */
    private String ipv6;

    /**
     * anycast az信息，多个az名字以','分割，例如: YP,JD
     */
    private String az;

    /**
     * 网段
     */
    private String segment;

    /**
     * 网络
     */
    private String network;

}
