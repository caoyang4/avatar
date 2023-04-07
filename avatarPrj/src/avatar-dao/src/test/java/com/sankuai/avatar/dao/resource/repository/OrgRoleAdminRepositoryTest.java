package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.impl.OrgRoleAdminRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.model.OrgRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.OrgRoleAdminRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author caoyang
 * @create 2022-11-01 22:07
 */
public class OrgRoleAdminRepositoryTest extends TestBase {

    private final OrgRoleAdminRepository repository;

    public OrgRoleAdminRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (OrgRoleAdminRepositoryImpl) ctx.getBean("orgRoleAdminRepositoryImpl");
    }

    static OrgRoleAdminDO orgRoleAdminDO = new OrgRoleAdminDO();
    static {
        orgRoleAdminDO.setOrgId(UUID.randomUUID().toString().substring(0,8));
        orgRoleAdminDO.setOrgName("unit");
        orgRoleAdminDO.setRole("op_admin");
        orgRoleAdminDO.setRoleUsers("unit");
        orgRoleAdminDO.setUpdateUser("unit");
        orgRoleAdminDO.setOrgPath("unit");
    }

    @Test
    public void query() {
        List<String> orgIds = Arrays.asList("104074", "105289");
        List<OrgRoleAdminDO> query = repository.query(OrgRoleAdminRequest.builder().orgIds(orgIds).build());
        Assert.assertEquals(orgIds.size(), query.size());
    }

    @Test
    public void insert() {
        Assert.assertTrue(repository.insert(orgRoleAdminDO));
    }

    @Test
    public void update() {
        List<OrgRoleAdminDO> doList = repository.query(OrgRoleAdminRequest.builder().orgPath("unit").build());
        Assert.assertNotNull(doList);
        if (CollectionUtils.isNotEmpty(doList)) {
            OrgRoleAdminDO orgRoleAdminDO = doList.get(0);
            orgRoleAdminDO.setUpdateUser("unit");
            Assert.assertTrue(repository.update(orgRoleAdminDO));
        }
    }

    @Test
    public void delete() {
        List<OrgRoleAdminDO> doList = repository.query(OrgRoleAdminRequest.builder().orgPath("unit").build());
        Assert.assertNotNull(doList);
        if (CollectionUtils.isNotEmpty(doList)) {
            OrgRoleAdminDO orgRoleAdminDO = doList.get(0);
            Assert.assertTrue(repository.delete(orgRoleAdminDO.getId()));
        }
    }
}