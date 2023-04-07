package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.SetName;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


public class UtilizationDataCollectServiceImplTest extends com.sankuai.avatar.TestBase {
    @Autowired
    private UtilizationCollectionItemImpl utilizationDataCollectService;

    @Test
    public void getData() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        utilizationDataCollectService.getData(appKeyNode);
    }

    @Test
    public void setAppkeyCapacityProperty() throws IOException {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        appKeyNode.setSetName(new SetName(""));
        appKeyNode.setSrv("dianping.tbd.tools.avatar-workflow-web");
        System.out.println(appKeyNode);
        utilizationDataCollectService.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);

    }
}
