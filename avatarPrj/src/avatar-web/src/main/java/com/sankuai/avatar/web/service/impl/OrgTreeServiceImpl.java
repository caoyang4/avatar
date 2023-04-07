package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.resource.orgtree.OrgTreeResource;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeOrgInfoBO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeNodeDTO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeOrgInfoDTO;
import com.sankuai.avatar.web.service.OrgTreeService;
import com.sankuai.avatar.web.transfer.orgtree.OrgTreeNodeTransfer;
import com.sankuai.avatar.web.transfer.orgtree.OrgTreeOrgInfoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OrgTree的Service实现
 *
 * @author zhangxiaoning07
 * @create 2022/11/16
 **/
@Service
public class OrgTreeServiceImpl implements OrgTreeService {
    private final OrgTreeResource orgTreeResource;

    @Autowired
    public OrgTreeServiceImpl(OrgTreeResource orgTreeResource){
        this.orgTreeResource = orgTreeResource;
    }

    @Override
    public List<OrgTreeNodeDTO> getOrgTree(String user) {
        List<OrgTreeNodeBO> tree = orgTreeResource.getOrgTree(user);
        return OrgTreeNodeTransfer.INSTANCE.batchToDTO(tree);
    }

    @Override
    public OrgTreeOrgInfoDTO getOrgInfo(String orgIds) {
        OrgTreeOrgInfoBO orgInfo = orgTreeResource.getOrgInfo(orgIds);
        return OrgTreeOrgInfoTransfer.INSTANCE.toDTO(orgInfo, orgInfo.getLeader());
    }
}
