package com.sankuai.avatar.client.ops.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * OPS的Pdl数据对象
 *
 * @author zhangxiaoning07
 * @create 2022/10/31
 **/
@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpsPdl {
    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * Pdl的Key,如变更管理工具是dianping.tbd.change
     */
    private String key;

    /**
     * Pdl描述
     */
    private String comment;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 运维负责人
     */
    private String opAdmin;

    /**
     * 测试负责人
     */
    private String epAdmin;

    /**
     * 开发负责人
     */
    private String rdAdmin;

}