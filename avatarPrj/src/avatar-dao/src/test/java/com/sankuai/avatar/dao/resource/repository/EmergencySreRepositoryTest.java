package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.EmergencySreDO;
import com.sankuai.avatar.dao.resource.repository.request.EmergencySreRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-17 15:55
 */
public class EmergencySreRepositoryTest extends TestBase {

    private final EmergencySreRepository repository;

    static EmergencySreDO emergencySreDO = new EmergencySreDO();
    static {
        emergencySreDO.setSourceId(10086);
        emergencySreDO.setTime(1);
        emergencySreDO.setState("FINISH");
        emergencySreDO.setAppkey("appkey");
        emergencySreDO.setOpAdmin("avatar");
        emergencySreDO.setAttachAdmin("mcm");
        emergencySreDO.setCreateUser("长者");
    }

    public EmergencySreRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (EmergencySreRepository) ctx.getBean("emergencySreRepositoryImpl");
    }

    @Test
    public void query() {
        String appkey = "com.sankuai.avatar.develop";
        List<EmergencySreDO> doList = repository.query(EmergencySreRequest.builder().appkey(appkey).build());
        Assert.assertEquals(appkey, doList.get(0).getAppkey());
    }

    @Test
    public void insert() {
        Boolean insert = repository.insert(emergencySreDO);
        Assert.assertTrue(insert);
    }

    @Test
    public void update() {
        List<EmergencySreDO> doList = repository.query(EmergencySreRequest.builder().build());
        Assert.assertNotNull(doList);
        if (CollectionUtils.isNotEmpty(doList)) {
            EmergencySreDO sreDO = doList.get(0);
            sreDO.setUpdateTime(new Date());
            Boolean update = repository.update(sreDO);
            Assert.assertTrue(update);
        }
    }

    @Test
    public void delete() {
        List<EmergencySreDO> doList = repository.query(EmergencySreRequest.builder().appkey("appkey").build());
        Assert.assertNotNull(doList);
        if (CollectionUtils.isNotEmpty(doList)) {
            Boolean delete = repository.delete(doList.get(0).getId());
            Assert.assertTrue(delete);
        }
    }

}