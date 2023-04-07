package com.sankuai.avatar.client.soa.model;

import lombok.Data;

import java.util.List;

/**
 * SC接口的团队对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 **/
@Data
public class ScTeam {
    /**
     *  组织架构节点
     */
    private ScOrgTreeNode org;

    /**
     * 用户列表
     */
    private List<ScUser> users;

}
