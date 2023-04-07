package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.MiddleWare;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.GsonUtils;
import com.sankuai.avatar.capacity.util.OceanusUtils;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chenxinli
 */
@Service
public class OceanusCollectionItemImpl implements ICapacityCollectionItem {
    private static final String cKey = "oceanus_appkeys";

    private static Set<String> oceanusAppKeyList = new HashSet<>();

    /**
     * 容灾等级相关数据采集
     *
     * @throws IOException some exception
     */
    @Override
    public void collect() throws IOException {
        List<String> appkeys = OceanusUtils.getOceanusAppkeys();
        Set<String> ao = Collections.synchronizedSet(new HashSet<>());
        ao.addAll(appkeys);
        if (!ao.isEmpty()) {
            String v = GsonUtils.serialization(ao);
            SquirrelUtils.set(cKey, v);
        }
    }

    public static void setOceanusAppKeyList() {
        String v = SquirrelUtils.get(cKey);
        if (v != null && !v.isEmpty()) {
            oceanusAppKeyList = GsonUtils.deserialization(v, Set.class);
        }
    }

    @Override
    public void refresh() {
        setOceanusAppKeyList();
    }

    /**
     * 获取数据
     *
     * @param appKeyNode Appkey节点
     * @return 返回
     */
    @Override
    public Set<String> getData(AppKeyNode appKeyNode) {
        if (oceanusAppKeyList.size() < 1) {
            refresh();
        }
        return oceanusAppKeyList;
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        MiddleWare middleWare = new MiddleWare();
        middleWare.setMiddleWareName(MiddleWareName.OCEANUS_HTTP);
        if (this.getData(appKeyNode).contains(appKeyNode.getAppkey())) {
            middleWare.setUsed(true);
        } else {
            middleWare.setUsed(false);
        }
        appKeyNode.getMiddleWareInfoList().add(middleWare);
    }
}
