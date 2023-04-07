package com.sankuai.avatar.web.vo.user;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-13 16:28
 */
@Data
public class DxUserVO {
    /**
     * mis号
     */
    private String mis;
    /**
     * 中文名
     */
    private String name;
    /**
     * 直属 leader
     */
    private String leader;
    /**
     * dx头像
     */
    private String avatarUrl;
    /**
     * 组织架构名称
     */
    private String org;

}
