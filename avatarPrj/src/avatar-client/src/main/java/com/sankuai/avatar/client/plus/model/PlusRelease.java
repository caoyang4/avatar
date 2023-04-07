package com.sankuai.avatar.client.plus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/10/7 16:30
 * @version 1.0
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlusRelease {
    /**
     * 发布项id
     */
    private int id;
    /**
     * 发布项名称
     */
    private String name;
    /**
     * 发布项地址
     */
    private String repository;
    /**
     * 绑定的Appkey
     */
    private String bindAppKey;
    /**
     * 发布项类型; 通用general，其他 maven/1-maven/crane/nest/plugin
     */
    @JsonProperty("release_type")
    private String releaseType;

}
