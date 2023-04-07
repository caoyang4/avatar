package com.sankuai.avatar.web.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户所有者应用程序
 *
 * @author qinwei05
 * @date 2023/02/09
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOwnerApplicationDTO {

    /**
     * 应用程序名称
     */
    private String applicationName;

    /**
     * 应用程序名称
     */
    private String applicationChName;

    /**
     * 名字
     */
    private String name;

    /**
     * 管理员
     */
    private String admin;

    /**
     * 管理员MIS
     */
    private String adminMis;

    /**
     * 管理员名称
     */
    private String adminName;

    /**
     * 结算单元名称
     */
    private String billingUnitName;

    /**
     * 结算单元id
     */
    private Integer billingUnitId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品id
     */
    private Integer productId;
}

