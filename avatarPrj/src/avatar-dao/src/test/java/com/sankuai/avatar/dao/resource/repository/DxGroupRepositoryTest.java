package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.impl.DxGroupRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.model.DxGroupDO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author caoyang
 * @create 2022-11-01 22:07
 */
public class DxGroupRepositoryTest extends TestBase {

    private final DxGroupRepository repository;
    public DxGroupRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (DxGroupRepositoryImpl) ctx.getBean("dxGroupRepositoryImpl");
    }

    static DxGroupDO dxGroupDO = new DxGroupDO();
    static {
        dxGroupDO.setGroupId(UUID.randomUUID().toString().substring(0,8));
        dxGroupDO.setGroupName("勤劳的小蜜蜂");
        dxGroupDO.setUpdateUser("unit");
    }

    @Test
    public void queryByGroupId() {
        String groupId = "65455601106";
        List<DxGroupDO> dxGroupDOList = repository.queryByGroupId(Collections.singletonList(groupId));
        Assert.assertEquals(1, dxGroupDOList.size());
        Assert.assertEquals(groupId, dxGroupDOList.get(0).getGroupId());
    }

    @Test
    public void insert() {
        Assert.assertTrue(repository.insert(dxGroupDO));
    }

    @Test
    public void update() {
        DxGroupDO dxGroupDO = new DxGroupDO();
        Assert.assertFalse(repository.update(dxGroupDO));
        dxGroupDO.setId(140);
        dxGroupDO.setUpdateUser("unit");
        Assert.assertTrue(repository.update(dxGroupDO));
    }

    @Test
    public void queryAllGroup() {
        Assert.assertTrue(CollectionUtils.isNotEmpty(repository.queryAllGroup()));
    }
}