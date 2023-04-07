package com.sankuai.avatar.client.ops.model;

import com.sankuai.avatar.client.ops.response.OpsResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * OPS树返回体
 *
 * @author qinwei05
 * @date 2022/12/18
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OpsTree extends OpsResponse {

    /**
     * srv
     */
    private OpsSrv srv;

    /**
     * pdl
     */
    private OpsPdl pdl;

    /**
     * owt
     */
    private OpsOwt owt;

    /**
     * 集团
     */
    private OpsBg corp;
}
