package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.ApplicationRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.ApplicationRoleAdminRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-11 16:04
 */
public class ApplicationRoleAdminRepositoryTest extends TestBase {

    private ApplicationRoleAdminRepository repository;

    static ApplicationRoleAdminDO applicationRoleAdminDO = new ApplicationRoleAdminDO();
    static {
        applicationRoleAdminDO.setApplicationId(1);
        applicationRoleAdminDO.setApplicationName("UnitTest");
        applicationRoleAdminDO.setApplicationCnName("单测");
        applicationRoleAdminDO.setEpAdmin("avatar");
        applicationRoleAdminDO.setOpAdmin("avatar");
        applicationRoleAdminDO.setCreateUser("avatar");
        applicationRoleAdminDO.setUpdateUser("avatar");
    }

    public ApplicationRoleAdminRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (ApplicationRoleAdminRepository) ctx.getBean("applicationRoleAdminRepositoryImpl");
    }

    @Test
    public void query() {
        List<ApplicationRoleAdminDO> doList = repository.query(ApplicationRoleAdminRequest.builder().applicationName("AvatarTestApp").build());
        Assert.assertTrue(CollectionUtils.isNotEmpty(doList));
    }

    @Test
    public void insert() {
        boolean insert = repository.insert(applicationRoleAdminDO);
        Assert.assertTrue(insert);
    }

    @Test
    public void update() {
        List<ApplicationRoleAdminDO> doList = repository.query(ApplicationRoleAdminRequest.builder().applicationName("Avatar").build());
        if (CollectionUtils.isNotEmpty(doList)) {
            applicationRoleAdminDO.setEpAdmin("qinwei05");
            Assert.assertTrue(repository.update(applicationRoleAdminDO));
        }
    }

    @Test
    public void delete() {
        List<ApplicationRoleAdminDO> doList = repository.query(ApplicationRoleAdminRequest.builder().applicationName("UnitTest").build());
        if (CollectionUtils.isNotEmpty(doList)) {
            ApplicationRoleAdminDO applicationRoleAdminDO = doList.get(0);
            Assert.assertTrue(repository.delete(applicationRoleAdminDO.getId()));
        }
    }

}