package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.impl.WindowPeriodRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.model.ResourceWindowPeriodDO;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.request.WindowPeriodRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * @author caoyang
 * @create 2023-03-15 16:17
 */
public class WindowPeriodRepositoryTest extends TestBase {

    private final WindowPeriodRepository repository;

    public WindowPeriodRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (WindowPeriodRepositoryImpl) ctx.getBean("windowPeriodRepositoryImpl");
    }
    static ResourceWindowPeriodDO windowPeriodDO = new ResourceWindowPeriodDO();
    static {
        windowPeriodDO.setName("unit-test");
        windowPeriodDO.setDescription("大新闻");
        windowPeriodDO.setCreateUser("unit");
        windowPeriodDO.setStartTime(new Date());
        windowPeriodDO.setEndTime(new Date());
        windowPeriodDO.setExpectedDeliveryTime(new Date());
        windowPeriodDO.setCreateTime(new Date());
        windowPeriodDO.setUpdateTime(new Date());
    }

    @Test
    public void query() {
        WindowPeriodRequest request = new WindowPeriodRequest();
        request.setCreateUser("caoyang42");
        List<ResourceWindowPeriodDO> doList = repository.query(request);
        Assert.assertTrue(CollectionUtils.isNotEmpty(doList));
    }

    @Test
    public void getMaxWindowId(){
        Integer id = repository.getMaxWindowId();
        Assert.assertNotNull(id);
    }

    @Test
    public void insert() {
        Assert.assertTrue(repository.insert(windowPeriodDO));
    }

    @Test
    public void update() {
        WindowPeriodRequest request = new WindowPeriodRequest();
        request.setId(7);
        List<ResourceWindowPeriodDO> doList = repository.query(request);
        Assert.assertNotNull(doList);
        if (CollectionUtils.isNotEmpty(doList)) {
            ResourceWindowPeriodDO periodDO = doList.get(0);
            periodDO.setDescription("无可奉告");
            Assert.assertTrue(repository.update(periodDO));
        }
    }

    @Test
    public void delete() {
        WindowPeriodRequest request = new WindowPeriodRequest();
        request.setName("unit-test");
        List<ResourceWindowPeriodDO> doList = repository.query(request);
        Assert.assertNotNull(doList);

        if (CollectionUtils.isNotEmpty(doList)) {
            int id = doList.get(0).getId();
            Assert.assertTrue(repository.delete(id));
        }
    }
}