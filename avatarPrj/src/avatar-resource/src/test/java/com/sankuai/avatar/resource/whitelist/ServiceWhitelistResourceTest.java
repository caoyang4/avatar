package com.sankuai.avatar.resource.whitelist;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.dao.resource.repository.ServiceWhitelistRepository;
import com.sankuai.avatar.dao.resource.repository.model.ServiceWhitelistDO;
import com.sankuai.avatar.dao.resource.repository.request.ServiceWhitelistRequest;
import com.sankuai.avatar.resource.whitelist.bo.ServiceWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.impl.ServiceWhitelistResourceImpl;
import com.sankuai.avatar.resource.whitelist.request.ServiceWhitelistRequestBO;
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

import static org.mockito.Mockito.when;

/**
 * @author caoyang
 * @create 2022-10-27 21:00
 */
public class ServiceWhitelistResourceTest extends TestBase {

    private ServiceWhitelistResource resource;

    @Mock
    private ServiceWhitelistRepository repository;

    private static ServiceWhitelistDO serviceWhitelistDO;
    static {
        serviceWhitelistDO = new ServiceWhitelistDO();
        serviceWhitelistDO.setApp("capacity");
        serviceWhitelistDO.setReason("无可奉告");
        serviceWhitelistDO.setEndTime(new Date());
        serviceWhitelistDO.setInputUser("unitTest");
        serviceWhitelistDO.setAppkey("test-appkey");
        serviceWhitelistDO.setApplication("unit-test-application");
        serviceWhitelistDO.setOrgIds("100046,150042,1573,150044,1021866");
        serviceWhitelistDO.setSetName("test-set");
    }
    private static ServiceWhitelistBO serviceWhitelistBO;
    static {
        serviceWhitelistBO = new ServiceWhitelistBO();
        serviceWhitelistBO.setApp(WhiteType.CAPACITY);
        serviceWhitelistBO.setReason("无可奉告");
        serviceWhitelistBO.setEndTime(new Date());
        serviceWhitelistBO.setInputUser("unitTest");
        serviceWhitelistBO.setAppkey("test-appkey");
        serviceWhitelistBO.setApplication("unit-test-application");
        serviceWhitelistBO.setOrgIds("100046,150042,1573,150044,1021866");
        serviceWhitelistBO.setSetName("test-set");
    }

    @Before
    @Override
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        EntityHelper.initEntityNameMap(ServiceWhitelistDO.class, new Config());
        resource = new ServiceWhitelistResourceImpl(repository);
    }

    @Test
    public void queryPage(){
        when(repository.query(Mockito.any(ServiceWhitelistRequest.class))).thenReturn(Collections.singletonList(serviceWhitelistDO));
        PageResponse<ServiceWhitelistBO> pageResponse = resource.queryPage(ServiceWhitelistRequestBO.builder().build());
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(10, pageResponse.getPageSize());
    }

    @Test
    public void query() {
        when(repository.query(Mockito.any(ServiceWhitelistRequest.class))).thenReturn(Collections.singletonList(serviceWhitelistDO));
        List<ServiceWhitelistBO> boList = resource.query(ServiceWhitelistRequestBO.builder().build());
        Assert.assertEquals(1, boList.size());
        Assert.assertEquals("test-appkey", boList.get(0).getAppkey());
        Assert.assertEquals(WhiteType.CAPACITY, boList.get(0).getApp());
    }

    @Test
    public void save() {
        when(repository.query(Mockito.any(ServiceWhitelistRequest.class))).thenReturn(Collections.emptyList());
        when(repository.insert(Mockito.any(ServiceWhitelistDO.class))).thenReturn(true);
        Assert.assertTrue(resource.save(serviceWhitelistBO));
    }

    @Test
    public void deleteByCondition() {
        Assert.assertFalse(resource.deleteByCondition(null));
        serviceWhitelistDO.setId(1);
        when(repository.query(Mockito.any(ServiceWhitelistRequest.class))).thenReturn(Collections.singletonList(serviceWhitelistDO));
        when(repository.delete(Mockito.anyInt())).thenReturn(true);
        Assert.assertTrue(resource.deleteByCondition(ServiceWhitelistRequestBO.builder().appkeys(Collections.singletonList("x")).build()));
    }

    @Test
    public void isWhiteOfAppkeySet() {
        when(repository.query(Mockito.any(ServiceWhitelistRequest.class))).thenReturn(Collections.emptyList());
        Assert.assertFalse(resource.isWhiteOfAppkeySet("x",WhiteType.CAPACITY, "x"));
        serviceWhitelistDO.setSetName("");
        when(repository.query(Mockito.any(ServiceWhitelistRequest.class))).thenReturn(Collections.singletonList(serviceWhitelistDO));
        Assert.assertTrue(resource.isWhiteOfAppkeySet("x",WhiteType.CAPACITY, "x"));
        serviceWhitelistDO.setSetName("y,z");
        when(repository.query(Mockito.any(ServiceWhitelistRequest.class))).thenReturn(Collections.singletonList(serviceWhitelistDO));
        Assert.assertTrue(resource.isWhiteOfAppkeySet("x",WhiteType.CAPACITY, "y"));
    }

    @Test
    public void getExpireWhitelist() {
        when(repository.query(Mockito.any(ServiceWhitelistRequest.class))).thenReturn(Collections.singletonList(serviceWhitelistDO));
        List<ServiceWhitelistBO> expireWhitelist = resource.getExpireWhitelist();
        Assert.assertEquals(1, expireWhitelist.size());
    }
}