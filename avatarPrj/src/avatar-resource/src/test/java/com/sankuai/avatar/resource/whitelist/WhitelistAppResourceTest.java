package com.sankuai.avatar.resource.whitelist;

import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.dao.resource.repository.WhitelistAppRepository;
import com.sankuai.avatar.dao.resource.repository.model.WhitelistAppDO;
import com.sankuai.avatar.resource.whitelist.bo.WhitelistAppBO;
import com.sankuai.avatar.resource.whitelist.impl.WhitelistAppResourceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Collections;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-15 10:31
 */
public class WhitelistAppResourceTest extends TestBase {

    private WhitelistAppResource resource;

    @Mock
    private WhitelistAppRepository repository;

    static WhitelistAppDO whitelistAppDO = new WhitelistAppDO();
    static {
        whitelistAppDO.setId(1);
        whitelistAppDO.setApp("capacity");
        whitelistAppDO.setCname("容灾等级");
        whitelistAppDO.setDescription("xxx");
    }

    @Before
    @Override
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        EntityHelper.initEntityNameMap(WhitelistAppDO.class, new Config());
        resource = new WhitelistAppResourceImpl(repository);
    }

    @Test
    public void getAllWhitelistApp() {
        Mockito.when(repository.query()).thenReturn(Collections.singletonList(whitelistAppDO));
        List<WhitelistAppBO> boList = resource.getAllWhitelistApp();
        Assert.assertEquals(1, boList.size());
        Assert.assertEquals("capacity", boList.get(0).getApp());
        Assert.assertEquals(1, boList.get(0).getId().intValue());
        Assert.assertEquals("容灾等级", boList.get(0).getCname());
        Assert.assertEquals("xxx", boList.get(0).getDescription());
    }
}