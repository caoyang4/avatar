package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.web.dto.orgtree.OrgTreeNodeDTO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeOrgInfoDTO;
import com.sankuai.avatar.web.service.OrgTreeService;
import com.sankuai.avatar.web.transfer.orgtree.OrgTreeNodeTransfer;
import com.sankuai.avatar.web.transfer.orgtree.OrgTreeOrgInfoTransfer;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.orgtree.OrgTreeNodeVO;
import com.sankuai.avatar.web.vo.orgtree.OrgTreeOrgInfoVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * OrgTree的controller类
 *
 * @author zhangxiaoning07
 * @create 2022/11/16
 **/
@RestController
@RequestMapping("/api/v2/avatar/orgTree")
public class OrgTreeController {
    private final OrgTreeService orgTreeService;

    @Autowired
    public OrgTreeController(OrgTreeService orgTreeService) {
        this.orgTreeService = orgTreeService;
    }

    /**
     * 查询组织树接口
     *
     * @return 用户的组织树
     */
    @GetMapping("/user/org/tree")
    public List<OrgTreeNodeVO> getOrgTree(@RequestParam(value = "user", required = false) String user) {
        if (StringUtils.isEmpty(user)) {
            user = UserUtils.getCurrentCasUser().getLoginName();
        }
        List<OrgTreeNodeDTO> tree = orgTreeService.getOrgTree(user);
        return OrgTreeNodeTransfer.INSTANCE.batchToVO(tree);
    }

    /**
     * 查询组织信息接口
     *
     * @param orgIds 逗号隔开的部门节点id的字符串
     * @return 组织信息
     */
    @GetMapping("/org")
    public OrgTreeOrgInfoVO getOrgInfo(@RequestParam() String orgIds) {
        OrgTreeOrgInfoDTO orgInfoDTO = orgTreeService.getOrgInfo(orgIds);
        return OrgTreeOrgInfoTransfer.INSTANCE.toVO(orgInfoDTO, orgInfoDTO.getLeader());
    }


}
