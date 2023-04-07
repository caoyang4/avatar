package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PaasDataCollectServiceImplTest extends com.sankuai.avatar.TestBase {
    @Autowired
    private PaasCollectionItemImpl paasDataCollectService;



    @Test
    public void setAppkeyCapacityProperty() {
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("avatar-workflow-web");
        appKeyNode.setSrv("dianping.tbd.tools.avatar-workflow-web");
        paasDataCollectService.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);
        assert !appKeyNode.getPaas();
    }

    @Test
    public void testPaasAndCalculate(){
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("com.sankuai.dts.pay.dswriter05");
        appKeyNode.setSrv("meituan.buffalo.pay.dts-pay-dswriter05");
//        appKeyNode.setAppkey("com.sankuai.mafka.adp.brokerad");
//        appKeyNode.setSrv("meituan.mafka.adp.brokerad");
        paasDataCollectService.setAppkeyCapacityProperty(appKeyNode);
//        System.out.println(appKeyNode.getPaas());
//        System.out.println(appKeyNode.getCalculate());
        assert appKeyNode.getPaas();
        assert appKeyNode.getCalculate();
    }

    @Test
    public void testIsPaas(){
        AppKeyNode appKeyNode = AppKeyNode.builder().build();
        appKeyNode.setAppkey("com.sankuai.s3plus.bj01.objpeisong01ups");
        appKeyNode.setSrv("meituan.mss.mssplusbj.s3plus-bj01-objpeisong01ups");
        paasDataCollectService.setAppkeyCapacityProperty(appKeyNode);
        System.out.println(appKeyNode);
        assert appKeyNode.getPaas();
        assert !appKeyNode.getCalculate();
    }
}
