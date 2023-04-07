package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowTemplateEntity;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class FlowTemplateRepositoryTest {

    private final FlowTemplateRepository repository;

    public FlowTemplateRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowTemplateRepository) ctx.getBean("flowTemplateRepositoryImpl");
    }

    @Test
    public void getFlowTemplateByName() {
        String templateName = "delegate_work";
        FlowTemplateEntity flowTemplateEntity = repository.getFlowTemplateByName(templateName);
        Assert.assertNotNull(flowTemplateEntity);
        log.info("TemplateName {} templateData {}", templateName, flowTemplateEntity);
    }
}