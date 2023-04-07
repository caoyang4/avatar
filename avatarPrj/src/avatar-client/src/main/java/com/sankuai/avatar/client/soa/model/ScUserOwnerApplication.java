package com.sankuai.avatar.client.soa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户所在部门下应用
 *
 * @author qinwei05
 * @date 2023/02/09
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScUserOwnerApplication {

    /**
     * 应用程序名称
     */
    private String applicationName;

    /**
     * 名字
     */
    private String name;

    /**
     * 管理员
     */
    private String admin;

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

