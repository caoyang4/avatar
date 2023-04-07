package com.sankuai.avatar.client.soa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SC检索返回应用对象
 *
 * @author qinwei05
 * @date 2023/02/09
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScQueryApplication {

    /**
     * 应用id
     */
    private Integer id;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 应用中文名称
     */
    private String applicationChName;

    /**
     * 描述
     */
    private String description;

    /**
     * 管理员
     */
    private Admin admin;

    /**
     * 结算单元名称
     */
    private String billingUnit;

    /**
     * 结算单元id
     */
    private Integer billingUnitId;

    /**
     * 产品名称
     */
    private String costProduct;

    /**
     * 产品id
     */
    private Integer costProductId;

    /**
     * 类型
     */
    private String type;

    /**
     * 反馈链接
     */
    private String feedbackLink;

    /**
     * 是否为PaaS应用
     */
    private Boolean isPaas;

    @Data
    public static class Admin {
        private String mis;
        private String name;
    }
}

