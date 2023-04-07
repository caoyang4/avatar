package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.OpsUtils;
import com.sankuai.avatar.capacity.util.RocketUtil;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chenxinli
 */
@Slf4j
@Service
public class CellCollectionItemImpl implements ICapacityCollectionItem {
    private static final String cKey = "appkey_cells";

    /**
     * 容灾等级相关数据采集
     *
     * @throws IOException some exception
     */
    @Override
    public void collect() throws IOException {
        List<Map<String, ?>> srvs = OpsUtils.getFullSrv();
        srvs.parallelStream().forEach(srv -> {
            String appKey = (String) srv.get("appkey");
            int key = appKey.hashCode() % 1000;
            String hashKey = cKey + key;
            Set<String> setList = RocketUtil.listSet(appKey);
            SquirrelUtils.hset(hashKey, appKey, setList);
        });

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
       return null;
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
    }
}
