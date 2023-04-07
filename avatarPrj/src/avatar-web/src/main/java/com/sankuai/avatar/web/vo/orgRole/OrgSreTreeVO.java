package com.sankuai.avatar.web.vo.orgRole;

import com.sankuai.avatar.web.vo.user.DxUserVO;
import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-13 19:38
 */
@Data
public class OrgSreTreeVO {

    /**
     * orgId
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * appkey数量
     */
    private Integer appkeyCount;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * org路径
     */
    private String orgPath;

    /**
     * 用户角色
     */
    private String roleUsers;

    /**
     * 子组织配置
     */
    private List<OrgSreTreeVO> children;

    /**
     * 运维负责人
     */
    private List<DxUserVO> opAdmins;

}
