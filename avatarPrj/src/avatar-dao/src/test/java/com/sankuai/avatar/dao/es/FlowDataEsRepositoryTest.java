package com.sankuai.avatar.dao.es;

import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.es.impl.FlowDataEsRepositoryImpl;
import com.sankuai.avatar.dao.es.request.FlowDataUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

@Slf4j
public class FlowDataEsRepositoryTest {

    private final FlowDataEsRepository repository;

    public FlowDataEsRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowDataEsRepositoryImpl) ctx.getBean("flowDataEsRepositoryImpl");
        ReflectionTestUtils.setField(repository, "indexPrefix", "avatar_workflow_service_flows");
    }

    @Test
    public void testUpdateNull() {
        boolean status = repository.update(null);
        Assert.assertFalse(status);
    }

    @Test
    public void testUpdate() {
        String json = "{\"id\": 2168913,\"template_name\":\"reduced_service\",\"object_type\":\"\",\"create_user\":\"ningyueqiang\",\"is_failed\":1,\"create_user_name\":\"宁越强\",\"index\":4,\"approve_users\":\"\",\"uuid\":\"6635b364-cd5a-4cf7-8652-6e09edbc5e90\",\"approved_role\":\"auto\",\"object_name\":\"EcsOffline\",\"version\":0,\"env\":\"prod\",\"week_of_year\":\"W38(2022-09-12~2022-09-18)\",\"status\":\"SUCCESS\",\"approve_type\":0,\"start_time\":\"2022-09-15 20:28:42\",\"reason\":null,\"cn_name\":\"机器下线\",\"appkey\":\"com.sankuai.banma.business.lbsweb\",\"approve_status\":\"AUTO_ACCESS\",\"template_cn_name\":\"机器下线\",\"interval\":24,\"srv\":\"meituan.banma.business.lbsweb\",\"end_time\":\"2022-09-15 20:29:06\",\"flow_type\":2,\"template_id\":6}";
        FlowDataUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowDataUpdateRequest.class);
        updateRequest.setId(updateRequest.getId());
        boolean status = repository.update(updateRequest);
        Assert.assertTrue(status);
        log.info("Update work flow(id:{}) status {}", updateRequest.getUuid(), status);
    }
}