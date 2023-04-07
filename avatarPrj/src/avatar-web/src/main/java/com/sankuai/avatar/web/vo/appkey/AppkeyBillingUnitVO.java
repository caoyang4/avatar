package com.sankuai.avatar.web.vo.appkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务结算单元对象
 *
 * @author qinwei05
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyBillingUnitVO {

    /**
     * 结算单元ID
     */
    private Integer billingUnitId;

    /**
     * 结算单元名称
     */
    private String billingUnitName;
}
