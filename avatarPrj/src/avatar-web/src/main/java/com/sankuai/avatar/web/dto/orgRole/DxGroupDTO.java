package com.sankuai.avatar.web.dto.orgRole;

import lombok.Data;

/**
 * org 大象群 DTO
 * @author caoyang
 * @create 2022-11-10 20:35
 */
@Data
public class DxGroupDTO {
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
