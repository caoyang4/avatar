package com.sankuai.avatar.dao.cache;

import com.sankuai.avatar.dao.cache.impl.OrgRoleAdminCacheRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.OrgRoleAdminRepository;
import com.sankuai.avatar.dao.resource.repository.TestBase;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.impl.OrgRoleAdminRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.model.OrgRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.OrgRoleAdminRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-01 22:07
 */
@Slf4j
public class OrgRoleAdminCacheRepositoryTest extends TestBase {

    private final OrgRoleAdminRepository repository;
    private final OrgRoleAdminCacheRepository cacheRepository;

    public OrgRoleAdminCacheRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (OrgRoleAdminRepositoryImpl) ctx.getBean("orgRoleAdminRepositoryImpl");
        cacheRepository = (OrgRoleAdminCacheRepositoryImpl) ctx.getBean("orgRoleAdminCacheRepositoryImpl");
    }

    @Test
    public void getOpRole() {
        String orgId = "1021866";
        OrgRoleAdminDO orgRoleAdminDO = cacheRepository.getOpRole(orgId);
        Assert.assertNotNull(orgRoleAdminDO);
        Assert.assertEquals(orgId, orgRoleAdminDO.getOrgId());
    }

    @Test
    public void multiGetOpRole() {
        List<String> orgIds = Arrays.asList("1021866", "152442");
        List<OrgRoleAdminDO> orgRoleAdminDOList = cacheRepository.multiGetOpRole(orgIds);
        Assert.assertEquals(orgIds.size(), orgRoleAdminDOList.size());
    }

    @Test
    public void getEpRole() {
        String orgId = "102033";
        OrgRoleAdminDO orgRoleAdminDO = cacheRepository.getEpRole(orgId);
        Assert.assertNotNull(orgRoleAdminDO);
        Assert.assertEquals(orgId, orgRoleAdminDO.getOrgId());
    }

    @Test
    public void multiGetEpRole() {
        List<String> orgIds = Arrays.asList("102033", "153057");
        List<OrgRoleAdminDO> orgRoleAdminDOList = cacheRepository.multiGetEpRole(orgIds);
        Assert.assertEquals(orgIds.size(), orgRoleAdminDOList.size());
    }

    @Test
    public void set() {
        List<OrgRoleAdminDO> query = repository.query(OrgRoleAdminRequest.builder().orgIds(Arrays.asList("102033", "153057")).build());
        Assert.assertEquals(2, query.size());
        Assert.assertTrue(cacheRepository.set(query.get(0), 0));
        Assert.assertTrue(cacheRepository.set(query.get(1), 0));
    }

    @Test
    public void multiSet() {
        String orgId = "1573";
        List<OrgRoleAdminDO> query = repository.query(OrgRoleAdminRequest.builder().orgPath(orgId).build());
        assert query.size() > 0;
        log.info("size: {}", query.size());
        Assert.assertTrue(cacheRepository.multiSet(query, 0));
    }
}