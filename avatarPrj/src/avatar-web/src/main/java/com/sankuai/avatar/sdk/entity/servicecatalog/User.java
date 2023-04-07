package com.sankuai.avatar.sdk.entity.servicecatalog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Jie.li.sh
 * @create 2020-02-16
 **/

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    /**
     * mis账号
     */
    private String mis;
    /**
     * 中文名
     */
    private String name;
    /**
     * 组织架构
     */
    private Org org;
    /**
     * 头像链接
     */
    private String avatarUrl;
}
