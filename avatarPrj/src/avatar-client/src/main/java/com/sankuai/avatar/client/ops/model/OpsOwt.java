package com.sankuai.avatar.client.ops.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * OPS服务树OWT的DTO对象
 *
 * @author zhangxiaoning07
 * @create 2022-10-19
 */
@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpsOwt {
    /**
     * ID
     */
    private Integer id;

    /**
     * Owt的key, 主键, 例如 meituan.avatar
     */
    private String key;

    /**
     * 中文名称
     */
    private String name;

    /**
     * Owt的说明
     */
    private String comment;

    /**
     * 类型，有business, resource, paas三种类型
     */
    private String kind;
    /**
     * nginx集群
     */
    private String nginxCluster;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 所属bg
     */
    private String businessGroup;

    /**
     * sre大象群
     */
    private String sreXmGroup;

    /**
     * RD负责人, 多个以逗号分割
     */
    private String rdAdmin;

    /**
     * 运维负责人, 多个以逗号分割
     */
    private String opAdmin;

    /**
     * 测试负责人
     */
    private String epAdmin;
}
