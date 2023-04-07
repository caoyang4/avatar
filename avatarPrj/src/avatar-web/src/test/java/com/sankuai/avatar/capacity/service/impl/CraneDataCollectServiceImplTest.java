package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Set;


public class CraneDataCollectServiceImplTest extends com.sankuai.avatar.TestBase {
    @Autowired
    private CraneCollectionItemImpl craneDataCollectService;

    @Test
    public void getData() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        Set<String> set = craneDataCollectService.getData(appKeyNode);
        System.out.println(set);
    }

    @Test
    public void setAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setMiddleWareInfoList(new ArrayList<>());
        appKeyNode.setAppkey("com.sankuai.inf.hulk.bannermanager");
        craneDataCollectService.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);
    }
}
