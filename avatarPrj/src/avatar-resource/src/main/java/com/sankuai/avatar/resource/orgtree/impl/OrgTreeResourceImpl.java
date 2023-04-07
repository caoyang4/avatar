package com.sankuai.avatar.resource.orgtree.impl;

import com.dianping.cat.Cat;
import com.sankuai.avatar.client.soa.ScHttpClient;
import com.sankuai.avatar.client.soa.model.ScOrg;
import com.sankuai.avatar.client.soa.model.ScOrgTreeNode;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.orgtree.OrgTreeResource;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeOrgInfoBO;
import com.sankuai.avatar.resource.orgtree.transfer.OrgTreeNodeTransfer;
import com.sankuai.avatar.resource.orgtree.transfer.OrgTreeOrgInfoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * OrgTreeResource的实现类
 *
 * @author zhangxiaoning07
 * @create 2022/11/15
 **/
@Service
public class OrgTreeResourceImpl implements OrgTreeResource {

    private final ScHttpClient scHttpClient;

    @Autowired
    public OrgTreeResourceImpl(ScHttpClient scHttpClient) {
        this.scHttpClient = scHttpClient;
    }

    @Override
    public List<OrgTreeNodeBO> getOrgTree(String user) {
        List<ScOrgTreeNode> tree = scHttpClient.getOrgTreeByUser(user);
        return OrgTreeNodeTransfer.INSTANCE.batchToBO(tree);
    }

    @Override
    public OrgTreeOrgInfoBO getOrgInfo(String orgIds) {
        ScOrg org = scHttpClient.getOrg(orgIds);
        return OrgTreeOrgInfoTransfer.INSTANCE.toBO(org);
    }
}
