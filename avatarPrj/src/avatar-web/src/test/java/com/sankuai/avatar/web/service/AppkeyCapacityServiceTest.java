package com.sankuai.avatar.web.service;

import com.google.common.collect.ImmutableSet;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.capacity.AppkeyCapacityResource;
import com.sankuai.avatar.resource.capacity.bo.*;
import com.sankuai.avatar.resource.capacity.constant.IdcRegionType;
import com.sankuai.avatar.resource.capacity.constant.MiddleWareType;
import com.sankuai.avatar.resource.capacity.constant.UtilizationStandardType;
import com.sankuai.avatar.resource.capacity.request.AppkeyCapacityRequestBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacityDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacitySummaryDTO;
import com.sankuai.avatar.web.request.AppkeyCapacityPageRequest;
import com.sankuai.avatar.web.service.impl.AppkeyCapacityServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author caoyang
 * @create 2022-11-07 17:19
 */
@RunWith(MockitoJUnitRunner.class)
public class AppkeyCapacityServiceTest {

    private AppkeyCapacityService appkeyCapacityService;

    @Mock
    private AppkeyCapacityResource appkeyCapacityResource;

    static AppkeyCapacityBO appkeyCapacityBO = new AppkeyCapacityBO();
    static AppkeyCapacityUtilizationBO utilizationBO = new AppkeyCapacityUtilizationBO();
    static AppkeyCapacityWhiteBO appkeyCapacityWhiteBO = new AppkeyCapacityWhiteBO();
    static AppkeyCapacityMiddleWareBO middleWareBO = new AppkeyCapacityMiddleWareBO();
    static AppkeyCapacityHostBO hostBO = new AppkeyCapacityHostBO();
    static AppkeyCapacityOctoProviderBO appkeyCapacityOctoProviderBO = new AppkeyCapacityOctoProviderBO();
    static AppkeyCapacityAccessComponentBO appkeyCapacityAccessComponentBO = new AppkeyCapacityAccessComponentBO();

    static {
        utilizationBO.setValue(0.1);
        utilizationBO.setLastWeekValue(0.3);
        utilizationBO.setLastWeekValueWithoutSet(0.2);
        utilizationBO.setYearPeekValue(0.25);

        appkeyCapacityWhiteBO.setWhiteApp(WhiteType.AUTO_MIGRATE);
        appkeyCapacityWhiteBO.setCName(WhiteType.AUTO_MIGRATE.getCname());
        appkeyCapacityWhiteBO.setEndTime(new Date());
        appkeyCapacityWhiteBO.setReason("无可奉告");

        middleWareBO.setMiddleWareName(MiddleWareType.MQ);
        middleWareBO.setUsed(true);

        hostBO.setHostName("host");
        hostBO.setIp("0.0.0.0");
        hostBO.setCell("");
        hostBO.setIdc("jd");
        hostBO.setCpuCount(4);
        hostBO.setMemSize(8);
        hostBO.setRegion(IdcRegionType.SH);

        appkeyCapacityOctoProviderBO.setHostName("host");
        appkeyCapacityOctoProviderBO.setIp("127.0.0.1");
        appkeyCapacityOctoProviderBO.setCell("");
        appkeyCapacityOctoProviderBO.setIdc("jd");
        appkeyCapacityOctoProviderBO.setProtocol("tcp");
        appkeyCapacityOctoProviderBO.setStatus(2);

        appkeyCapacityAccessComponentBO.setName("plus");
        appkeyCapacityAccessComponentBO.setCName("部署");
        appkeyCapacityAccessComponentBO.setAccess(true);

        appkeyCapacityBO.setAppkey("unit");
        appkeyCapacityBO.setSetName("unit");
        appkeyCapacityBO.setCapacityLevel(3);
        appkeyCapacityBO.setStandardLevel(3);
        appkeyCapacityBO.setIsCapacityStandard(true);
        appkeyCapacityBO.setStandardReason("");
        appkeyCapacityBO.setStandardTips("");
        appkeyCapacityBO.setUtilization(utilizationBO);
        appkeyCapacityBO.setUtilizationStandard(UtilizationStandardType.STANDARD);
        appkeyCapacityBO.setWhiteList(Collections.singletonList(appkeyCapacityWhiteBO));
        appkeyCapacityBO.setMiddleWareList(Collections.singletonList(middleWareBO));
        appkeyCapacityBO.setHostList(Collections.singletonList(hostBO));
        appkeyCapacityBO.setOctoHttpProviderList(Collections.singletonList(appkeyCapacityOctoProviderBO));
        appkeyCapacityBO.setOctoThriftProviderList(Collections.emptyList());
        appkeyCapacityBO.setAccessComponentList(Collections.singletonList(appkeyCapacityAccessComponentBO));
        appkeyCapacityBO.setStateful(true);
        appkeyCapacityBO.setCanSingleHostRestart(true);
        appkeyCapacityBO.setIsPaas(false);
        appkeyCapacityBO.setOrgPath("100046,150042,1817,40001501");
        appkeyCapacityBO.setOrgDisplayName("基础研发平台/基础技术部/数据库研发中心/Blade内核技术组");
        appkeyCapacityBO.setUpdateBy("unit");
    }

