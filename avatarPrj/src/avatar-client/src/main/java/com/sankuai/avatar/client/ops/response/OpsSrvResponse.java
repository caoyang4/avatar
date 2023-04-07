package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsOwt;
import com.sankuai.avatar.client.ops.model.OpsPdl;
import com.sankuai.avatar.client.ops.model.OpsSrv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhaozhifan02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OpsSrvResponse extends OpsResponse {
    /**
     * 服务树节点数据
     */
    private OpsSrv srv;

    /**
     * 服务树OWT
     */
    private OpsOwt owt;

    /**
     * 服务树PDL
     */
    private OpsPdl pdl;
}
