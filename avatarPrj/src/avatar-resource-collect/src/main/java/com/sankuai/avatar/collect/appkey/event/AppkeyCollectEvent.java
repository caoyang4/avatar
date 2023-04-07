package com.sankuai.avatar.collect.appkey.event;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jie.li.sh
 * @create 2022-11-18
 **/
@Data
@Builder
public class AppkeyCollectEvent implements CollectEvent {

    /**
     * 事件名称
     */
    CollectEventName collectEventName;

    /**
     * 事件数据
     */
    AppkeyCollectEventData collectEventData;
}
