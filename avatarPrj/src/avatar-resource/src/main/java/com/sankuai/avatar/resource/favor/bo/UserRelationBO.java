package com.sankuai.avatar.resource.favor.bo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2022-12-27 16:07
 */
@Builder
@Data
public class UserRelationBO {

    private Integer id;

    /**
     * dx登录 mis
     */
    private String loginName;

    /**
     * 标签
     */
    private String tag;

    /**
     * appkey服务名称
     */
    private String appkey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
