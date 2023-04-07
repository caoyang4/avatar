package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomRecordAddRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FlowAtomRecordRepositoryTest {

    private final FlowAtomRecordRepository repository;

    public FlowAtomRecordRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowAtomRecordRepository) ctx.getBean("flowAtomRecordRepositoryImpl");
    }

    @Test
    public void testAddAtomRecord() {
        FlowAtomRecordAddRequest request = FlowAtomRecordAddRequest.builder()
                .atomName("JumperUserUnlock")
                .flowId(111)
                .build();
        boolean status = repository.addAtomRecord(request);
        Assert.assertTrue(status);
    }
}