    @Before
    public void setUp(){
        appkeyCapacityService = new AppkeyCapacityServiceImpl(appkeyCapacityResource);
    }

    @Test
    public void queryPage() {
        PageResponse<AppkeyCapacityBO> response = new PageResponse<>();
        response.setPage(1);
        response.setPageSize(10);
        response.setTotalPage(1);
        response.setTotalCount(1);
        response.setItems(Collections.singletonList(appkeyCapacityBO));
        when(appkeyCapacityResource.queryPage(Mockito.any())).thenReturn(response);
        PageResponse<AppkeyCapacityDTO> pageResponse = appkeyCapacityService.queryPage(AppkeyCapacityPageRequest.builder().build());
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertEquals(1, pageResponse.getItems().size());
        Assert.assertEquals("unit", pageResponse.getItems().get(0).getAppkey());
        verify(appkeyCapacityResource).queryPage(Mockito.any());
    }

    @Test
    public void getAppkeyCapacityByAppkey() {
        when(appkeyCapacityResource.query(Mockito.any(AppkeyCapacityRequestBO.class))).thenReturn(Collections.singletonList(appkeyCapacityBO));
        List<AppkeyCapacityDTO> dtoList = appkeyCapacityService.getAppkeyCapacityByAppkey("appkey");
        Assert.assertTrue(dtoList.size() > 0);
        for (AppkeyCapacityDTO dto : dtoList) {
            Assert.assertEquals("unit", dto.getAppkey());
        }
        verify(appkeyCapacityResource).query(Mockito.any());
    }

    @Test
    public void getUnStandardAppkeyCapacity() {
        when(appkeyCapacityResource.query(Mockito.any(AppkeyCapacityRequestBO.class))).thenReturn(Collections.singletonList(appkeyCapacityBO));
        List<AppkeyCapacityDTO> dtoList = appkeyCapacityService.getUnStandardAppkeyCapacity();
        Assert.assertTrue(dtoList.size() > 0);
        for (AppkeyCapacityDTO dto : dtoList) {
            Assert.assertEquals("unit", dto.getAppkey());
        }
        verify(appkeyCapacityResource).query(Mockito.any());
    }

    @Test
    public void saveAppkeyCapacity() {
        Assert.assertFalse(appkeyCapacityService.saveAppkeyCapacity(null));
        when(appkeyCapacityResource.save(Mockito.any(AppkeyCapacityBO.class))).thenReturn(true);
        boolean save = appkeyCapacityService.saveAppkeyCapacity(Collections.singletonList(new AppkeyCapacityDTO()));
        Assert.assertTrue(save);
        verify(appkeyCapacityResource).save(Mockito.any(AppkeyCapacityBO.class));
    }

