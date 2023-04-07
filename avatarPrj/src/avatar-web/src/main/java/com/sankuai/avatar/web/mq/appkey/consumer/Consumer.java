package com.sankuai.avatar.web.mq.appkey.consumer;

import com.meituan.mafka.client.consumer.ConsumeStatus;

/**
 * 服务数据采集（被动监听）
 * @author qinwei05
 * @date 2022/11/3 16:22
 */
public interface Consumer {

    /**
     * 监听消费Mafka消息
     * @param msgBody 消息体
     * @return {@link ConsumeStatus}
     */
    ConsumeStatus consume(String msgBody);

    default ConsumeStatus consume(byte[] msgBody){
        return consume(new String(msgBody));
    }
}
