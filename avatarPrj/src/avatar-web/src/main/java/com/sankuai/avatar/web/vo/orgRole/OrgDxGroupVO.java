package com.sankuai.avatar.web.vo.orgRole;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * org 大象群
 * @author caoyang
 * @create 2022-11-11 17:47
 */
@Data
public class OrgDxGroupVO {

    /**
     * org id
     */
    @NotBlank(message = "orgId不能为空")
    private String orgId;

    /**
     * org路径
     */
    private String orgPath;

    /**
     * 组织名字
     */
    private String orgName;

    /**
     * dx群列表
     */
    @NotNull(message = "大象群信息不能为空")
    List<DxGroupVO> dxGroupList;
}
