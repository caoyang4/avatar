package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowAtomTemplateEntity;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@Slf4j
public class FlowAtomTemplateRepositoryTest {

    private final FlowAtomTemplateRepository repository;

    public FlowAtomTemplateRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowAtomTemplateRepository) ctx.getBean("flowAtomTemplateRepositoryImpl");
    }

    @Test
    public void testGetFlowAtomTemplate() {
        String templateName = "user_unlock";
        List<FlowAtomTemplateEntity> entities = repository.getFlowAtomTemplate(templateName, 0);
        Assert.assertFalse(entities.isEmpty());
        log.info("Flow templateName:{} data {}", templateName, entities);
    }
}