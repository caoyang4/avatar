package com.sankuai.avatar.client.soa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * SC接口返回的Application对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/21
 **/
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScApplication {
    /**
     * id，唯一标识
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 中文名称
     */
    private String chName;

    /**
     * 是否公共服务
     */
    private Boolean isPublic;

    /**
     * 所属组织
     */
    private ScOrgTreeNode team;

    /**
     * 负责人
     */
    private ScUser admin;

    /**
     * 服务数量
     */
    private Integer appKeyCount;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}

