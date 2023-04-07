package com.sankuai.avatar.web.mq.capacity.consumer;

import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.sankuai.avatar.web.exception.ValueValidException;
import com.sankuai.avatar.web.mq.capacity.consumer.impl.PaasCapacityReportConsumerImpl;
import com.sankuai.avatar.web.service.AppkeyPaasCapacityService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
/**
 * @author caoyang
 * @create 2023-03-01 10:32
 */
@RunWith(MockitoJUnitRunner.class)
public class PaasCapacityReportConsumerTest {

    private PaasCapacityReportConsumer consumer;

    @Mock
    private AppkeyPaasCapacityService service;

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
    public void setUp(){
        consumer = new PaasCapacityReportConsumerImpl(service);
    }

    @Test
    public void consume() {
        when(service.reportAppkeyPaasCapacity(Mockito.anyList())).thenReturn(true);
        ConsumeStatus status = consumer.consume(MSG);
        verify(service).reportAppkeyPaasCapacity(Mockito.anyList());
        Assert.assertEquals(ConsumeStatus.CONSUME_SUCCESS, status);
    }

    @Test
    public void consumeThrowException() {
        when(service.reportAppkeyPaasCapacity(Mockito.anyList())).thenThrow(ValueValidException.class);
        ConsumeStatus status = consumer.consume(MSG);
        verify(service).reportAppkeyPaasCapacity(Mockito.anyList());
    }
}