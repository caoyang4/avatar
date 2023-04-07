package com.sankuai.avatar.web.vo.emergency;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.web.dto.emergency.HostIdcDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-30 16:43
 */
@Data
public class EmergencyOnlineVO {

    /**
     * 环境
     */
    private String env;

    /**
     * 服务
     */
    private String appkey;

    /**
     * 流程名称
     */
    private String template;

    /**
     * set
     */
    private String set;

    /**
     * 资源池
     */
    private String channel;

    /**
     * 资源池中文名称
     */
    private String channelCn;

    /**
     * 集群
     */
    private String cluster;

    /**
     * 集群中文名
     */
    private String clusterCn;

    /**
     * 城市
     */
    private String city;

    /**
     * 地域
     */
    private String region;

    /**
     * 机器总数量
     */
    private Integer count;

    /**
     * CPU大小
     */
    private Integer cpu;

    /**
     * 内存大小
     */
    private Integer memory;

    /**
     * 磁盘大小
     */
    private Integer disk;

    /**
     * 磁盘类型
     */
    private String diskType;

    /**
     * 磁盘类型中文名称
     */
    private String diskTypeCn;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 机器配置的其他描述信息
     */
    private String configExtraInfo;

    /**
     * 流程 uuid
     */
    private String flowUuid;

    /**
     * 流程 id
     */
    private Integer flowId;

    private List<HostIdcDTO> idcs;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date onlineDate;

}
