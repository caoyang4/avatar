package com.sankuai.avatar.collect.appkey.producer.impl;


import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.meituan.mafka.client.producer.*;
import com.sankuai.avatar.collect.appkey.producer.AppkeyChangeProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author qinwei05
 */
@Service
@Slf4j
public class AppkeyChangeProducerImpl implements AppkeyChangeProducer {

    @Autowired
    @Qualifier("appkeyProducer")
    private IProducerProcessor<String, String> producer;

    @Override
    public ProducerStatus defaultAction(Object object) {
        return ProducerStatus.SEND_OK;
    }

    @Override
    public ProducerResult appkeyChange(String appkeyMsgString) {
        Transaction t = Cat.newTransaction("Mafka.Appkey.Producer", "AppkeyChangeProducer");
        try {
            ProducerResult sendResult = producer.sendAsyncMessage(appkeyMsgString, new FutureCallback() {
                @Override
                public void onSuccess(AsyncProducerResult result) {
                    log.info("appkeyChange Push Success: {}", appkeyMsgString);
                }
                @Override
                public void onFailure(Throwable t) {
                    log.warn("appkeyChange Push Failed: {}", t.getMessage());
                    Cat.logError("Send appkeyChange message failed", t);
                }
            });
            log.info("appkeyChange Push Status: msgId:【{}】, producerStatus【{}】, MSG【{}】",
                    sendResult.getMessageID(), sendResult.getProducerStatus(), appkeyMsgString);
            t.addData(String.format("appkeyChange Push Status: msgId:【%s】, producerStatus【%s】, MSG【%s】",
                    sendResult.getMessageID(), sendResult.getProducerStatus(), appkeyMsgString));
            return sendResult;
        } catch (Exception e) {
            Cat.logError(e);
            t.setStatus(e);
        } finally {
            t.complete();
        }
        return new ProducerResult(ProducerStatus.SEND_FAILURE);
    }
}
