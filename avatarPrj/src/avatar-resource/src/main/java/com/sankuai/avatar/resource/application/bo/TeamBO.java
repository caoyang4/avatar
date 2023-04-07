package com.sankuai.avatar.resource.application.bo;

import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeUserBO;
import lombok.Data;

import java.util.List;

/**
 * 团队BO
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 **/
@Data
public class TeamBO {
    /**
     *  组织架构节点
     */
    private OrgTreeNodeBO org;

    /**
     * 用户列表
     */
    private List<OrgTreeUserBO> users;

}
