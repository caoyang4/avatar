package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.event.CollectEvent;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.event.CollectEventResult;

/**
 * @author Jie.li.sh
 * @create 2022-11-17
 **/
public interface Collector {

    /**
     * 是否采集
     * @param collectEventName 事件名称
     * @return true/false
     */
    boolean onFire(CollectEventName collectEventName);

    /**
     * 采集逻辑实现
     *
     * @param collectEvent 采集事件
     * @return {@link CollectEventResult}
     */
    CollectEventResult collect(CollectEvent collectEvent);

    /**
     * 运行采集
     *
     * @param collectEvent 采集事件
     * @return {@link CollectEventResult}
     */
    CollectEventResult run(CollectEvent collectEvent);
}
