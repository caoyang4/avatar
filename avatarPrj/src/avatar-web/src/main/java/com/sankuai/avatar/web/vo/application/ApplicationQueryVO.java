package com.sankuai.avatar.web.vo.application;

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
public class ApplicationQueryVO {

    /**
     * 应用id
     */
    private Integer id;

    /**
     * 应用程序名称
     */
    private String applicationName;

    /**
     * 应用程序名称
     */
    private String applicationChName;

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
}

