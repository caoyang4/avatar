package com.sankuai.avatar.capacity.collect;

import com.dianping.cat.Cat;
import com.sankuai.avatar.capacity.exception.CollectException;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.service.impl.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxinli
 */
@Slf4j
@Component
@NoArgsConstructor
public class CollectChain {

    public void doHandle(AppKeyNode appKeyNode){
        for (ICapacityCollectionItem handler : getHandlers()) {
            long start = System.currentTimeMillis();
            try {
                handler.setAppkeyCapacityProperty(appKeyNode);
            } catch (Exception e) {
                throw new CollectException(String.format("%s属性设置异常，异常详情:%s", handler.getClass().getName(), e.getMessage()));
            }
            long cost = System.currentTimeMillis() - start;
            Cat.logMetricForDuration(handler.getClass().getName(), cost);
        }
    }

    public static List<ICapacityCollectionItem> getHandlers(){
        return Handlers.handlers;
    }

    private static class Handlers{
        private static List<ICapacityCollectionItem> handlers = new ArrayList<ICapacityCollectionItem>() {{
            add(new CraneCollectionItemImpl());
            add(new MgwCollectionItemImpl());
            add(new HulkCollectionItemImpl());
            add(new NestCollectionItemImpl());
            add(new OceanusCollectionItemImpl());
            add(new PaasCollectionItemImpl());
            add(new MqCollectionItemImpl());
            add(new StandardLevelDataCollectImpl());
            add(new PlusDeployCollectionItemImpl());
            add(new WhitelistCollectionItemImpl());
            add(new UtilizationCollectionItemImpl());
            add(new HostCollectionItemImpl());
            add(new OctoHttpCollectionItemImpl());
            add(new OctoThriftCollectionItemImpl());
            add(new ServiceCatalogCollectionItemImpl());
            add(new CellCollectionItemImpl());
            add(new OtherCollectionItemImpl());
            add(new JboxCollectionItemImpl());
        }};
    }
}
