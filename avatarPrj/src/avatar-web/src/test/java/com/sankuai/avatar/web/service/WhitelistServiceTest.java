package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.AppkeyBO;
import com.sankuai.avatar.resource.capacity.AppkeyCapacityResource;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacityBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyCapacityRequestBO;
import com.sankuai.avatar.resource.dxMessage.DxMessageResource;
import com.sankuai.avatar.resource.whitelist.ServiceWhitelistResource;
import com.sankuai.avatar.resource.whitelist.WhitelistAppResource;
import com.sankuai.avatar.resource.whitelist.bo.ServiceWhitelistBO;
import com.sankuai.avatar.resource.whitelist.bo.WhitelistAppBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.request.ServiceWhitelistRequestBO;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.dto.whitelist.WhitelistAppDTO;
import com.sankuai.avatar.web.request.WhitelistPageRequest;
import com.sankuai.avatar.web.service.impl.WhitelistServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2022-11-15 20:00
 */
@RunWith(MockitoJUnitRunner.class)
public class WhitelistServiceTest {

    @Mock
    private ServiceWhitelistResource resource;

    @Mock
    private WhitelistAppResource appResource;

    @Mock
    private AppkeyCapacityResource capacityResource;

    @Mock
    private DxMessageResource dxResource;

    @Mock
    private AppkeyResource appkeyResource;

    private WhitelistService service;

    static ServiceWhitelistBO serviceWhitelistBO = new ServiceWhitelistBO();
    static {
        serviceWhitelistBO.setApp(WhiteType.CAPACITY);
        serviceWhitelistBO.setReason("无可奉告");
        serviceWhitelistBO.setEndTime(new Date());
        serviceWhitelistBO.setInputUser("unitTest");
        serviceWhitelistBO.setAppkey("test-appkey");
        serviceWhitelistBO.setApplication("unit-test-application");
        serviceWhitelistBO.setOrgIds("100046,150042,1573,150044,1021866");
        serviceWhitelistBO.setSetName("test-set");
    }

    static ServiceWhitelistDTO serviceWhitelistDTO = new ServiceWhitelistDTO();
    static {
        serviceWhitelistDTO.setApp(WhiteType.CAPACITY);
        serviceWhitelistDTO.setReason("无可奉告");
        serviceWhitelistDTO.setEndTime(new Date());
        serviceWhitelistDTO.setInputUser("unitTest");
        serviceWhitelistDTO.setAppkey("test-appkey");
        serviceWhitelistDTO.setApplication("unit-test-application");
        serviceWhitelistDTO.setOrgIds("100046,150042,1573,150044,1021866");
        serviceWhitelistDTO.setSetName("test-set");
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        service = new WhitelistServiceImpl(resource, appResource, capacityResource, dxResource, appkeyResource);
    }

