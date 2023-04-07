package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.AtomStepEntity;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class AtomStepRepositoryTest {

    private final AtomStepRepository repository;

    public AtomStepRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (AtomStepRepository) ctx.getBean("atomStepRepositoryImpl");
    }

    @Test
    public void testGetAtomStepByName() {
        String atomName = "JumperUserUnlock";
        AtomStepEntity atomStepEntity = repository.getAtomStepByName(atomName);
        Assert.assertNotNull(atomStepEntity);
        log.info("atomStep {} data: {}", atomName, atomStepEntity);
    }

    @Test
    public void testGetAtomStepWithEmptyName() {
        String atomName = "";
        AtomStepEntity atomStepEntity = repository.getAtomStepByName(atomName);
        Assert.assertNull(atomStepEntity);
        log.info("atomStep {} data: {}", atomName, atomStepEntity);
    }
}