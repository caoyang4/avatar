package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.BladeUtils;
import com.sankuai.avatar.capacity.util.DayuUtils;
import com.sankuai.avatar.capacity.util.OpsUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author chenxinli
 */
@Service
public class StandardLevelDataCollectImpl implements ICapacityCollectionItem {
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
     * @param appKeyNode AppKey节点
     * @return 返回
     */
    @Override
    public Double getData(AppKeyNode appKeyNode) {
        double level;
        if (appKeyNode.getSrv().startsWith("meituan.blade")) {
            level = BladeUtils.getBladeStandardLevel(appKeyNode.getAppkey());
        } else if (appKeyNode.getSetName().isSet()){
            boolean exist = DayuUtils.isExistLogicSite(appKeyNode.getSetName().getSetName());
            if (exist) {
                return 4.0;
            } else {
                return  "核心服务".equals(appKeyNode.getRank()) ? 4.0 : 3.0;
            }
        } else if (appKeyNode.getSetName().isLiteSet()) {
            level = "核心服务".equals(appKeyNode.getRank()) ? 4.0 : 3.0;
        } else {
            level = OpsUtils.getAppKeyStandardLevel(appKeyNode.getAppkey());
        }
        return level;
    }

    /**
     * 设置AppKey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        appKeyNode.setStandardLevel(getData(appKeyNode).intValue());
    }
}
