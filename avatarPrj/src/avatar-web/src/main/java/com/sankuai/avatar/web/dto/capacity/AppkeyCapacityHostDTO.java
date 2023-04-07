package com.sankuai.avatar.web.dto.capacity;

import com.sankuai.avatar.resource.capacity.constant.IdcRegionType;
import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-04 16:08
 */
@Data
public class AppkeyCapacityHostDTO {
    /**
     * 机器名
     */
    private String hostName;

    /**
     * ip
     */
    private String ip;

    /**
     * 机器所属set
     */
    private String cell;

    /**
     * 所属机房
     */
    private String idc;

    /**
     * 机器 cpu 核数
     */
    private Integer cpuCount;

    /**
     * 机器内存大小
     */
    private Integer memSize;

    /**
     * 所属地域
     */
    private IdcRegionType region;
}
