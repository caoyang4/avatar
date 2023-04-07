package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.GsonUtils;
import com.sankuai.avatar.capacity.util.NestUtils;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxinli
 */
@Service
public class NestCollectionItemImpl implements ICapacityCollectionItem {
    private static String cKey = "nest_appkey";

    private static Map nestAppKeyMap = new HashMap();

    /**
     * 容灾等级相关数据采集
     *
     * @throws IOException some exception
     */
    @Override
    public void collect() throws IOException {
        Map hm = NestUtils.getDisasterRecoveryLevels();
        if (!hm.isEmpty()) {
            String json = GsonUtils.serialization(hm);
            SquirrelUtils.set(cKey, json);
        }
    }

    private static void setNestAppKeyMap() {
        String result = SquirrelUtils.get(cKey);
        if (result != null && !result.isEmpty()) {
            nestAppKeyMap = GsonUtils.deserialization(result, Map.class);
        }
    }

    @Override
    public void refresh() {
        setNestAppKeyMap();
    }

    /**
     * 获取数据
     *
     * @param appKeyNode Appkey节点
     * @return 返回
     */
    @Override
    public Map getData(AppKeyNode appKeyNode) {
        if (nestAppKeyMap.size() < 1) {
            refresh();
        }
        return nestAppKeyMap;
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        if (this.getData(appKeyNode).containsKey(appKeyNode.getAppkey())) {
            appKeyNode.setNest(true);
        } else {
            appKeyNode.setNest(false);
        }
    }
}
