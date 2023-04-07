package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.PermissionEntity;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.workflow.repository.request.PermissionRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@Slf4j
public class PermissionRepositoryTest {

    private static final String TEST_TEMPLATE_NAME = "jumper_add_perm";

    private static final String PERMISSION_ROLE = "any";

    private static final String PERMISSION_IS_APPLY = "Y";

    private final PermissionRepository repository;

    public PermissionRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (PermissionRepository) ctx.getBean("permissionRepositoryImpl");
    }

    @Test
    public void testQuery() {
        PermissionRequest request = PermissionRequest.builder()
                .templateName(TEST_TEMPLATE_NAME).role(PERMISSION_ROLE).isApply(PERMISSION_IS_APPLY).build();
        List<PermissionEntity> permissionEntities = repository.query(request);
        Assert.assertNotNull(permissionEntities);
        log.info("{} permission {}", TEST_TEMPLATE_NAME, permissionEntities);
    }
}