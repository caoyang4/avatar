package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.SetName;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HulkDataCollectServiceImplTest extends com.sankuai.avatar.TestBase {
    @Autowired
    private HulkCollectionItemImpl hulkDataCollectService;


    @Test
    public void setAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        appKeyNode.setSetName(new SetName(""));
        hulkDataCollectService.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);
    }
}
