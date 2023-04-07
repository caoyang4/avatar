package com.sankuai.avatar.web.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人员权限 vo 对象
 * @author caoyang
 * @create 2023-03-22 13:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionVO {

    /**
     * 权限描述
     */
    private String msg;

    /**
     * 是否是 sre
     */
    private Boolean sre;
}
