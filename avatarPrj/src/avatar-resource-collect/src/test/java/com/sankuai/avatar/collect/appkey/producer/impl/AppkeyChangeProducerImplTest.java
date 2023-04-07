package com.sankuai.avatar.collect.appkey.producer.impl;

import com.meituan.mafka.client.producer.FutureCallback;
import com.meituan.mafka.client.producer.IProducerProcessor;
import com.meituan.mafka.client.producer.ProducerResult;
import com.meituan.mafka.client.producer.ProducerStatus;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppkeyChangeProducerImplTest {

    @Mock
    private IProducerProcessor<String, String> mockProducer;

    @InjectMocks
    private AppkeyChangeProducerImpl appkeyChangeProducerImplUnderTest;

    @Test
    public void testDefaultAction() {
        assertThat(appkeyChangeProducerImplUnderTest.defaultAction("object")).isEqualTo(ProducerStatus.SEND_OK);
    }

    @Test
    public void testAppkeyChange() throws Exception {
        // Setup
        // Configure IProducerProcessor.sendAsyncMessage(...).
        final ProducerResult producerResult = new ProducerResult(ProducerStatus.SEND_OK, "clusterName", "traceId");
        when(mockProducer.sendAsyncMessage(eq("appkeyMsgString"), any(FutureCallback.class)))
                .thenReturn(producerResult);

        // Run the test
        final ProducerResult result = appkeyChangeProducerImplUnderTest.appkeyChange("appkeyMsgString");
        Mockito.verify(mockProducer, times(1)).sendAsyncMessage(eq("appkeyMsgString"), any(FutureCallback.class));
        // Verify the results
        Assertions.assertThat(result.getProducerStatus()).isEqualTo(ProducerStatus.SEND_OK);
    }

    @Test
    public void testAppkeyChange_IProducerProcessorThrowsException() throws Exception {
        // Setup
        when(mockProducer.sendAsyncMessage(eq("appkeyMsgString"), any(FutureCallback.class)))
                .thenThrow(Exception.class);

        // Run the test
        final ProducerResult result = appkeyChangeProducerImplUnderTest.appkeyChange("appkeyMsgString");
        Mockito.verify(mockProducer, times(1)).sendAsyncMessage(eq("appkeyMsgString"), any(FutureCallback.class));

        // Verify the results
        Assertions.assertThat(result.getProducerStatus()).isEqualTo(ProducerStatus.SEND_FAILURE);
    }
}
