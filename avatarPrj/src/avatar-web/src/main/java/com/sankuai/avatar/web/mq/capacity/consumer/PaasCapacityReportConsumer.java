package com.sankuai.avatar.web.mq.capacity.consumer;

import com.meituan.mafka.client.consumer.ConsumeStatus;

/**
 * @author caoyang
 * @create 2023-02-24 14:57
 */
public interface PaasCapacityReportConsumer {

    ConsumeStatus consume(String msgBody);

}
