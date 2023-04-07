package com.sankuai.avatar.web.vo;

import lombok.Data;

/**
 * @author Jie.li.sh
 * @create 2020-02-18
 **/
@Data
public class DXUserVO {
    /**
     * mis号
     */
    String mis;
    /**
     * 中文名
     */
    String name;
    /**
     * dx头像
     */
    String avatarUrl;
    /**
     * 组织架构名称
     */
    String org;
}
