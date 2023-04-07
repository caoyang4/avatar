package com.sankuai.avatar.workflow.server.mafka.producer;

import com.dianping.cat.Cat;
import com.meituan.mafka.client.producer.AsyncProducerResult;
import com.meituan.mafka.client.producer.FutureCallback;
import com.meituan.mafka.client.producer.IProducerProcessor;
import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.workflow.server.mafka.entity.FlowMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Component
public class FlowUpdateProducer implements Producer<FlowMessage> {

    @Autowired
    @Qualifier("workFlowUpdateProducer")
    private IProducerProcessor producerProcessor;

    @Override
    public void produce(FlowMessage msg) {
        log.info("produce flow message {}", msg);
        if (Objects.isNull(msg) || Objects.isNull(msg.getId())) {
            return;
        }
        try {
            String json = GsonUtils.serialization(msg);
            producerProcessor.sendAsyncMessage(json, new FutureCallback() {
                @Override
                public void onSuccess(AsyncProducerResult result) {
                    log.info("Send flow message success");
                }

                @Override
                public void onFailure(Throwable t) {
                    log.error("Send flow message failed");
                    Cat.logError("Send flow message failed", t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Send async flow message caught exception", e);
            Cat.logError("Send async flow message caught exception", e);

        }

    }
}
