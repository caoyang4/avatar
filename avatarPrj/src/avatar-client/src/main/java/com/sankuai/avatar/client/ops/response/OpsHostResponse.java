package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsHost;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author qinwei
 **/
@Data
@EqualsAndHashCode(callSuper=true)
public class OpsHostResponse extends OpsResponse{
    /**
     * 主机数据
     */
    private OpsHost host;
}
