package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PlusDeployDataCollectServiceImplTest extends com.sankuai.avatar.TestBase {

    @Autowired
    private PlusDeployCollectionItemImpl plusDeployDataCollectService;

    @Test
    public void getData() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        appKeyNode.setSrv("dianping.tbd.tools.avatar-workflow-web");
        boolean isDeploy = plusDeployDataCollectService.getData(appKeyNode);
        System.out.println(isDeploy);
    }

    @Test
    public void setAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        appKeyNode.setSrv("dianping.tbd.tools.avatar-workflow-web");
        plusDeployDataCollectService.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);
    }
}
