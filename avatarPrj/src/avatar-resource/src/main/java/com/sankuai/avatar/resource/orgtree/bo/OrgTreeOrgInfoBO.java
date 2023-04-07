package com.sankuai.avatar.resource.orgtree.bo;

import lombok.Builder;
import lombok.Data;

/**
 * OrgTree组织信息BO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/10
 **/
@Data
@Builder
public class OrgTreeOrgInfoBO {
    /**
     * 服务数
     */
    private Integer appKeyCount;

    /**
     * 应用数
     */
    private Integer applicationCount;

    /**
     * 负责人
     */
    private OrgTreeUserBO leader;
}
