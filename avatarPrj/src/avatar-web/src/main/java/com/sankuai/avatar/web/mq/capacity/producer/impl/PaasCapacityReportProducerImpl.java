package com.sankuai.avatar.web.mq.capacity.producer.impl;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.meituan.mafka.client.producer.*;
import com.sankuai.avatar.web.mq.capacity.producer.PaasCapacityReportProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author caoyang
 * @create 2023-02-24 14:44
 */

@Service
@Slf4j
public class PaasCapacityReportProducerImpl implements PaasCapacityReportProducer {

    @Autowired
    @Qualifier("paasCapacityProducer")
    private IProducerProcessor<String, String> producer;

    @Override
    public ProducerResult paasCapacityReport(String paasCapacityReport) {
        Transaction t = Cat.newTransaction("Mafka.PaasCapacity.Producer", "PaasCapacityProducer");
        try {
            ProducerResult sendResult = producer.sendAsyncMessage(paasCapacityReport, new FutureCallback() {
                @Override
                public void onSuccess(AsyncProducerResult result) {
                    log.info("paasCapacity Push Success: {}", paasCapacityReport);
                }
                @Override
                public void onFailure(Throwable t) {
                    log.warn("paasCapacity Push Failed: {}", t.getMessage());
                    Cat.logError("Send paasCapacity report failed", t);
                }
            });
            log.info("paasCapacity Push Status: msgId:【{}】, producerStatus【{}】, MSG【{}】",
                    sendResult.getMessageID(), sendResult.getProducerStatus(), paasCapacityReport);
            t.addData(String.format("paasCapacity Push Status: msgId:【%s】, producerStatus【%s】, MSG【%s】",
                    sendResult.getMessageID(), sendResult.getProducerStatus(), paasCapacityReport));
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


