package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.GsonUtils;
import com.sankuai.avatar.capacity.util.HulkUtils;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenxinli
 */
@Slf4j
@Service
public class HulkCollectionItemImpl implements ICapacityCollectionItem {
    private static final String cKey = "hulk_appkeys";

    private static Set<String> hulkAppKeyList = new HashSet<>();

    /**
     * 容灾等级相关数据采集
     *
     * @throws IOException some exception
     */
    @Override
    public void collect() throws IOException {
        Set<String> appkeys = new HashSet<>();
        try {
            appkeys.addAll(HulkUtils.getHulkPolicy());
        } catch (Exception e) {
            log.error("获取接入 Hulk 的 appkey 列表失败...");
        } finally {
            SquirrelUtils.set(cKey, GsonUtils.serialization(appkeys));
        }
    }

    private static void setHulkAppKeyList() {
        String v = SquirrelUtils.get(cKey);
        if (v != null && !v.isEmpty()) {
            hulkAppKeyList = GsonUtils.deserialization(v, Set.class);
        }
    }

    @Override
    public void refresh() {
        setHulkAppKeyList();
    }

    /**
     * 获取数据
     *
     * @param appKeyNode Appkey节点
     * @return 返回
     */
    @Override
    public Set<String> getData(AppKeyNode appKeyNode) {
        if (CollectionUtils.isEmpty(hulkAppKeyList)) {
            refresh();
        }
        return hulkAppKeyList;
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        appKeyNode.setElastic(HulkUtils.isSetElastic(appKeyNode.getAppkey(), appKeyNode.getSetName().getSetName()));
    }
}
