package com.sankuai.avatar.resource.orgRole.bo;

import lombok.Data;

/**
 * 部门大象群 BO
 * @author caoyang
 * @create 2022-11-10 15:16
 */
@Data
public class DxGroupBO {
    /**
     * 群 id
     */
    private String groupId;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群状态
     */
    private String groupStatus;

    /**
     * 群信息更新人员
     */
    private String updateUser;
}
