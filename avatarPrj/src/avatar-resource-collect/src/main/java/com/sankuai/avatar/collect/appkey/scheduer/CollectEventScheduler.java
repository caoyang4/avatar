package com.sankuai.avatar.collect.appkey.scheduer;

import com.sankuai.avatar.collect.appkey.event.CollectEvent;
import com.sankuai.avatar.collect.appkey.event.CollectEventResult;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2022-11-18
 **/
public interface CollectEventScheduler {

    /**
     * 触发采集
     *
     * @param collectEvent 采集事件
     * @return {@link List}<{@link CollectEventResult}>
     */
    List<CollectEventResult> fireEvent(CollectEvent collectEvent);
}
