package com.sankuai.avatar.collect.appkey.event;

import lombok.Builder;
import lombok.Data;

/**
 * 默认服务采集对应的发送数据
 * @author Jie.li.sh
 * @create 2022-11-17
 **/
@Data
@Builder
public class AppkeyCollectEventData<T> implements CollectEventData {

    /**
     * appkey
     */
    private String appkey;

    /**
     * eventData
     */
    private T eventData;

    private AppkeyCollectEventData(String appkey) {
        this.appkey = appkey;
    }

    private AppkeyCollectEventData(String appkey, T eventData) {
        this.appkey = appkey;
        this.eventData = eventData;
    }

    public static AppkeyCollectEventData<String> of(String appkey) {
        return new AppkeyCollectEventData<>(appkey);
    }

    public static <K> AppkeyCollectEventData<K> of(String appkey, K eventData) {
        // 静态泛型方法应该使用其他类型区分
        return new AppkeyCollectEventData<>(appkey, eventData);
    }
}
