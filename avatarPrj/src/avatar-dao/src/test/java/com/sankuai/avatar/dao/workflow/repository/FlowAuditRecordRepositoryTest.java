package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowAuditRecordEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditRecordAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditRecordUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;

@Slf4j
public class FlowAuditRecordRepositoryTest {

    private final FlowAuditRecordRepository repository;

    public FlowAuditRecordRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowAuditRecordRepository) ctx.getBean("flowAuditRecordRepositoryImpl");
    }

    @Test(expected = NullPointerException.class)
    public void testAddAuditRecordNodeNullRequest() {
        repository.addAuditRecord(null);

    }

    @Test(expected = NullPointerException.class)
    public void testAddAuditRecordNullFlowIdRequest() {
        repository.addAuditRecord(FlowAuditRecordAddRequest.builder().build());
    }

    @Test
    public void testAddAuditRecord() {
        FlowAuditRecordAddRequest request = FlowAuditRecordAddRequest.builder()
                .auditNodeId(1)
                .auditor("zhaozhifan02")
                .comment("test")
                .auditOperation(1)
                .build();
        boolean status = repository.addAuditRecord(request);
        Assert.assertTrue(status);
        log.info("Add AuditRecord:{} status {}", request.getId(), status);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateAuditRecordNullRequest() {
        repository.updateAuditRecord(null);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateAuditRecordNullFlowIdRequest() {
        repository.updateAuditRecord(FlowAuditRecordUpdateRequest.builder().build());
    }

    @Test
    public void updateAuditRecord() {
        FlowAuditRecordUpdateRequest request = FlowAuditRecordUpdateRequest.builder()
                .id(1)
                .auditOperation(2)
                .operateTime(new Date())
                .build();
        boolean status = repository.updateAuditRecord(request);
        Assert.assertTrue(status);
        log.info("Update AuditRecord:{} status {}", request.getId(), status);
    }

    @Test
    public void testQueryAuditRecordNullAuditNodeId() {
        List<FlowAuditRecordEntity> entityList = repository.queryAuditRecord(null);
        Assert.assertTrue(entityList.isEmpty());
    }

    @Test
    public void testQueryAuditRecord() {
        Integer auditNodeId = 1;
        List<FlowAuditRecordEntity> entityList = repository.queryAuditRecord(auditNodeId);
        Assert.assertFalse(entityList.isEmpty());
        log.info("Query AuditNode(id:{}) records:{}", auditNodeId, entityList);
    }
}