    @Test
    public void queryPage() {
        PageResponse<ServiceWhitelistBO> boPageResponse = new PageResponse<>();
        boPageResponse.setTotalPage(1);
        boPageResponse.setPage(1);
        boPageResponse.setPageSize(10);
        boPageResponse.setTotalCount(1);
        boPageResponse.setItems(Collections.singletonList(serviceWhitelistBO));
        when(resource.queryPage(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(boPageResponse);
        String appkey = "test-appkey";
        PageResponse<ServiceWhitelistDTO> pageResponse = service.queryPage(WhitelistPageRequest.builder().appkeys(Collections.singletonList(appkey)).build());
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertEquals(1, pageResponse.getPage());
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
        for (ServiceWhitelistDTO dto : pageResponse.getItems()) {
            Assert.assertEquals(appkey, dto.getAppkey());
        }
        verify(resource, times(1)).queryPage(any());
    }

    @Test
    public void getAllWhitelistType() {
        WhitelistAppBO whitelistAppBO = new WhitelistAppBO();
        whitelistAppBO.setApp("capacity");
        when(appResource.getAllWhitelistApp()).thenReturn(Collections.singletonList(whitelistAppBO));
        List<WhitelistAppDTO> allWhitelistType = service.getAllWhitelistType();
        Assert.assertTrue(CollectionUtils.isNotEmpty(allWhitelistType));
        for (WhitelistAppDTO dto : allWhitelistType) {
            Assert.assertTrue(StringUtils.isNotEmpty(dto.getApp()));
        }
        verify(appResource, times(1)).getAllWhitelistApp();
    }

    @Test
    public void getServiceWhitelistByAppkey() {
        when(resource.query(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(Collections.singletonList(serviceWhitelistBO));
        String appkey = "test-appkey";
        List<ServiceWhitelistDTO> dtoList = service.getServiceWhitelistByAppkey(appkey);
        Assert.assertTrue(CollectionUtils.isNotEmpty(dtoList));
        for (ServiceWhitelistDTO dto : dtoList) {
            Assert.assertEquals(appkey, dto.getAppkey());
        }
        verify(resource, times(1)).query(any());
    }

    @Test
    public void saveServiceWhitelist() {
        Assert.assertFalse(service.saveServiceWhitelist(null));
        when(resource.save(Mockito.any(ServiceWhitelistBO.class))).thenReturn(true);
        Assert.assertTrue(service.saveServiceWhitelist(serviceWhitelistDTO));
        verify(resource, times(1)).save(any());

    }

    @Test
    public void deleteWhitelistByPk() {
        when(resource.deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(true);
        Assert.assertTrue(service.deleteWhitelistByPk(0));
        verify(resource, times(1)).deleteByCondition(ServiceWhitelistRequestBO.builder().id(0).build());
    }

    @Test
    public void deleteWhitelistByAppkey() {
        when(resource.deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(true);
        String appkey = "xxx";
        Assert.assertTrue(service.deleteWhitelistByAppkey(appkey));
        verify(resource, times(1)).deleteByCondition(any());
    }

    @Test
    public void deleteWhitelistByAppkeyAndType() {
        when(resource.deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(true);
        String appkey = "xxx";
        WhiteType type = WhiteType.CAPACITY;
        Assert.assertTrue(service.deleteWhitelistByAppkeyAndType(appkey, type));
        verify(resource, times(1)).deleteByCondition(any());
    }

    @Test
    public void testGetServiceWhitelistByAppkey() {
        when(resource.query(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(Collections.singletonList(serviceWhitelistBO));
        String appkey = "test-appkey";
        List<ServiceWhitelistDTO> dtoList = service.getServiceWhitelistByAppkey(appkey, WhiteType.CAPACITY);
        Assert.assertTrue(CollectionUtils.isNotEmpty(dtoList));
        for (ServiceWhitelistDTO dto : dtoList) {
            Assert.assertEquals(appkey, dto.getAppkey());
        }
        verify(resource, times(1)).query(any());
    }

    @Test
    public void cancelWhitelistCaseAppkey() {
        when(resource.deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(true);
        Boolean cancel = service.cancelWhitelist("x", WhiteType.CAPACITY, null);
        Assert.assertTrue(cancel);
        verify(resource).deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class));
    }

    @Test
    public void cancelWhitelistCaseAllSet() {
        ServiceWhitelistBO whitelistBO = JsonUtil.json2Bean(JsonUtil.bean2Json(serviceWhitelistBO), ServiceWhitelistBO.class);
        whitelistBO.setSetName("");
        when(resource.query(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(Collections.singletonList(whitelistBO));
        AppkeyCapacityBO appkeyCapacityBO = new AppkeyCapacityBO();
        appkeyCapacityBO.setSetName("test-set");
        when(capacityResource.query(Mockito.any(AppkeyCapacityRequestBO.class))).thenReturn(Collections.singletonList(appkeyCapacityBO));
        when(resource.save(Mockito.any(ServiceWhitelistBO.class))).thenReturn(true);
        Boolean cancel = service.cancelWhitelist("x", WhiteType.CAPACITY, Collections.singletonList("test-set"));
        Assert.assertTrue(cancel);
        verify(resource).query(Mockito.any(ServiceWhitelistRequestBO.class));
        verify(capacityResource).query(Mockito.any(AppkeyCapacityRequestBO.class));
        verify(resource).save(Mockito.any(ServiceWhitelistBO.class));
        verify(resource, times(0)).deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class));
    }

    @Test
    public void cancelWhitelistCaseSpecificSet() {
        ServiceWhitelistBO whitelistBO = JsonUtil.json2Bean(JsonUtil.bean2Json(serviceWhitelistBO), ServiceWhitelistBO.class);
        whitelistBO.setSetName("test-set");
        when(resource.query(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(Collections.singletonList(whitelistBO));
        when(resource.deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(true);
        Boolean cancel = service.cancelWhitelist("x", WhiteType.CAPACITY, Collections.singletonList("test-set"));
        Assert.assertTrue(cancel);
        verify(resource).query(Mockito.any(ServiceWhitelistRequestBO.class));
        verify(capacityResource, times(0)).query(Mockito.any(AppkeyCapacityRequestBO.class));
        verify(resource, times(0)).save(Mockito.any(ServiceWhitelistBO.class));
        verify(resource).deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class));

    }

    @Test
    public void sendCancelWhiteNoticeCaseCapacity() {
        AppkeyBO appkeyBO = new AppkeyBO();
        appkeyBO.setRdAdmin("x,y,z");
        when(appkeyResource.getByAppkey(Mockito.anyString())).thenReturn(appkeyBO);
        ServiceWhitelistDTO whitelistDTO = new ServiceWhitelistDTO();
        whitelistDTO.setOrgIds("10086");
        service.sendCancelWhiteNotice("x", "appkey", WhiteType.CAPACITY, Collections.singletonList("test-set"), serviceWhitelistDTO);
        verify(appkeyResource).getByAppkey(Mockito.anyString());
    }

    @Test
    public void sendCancelWhiteNoticeCaseNoCapacity() {
        AppkeyBO appkeyBO = new AppkeyBO();
        appkeyBO.setRdAdmin("x,y,z");
        when(appkeyResource.getByAppkey(Mockito.anyString())).thenReturn(appkeyBO);
        ServiceWhitelistDTO whitelistDTO = new ServiceWhitelistDTO();
        whitelistDTO.setOrgIds("10086");
        service.sendCancelWhiteNotice("x", "appkey", WhiteType.AUTO_MIGRATE, Collections.singletonList("test-set"), serviceWhitelistDTO);
        verify(appkeyResource).getByAppkey(Mockito.anyString());
    }
}