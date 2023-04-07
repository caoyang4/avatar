package com.sankuai.avatar.capacity.service.impl;

import com.google.common.collect.ImmutableSet;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.OpsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

/**
 * @author caoyang
 * @create 2022-08-17 21:17
 */
@Service
public class OtherCollectionItemImpl implements ICapacityCollectionItem {

    private final Set<String> parasitedOwts = ImmutableSet.of("meituan.cloudinf");

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
        String owt = appKeyNode.getOwt();
        if (StringUtils.isEmpty(owt)) {
            owt = OpsUtils.getOwtFromSrv(appKeyNode.getSrv());
            appKeyNode.setOwt(owt);
        }
        if (parasitedOwts.contains(owt)) {
            appKeyNode.setParasited(true);
            appKeyNode.setCalculate(false);
        }

    }
}
