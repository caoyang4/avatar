package com.sankuai.avatar.client.dx;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.dx.model.DxUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * @author caoyang
 * @create 2022-10-26 15:29
 */
public class DxClientTest extends TestBase {

    @Autowired
    private DxClient client;

    @Test
    public void testGetDxUserByMis() {
        String mis = "caoyang42";
        DxUser dxUser = client.getDxUserByMis(mis);
        Assert.assertNotNull(dxUser);
    }

    @Test
    public void pushDxMessage() {
        String mis = "zhaozhifan02";
        String message = "虽千万人吾往矣";
        Boolean dxMessage = client.pushDxMessage(Collections.singletonList(mis), message);
        Assert.assertTrue(dxMessage);
    }

    @Test
    public void pushDxAuditMessage() {
        String mis = "zhaozhifan02";
        String message = "[测试]您有变更流程待审批";
        Boolean dxMessage = client.pushDxAuditMessage(Collections.singletonList(mis), message);
        Assert.assertFalse(dxMessage);
    }
}