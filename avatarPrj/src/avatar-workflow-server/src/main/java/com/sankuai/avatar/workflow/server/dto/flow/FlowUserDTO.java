package com.sankuai.avatar.workflow.server.dto.flow;

import lombok.Builder;
import lombok.Data;

/**
 * @author caoyang
 * @create 2023-02-21 15:15
 */
@Data
@Builder
public class FlowUserDTO {

    /**
     * 人员中文姓名
     */
    private String name;

    /**
     * dx登录 mis
     */
    private String loginName;

    /**
     * 人员来源分为 MT(美团)，DP(点评)
     */
    private String source;

    /**
     * 部门组织名称
     */
    private String organization;

    /**
     * 人员角色
     */
    private String role;

    /**
     * 上级 leader
     */
    private String leader;

    /**
     * 用户图像
     */
    private String userImage;

    /**
     * 人员所在部门编号
     */
    private String orgId;

    /**
     * org部门全路径编号
     */
    private String orgPath;

    /**
     * 在职状态：在职/离职
     */
    private String jobStatus;

}
