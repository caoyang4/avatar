package com.sankuai.avatar.dao.es;

import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.es.impl.FlowAtomEsRepositoryImpl;
import com.sankuai.avatar.dao.es.request.FlowAtomUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

@Slf4j
public class FlowAtomEsRepositoryTest {

    private final FlowAtomEsRepository repository;

    public FlowAtomEsRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowAtomEsRepositoryImpl) ctx.getBean("flowAtomEsRepositoryImpl");
        ReflectionTestUtils.setField(repository, "indexPrefix", "avatar_workflow_service_atoms");

    }

    @Test
    public void testUpdateNull() {
        boolean status = repository.update(null);
        Assert.assertFalse(status);
    }

    @Test
    public void testUpdate() {
        String json = "{\"status\":\"SUCCESS\",\"uuid\":\"49d047fe-6f71-4d67-9cfe-81fa2c273545\",\"exec_time\":0,\"template_name\":\"service_expand\",\"start_time\":\"2022-09-15 20:08:08\",\"end_time\":\"2022-09-15 20:08:08\",\"flow_id\":2168772,\"errs\":null,\"atom_name\":\"NodeRegisterOcto\",\"atom_cn_name\":\"octo http 节点注册\"}";
        FlowAtomUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowAtomUpdateRequest.class);
        boolean status = repository.update(updateRequest);
        Assert.assertTrue(status);
        log.info("Update atom flow(id:{}) status {}", updateRequest.getUuid(), status);
    }
}