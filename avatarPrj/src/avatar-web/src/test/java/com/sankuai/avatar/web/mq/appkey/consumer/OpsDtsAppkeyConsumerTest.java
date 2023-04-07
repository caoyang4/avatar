package com.sankuai.avatar.web.mq.appkey.consumer;

import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEvent;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventData;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.scheduer.AppkeyCollectEventScheduler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OpsDtsAppkeyConsumerTest {

    @Mock
    private AppkeyCollectEventScheduler mockAppkeyCollectEventScheduler;

    private OpsDtsAppkeyConsumer opsDtsAppkeyConsumerUnderTest;

    @Before
    public void setUp() throws Exception {
        opsDtsAppkeyConsumerUnderTest = new OpsDtsAppkeyConsumer(mockAppkeyCollectEventScheduler);
    }

    @Test
    public void testConsume() {
        // Setup
        String msg = "{\n" +
                "    \"tableName\": \"cmdb_test.service_service\",\n" +
                "    \"timestamp\": 1670985366000,\n" +
                "    \"scn\": 1055588832,\n" +
                "    \"type\": \"update\",\n" +
                "    \"sourceIP\": \"10.171.128.32\",\n" +
                "    \"data\": {\n" +
                "        \"host_prefix\": \"\",\n" +
                "        \"single_host_restart\": 1,\n" +
                "        \"backup_rd\": null,\n" +
                "        \"ep_admin\": \"huangweiwei,quanzhiyu,songjiyang,zhaocongcong03\",\n" +
                "        \"project_status\": null,\n" +
                "        \"language\": \"\",\n" +
                "        \"op_admin\": \"wangnaien\",\n" +
                "        \"is_liteset\": 0,\n" +
                "        \"meet_soa\": 0,\n" +
                "        \"rank\": \"核心服务\",\n" +
                "        \"containerable\": 1,\n" +
                "        \"id\": 1748,\n" +
                "        \"tenant\": \"\",\n" +
                "        \"scale_config\": \"{\\\"staging\\\":{\\\"mem_size\\\":8, \\\"cpu_count\\\":4, \\\"disk_size\\\":300}, \\\"ppe\\\":{\\\"mem_size\\\":8, \\\"cpu_count\\\":4, \\\"disk_size\\\":10}, \\\"ml\\\":{\\\"mem_size\\\":8, \\\"cpu_count\\\":4, \\\"disk_size\\\":200}, \\\"dev\\\":{\\\"mem_size\\\":4, \\\"cpu_count\\\":2, \\\"disk_size\\\":100}, \\\"beta\\\":{\\\"mem_size\\\":8, \\\"cpu_count\\\":4, \\\"disk_size\\\":10}, \\\"test\\\":{\\\"mem_size\\\":8, \\\"cpu_count\\\":8, \\\"disk_size\\\":100}, \\\"prod\\\":{\\\"mem_size\\\":8, \\\"cpu_count\\\":4, \\\"disk_size\\\":300}}\",\n" +
                "        \"capacity_update_by\": \"octoAuth:com.sankuai.avatar.web\",\n" +
                "        \"project_type\": \"\",\n" +
                "        \"switch_divide_max\": 1,\n" +
                "        \"module\": \"\",\n" +
                "        \"container_divide_max\": 1,\n" +
                "        \"name\": \"火车票c端服务\",\n" +
                "        \"update_at\": 1669095561000,\n" +
                "        \"container_type\": null,\n" +
                "        \"min_instances\": null,\n" +
                "        \"stateful_reason\": \"批量确认无状态-from_hulk_sync_task\",\n" +
                "        \"uncontainerable_reason\": \"服务单元和绑定的 AppKey 不是 1:1 的\",\n" +
                "        \"risk_host_rate\": 0,\n" +
                "        \"rd_owner\": \"\",\n" +
                "        \"capacity_update_at\": 1670852308000,\n" +
                "        \"capacity\": 5,\n" +
                "        \"network\": \"\",\n" +
                "        \"create_by\": \"\",\n" +
                "        \"compatible_ipv6\": 1,\n" +
                "        \"rd_admin\": \"gaoyongkun,huyue03,wuyutong03,litao77,pengweikang,jianyufeng,suntingting12,huixianwang,chenguixu,shichaoqian,yangzhe14,zhaocongcong03,liyuzheng02,lipu05,pubaojian,luyouyuan,quanzhiyu,xuhuaxiu\",\n" +
                "        \"duty_admin\": \"huixianwang\",\n" +
                "        \"capacity_reason\": \"该服务已接入弹性伸缩，且满足3级容灾，判定容灾等级为5\",\n" +
                "        \"soasrv\": \"\",\n" +
                "        \"soamod\": \"\",\n" +
                "        \"key\": \"meituan.train.train.server\",\n" +
                "        \"container_divide_rate\": 1,\n" +
                "        \"stateful\": 0,\n" +
                "        \"rack_divide_rate\": 1,\n" +
                "        \"rack_divide_max\": 1,\n" +
                "        \"is_set\": 0,\n" +
                "        \"switch_divide_rate\": 1,\n" +
                "        \"beta_deploy_owner\": \"[]\",\n" +
                "        \"autoscale\": null,\n" +
                "        \"service_type\": \"Code: Java\",\n" +
                "        \"upstream_name\": \"\",\n" +
                "        \"comment\": \"火车票售前API；提供交通首页聚合信息，车次列表，车次详情，渠道分发，乘车人增删改查，推荐等内容；面向C端用户侧；适合各业务提供页面的情况下，快速搭建火车票售前流程。\",\n" +
                "        \"appkey\": \"appkey\",\n" +
                "        \"soaapp\": \"\",\n" +
                "        \"create_at\": 1459824249000,\n" +
                "        \"octo\": \"\",\n" +
                "        \"category\": \"\"\n" +
                "    },\n" +
                "    \"diffMapJson\": \"{\\\"rd_admin\\\":\\\"huixianwang,huyue03,jianyufeng,litao77,liyuzheng02,pubaojian,quanzhiyu,shichaoqian,xuhuaxiu,zhaocongcong03,wuyutong03,yangzhe14\\\"}\"\n" +
                "}";
        // Configure AppkeyCollectEventScheduler.collect(...).
        final Appkey appkey = new Appkey("appkey");
        when(mockAppkeyCollectEventScheduler.collect(AppkeyCollectEvent.builder()
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .collectEventData(AppkeyCollectEventData.of("appkey", any()))
                .build())).thenReturn(appkey);
        when(mockAppkeyCollectEventScheduler.collect(AppkeyCollectEvent.builder()
                .collectEventName(CollectEventName.OPS_APPKEY_UPDATE)
                .collectEventData(AppkeyCollectEventData.of("appkey", any()))
                .build())).thenReturn(appkey);
        // Run the test
        final ConsumeStatus result = opsDtsAppkeyConsumerUnderTest.consume(msg);

        // Verify the results
        assertThat(result).isEqualTo(ConsumeStatus.CONSUME_SUCCESS);
    }
}
