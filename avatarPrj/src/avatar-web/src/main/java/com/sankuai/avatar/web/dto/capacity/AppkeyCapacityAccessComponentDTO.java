package com.sankuai.avatar.web.dto.capacity;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-04 17:54
 */
@Data
public class AppkeyCapacityAccessComponentDTO {
    /**
     * 组件
     */
    private String name;
    /**
     * 组件中文名
     */
    private String cName;
    /**
     * 是否接入
     */
    private Boolean access;
}
