package com.sankuai.avatar.web.mq.capacity.producer;

import com.meituan.mafka.client.producer.ProducerResult;

/**
 * @author caoyang
 * @create 2023-02-24 14:42
 */
public interface PaasCapacityReportProducer {

    /**
     * paas容灾上报
     *
     * @param event 事件
     * @return {@link ProducerResult}
     */
    ProducerResult paasCapacityReport(String event);

}
