package com.sankuai.avatar.resource.emergency.bo;

import com.sankuai.avatar.resource.emergency.constant.OperationType;
import lombok.Data;

import java.util.Date;

/**
 * 紧急资源 BO 对象
 * @author caoyang
 * @create 2022-12-02 21:10
 */
@Data
public class EmergencyResourceBO {

    /**
     * 主键 id
     */
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
     * 新增机器的配置数据
     */
    private OnlineHostBO hostConfig;

    /**
     * 下线机器信息
     */
    private OfflineHostBO offlineHost;

    /**
     * 紧急资源新增、归还
     */
    private OperationType operationType;

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
