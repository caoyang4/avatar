package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.OctoProvider;
import com.sankuai.avatar.capacity.node.SetName;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OctoAppKeyDataCollectServiceImplTest extends com.sankuai.avatar.TestBase {

    @Autowired
    private OctoHttpCollectionItemImpl octoAppKeyDataCollectService;

    @Test
    public void getData() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        List<OctoProvider> octoProviderList = octoAppKeyDataCollectService.getData(appKeyNode);
        System.out.println(octoProviderList);
    }

    @Test
    public void setAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        appKeyNode.setMiddleWareInfoList(new ArrayList<>());
        appKeyNode.setOctoHttpProviderList(new ArrayList<>());
        appKeyNode.setSetName(new SetName(""));
        octoAppKeyDataCollectService.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);
    }
}
