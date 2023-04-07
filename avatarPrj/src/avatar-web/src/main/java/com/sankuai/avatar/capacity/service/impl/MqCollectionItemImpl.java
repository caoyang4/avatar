package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.MiddleWare;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.MqUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author chenxinli
 */
@Service
public class MqCollectionItemImpl implements ICapacityCollectionItem {
    /**
     * 容灾等级相关数据采集
     *
     * @throws IOException some exception
     */
    @Override
    public void collect() throws IOException {

    }

    @Override
    public void refresh() {

    }

    /**
     * 获取数据
     *
     * @param appKeyNode Appkey节点
     * @return 返回
     */
    @Override
    public Boolean getData(AppKeyNode appKeyNode) {
        String appkey = appKeyNode.getAppkey();
        return MqUtils.isAccessToMQ(appkey) || MqUtils.isAccessToMQProducer(appkey);
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        MiddleWare middleWare = new MiddleWare();
        middleWare.setMiddleWareName(MiddleWareName.MQ);
        middleWare.setUsed(this.getData(appKeyNode));
        appKeyNode.getMiddleWareInfoList().add(middleWare);
    }
}