    @Test
    public void clearAppkeyCapacityInvalidSet() {
        when(appkeyCapacityResource.query(Mockito.any())).thenReturn(Collections.singletonList(appkeyCapacityBO));
        when(appkeyCapacityResource.deleteByCondition(Mockito.any())).thenReturn(true);
        boolean clear = appkeyCapacityService.clearAppkeyCapacityInvalidSet("appkey", ImmutableSet.of("test"));
        Assert.assertTrue(clear);
        verify(appkeyCapacityResource).query(Mockito.any());
        verify(appkeyCapacityResource).deleteByCondition(Mockito.any());
    }

    @Test
    public void getAppkeyCapacitySummary() {
        AppkeyCapacityBO bo1 = JsonUtil.json2Bean(JsonUtil.bean2Json(appkeyCapacityBO), AppkeyCapacityBO.class);
        bo1.setCapacityLevel(4);
        bo1.setStandardLevel(5);
        bo1.setIsCapacityStandard(false);
        bo1.setWhiteList(Collections.emptyList());
        AppkeyCapacityBO bo2 = JsonUtil.json2Bean(JsonUtil.bean2Json(appkeyCapacityBO), AppkeyCapacityBO.class);
        bo2.setCapacityLevel(1);
        bo2.setStandardLevel(4);
        bo2.setIsCapacityStandard(false);
        AppkeyCapacityWhiteBO whiteBo1 = JsonUtil.json2Bean(JsonUtil.bean2Json(appkeyCapacityWhiteBO), AppkeyCapacityWhiteBO.class);
        whiteBo1.setWhiteApp(WhiteType.CAPACITY);
        whiteBo1.setCName(WhiteType.CAPACITY.getCname());
        bo2.setWhiteList(Collections.singletonList(whiteBo1));
        when(appkeyCapacityResource.query(Mockito.any())).thenReturn(Arrays.asList(appkeyCapacityBO, bo1, bo2));
        AppkeyCapacitySummaryDTO summaryDTO = appkeyCapacityService.getAppkeyCapacitySummary("appkey");
        Assert.assertNotNull(summaryDTO);
        Assert.assertEquals(4, summaryDTO.getCapacityLevel().intValue());
        Assert.assertEquals(5, summaryDTO.getStandardCapacityLevel().intValue());
        Assert.assertFalse(summaryDTO.getIsCapacityStandard());
        verify(appkeyCapacityResource).query(Mockito.any());
    }

    @Test
    public void getAllCapacityAppkey() {
        when(appkeyCapacityResource.query(Mockito.any())).thenReturn(Collections.singletonList(appkeyCapacityBO));
        Set<String> allCapacityAppkey = appkeyCapacityService.getAllCapacityAppkey();
        Assert.assertTrue(allCapacityAppkey.size() > 0);
        verify(appkeyCapacityResource).query(Mockito.any());
    }

    @Test
    public void updateOpsCapacity() {
        when(appkeyCapacityResource.updateOpsCapacity(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
        Boolean update = appkeyCapacityService.updateOpsCapacity("x", "z", 5);
        verify(appkeyCapacityResource).updateOpsCapacity(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt());
        Assert.assertTrue(update);
    }

    @Test
    public void isExistAppkeySetCapacity(){
        when(appkeyCapacityResource.query(Mockito.any())).thenReturn(Collections.singletonList(appkeyCapacityBO));
        Boolean exist = appkeyCapacityService.isExistAppkeySetCapacity("x", "y");
        Assert.assertTrue(exist);
        verify(appkeyCapacityResource).query(Mockito.any());
    }

    @Test
    public void noExistAppkeySetCapacity(){
        when(appkeyCapacityResource.query(Mockito.any())).thenReturn(Collections.emptyList());
        Boolean exist = appkeyCapacityService.isExistAppkeySetCapacity("x", "y");
        Assert.assertFalse(exist);
        verify(appkeyCapacityResource).query(Mockito.any());
    }

}