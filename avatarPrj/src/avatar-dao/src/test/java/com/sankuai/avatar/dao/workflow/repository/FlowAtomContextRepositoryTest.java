package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowAtomContextEntity;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class FlowAtomContextRepositoryTest {

    private final FlowAtomContextRepository repository;

    public FlowAtomContextRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowAtomContextRepository) ctx.getBean("flowAtomContextRepositoryImpl");
    }

    @Test
    @Rollback
    @Transactional
    public void testAddFlowAtomContext() {
        FlowAtomContextAddRequest request = FlowAtomContextAddRequest.builder()
                .atomName("JumperUserUnlock")
                .seq(1)
                .flowId(111)
                .status("NEW")
                .build();
        boolean status = repository.addFlowAtomContext(request);
        Assert.assertTrue(status);
        log.info("add flowAtomContext(id:{})", request.getId());
    }

    @Test
    public void testGetFlowAtomContextByFlowId() {
        Integer flowId = 111;
        List<FlowAtomContextEntity> entities = repository.getFlowAtomContextByFlowId(flowId);
        Assert.assertFalse(entities.isEmpty());
        log.info("Flow id:{} data {}", flowId, entities);
    }

    @Test
    public void testGetFlowAtomContextByEmptyFlowId() {
        Integer flowId = null;
        List<FlowAtomContextEntity> entities = repository.getFlowAtomContextByFlowId(flowId);
        Assert.assertTrue(entities.isEmpty());
        log.info("Flow id:{} data {}", flowId, entities);
    }

    @Test
    public void testUpdateFlowAtomContext() {
        FlowAtomContextUpdateRequest request = FlowAtomContextUpdateRequest.builder()
                .id(75)
                .atomName("JumperUserUnlock")
                .seq(1)
                .flowId(111)
                .status("SUCCESS")
                .build();
        boolean status = repository.updateFlowAtomContext(request);
        Assert.assertTrue(status);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateFlowAtomContextByEmptyPk() {
        FlowAtomContextUpdateRequest request = FlowAtomContextUpdateRequest.builder()
                .atomName("JumperUserUnlock")
                .seq(1)
                .flowId(111)
                .status("SUCCESS")
                .build();
        boolean status = repository.updateFlowAtomContext(request);
        Assert.assertFalse(status);
    }
}