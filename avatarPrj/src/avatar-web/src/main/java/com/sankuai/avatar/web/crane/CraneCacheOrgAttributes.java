package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.capacity.util.GsonUtils;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import com.sankuai.avatar.resource.orgRole.OrgRoleAdminResource;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.sdk.entity.servicecatalog.Org;
import com.sankuai.avatar.sdk.entity.servicecatalog.OrgInfo;
import com.sankuai.avatar.sdk.manager.ServiceCatalogHttpClient;
import com.sankuai.avatar.sdk.manager.ServiceCatalogOrg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Author: qinwei05
 * @Date: 2021/12/06 18:25
 */

@CraneConfiguration
@Component
@Slf4j
public class CraneCacheOrgAttributes {

    private static final String ORG_INFO_KEY = "ORG_INFO_KEY";

    private final ServiceCatalogOrg serviceCatalogOrg;

    private final ServiceCatalogHttpClient serviceCatalogHttpClient;

    private final OrgRoleAdminResource orgRoleAdminResource;

    public CraneCacheOrgAttributes(ServiceCatalogOrg serviceCatalogOrg,
                                   ServiceCatalogHttpClient serviceCatalogHttpClient,
                                   OrgRoleAdminResource orgRoleAdminResource) {
        this.serviceCatalogOrg = serviceCatalogOrg;
        this.serviceCatalogHttpClient = serviceCatalogHttpClient;
        this.orgRoleAdminResource = orgRoleAdminResource;
    }

    /**
     * 缓存所有部门的运维负责人信息
     */
    @Crane("com.sankuai.avatar.web.cacheRootOrgOpAdmin")
    public void cacheRootOrgOpAdmin() {
        Transaction t = Cat.newTransaction("OrgSreTree", "PolishOrgSre");
        try {

            List<String> orgIds = getTreePaths().stream().filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(orgIds)) {
                orgIds.forEach(orgId -> {
                    String[] orgIdList = orgId.split(",");
                    orgId = orgIdList[orgIdList.length-1];
                    orgRoleAdminResource.cacheRoleUsers(orgId, OrgRoleType.OP_ADMIN);
                });
                t.addData("缓存部门 sre 成功");
            }
        } catch (Exception e) {
            Cat.logError(e);
        } finally {
            t.complete();
        }
    }

    @Crane("com.sankuai.avatar.web.cacheOrgAppkeyHostCount")
    public void cacheOrgAppkeyHostCount() {
        Transaction t = Cat.newTransaction("OrgTree", "cacheOrgAppkeyHostCount");
        try {
            List<String> orgIds = getTreePaths().stream().filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
            for (String orgId : orgIds) {
                OrgInfo orgInfo = serviceCatalogOrg.getOrgInfo(orgId);
                String orgInfoString = GsonUtils.serialization(orgInfo);
                if (StringUtils.isNotBlank(orgInfoString) && orgInfo != null) {
                    SquirrelUtils.setEx(ORG_INFO_KEY + orgId, orgInfoString, 60 * 60 * 12);
                }
            }
        } catch (Exception e) {
            Cat.logError(e);
        } finally {
            t.complete();
        }
    }

    private void dfs(List<Org> root, String path, List<String> res) {
        for (Org org : root) {
            List<Org> childes = org.getChildren();
            if (CollectionUtils.isNotEmpty(childes)) {
                res.add(path + org.getId());
                dfs(childes, path + org.getId() + ",", res);
            }
            if (childes == null) {
                res.add(path + org.getId());
            }
        }
    }

    public List<String> getTreePaths() throws Exception {
        // TODO 替换serviceCatalogOrg
        List<String> treePath = new ArrayList<>();
        String path = "/open/api/avatar/v1/org/tree";
        Map<String, Object> params = new HashMap<>(2);
        params.put("source", "avatar");
        List<Org> treeNode =  serviceCatalogHttpClient.getListData(path, params, Org.class);
        dfs(treeNode, "", treePath);
        return treePath;
    }
}

