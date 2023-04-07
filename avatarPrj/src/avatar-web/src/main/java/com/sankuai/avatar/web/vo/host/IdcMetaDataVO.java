package com.sankuai.avatar.web.vo.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ECS机房原始信息对象
 *
 * @author qinwei05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdcMetaDataVO {

    /**
     * 城市
     */
    private String city;

    /**
     * idc名字
     */
    private String idcName;

    /**
     * 地区
     */
    private String region;

    /**
     * 机房
     */
    private String idc;

    /**
     * desc
     */
    private String desc;
}
