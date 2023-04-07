package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class MqDataCollectServiceImplTest extends com.sankuai.avatar.TestBase {

    @Autowired
    private MqCollectionItemImpl mqDataCollectService;


    @Test
    public void setAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        appKeyNode.setMiddleWareInfoList(new ArrayList<>());
        Boolean hit = mqDataCollectService.getData(appKeyNode);
        mqDataCollectService.setAppkeyCapacityProperty(appKeyNode);
        System.out.println("接入mq: " + hit);
    }
}
