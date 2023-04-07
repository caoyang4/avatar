package com.sankuai.avatar.workflow.server.mafka.producer;

/**
 * @author zhaozhifan02
 */
public interface Producer<T> {
    void produce(T msg);
}
