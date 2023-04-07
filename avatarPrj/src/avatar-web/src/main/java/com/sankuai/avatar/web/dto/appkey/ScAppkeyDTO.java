package com.sankuai.avatar.web.dto.appkey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * sc appkey
 *
 * @author qinwei05
 * @date 2022/11/17
 */
@Data
public class ScAppkeyDTO {
    /**
     * 服务名称
     */
    private String appKey;

    /**
     * 描述
     */
    private String description;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 服务Owner
     */
    private Admin admin;

    /**
     * 团队
     */
    private Team team;

    /**
     * 类别
     */
    private List<String> categories;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 服务框架
     */
    private List<String> frameworks;

    /**
     * 服务是否外采
     * 1.TRUE
     * 2.FALSE
     * 3.UNCERTAIN（不确定）
     */
    private String isBoughtExternal;

    /**
     * 类型
     */
    private String type;

    /**
     * 结算单元
     */
    private String billingUnit;

    /**
     * 结算单元id
     */
    private Integer billingUnitId;


    /**
     * 服务团队
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Team {
        private String id;
        private String name;
        private Boolean hasChild;
        private String displayName;
        private String orgIdList;
    }

    /**
     * 服务团队
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Admin {
        private String mis;
        private String name;
        private Boolean onJob;
    }
}
