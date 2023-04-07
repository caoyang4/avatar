package com.sankuai.avatar.client.org;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.org.model.Org;
import com.sankuai.avatar.client.org.model.OrgUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author caoyang
 * @create 2022-10-26 15:33
 */
public class OrgClientTest extends TestBase {

    @Autowired
    private OrgClient client;

    @Test
    public void testGetOrgUserByMis() {
        String mis1 = "caoyang42";
        OrgUser orgUser = client.getOrgUserByMis(mis1);
        Assert.assertNotNull(orgUser);
        Assert.assertEquals(mis1, orgUser.getMis());
        Assert.assertEquals("曹阳", orgUser.getName());
        Assert.assertEquals("ORG", orgUser.getSource());
        String mis2 = "guoxinye";
        OrgUser myUser = client.getOrgUserByMis(mis2);
        Assert.assertNotNull(myUser);
        Assert.assertEquals(mis2, myUser.getMis());
        Assert.assertEquals("MY", myUser.getSource());
    }

    @Test
    public void testGetOrgByOrgId() {
        String orgId = "1021866";
        Org org = client.getOrgByOrgId(orgId);
        Assert.assertNotNull(org);
        Assert.assertEquals(orgId, org.getOrgId());
    }
}