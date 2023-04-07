package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Set;


public class MgwDataCollectServiceImplTest extends com.sankuai.avatar.TestBase {

    @Autowired
    private MgwCollectionItemImpl mgwDataCollectService;


    @Test
    public void setAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("com.sankuai.mtsdb.cloud.mgwmonitor");
        appKeyNode.setSrv("meituan.mtsdb.cloud.mtsdb-cloud-mgwmonitor");
        appKeyNode.setHostList(new ArrayList<>());
        appKeyNode.setMiddleWareInfoList(new ArrayList<>());
        Set<String> set = mgwDataCollectService.getData(appKeyNode);
        System.out.println(set);
        mgwDataCollectService.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);
        appKeyNode.getMiddleWareInfoList().forEach(System.out::println);
    }
}
