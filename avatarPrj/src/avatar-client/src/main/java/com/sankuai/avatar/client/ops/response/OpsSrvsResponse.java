package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsSrv;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author qinwei
 **/
@Data
@EqualsAndHashCode(callSuper=true)
public class OpsSrvsResponse extends OpsResponse {
    /**
     * 服务树节点数据
     */
    private List<OpsSrv> srvs;
}
