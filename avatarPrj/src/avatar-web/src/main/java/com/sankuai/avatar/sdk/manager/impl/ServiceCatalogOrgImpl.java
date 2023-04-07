package com.sankuai.avatar.sdk.manager.impl;

import com.sankuai.avatar.sdk.entity.servicecatalog.Org;
import com.sankuai.avatar.sdk.entity.servicecatalog.OrgInfo;
import com.sankuai.avatar.sdk.manager.ServiceCatalogHttpClient;
import com.sankuai.avatar.sdk.manager.ServiceCatalogOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jie.li.sh
 * @create 2020-02-11
 **/

@Service
public class ServiceCatalogOrgImpl implements ServiceCatalogOrg {
    @Autowired
    private ServiceCatalogHttpClient serviceCatalogHttpClient;

    @Override
    public List<Org> listUserOrg(String mis) throws Exception {
        String path = "/open/api/avatar/v1/org/tree";
        Map<String, Object> params = new HashMap<>(1);
        params.put("mis", mis);
        return serviceCatalogHttpClient.getListData(path, params, Org.class);
    }

    @Override
    public List<Org> getAllOrg() throws Exception {
        String path = "/open/api/avatar/v1/org/tree";
        Map<String, Object> params = new HashMap<>(1);
        params.put("source", "avatar");
        return serviceCatalogHttpClient.getListData(path, params, Org.class);
    }

    @Override
    public OrgInfo getOrgInfo(String orgIds) throws Exception {
        String path = "/open/api/avatar/v1/operationEvaluation/meta";
        Map<String, Object> params = new HashMap<>(1);
        params.put("orgIds", orgIds);
        return serviceCatalogHttpClient.get(path, params, OrgInfo.class);
    }
}
