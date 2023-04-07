package com.sankuai.avatar.client.ops.response;

import com.sankuai.avatar.client.ops.model.OpsHost;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author qinwei
 **/
@Data
@EqualsAndHashCode(callSuper=true)
public class OpsHostListResponse extends OpsResponse{
    /**
     * 主机数据列表
     */
    private List<OpsHost> hosts;
}
