package com.sankuai.avatar.client.workflow;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.workflow.model.AppkeyFlow;
import com.sankuai.avatar.common.vo.PageResponse;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * @author qinwei05
 * @date 2023/2/14 11:05
 * @version 1.0
 */

public class AvatarWorkflowHttpClientImplTest extends TestBase {

    @Autowired
    private AvatarWorkflowHttpClient avatarWorkflowHttpClient;

    @Test
    public void batchGetAppkeyRunningAndHoldingFlowList() {
        PageResponse<AppkeyFlow> appkeyFlowPageResponse = avatarWorkflowHttpClient.batchGetAppkeyFlowList(Collections.singletonList(testAppkey), "RUNNING,HOLDING");
        Assert.assertTrue(appkeyFlowPageResponse.getPage() > 0);
    }
}
