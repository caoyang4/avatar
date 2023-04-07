package com.sankuai.avatar.client.ecs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * ecs idc
 *
 * @author qinwei05
 * @date 2023/02/26
 */
@Data
@Builder
public class EcsIdc {

    /**
     * 城市
     */
    private String city;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * idc名字
     */
    @JsonProperty("idc_name")
    private String idcName;

    /**
     * id
     */
    private int id;

    /**
     * 地区
     */
    private String region;

    /**
     * 机房
     */
    private String idc;

    /**
     * 机器类型
     */
    private String channel;

    /**
     * desc
     */
    private String desc;

}
