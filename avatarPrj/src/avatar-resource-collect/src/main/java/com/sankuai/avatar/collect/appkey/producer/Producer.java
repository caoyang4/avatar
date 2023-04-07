package com.sankuai.avatar.collect.appkey.producer;


import com.meituan.mafka.client.producer.ProducerStatus;

/**
 * The interface Producer.
 *
 * @author qinwei05
 */
public interface Producer {

    /**
     * Default action.
     *
     * @param object the object
     * @return status send status
     */
    ProducerStatus defaultAction(Object object);
}
