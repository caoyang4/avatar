package com.sankuai.avatar.client.dx.model;

import lombok.Data;

/**
 * dx 用户信息
 * @author caoyang
 * @create 2022-10-25 16:08
 */
@Data
public class DxUser {
    /**
     * mis号
     */
    private String mis;
    /**
     * 中文名
     */
    private String name;
    /**
     * dx头像
     */
    private String avatarUrl;
}
