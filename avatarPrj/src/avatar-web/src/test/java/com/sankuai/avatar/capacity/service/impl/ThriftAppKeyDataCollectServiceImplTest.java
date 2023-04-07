package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.SetName;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class ThriftAppKeyDataCollectServiceImplTest extends com.sankuai.avatar.TestBase {
    @Autowired
    private OctoThriftCollectionItemImpl thriftAppKeyDataCollectService;

    @Test
    public void setAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        appKeyNode.setSetName(new SetName(""));
        appKeyNode.setOctoThriftProviderList(new ArrayList<>());
        appKeyNode.setMiddleWareInfoList(new ArrayList<>());
        thriftAppKeyDataCollectService.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);
    }
}
