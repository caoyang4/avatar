package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import org.junit.Test;


public class WhitelistDataCollectServiceImplTest extends com.sankuai.avatar.TestBase {

//    private WhitelistCollectionItemImpl whitelistDataCollectService = new WhitelistCollectionItemImpl();


    @Test
    public void setAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-service");
        System.out.println(appKeyNode);
//        whitelistDataCollectService.setAppkeyCapacityProperty(appKeyNode);
//        System.out.println(appKeyNode);
    }
}
