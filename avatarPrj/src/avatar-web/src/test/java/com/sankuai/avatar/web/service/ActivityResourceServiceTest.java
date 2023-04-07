package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.ActivityResource;
import com.sankuai.avatar.resource.activity.bo.ActivityHostBO;
import com.sankuai.avatar.resource.activity.bo.ActivityResourceBO;
import com.sankuai.avatar.resource.activity.constant.ResourceStatusType;
import com.sankuai.avatar.resource.activity.request.ActivityResourceRequestBO;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.sdk.entity.servicecatalog.AppKey;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.avatar.web.dto.activity.ActivityHostDTO;
import com.sankuai.avatar.web.dto.activity.ActivityResourceDTO;
import com.sankuai.avatar.web.dto.activity.WindowPeriodResourceDTO;
import com.sankuai.avatar.web.request.ActivityResourcePageRequest;
import com.sankuai.avatar.web.service.impl.ActivityResourceServiceImpl;
import com.sankuai.avatar.web.vo.activity.ResourceSumResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2023-03-09 14:53
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityResourceServiceTest {

    private ActivityResourceService service;

    @Mock
    private ActivityResource resource;
    @Mock
    private AppkeyResource appkeyResource;
    @Mock
    private WindowPeriodResourceService windowService;
    @Mock
    private ServiceCatalogAppKey serviceCatalogAppKey;

    static ActivityResourceBO resourceBO = new ActivityResourceBO();
    static ActivityResourceDTO resourceDTO = new ActivityResourceDTO();
    static ActivityHostBO hostBO = new ActivityHostBO();
    static PageResponse<ActivityResourceBO> boPageResponse = new PageResponse<>();
    static {
        hostBO.setMemory(1);
        hostBO.setCpu(1);
        hostBO.setDisk(1);
        hostBO.setCount(1);
        hostBO.setRegion("shanghai");

        resourceBO.setId(666);
        resourceBO.setFlowId(666);
        resourceBO.setFlowUuid(UUID.randomUUID().toString());
        resourceBO.setAppkey("test-appkey");
        resourceBO.setWindowPeriodId(233);
        resourceBO.setOrgId("x");
        resourceBO.setOrgName("x");
        resourceBO.setDescription("x");
        resourceBO.setName("x");
        resourceBO.setCount(1);
        resourceBO.setRetainCount(0);
        resourceBO.setCreateUser("x");
        resourceBO.setHostConfig(hostBO);
        resourceBO.setStatus(ResourceStatusType.CLOSE);
        resourceBO.setStartTime(new Date());
        resourceBO.setEndTime(new Date());
        resourceBO.setDeliverTime(new Date());
        resourceBO.setReturnTime(new Date());

        resourceDTO.setId(666);
        resourceDTO.setFlowId(666);
        resourceDTO.setFlowUuid(UUID.randomUUID().toString());
        resourceDTO.setAppkey("test-appkey");
        resourceDTO.setWindowPeriodId(233);
        resourceDTO.setOrgId("x");
        resourceDTO.setOrgName("x");
        resourceDTO.setDescription("x");
        resourceDTO.setName("x");
        resourceDTO.setCount(1);
        resourceDTO.setRetainCount(0);
        resourceDTO.setCreateUser("x");
        resourceDTO.setHostConfig(new ActivityHostDTO());
        resourceDTO.setStatus(ResourceStatusType.CLOSE);
        resourceDTO.setStartTime(new Date());
        resourceDTO.setEndTime(new Date());
        resourceDTO.setDeliverTime(new Date());
        resourceDTO.setReturnTime(new Date());

        boPageResponse.setPage(1);
        boPageResponse.setPageSize(10);
        boPageResponse.setTotalCount(1);
        boPageResponse.setTotalPage(1);
    }

    @Before
    public void setUp() throws Exception {
        service = new ActivityResourceServiceImpl(resource, appkeyResource, windowService, serviceCatalogAppKey);
    }

    @Test
    public void getPageActivityResource() {
        boPageResponse.setItems(Collections.singletonList(resourceBO));
        when(resource.queryPage(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(boPageResponse);
        ActivityResourcePageRequest request = new ActivityResourcePageRequest();
        PageResponse<ActivityResourceDTO> pageResponse = service.getPageActivityResource(request);
        Assert.assertEquals("test-appkey", pageResponse.getItems().get(0).getAppkey());
        verify(resource).queryPage(Mockito.any(ActivityResourceRequestBO.class));
    }

    @Test
    public void getActivityResource() {
        when(resource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.singletonList(resourceBO));
        ActivityResourcePageRequest request = new ActivityResourcePageRequest();
        List<ActivityResourceDTO> dtoList = service.getActivityResource(request);
        Assert.assertEquals("test-appkey", dtoList.get(0).getAppkey());
        verify(resource).query(Mockito.any(ActivityResourceRequestBO.class));
    }

    @Test
    public void getActivityResourceByPk() {
        when(resource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.singletonList(resourceBO));
        ActivityResourceDTO dto = service.getActivityResourceByPk(1);
        Assert.assertEquals("test-appkey", dto.getAppkey());
        verify(resource).query(Mockito.any(ActivityResourceRequestBO.class));
    }

    @Test
    public void getNoActivityResourceByPk() {
        when(resource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.emptyList());
        ActivityResourceDTO dto = service.getActivityResourceByPk(1);
        Assert.assertNull(dto);
        verify(resource).query(Mockito.any(ActivityResourceRequestBO.class));
    }

    @Test
    public void getActivityResourceByWindowId() {
        when(resource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.singletonList(resourceBO));
        List<ActivityResourceDTO> dtoList = service.getActivityResourceByWindowId(1);
        Assert.assertEquals("test-appkey", dtoList.get(0).getAppkey());
        verify(resource).query(Mockito.any(ActivityResourceRequestBO.class));
        verify(windowService, times(0)).getMaxWindowId();
    }
    @Test
    public void getActivityResourceByWindowIdNull() {
        when(resource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.singletonList(resourceBO));
        when(windowService.getMaxWindowId()).thenReturn(1);
        List<ActivityResourceDTO> dtoList = service.getActivityResourceByWindowId(null);
        Assert.assertEquals("test-appkey", dtoList.get(0).getAppkey());
        verify(resource).query(Mockito.any(ActivityResourceRequestBO.class));
        verify(windowService).getMaxWindowId();
    }

    @Test
    public void save() throws Exception {
        when(serviceCatalogAppKey.getAppKey(Mockito.anyString())).thenReturn(new AppKey());
        when(resource.save(Mockito.any(ActivityResourceBO.class))).thenReturn(true);
        Boolean save = service.save(resourceDTO);
        Assert.assertTrue(save);
        verify(serviceCatalogAppKey).getAppKey(Mockito.anyString());
        verify(resource).save(Mockito.any(ActivityResourceBO.class));
    }

    @Test
    public void delete() {
        when(resource.deleteByPk(Mockito.anyInt())).thenReturn(true);
        Boolean delete = service.delete(1);
        Assert.assertTrue(delete);
    }

    @Test
    public void getActivityResourceSum() {
        ActivityHostBO activityHostBO = JsonUtil.json2Bean(JsonUtil.bean2Json(hostBO), ActivityHostBO.class);
        activityHostBO.setRegion("beijing");
        ActivityResourceBO activityResourceBO = JsonUtil.json2Bean(JsonUtil.bean2Json(resourceBO), ActivityResourceBO.class);
        activityResourceBO.setHostConfig(activityHostBO);
        when(resource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Arrays.asList(resourceBO, activityResourceBO));
        when(windowService.getHitWindowPeriod(Mockito.any())).thenReturn(new WindowPeriodResourceDTO());
        ResourceSumResult resourceSum = service.getActivityResourceSum(1);
        Assert.assertEquals(2, resourceSum.getData().size());
        verify(resource).query(Mockito.any(ActivityResourceRequestBO.class));
        verify(windowService).getHitWindowPeriod(Mockito.any());
    }
}