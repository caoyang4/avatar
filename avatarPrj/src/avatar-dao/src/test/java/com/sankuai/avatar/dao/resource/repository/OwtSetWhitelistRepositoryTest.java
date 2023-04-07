package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.OwtSetWhitelistDO;
import com.sankuai.avatar.dao.resource.repository.request.OwtSetWhitelistRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author caoyang
 * @create 2022-10-21 19:04
 */
public class OwtSetWhitelistRepositoryTest extends TestBase {

    private final OwtSetWhitelistRepository repository;

    private static OwtSetWhitelistDO owtSetWhitelistDO;
    static {
        owtSetWhitelistDO = new OwtSetWhitelistDO();
        owtSetWhitelistDO.setApp("capacity");
        owtSetWhitelistDO.setSetName(UUID.randomUUID().toString());
        owtSetWhitelistDO.setApplyBy("unitTest");
        owtSetWhitelistDO.setStartTime(new Date());
        owtSetWhitelistDO.setEndTime(new Date());
        owtSetWhitelistDO.setReason("无可奉告");
    }

    public OwtSetWhitelistRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (OwtSetWhitelistRepository) ctx.getBean("owtSetWhitelistRepositoryImpl");
    }

    @Test
    public void testQuery() {
        String app = "capacity";
        List<OwtSetWhitelistDO> doList = repository.query(OwtSetWhitelistRequest.builder().app(app).build());
        assert CollectionUtils.isNotEmpty(doList);
        for (OwtSetWhitelistDO whitelistDO : doList) {
            Assert.assertEquals(app, whitelistDO.getApp());
            assert StringUtils.isNotEmpty(whitelistDO.getOwt());
            assert StringUtils.isNotEmpty(whitelistDO.getSetName());
        }
    }

    @Test
    public void testInsert() {
        owtSetWhitelistDO.setOwt("test-owt1");
        boolean insert = repository.insert(owtSetWhitelistDO);
        Assert.assertTrue(insert);
    }

    @Test
    public void testUpdate() {
        OwtSetWhitelistDO whitelistDO = JsonUtil.json2Bean(JsonUtil.bean2Json(OwtSetWhitelistRepositoryTest.owtSetWhitelistDO), OwtSetWhitelistDO.class);
        Assert.assertNotNull(whitelistDO);
        whitelistDO.setId(null);
        Assert.assertFalse(repository.update(whitelistDO));
        List<OwtSetWhitelistDO> doList = repository.query(OwtSetWhitelistRequest.builder().owt("test-owt1").build());
        if (CollectionUtils.isNotEmpty(doList)) {
            OwtSetWhitelistDO owtSetWhitelistDO = doList.get(0);
            owtSetWhitelistDO.setReason("无可奉告");
            Assert.assertTrue(repository.update(owtSetWhitelistDO));
        }
    }

    @Test
    public void testDelete() {
        List<OwtSetWhitelistDO> doList = repository.query(OwtSetWhitelistRequest.builder().owt("test-owt1").build());
        Assert.assertNotNull(doList);
        if (!doList.isEmpty()) {
            int id = doList.get(0).getId();
            Assert.assertTrue(repository.delete(id));
        }
    }
}