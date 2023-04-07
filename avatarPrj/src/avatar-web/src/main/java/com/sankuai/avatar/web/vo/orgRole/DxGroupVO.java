package com.sankuai.avatar.web.vo.orgRole;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author caoyang
 * @create 2022-11-11 14:04
 */
@Data
public class DxGroupVO {
    /**
     * 群 id
     */
    @NotBlank(message = "群id不能为空")
    private String groupId;

    /**
     * 群名称
     */
    @NotBlank(message = "群名不能为空")
    private String groupName;

    /**
     * 群状态
     */
    private String groupStatus;

}
