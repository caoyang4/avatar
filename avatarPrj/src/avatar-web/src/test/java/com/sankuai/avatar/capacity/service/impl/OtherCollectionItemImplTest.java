package com.sankuai.avatar.capacity.service.impl;


import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author caoyang
 * @create 2022-08-17 21:29
 */
public class OtherCollectionItemImplTest extends TestBase {

    @Autowired
    private OtherCollectionItemImpl otherCollectionItem;

    @Test
    public void testSetAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("com.sankuai.hulknode.eightbj.jinrong");
        appKeyNode.setSrv("meituan.cloudinf.node.hulknode-eightbj-jinrong");
        otherCollectionItem.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode.getOwt());
        System.out.println(appKeyNode.getParasited());
        assert appKeyNode.getParasited();
    }
}