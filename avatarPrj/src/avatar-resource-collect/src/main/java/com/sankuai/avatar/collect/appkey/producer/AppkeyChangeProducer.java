package com.sankuai.avatar.collect.appkey.producer;

import com.meituan.mafka.client.producer.ProducerResult;

/**
 * 服务资源中心: 服务信息变更发送Mafka消息队列
 *
 * @author qinwei05
 * @date 2022/12/05
 */
public interface AppkeyChangeProducer extends Producer {

    /**
     * 服务变更消息发送Mafka Topic消息队列
     *
     * @param event the event
     * @return status
     */
    ProducerResult appkeyChange(String event);
}
