package com.sankuai.avatar.resource.tree.bo;

import lombok.*;

/**
 * 服务树BG对象BO
 * @author zhangxiaoning07
 * @create 2022-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyTreeBgBO {

    private Integer id;

    /**
     * BG名称
     */
    private String name;
}
