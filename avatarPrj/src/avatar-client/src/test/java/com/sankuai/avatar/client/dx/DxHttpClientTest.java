package com.sankuai.avatar.client.dx;

import com.sankuai.avatar.client.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Slf4j
public class DxHttpClientTest extends TestBase {
    @Autowired
    private DxHttpClient dxHttpClient;

    @Test
    public void pushDxMessage() {
        String mis = "zhaozhifan02";
        String message = "[测试]您的变更已发起";
        Boolean status = dxHttpClient.pushDxMessage(Collections.singleton(mis), message);
        Assert.assertTrue(status);

    }

    @Test
    public void pushDxAuditMessage() {
        String mis = "zhaozhifan02";
        String message = "[测试]您有变更流程待审批";
        Boolean status = dxHttpClient.pushDxAuditMessage(Collections.singleton(mis), message);
        Assert.assertTrue(status);
    }

    @Test
    public void pushGroupMessage() {
        Long group = Long.parseLong("66765549986");
        String message = "[测试]❌您有一个变更失败了，请关注";
        Boolean status = dxHttpClient.pushGroupMessage(group, message);
        Assert.assertTrue(status);
    }
}
