package com.sankuai.avatar.manager;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.sdk.entity.servicecatalog.Org;
import com.sankuai.avatar.sdk.entity.servicecatalog.OrgInfo;
import com.sankuai.avatar.sdk.manager.ServiceCatalogOrg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ServiceCatalogOrgTest extends TestBase {
    @Autowired
    private ServiceCatalogOrg serviceCatalogOrg;

    @Test
    public void listUserOrg() throws Exception {
        String mis = "jie.li.sh";
        List<Org> orgList = serviceCatalogOrg.listUserOrg(mis);
        assertEquals("基础研发平台", orgList.get(0).getName());
    }

    @Test
    public void getInfo() throws Exception {
        OrgInfo orgInfo = serviceCatalogOrg.getOrgInfo("100046,150042");
        assertTrue(orgInfo.getAppKeyCount() > 0);
    }
}
