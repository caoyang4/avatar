package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.MiddleWare;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.MgwUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenxinli
 */
@Service
public class MgwCollectionItemImpl implements ICapacityCollectionItem {
    private static final String cKey = "mgw_access_srv";

    private static Set<String> mgwSrvList = new HashSet<>();


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
    public Set<String> getData(AppKeyNode appKeyNode) {
        return mgwSrvList;
    }



    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        MiddleWare middleWare = new MiddleWare();
        middleWare.setMiddleWareName(MiddleWareName.MGW);
        middleWare.setUsed(MgwUtils.isMiddleWareMgw(appKeyNode));
        appKeyNode.getMiddleWareInfoList().add(middleWare);
    }
}
