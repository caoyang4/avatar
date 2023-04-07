package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowProcessEntity;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@Slf4j
public class FlowProcessRepositoryTest {

    private final FlowProcessRepository repository;

    public FlowProcessRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowProcessRepository) ctx.getBean("flowProcessRepositoryImpl");
    }


    @Test
    public void addFlowProcess() {
        FlowProcessEntity request = FlowProcessEntity.builder()
                .flowId(109107)
                .flowUuid("47dba7f1-0546-47e7-8f84-1d93cc170f0e")
                .name("preCheckProcess")
                .state(1)
                .seq(0)
                .build();
        boolean status = repository.addFlowProcess(request);
        Assert.assertTrue(status);
        log.info("Add FlowProcess status: {}", status);
    }

    @Test
    public void getFlowProcessByFlowId() {
        Integer flowId = 109107;
        List<FlowProcessEntity> flowProcessEntityList = repository.getFlowProcessByFlowId(flowId);
        Assert.assertNotNull(flowProcessEntityList);
        log.info("Flow id:{} process: {}", flowId, flowProcessEntityList);
    }

    @Test
    public void getFlowProcessByFlowUuid() {
        String flowUuid = "47dba7f1-0546-47e7-8f84-1d93cc170f0e";
        List<FlowProcessEntity> flowProcessEntityList = repository.getFlowProcessByFlowUuid(flowUuid);
        Assert.assertNotNull(flowProcessEntityList);
        log.info("Flow uuid:{} process: {}", flowUuid, flowProcessEntityList);
    }

    @Test
    public void updateFlowProcess() {
        FlowProcessEntity request = FlowProcessEntity.builder()
                .id(5997)
                .state(2)
                .build();
        boolean status = repository.updateFlowProcess(request);
        Assert.assertTrue(status);
    }
}
