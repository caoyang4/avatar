package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.OpsUtils;
import com.sankuai.avatar.capacity.util.ScUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author caoyang
 * @create 2022-08-22 11:18
 */
@Service
public class JboxCollectionItemImpl implements ICapacityCollectionItem {
    @Override
    public void collect() throws IOException {

    }

    @Override
    public void refresh() {

    }

    @Override
    public <T> T getData(AppKeyNode appKeyNode) {
        return null;
    }

    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        appKeyNode.setJbox(ScUtils.isJboxAppkey(appKeyNode.getAppkey()));
    }
}
