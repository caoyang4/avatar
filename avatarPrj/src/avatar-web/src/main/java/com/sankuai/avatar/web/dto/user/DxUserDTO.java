package com.sankuai.avatar.web.dto.user;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-14 11:14
 */
@Data
public class DxUserDTO {
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
