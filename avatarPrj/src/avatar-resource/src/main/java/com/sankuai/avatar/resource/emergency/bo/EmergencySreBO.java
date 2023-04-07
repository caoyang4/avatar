package com.sankuai.avatar.resource.emergency.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-01-20 15:37
 */
@Data
public class EmergencySreBO {

    private Integer id;

    /**
     * 来源ID
     */
    private Integer sourceId;

    /**
     *  服务
     */
    private String appkey;

    /**
     *  SRE
     */
    private String opAdmin;

    /**
     *  临时新增SRE
     */
    private String attachAdmin;

    /**
     *  次数
     */
    private Integer time;

    /**
     *  状态
     */
    private String state;

    /**
     *  创建人
     */
    private String createUser;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date updateTime;
}
