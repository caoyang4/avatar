package com.sankuai.avatar.web.mq.capacity.producer;

import com.meituan.mafka.client.producer.FutureCallback;
import com.meituan.mafka.client.producer.IProducerProcessor;
import com.meituan.mafka.client.producer.ProducerResult;
import com.meituan.mafka.client.producer.ProducerStatus;
import com.sankuai.avatar.web.mq.capacity.producer.impl.PaasCapacityReportProducerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author caoyang
 * @create 2023-03-01 10:32
 */
@RunWith(MockitoJUnitRunner.class)
public class PaasCapacityReportProducerTest {

    private PaasCapacityReportProducer report;

    @Mock
    private IProducerProcessor<String, String> producer;

    final static String MSG = "{\n" +
            "    \"capacityLevel\": 2,\n" +
            "    \"clientAppkey\": [\n" +
            "      \"com.meituan.movie.mmdb.comment1\",\n" +
            "      \"com.meituan.movie.mmdb.comment2\"\n" +
            "    ],\n" +
            "    \"clientConfig\": [\n" +
            "      {\n" +
            "        \"value\": \"默认分配\",\n" +
            "        \"key\": \"producer.cluster.dispatch.type\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"value\": \"true\",\n" +
            "        \"key\": \"has.traffic.cluster\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"clientSdkVersion\": [\n" +
            "      {\n" +
            "        \"clientAppkey\": \"com.meituan.movie.mmdb.comment1\",\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"groupId\": \"com.meituan.mafka\",\n" +
            "            \"artifactId\": \"mafka-clientx\",\n" +
            "            \"language\": \"java\",\n" +
            "            \"version\": \"3.3.4\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"clientAppkey\": \"com.meituan.movie.mmdb.comment2\",\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"groupId\": \"com.meituan.mafka\",\n" +
            "            \"artifactId\": \"mafka-clientx\",\n" +
            "            \"language\": \"java\",\n" +
            "            \"version\": \"3.3.4\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"isCapacityStandard\": false,\n" +
            "    \"isConfigStandard\": false,\n" +
            "    \"isCore\": true,\n" +
            "    \"isWhite\": false,\n" +
            "    \"owner\": \"yangying14,lutao02,tangchao03,lichao23,zhenghao08,wangxiao15,liyongqiang06,sunzhiwei02\",\n" +
            "    \"paasAppkey\": \"com.sankuai.inf.mafka.broker\",\n" +
            "    \"paasName\": \"Mafka\",\n" +
            "    \"standardConfig\": [\n" +
            "      {\n" +
            "        \"key\": \"producer.cluster.dispatch.type\",\n" +
            "        \"value\": \"同地域集群优先\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"producer.cluster.dispatch.type\",\n" +
            "        \"value\": \"全部集群\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"standardLevel\": 4,\n" +
            "    \"standardReason\": \"主题单集群!!!\",\n" +
            "    \"standardTips\": \"建议同地域双集群多分区多副本\",\n" +
            "    \"standardVersion\": [\n" +
            "      {\n" +
            "        \"groupId\": \"com.meituan.mafka\",\n" +
            "        \"artifactId\": \"mafka-clientx\",\n" +
            "        \"language\": \"java\",\n" +
            "        \"version\": \"3.5.7\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"type\": \"TOPIC\",\n" +
            "    \"typeComment\": \"auto import from zk when updating\",\n" +
            "    \"typeName\": \"maoyan-mmdb-comment\",\n" +
            "    \"updateBy\": \"zhangpanwei02\",\n" +
            "    \"whiteReason\": \"\",\n" +
            "    \"isSet\":true,\n" +
            "    \"setName\":\"waimai-south\",\n" +
            "    \"setType\":\"C\"\n" +
            "  }";

    @Before
    public void setUp()  {
        report = new PaasCapacityReportProducerImpl();
        ReflectionTestUtils.setField(report, "producer", producer);
    }

    @Test
    public void paasCapacityReport() throws Exception {
        when(producer.sendAsyncMessage(Mockito.anyString(), Mockito.any(FutureCallback.class))).thenReturn(new ProducerResult(ProducerStatus.SEND_OK));
        ProducerResult result = report.paasCapacityReport(MSG);
        verify(producer).sendAsyncMessage(Mockito.anyString(), Mockito.any(FutureCallback.class));
        Assert.assertEquals(ProducerStatus.SEND_OK, result.getProducerStatus());
    }

    @Test
    public void paasCapacityReportThrowException() throws Exception {
        when(producer.sendAsyncMessage(Mockito.anyString(), Mockito.any(FutureCallback.class))).thenThrow(Exception.class);
        ProducerResult result = report.paasCapacityReport(MSG);
        verify(producer).sendAsyncMessage(Mockito.anyString(), Mockito.any(FutureCallback.class));
        Assert.assertEquals(ProducerStatus.SEND_FAILURE, result.getProducerStatus());
    }
}