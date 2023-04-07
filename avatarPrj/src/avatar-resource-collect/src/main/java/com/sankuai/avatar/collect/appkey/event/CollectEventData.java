package com.sankuai.avatar.collect.appkey.event;

/**
 * 采集事件对应的数据
 * @author Jie.li.sh
 * @create 2022-11-17
 **/
public interface CollectEventData {

    /**
     * appkey名称
     * @return appkey
     */
    String getAppkey();

    /**
     * 事件数据
     * @return 事件数据
     */
    <T> T getEventData();
}
