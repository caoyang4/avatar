package com.sankuai.avatar.resource.octo.model;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-12-13 14:45
 */
@Data
public class OctoProviderGroupBO {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 1:同机房(同可用区)
     * 5:同城市(同地域)
     */
    private Integer category;

    /**
     * -1:同城市(同地域)
     * 0: 同机房(同可用区)
     * 1: 同中心
     */
    private Integer priority;

    /**
     * 1:启用 0:禁用
     */
    private Integer status;

    /**
     * 0:非强制 1:强制
     */
    private Integer force;

}
