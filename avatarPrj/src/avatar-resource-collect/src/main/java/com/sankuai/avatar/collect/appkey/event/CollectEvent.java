package com.sankuai.avatar.collect.appkey.event;

/**
 * 发布的采集事件
 * @author Jie.li.sh
 * @create 2022-11-17
 **/
public interface CollectEvent {
    /**
     * 采集事件的名称
     * @return name
     */
   CollectEventName getCollectEventName();

    /**
     * 采集事件接收的数据
     * @return data
     */
   CollectEventData getCollectEventData();
}
