package com.sankuai.avatar.resource.orgtree.bo;

import lombok.Builder;
import lombok.Data;

/**
 * OrgTreeUser的BO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/15
 **/
@Data
@Builder
public class OrgTreeUserBO {
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
    private OrgTreeNodeBO org;
}