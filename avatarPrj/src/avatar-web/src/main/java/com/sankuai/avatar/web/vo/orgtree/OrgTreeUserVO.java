package com.sankuai.avatar.web.vo.orgtree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 组织树用户VO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/16
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrgTreeUserVO {
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

    /**
     * 组织架构名称
     */
    private OrgTreeNodeVO org;
}
