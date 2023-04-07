package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.MiddleWare;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.CraneUtils;
import com.sankuai.avatar.capacity.util.GsonUtils;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chenxinli
 */
@Service
public class CraneCollectionItemImpl implements ICapacityCollectionItem {
    private static final String cKey = "crane_appkeys";

    private static Set<String> craneAppKeyList = new HashSet<>();

    /**
     * 容灾等级相关数据采集
     *
     * @throws IOException some exception
     */
    @Override
    public void collect() throws IOException {
        try {
            List<String> appkeys = new ArrayList<String>() {{
                addAll(CraneUtils.getCraneAppkys());
            }};
            Set<String> uniqAppKeys = new HashSet<>(appkeys);
            if (!uniqAppKeys.isEmpty()) {
                String v = GsonUtils.serialization(uniqAppKeys);
                SquirrelUtils.set(cKey, v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setCraneAppKeyList() {
        String v = SquirrelUtils.get(cKey);
        if (v != null && !v.isEmpty()) {
            craneAppKeyList = GsonUtils.deserialization(v, Set.class);
        }
    }

    @Override
    public void refresh() {
        setCraneAppKeyList();
    }

    /**
     * 获取数据
     *
     * @param appKeyNode Appkey节点
     * @return 返回
     */
    @Override
    public Set<String> getData(AppKeyNode appKeyNode) {
        if (craneAppKeyList.size() < 1) {
            refresh();
        }
        return craneAppKeyList;
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        MiddleWare middleWare = new MiddleWare();
        middleWare.setMiddleWareName(MiddleWareName.CRANE);
        if (this.getData(appKeyNode).contains(appKeyNode.getAppkey())) {
            middleWare.setUsed(true);
        } else {
            middleWare.setUsed(false);
        }
        appKeyNode.getMiddleWareInfoList().add(middleWare);
    }
}
