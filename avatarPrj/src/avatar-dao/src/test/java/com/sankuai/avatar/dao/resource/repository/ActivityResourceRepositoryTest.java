package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.impl.ActivityResourceRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.model.ActivityResourceDO;
import com.sankuai.avatar.dao.resource.repository.request.ActivityResourceRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author caoyang
 * @create 2023-03-07 16:34
 */
public class ActivityResourceRepositoryTest extends TestBase {

    private final ActivityResourceRepository repository;

    static ActivityResourceDO resourceDO = new ActivityResourceDO();
    static {
        resourceDO.setFlowId(666);
        resourceDO.setFlowUuid(UUID.randomUUID().toString());
        resourceDO.setAppkey("test-appkey");
        resourceDO.setWindowPeriodId(233);
        resourceDO.setOrgId("x");
        resourceDO.setOrgName("x");
        resourceDO.setDescription("x");
        resourceDO.setName("x");
        resourceDO.setCount(1);
        resourceDO.setRetainCount(0);
        resourceDO.setHostConfig("{\"env\":\"prod\",\"channel\":\"hulk\",\"channelCn\":\"HULK\",\"cluster\":\"hulk_10g\",\"clusterCn\":\"通用集群\",\"city\":\"北京\",\"region\":\"beijing\",\"count\":1,\"deliverCount\":0,\"cpu\":2,\"memory\":4,\"disk\":150,\"diskType\":\"system\",\"diskTypeCn\":\"本地磁盘\",\"idcs\":\"\",\"os\":\"CentOS 6\",\"nic\":\"nic_10g\",\"nicType\":\"common_nic\",\"set\":\"\",\"parallel\":null,\"deploy\":false,\"configExtraInfo\":\"\"}");
        resourceDO.setCreateUser("x");
        resourceDO.setStatus("CLOSE");
        resourceDO.setStartTime(new Date());
        resourceDO.setEndTime(new Date());
        resourceDO.setDeliverTime(new Date());
        resourceDO.setReturnTime(new Date());
    }

    public ActivityResourceRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (ActivityResourceRepositoryImpl) ctx.getBean("activityResourceRepositoryImpl");
    }

    @Test
    public void query() {
        ActivityResourceRequest request = new ActivityResourceRequest();
        request.setCreateUser("caoyang42");
        List<ActivityResourceDO> doList = repository.query(request);
        Assert.assertTrue(CollectionUtils.isNotEmpty(doList));
    }

    @Test
    public void insert() {
        boolean insert = repository.insert(resourceDO);
        Assert.assertTrue(insert);
    }

    @Test
    public void update() {
        ActivityResourceRequest request = new ActivityResourceRequest();
        request.setCreateUser("caoyang42");
        List<ActivityResourceDO> doList = repository.query(request);
        Assert.assertTrue(CollectionUtils.isNotEmpty(doList));
        ActivityResourceDO activityResourceDO = doList.get(0);
        activityResourceDO.setDescription("阖家欢落");
        boolean update = repository.update(activityResourceDO);
        Assert.assertTrue(update);
    }

    @Test
    public void delete() {
        ActivityResourceRequest request = new ActivityResourceRequest();
        request.setAppkey("test-appkey");
        List<ActivityResourceDO> doList = repository.query(request);
        Assert.assertNotNull(doList);
        if (CollectionUtils.isNotEmpty(doList)) {
            int id = doList.get(0).getId();
            Assert.assertTrue(repository.delete(id));
        }
    }
}