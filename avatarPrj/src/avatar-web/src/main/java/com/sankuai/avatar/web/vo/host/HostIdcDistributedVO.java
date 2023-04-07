package com.sankuai.avatar.web.vo.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Appkey下主机机房分布
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostIdcDistributedVO {

    /**
     * 服务机房分布数目
     */
    private Integer idcTotal;

    /**
     * 服务机房分布信息
     */
    private List<IdcVO> idcList;

    /**
     * 服务机器总数
     */
    private Integer total;

}
