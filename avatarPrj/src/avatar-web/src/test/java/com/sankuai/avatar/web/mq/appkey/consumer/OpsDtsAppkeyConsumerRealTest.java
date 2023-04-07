package com.sankuai.avatar.web.mq.appkey.consumer;

import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.sankuai.avatar.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class OpsDtsAppkeyConsumerRealTest extends TestBase {

    @Autowired
    private OpsDtsAppkeyConsumer opsDtsAppkeyConsumerUnderTest;

    @Test
    public void testInsertConsume() {
        // Setup
        String msg = "{\n" +
                "    \"tableName\": \"cmdb.service_service\",\n" +
                "    \"timestamp\": 1675391170000,\n" +
                "    \"scn\": 8131437961,\n" +
                "    \"type\": \"insert\",\n" +
                "    \"sourceIP\": \"10.40.200.49\",\n" +
                "    \"data\": {\n" +
                "        \"host_prefix\": \"\",\n" +
                "        \"single_host_restart\": 0,\n" +
                "        \"backup_rd\": null,\n" +
                "        \"ep_admin\": \"\",\n" +
                "        \"project_status\": null,\n" +
                "        \"language\": \"\",\n" +
                "        \"op_admin\": \"\",\n" +
                "        \"is_liteset\": 0,\n" +
                "        \"meet_soa\": 0,\n" +
                "        \"rank\": \"\",\n" +
                "        \"containerable\": 0,\n" +
                "        \"id\": 845998,\n" +
                "        \"tenant\": \"\",\n" +
                "        \"scale_config\": \"{\\\"staging\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":100},\\\"ppe\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"dev\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"beta\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"test\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"prod\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":200}}\",\n" +
                "        \"capacity_update_by\": \"\",\n" +
                "        \"project_type\": \"\",\n" +
                "        \"switch_divide_max\": null,\n" +
                "        \"module\": \"\",\n" +
                "        \"container_divide_max\": null,\n" +
                "        \"name\": \"\",\n" +
                "        \"update_at\": 1675391171000,\n" +
                "        \"container_type\": null,\n" +
                "        \"min_instances\": null,\n" +
                "        \"stateful_reason\": \"RD未确认\",\n" +
                "        \"uncontainerable_reason\": \"\",\n" +
                "        \"risk_host_rate\": null,\n" +
                "        \"rd_owner\": \"\",\n" +
                "        \"capacity_update_at\": null,\n" +
                "        \"capacity\": null,\n" +
                "        \"network\": \"\",\n" +
                "        \"create_by\": \"\",\n" +
                "        \"compatible_ipv6\": 1,\n" +
                "        \"rd_admin\": \"\",\n" +
                "        \"duty_admin\": \"\",\n" +
                "        \"capacity_reason\": \"\",\n" +
                "        \"soasrv\": \"\",\n" +
                "        \"soamod\": \"\",\n" +
                "        \"key\": \"meituan.sdk.plus.phoenixmessage-dxofflinepush-iface\",\n" +
                "        \"container_divide_rate\": null,\n" +
                "        \"stateful\": 1,\n" +
                "        \"rack_divide_rate\": null,\n" +
                "        \"rack_divide_max\": null,\n" +
                "        \"is_set\": 0,\n" +
                "        \"switch_divide_rate\": null,\n" +
                "        \"beta_deploy_owner\": \"[]\",\n" +
                "        \"autoscale\": null,\n" +
                "        \"service_type\": \"\",\n" +
                "        \"upstream_name\": \"\",\n" +
                "        \"comment\": \"\",\n" +
                "        \"appkey\": \"\",\n" +
                "        \"soaapp\": \"\",\n" +
                "        \"create_at\": 1675391171000,\n" +
                "        \"octo\": \"\",\n" +
                "        \"category\": \"\"\n" +
                "    }\n" +
                "}";
        // Configure AppkeyCollectEventScheduler.collect(...).
        // Run the test
        final ConsumeStatus result = opsDtsAppkeyConsumerUnderTest.consume(msg);

        // Verify the results
        assertThat(result).isEqualTo(ConsumeStatus.CONSUME_SUCCESS);
    }

    @Test
    public void testUpdateConsume() {
        // Setup
        String msg = "{\n" +
                "    \"tableName\": \"cmdb.service_service\",\n" +
                "    \"timestamp\": 1675391051000,\n" +
                "    \"scn\": 8131419406,\n" +
                "    \"type\": \"update\",\n" +
                "    \"sourceIP\": \"10.40.200.49\",\n" +
                "    \"data\": {\n" +
                "        \"host_prefix\": \"\",\n" +
                "        \"single_host_restart\": 1,\n" +
                "        \"backup_rd\": null,\n" +
                "        \"ep_admin\": \"qinwei05\",\n" +
                "        \"project_status\": null,\n" +
                "        \"language\": \"\",\n" +
                "        \"op_admin\": \"qinwei05\",\n" +
                "        \"is_liteset\": 0,\n" +
                "        \"meet_soa\": 1,\n" +
                "        \"rank\": \"核心服务\",\n" +
                "        \"containerable\": 0,\n" +
                "        \"id\": 751449,\n" +
                "        \"tenant\": \"isol\",\n" +
                "        \"scale_config\": \"{\\\"staging\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":100},\\\"ppe\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"dev\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"beta\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"test\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"prod\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":200}}\",\n" +
                "        \"capacity_update_by\": \"token.avatar\",\n" +
                "        \"project_type\": \"\",\n" +
                "        \"switch_divide_max\": 0,\n" +
                "        \"module\": \"\",\n" +
                "        \"container_divide_max\": 0,\n" +
                "        \"name\": \"app4\",\n" +
                "        \"update_at\": 1669052464000,\n" +
                "        \"container_type\": \"\",\n" +
                "        \"min_instances\": null,\n" +
                "        \"stateful_reason\": \"\",\n" +
                "        \"uncontainerable_reason\": \"服务单元和应用 PLUS 不是 1:1 的;服务类型为DB,KV: Cellar,DW,LB,MQ,Other,db,mq,Code: Node.js,Code: PHP,Code: Go,Code: Python之类的不可容器化\",\n" +
                "        \"risk_host_rate\": 0,\n" +
                "        \"rd_owner\": \"\",\n" +
                "        \"capacity_update_at\": 1663061426000,\n" +
                "        \"capacity\": -1,\n" +
                "        \"network\": \"\",\n" +
                "        \"create_by\": \"\",\n" +
                "        \"compatible_ipv6\": 1,\n" +
                "        \"rd_admin\": \"qinwei05,caoyang42\",\n" +
                "        \"duty_admin\": \"\",\n" +
                "        \"capacity_reason\": \"该服务下机器数量为0，avatar不予计算容灾等级\",\n" +
                "        \"soasrv\": \"app4isolt\",\n" +
                "        \"soamod\": \"app4\",\n" +
                "        \"key\": \"dianping.tbd.tools.avatartestapp-app4-app4isolt\",\n" +
                "        \"container_divide_rate\": 1,\n" +
                "        \"stateful\": 0,\n" +
                "        \"rack_divide_rate\": 1,\n" +
                "        \"rack_divide_max\": 0,\n" +
                "        \"is_set\": 0,\n" +
                "        \"switch_divide_rate\": 1,\n" +
                "        \"beta_deploy_owner\": \"[]\",\n" +
                "        \"autoscale\": null,\n" +
                "        \"service_type\": \"Other: Other\",\n" +
                "        \"upstream_name\": \"\",\n" +
                "        \"comment\": \"app4\",\n" +
                "        \"appkey\": \"\",\n" +
                "        \"soaapp\": \"AvatarTestApp\",\n" +
                "        \"create_at\": 1659631525000,\n" +
                "        \"octo\": \"\",\n" +
                "        \"category\": \"\"\n" +
                "    },\n" +
                "    \"diffMapJson\": \"{\\\"appkey\\\":\\\"com.sankuai.avatartestapp.app4.app4isolt\\\"}\"\n" +
                "}";
        // Configure AppkeyCollectEventScheduler.collect(...).
        // Run the test
        final ConsumeStatus result = opsDtsAppkeyConsumerUnderTest.consume(msg);

        // Verify the results
        assertThat(result).isEqualTo(ConsumeStatus.CONSUME_SUCCESS);
    }

    @Test
    public void testDeleteConsume() {
        // Setup
        String msg = "{\n" +
                "    \"tableName\": \"cmdb.service_service\",\n" +
                "    \"timestamp\": 1675391051000,\n" +
                "    \"scn\": 8131419411,\n" +
                "    \"type\": \"delete\",\n" +
                "    \"sourceIP\": \"10.40.200.49\",\n" +
                "    \"data\": {\n" +
                "        \"host_prefix\": \"\",\n" +
                "        \"single_host_restart\": 1,\n" +
                "        \"backup_rd\": null,\n" +
                "        \"ep_admin\": \"qinwei05\",\n" +
                "        \"project_status\": null,\n" +
                "        \"language\": \"\",\n" +
                "        \"op_admin\": \"qinwei05\",\n" +
                "        \"is_liteset\": 0,\n" +
                "        \"meet_soa\": 1,\n" +
                "        \"rank\": \"核心服务\",\n" +
                "        \"containerable\": 0,\n" +
                "        \"id\": 751449,\n" +
                "        \"tenant\": \"isol\",\n" +
                "        \"scale_config\": \"{\\\"staging\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":100},\\\"ppe\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"dev\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"beta\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"test\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":10},\\\"prod\\\":{\\\"mem_size\\\":8,\\\"cpu_count\\\":4,\\\"disk_size\\\":200}}\",\n" +
                "        \"capacity_update_by\": \"token.avatar\",\n" +
                "        \"project_type\": \"\",\n" +
                "        \"switch_divide_max\": 0,\n" +
                "        \"module\": \"\",\n" +
                "        \"container_divide_max\": 0,\n" +
                "        \"name\": \"app4\",\n" +
                "        \"update_at\": 1669052464000,\n" +
                "        \"container_type\": \"\",\n" +
                "        \"min_instances\": null,\n" +
                "        \"stateful_reason\": \"\",\n" +
                "        \"uncontainerable_reason\": \"服务单元和应用 PLUS 不是 1:1 的;服务类型为DB,KV: Cellar,DW,LB,MQ,Other,db,mq,Code: Node.js,Code: PHP,Code: Go,Code: Python之类的不可容器化\",\n" +
                "        \"risk_host_rate\": 0,\n" +
                "        \"rd_owner\": \"\",\n" +
                "        \"capacity_update_at\": 1663061426000,\n" +
                "        \"capacity\": -1,\n" +
                "        \"network\": \"\",\n" +
                "        \"create_by\": \"\",\n" +
                "        \"compatible_ipv6\": 1,\n" +
                "        \"rd_admin\": \"qinwei05,caoyang42\",\n" +
                "        \"duty_admin\": \"\",\n" +
                "        \"capacity_reason\": \"该服务下机器数量为0，avatar不予计算容灾等级\",\n" +
                "        \"soasrv\": \"app4isolt\",\n" +
                "        \"soamod\": \"app4\",\n" +
                "        \"key\": \"dianping.tbd.tools.avatartestapp-app4-app4isolt\",\n" +
                "        \"container_divide_rate\": 1,\n" +
                "        \"stateful\": 0,\n" +
                "        \"rack_divide_rate\": 1,\n" +
                "        \"rack_divide_max\": 0,\n" +
                "        \"is_set\": 0,\n" +
                "        \"switch_divide_rate\": 1,\n" +
                "        \"beta_deploy_owner\": \"[]\",\n" +
                "        \"autoscale\": null,\n" +
                "        \"service_type\": \"Other: Other\",\n" +
                "        \"upstream_name\": \"\",\n" +
                "        \"comment\": \"app4\",\n" +
                "        \"appkey\": \"\",\n" +
                "        \"soaapp\": \"AvatarTestApp\",\n" +
                "        \"create_at\": 1659631525000,\n" +
                "        \"octo\": \"\",\n" +
                "        \"category\": \"\"\n" +
                "    }\n" +
                "}";
        // Configure AppkeyCollectEventScheduler.collect(...).
        // Run the test
        final ConsumeStatus result = opsDtsAppkeyConsumerUnderTest.consume(msg);

        // Verify the results
        assertThat(result).isEqualTo(ConsumeStatus.CONSUME_SUCCESS);
    }
}
