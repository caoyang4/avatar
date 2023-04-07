package com.sankuai.avatar.sdk.entity.servicecatalog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 * @create 2020-2-21
 **/

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppKey {
    /**
     * appkey 名称，唯一标识
     */
    private String appKey;
    /**
     * 服务描述
     */
    private String comment;
    /**
     * 服务等级
     */
    private String serviceLevel;

    /**
     * 服务详细描述
     */
    private String description;

    /**
     * 扩容配置
     */
    private Map<String, Object> scaleConfig;

    /**
     * 服务类型: 例如 Code: Java
     */
    private String serviceType;

    /**
     * 预装软件:
     */
    private String containerType;

    /**
     * 是否为有服务状态
     */
    private Boolean stateful;

    /**
     * 服务有状态的原因
     */
    private String statefulReason;

    /**
     * 是否兼容IPv6
     */
    private Boolean compatibleIpv6;

    /**
     * 主机数量
     */
    private Integer host;

    /**
     * 容灾等级
     */
    private Byte capacity;

    /**
     * 容灾等级判定描述
     */
    private String capacityReason;

    /**
     * 容灾等级更新时间
     */
    private Date capacityUpdateAt;

    /**
     * 容灾等级更新人
     */
    private String capacityUpdateBy;

    /**
     * 应用名
     */
    private String applicationName;

    /**
     * 服务归属团队
     */
    private Org team;

    /**
     * 服务负责人
     */
    private String rdAdmin;

    /**
     * 运维负责人
     */
    private String opAdmin;

    /**
     * 测试负责人
     */
    private String epAdmin;

    /**
     * 服务关注人
     */
    private String dutyAdmin;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 服务创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getOrgIds() {
        List<String> ancestorsIds = team.getAncestors().stream().map(Org::getId).map(Objects::toString).collect(Collectors.toList());
        ancestorsIds.add(team.getId().toString());
        return String.join(",", ancestorsIds);
    }
}
