package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author caoyang
 * @create 2022-08-22 11:21
 */
public class JboxCollectionItemImplTest extends TestBase {
    @Autowired
    private JboxCollectionItemImpl jboxCollectionItem;

    @Test
    public void testSetAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("com.sankuai.dzsaas.operation");
        appKeyNode.setSrv("dianping.dzu.dzopen_saas.dzsaas-operation");
        jboxCollectionItem.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);
    }
}