package com.sankuai.avatar.capacity.node;

import com.sankuai.avatar.capacity.constant.IdcRegion;
import lombok.Builder;
import lombok.Data;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@Builder
@Data
public class Host {
    /**
     * 机器名
     */
    String hostName;
    /**
     * ip
     */
    String ip;
    /**
     * 机器set
     */
    String cell;
    /**
     * 所属机房
     */
    String idc;

    Integer cpuCount;

    Integer memSize;
    /**
     * 所属地域 bj or sh
     */
    IdcRegion region;
}
