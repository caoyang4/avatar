package com.sankuai.avatar.client.dayu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Dayu业务分组对象属性
 *
 * @author qinwei05
 * @date 2023/03/19
 */
@Data
public class DayuGroupTag {

    /**
     * id
     */
    private Integer id;

    /**
     * 业务分组名称
     */
    private String groupTagsName;

    /**
     * 业务分组别名
     */
    private String groupTagsAlias;

    /**
     * 业务分组
     */
    private String groupTags;

    /**
     * owt
     */
    private String owt;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
