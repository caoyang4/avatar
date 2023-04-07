package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEventEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class FlowEventRepositoryTest {

    private final FlowEventRepository repository;

    public FlowEventRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowEventRepository) ctx.getBean("flowEventRepositoryImpl");
    }

    @Test
    public void testAddFlowEvent() {
        FlowEventAddRequest request = FlowEventAddRequest.builder()
                .loginName("zhaozhifan02")
                .flowId(1)
                .sourceDomain("avatar.mws.cloud.test.sankuai.com")
                .sourceIp("1.1.1.1")
                .build();
        boolean status = repository.addFlowEvent(request);
        log.info("flow(id:{}) save event(id:{}) status:{}", request.getFlowId(), request.getId(), status);
        Assert.assertTrue(status);
    }

    @Test(expected = NullPointerException.class)
    public void testAddFlowEventNullRequest() {
        boolean status = repository.addFlowEvent(null);
        Assert.assertFalse(status);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateFlowEventNullRequest() {
        boolean status = repository.updateFlowEvent(null);
        Assert.assertFalse(status);
    }

    @Test
    public void testUpdateFlowEvent() {
        FlowEventUpdateRequest request = FlowEventUpdateRequest.builder()
                .id(5)
                .flowId(1)
                .startTime(1677733447170L)
                .build();
        boolean status = repository.updateFlowEvent(request);
        log.info("flow(id:{}) update event(id:{}) status:{}", request.getFlowId(), request.getId(), status);
        Assert.assertTrue(status);
    }

    @Test
    public void testGetFlowEventByFlowId() {
        Integer flowId = 1;
        FlowEventEntity flowEventEntity = repository.getFlowEventByFlowId(flowId);
        log.info("get flow(id:{}) event data {}", flowId, flowEventEntity);
        Assert.assertNotNull(flowEventEntity);
    }

    @Test(expected = NullPointerException.class)
    public void testGetFlowEventByEmptyFlowId() {
        Integer flowId = null;
        FlowEventEntity flowEventEntity = repository.getFlowEventByFlowId(flowId);
        log.info("get flow(id:{}) event data {}", flowId, flowEventEntity);
        Assert.assertNull(flowEventEntity);
    }
}