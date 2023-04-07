package com.sankuai.avatar.resource.capacity.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 组件接入信息
 * @author caoyang
 * @create 2022-11-03 16:05
 */
@Data
public class AppkeyCapacityAccessComponentBO {

    /**
     * 组件
     */
    private String name;
    /**
     * 组件中文名
     */
    @JsonProperty(value="cName")
    private String cName;
    /**
     * 是否接入
     */
    private Boolean access;
}
