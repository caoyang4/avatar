package com.sankuai.avatar.dao.resource.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 紧急资源对象
 * @author caoyang
 * @create 2022-11-25 23:10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "resource_host_emergency")
public class EmergencyResourceDO {

    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
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
     * 流程模板名称
     */
    private String template;

    /**
     * 机器数量
     */
    private Integer count;

    /**
     * 机器配置
     */
    private String hostConfig;

    /**
     * 下线机器信息
     */
    private String offlineHost;

    /**
     * 操作类型: 紧急资源新增、归还
     */
    private String operationType;

    /**
     * 申请人
     */
    private String createUser;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
