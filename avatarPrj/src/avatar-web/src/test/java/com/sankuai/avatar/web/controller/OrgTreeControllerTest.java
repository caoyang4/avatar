package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.web.vo.orgtree.OrgTreeNodeVO;
import com.sankuai.avatar.web.vo.orgtree.OrgTreeOrgInfoVO;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * OrgTreeController的测试类
 */
public class OrgTreeControllerTest extends TestBase {

    @Test
    public void testGetOrgTree() throws Exception {
        String url = "/api/v2/avatar/orgTree/user/org/tree";
        List<OrgTreeNodeVO> tree = Arrays.asList(getMock(url, OrgTreeNodeVO[].class));
        assertTrue(tree.size() > 0);
    }

    @Test
    public void testGetOrgInfo() throws Exception {
        String url = "/api/v2/avatar/orgTree/org?orgIds=100046,150042,1573,150044";
        OrgTreeOrgInfoVO orgInfo = getMock(url, OrgTreeOrgInfoVO.class);
        assertEquals("kui.xu", orgInfo.getLeader().getMis());
    }
}
