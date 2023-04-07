package com.sankuai.avatar.collect.appkey.scheduer;

import com.sankuai.avatar.collect.appkey.collector.Collector;
import com.sankuai.avatar.collect.appkey.event.CollectEvent;
import com.sankuai.avatar.collect.appkey.event.CollectEventResult;
import com.sankuai.avatar.common.exception.CollectErrorException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 采集通用调度端
 * @author Jie.li.sh
 * @create 2022-11-18
 **/
@Service
public abstract class AbstractCollectEventScheduler implements CollectEventScheduler {

    private final List<Collector> allRegisterCollectorList;

    @Autowired
    protected AbstractCollectEventScheduler(List<Collector> allRegisterCollectorList) {
        this.allRegisterCollectorList = allRegisterCollectorList;
    }

    /**
     * 选择事件对应的采集器
     * @param collectEvent 触发事件
     * @return list
     */
    List<Collector> selectCollector(CollectEvent collectEvent) {
        List<Collector> collectorList = new ArrayList<>();
        for (Collector collector : allRegisterCollectorList) {
            if (collector.onFire(collectEvent.getCollectEventName())) {
                collectorList.add(collector);
            }
        }
        return collectorList;
    }

    @Override
    public List<CollectEventResult> fireEvent(CollectEvent collectEvent) {
        if (collectEvent == null || collectEvent.getCollectEventName() == null || collectEvent.getCollectEventData() == null) {
            return Collections.emptyList();
        }
        List<Collector> collectorList = selectCollector(collectEvent);
        if (CollectionUtils.isEmpty(collectorList)) {
            return Collections.emptyList();
        }
        List<CollectEventResult> collectEventResultList = new ArrayList<>();
        for (Collector collector : collectorList) {
            // 某个采集器结果异常,忽略处理,源数据保持不变
            CollectEventResult collectEventResult = collector.run(collectEvent);
            if (collectEventResult != null) {
                collectEventResultList.add(collectEventResult);
            }
        }
        if (CollectionUtils.isEmpty(collectEventResultList)) {
            throw new CollectErrorException("服务资源采集异常：采集器全部采集失败！");
        }
        return collectEventResultList;
    }
}
