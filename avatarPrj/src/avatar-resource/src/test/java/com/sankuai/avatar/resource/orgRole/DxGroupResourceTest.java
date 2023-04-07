package com.sankuai.avatar.resource.orgRole;

import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.dao.resource.repository.DxGroupRepository;
import com.sankuai.avatar.dao.resource.repository.model.DxGroupDO;
import com.sankuai.avatar.resource.orgRole.bo.DxGroupBO;
import com.sankuai.avatar.resource.orgRole.impl.DxGroupResourceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * @author caoyang
 * @create 2022-11-10 17:21
 */
public class DxGroupResourceTest extends TestBase {

    @Mock
    private DxGroupRepository repository;

    private DxGroupResource resource;

    static DxGroupDO dxGroupDO = new DxGroupDO();
    static {
        dxGroupDO.setId(1);
        dxGroupDO.setGroupId(UUID.randomUUID().toString().substring(0,8));
        dxGroupDO.setGroupStatus("");
        dxGroupDO.setGroupName("勤劳的小蜜蜂");
        dxGroupDO.setUpdateUser("unit");
        dxGroupDO.setCreateTime(new Date());
        dxGroupDO.setUpdateTime(new Date());
    }

    static DxGroupBO dxGroupBO = new DxGroupBO();
    static {
        dxGroupBO.setGroupId(UUID.randomUUID().toString().substring(0,8));
        dxGroupBO.setGroupStatus("");
        dxGroupBO.setGroupName("勤劳的小蜜蜂");
        dxGroupBO.setUpdateUser("unit");
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        EntityHelper.initEntityNameMap(DxGroupDO.class, new Config());
        resource = new DxGroupResourceImpl(repository);
    }

    @Test
    public void getDxGroupByGroupIds() {
        List<String> groupIds = Collections.singletonList("123");
        when(repository.queryByGroupId(Mockito.anyList())).thenReturn(Collections.singletonList(dxGroupDO));
        List<DxGroupBO> boList = resource.getDxGroupByGroupIds(groupIds);
        Assert.assertEquals(1, boList.size());
        Assert.assertEquals("unit", boList.get(0).getUpdateUser());
    }

    @Test
    public void save() {
        when(repository.queryByGroupId(Mockito.anyList())).thenReturn(Collections.emptyList());
        when(repository.insert(Mockito.any(DxGroupDO.class))).thenReturn(true);
        Assert.assertTrue(resource.save(dxGroupBO));
        when(repository.queryByGroupId(Mockito.anyList())).thenReturn(Collections.singletonList(dxGroupDO));
        when(repository.update(Mockito.any(DxGroupDO.class))).thenReturn(false);
        Assert.assertFalse(resource.save(dxGroupBO));
    }

    @Test
    public void getAllDxGroup() {
        when(repository.queryAllGroup()).thenReturn(Collections.singletonList(dxGroupDO));
        Assert.assertTrue(CollectionUtils.isNotEmpty(resource.getAllDxGroup()));
    }
}