package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.SetName;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class StandardLevelDataCollectImplTest extends com.sankuai.avatar.TestBase {

    @Autowired
    private StandardLevelDataCollectImpl standardLevelDataCollect;

    @Test
    public void setAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        appKeyNode.setSetName(new SetName(""));
        appKeyNode.setSrv("dianping.tbd.tools.avatar-workflow-web");
        standardLevelDataCollect.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);
    }
}
