package com.sankuai.avatar.workflow.worker.mq.consumer;

import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.meituan.mdp.boot.starter.mafka.consumer.AbstractMdpListener;
import com.meituan.mdp.boot.starter.mafka.consumer.anno.MdpMafkaMsgReceive;
import com.sankuai.avatar.workflow.worker.LoaderFlow.LoaderFlowMq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 从mq消息队列中消费流程id
 *
 * @author kui.xu
 */
@Slf4j
@Service("FlowQueueConsumer")
public class FlowConsumer {

    @Autowired
    LoaderFlowMq loaderFlowMq;

    @MdpMafkaMsgReceive
    protected ConsumeStatus receiveMessage(Integer flowId, AbstractMdpListener.MdpMqContext context) {
        /*
        TODO 完善mafka配置文件, 申请topic
         */
        log.info(" 接收到流程消息，id={} body={}", context.getMessage().getMessageID(), flowId);
        if (loaderFlowMq.loadFlowId(flowId)) {
            return ConsumeStatus.CONSUME_SUCCESS;
        } else {
            return ConsumeStatus.RECONSUME_LATER;
        }
    }
}
