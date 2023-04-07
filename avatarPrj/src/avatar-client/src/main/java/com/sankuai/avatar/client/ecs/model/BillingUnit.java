package com.sankuai.avatar.client.ecs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * ecs BillingUnit
 *
 * @author qinwei05
 */
@Data
@Builder
public class BillingUnit {

    /**
     * 结算单元ID
     */
    @JsonProperty("billing_unit_id")
    private Integer billingUnitId;

    /**
     * 结算单元名称
     */
    @JsonProperty("billing_unit_name")
    private String billingUnitName;

    /**
     * 结算单元名称
     */
    @JsonProperty("server_product")
    private String serverProduct;
}
