package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowAuditNodeEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditNodeAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditNodeUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@Slf4j
public class FlowAuditNodeRepositoryTest {

    private final FlowAuditNodeRepository repository;

    public FlowAuditNodeRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowAuditNodeRepository) ctx.getBean("flowAuditNodeRepositoryImpl");
    }

    @Test(expected = NullPointerException.class)
    public void testAddAuditNodeNullRequest() {
        repository.addAuditNode(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddAuditNodeNullFlowIdRequest() {
        repository.addAuditNode(FlowAuditNodeAddRequest.builder().build());
    }

    @Test
    public void testAddAuditNode() {
        FlowAuditNodeAddRequest request = FlowAuditNodeAddRequest.builder()
                .name("SRE审核")
                .flowId(1)
                .processId(1)
                .auditor("zhaozhifan02")
                .auditType(1)
                .state(1)
                .stateName("AUDITING")
                .build();
        boolean status = repository.addAuditNode(request);
        Assert.assertTrue(status);
        log.info("Add AuditNode:{} status {}", request.getId(), status);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateAuditNodeNullRequest() {
        repository.updateAuditNode(null);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateAuditNodeNullFlowIdRequest() {
        repository.updateAuditNode(FlowAuditNodeUpdateRequest.builder().build());
    }

    @Test
    public void testUpdateAuditChain() {
        FlowAuditNodeUpdateRequest request = FlowAuditNodeUpdateRequest.builder()
                .id(5)
                .flowId(1)
                .stateName("ACCEPT")
                .build();
        boolean status = repository.updateAuditNode(request);
        Assert.assertTrue(status);
        log.info("Update AuditNode:{} status {}", request.getId(), status);
    }

    @Test
    public void testQueryAuditChainNullFlowId() {
        List<FlowAuditNodeEntity> entityList = repository.queryAuditNode(null);
        Assert.assertTrue(entityList.isEmpty());
    }


    @Test
    public void testQueryAuditChain() {
        Integer flowId = 2;
        List<FlowAuditNodeEntity> entityList = repository.queryAuditNode(flowId);
        Assert.assertFalse(entityList.isEmpty());
        log.info("Query flow(id:{}) AuditNode:{}", flowId, entityList);
    }
}