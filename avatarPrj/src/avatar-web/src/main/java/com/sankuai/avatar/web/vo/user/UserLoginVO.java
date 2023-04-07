package com.sankuai.avatar.web.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录的信息
 * @author caoyang
 * @create 2023-03-20 11:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVO {

    /**
     * dx登录 登录名称
     */
    private String loginName;

    /**
     * 人员中文姓名
     */
    private String name;

    /**
     * 部门组织名称
     */
    private String organization;

    /**
     * 用户图像
     */
    private String avatarUrl;

}
