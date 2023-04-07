package com.sankuai.avatar.client.ops.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sankuai.avatar.client.ops.model.OpsSrvDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-29 18:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OpsAvatarSrvsResponse extends OpsResponse{

    /**
     * 总数目
     */
    private Integer total;

    /**
     * 每页数目
     */
    private Integer count;

    /**
     * 服务树节点
     */
    private List<OpsSrvDetail> srvs;
}
