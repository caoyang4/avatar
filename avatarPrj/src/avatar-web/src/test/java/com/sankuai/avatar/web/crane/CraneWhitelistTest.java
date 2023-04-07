package com.sankuai.avatar.web.crane;

import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.dxMessage.DxMessageResource;
import com.sankuai.avatar.resource.whitelist.OwtSetWhitelistResource;
import com.sankuai.avatar.resource.whitelist.ServiceWhitelistResource;
import com.sankuai.avatar.resource.whitelist.bo.OwtSetWhitelistBO;
import com.sankuai.avatar.resource.whitelist.bo.ServiceWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.request.OwtSetWhitelistRequestBO;
import com.sankuai.avatar.resource.whitelist.request.ServiceWhitelistRequestBO;
import com.sankuai.avatar.sdk.entity.servicecatalog.AppKey;
import com.sankuai.avatar.sdk.entity.servicecatalog.Org;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CraneWhitelistTest {

    @Mock
    private ServiceCatalogAppKey serviceCatalogAppKey;
    @Mock
    private DxMessageResource dxMessageResource;
    @Mock
    private ServiceWhitelistResource serviceWhitelistResource;
    @Mock
    private OwtSetWhitelistResource owtSetWhitelistResource;

    private CraneWhitelist crane;
    static ServiceWhitelistBO serviceWhitelistBO = new ServiceWhitelistBO();
    static ServiceWhitelistBO serviceWhitelistBO2 = new ServiceWhitelistBO();
    static PageResponse<ServiceWhitelistBO> pageResponse1 = new PageResponse<>();
    static PageResponse<ServiceWhitelistBO> pageResponse2 = new PageResponse<>();
    static {
        serviceWhitelistBO.setId(0);
        serviceWhitelistBO.setApp(WhiteType.CAPACITY);
        serviceWhitelistBO.setReason("reason");
        serviceWhitelistBO.setAppkey("com.sankuai.avatar.web");
        serviceWhitelistBO.setApplication("application");
        serviceWhitelistBO.setOrgIds("1021866");
        serviceWhitelistBO.setSetName("test-set");
        serviceWhitelistBO.setInputUser("inputUser");
        serviceWhitelistBO.setAddTime(new Date());
        serviceWhitelistBO.setEndTime(DateUtils.getAfterDaysFromNow(2));
        serviceWhitelistBO2 = JsonUtil.json2Bean(JsonUtil.bean2Json(serviceWhitelistBO), ServiceWhitelistBO.class);
        serviceWhitelistBO2.setAppkey("");
        pageResponse1.setPage(1);
        pageResponse1.setPageSize(200);
        pageResponse1.setTotalPage(1);
        pageResponse1.setItems(Arrays.asList(serviceWhitelistBO, serviceWhitelistBO2));
        pageResponse2.setPage(1);
        pageResponse2.setPageSize(200);
        pageResponse2.setTotalPage(1);
        pageResponse2.setItems(Collections.emptyList());
    }
    static OwtSetWhitelistBO owtSetWhitelistBO = new OwtSetWhitelistBO();
    static {
        owtSetWhitelistBO.setId(0);
        owtSetWhitelistBO.setApp(WhiteType.CAPACITY);
        owtSetWhitelistBO.setOwt("owt");
        owtSetWhitelistBO.setSetName("test-set");
        owtSetWhitelistBO.setReason("reason");
        owtSetWhitelistBO.setApplyBy("applyBy");
        owtSetWhitelistBO.setStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        owtSetWhitelistBO.setEndTime(new GregorianCalendar(2022, Calendar.JANUARY, 1).getTime());
    }
    static AppKey scAppKey = new AppKey();
    static Org scOrg = new Org();
    static {
        scAppKey.setApplicationName("美团");
        scAppKey.setRdAdmin("x,y,z");
        scOrg.setAncestors(Collections.emptyList());
        scOrg.setId(1021866);
        scAppKey.setTeam(scOrg);
    }
    @Before
    public void setUp() {
        crane = new CraneWhitelist(serviceCatalogAppKey, dxMessageResource,
                serviceWhitelistResource, owtSetWhitelistResource);
    }
    @Test
    public void testClearExpireWhitelistWithItems() {
        final List<ServiceWhitelistBO> boList = Collections.singletonList(serviceWhitelistBO);
        when(serviceWhitelistResource.getExpireWhitelist()).thenReturn(boList);
        when(serviceWhitelistResource.deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(false);
        final List<OwtSetWhitelistBO> owtSetWhitelistBOS = Collections.singletonList(owtSetWhitelistBO);
        when(owtSetWhitelistResource.getExpireWhitelist()).thenReturn(owtSetWhitelistBOS);
        when(owtSetWhitelistResource.deleteByCondition(Mockito.any(OwtSetWhitelistRequestBO.class))).thenReturn(false);
        crane.clearExpireWhitelist();
        verify(serviceWhitelistResource, times(1)).deleteByCondition(any());
        verify(owtSetWhitelistResource, times(1)).deleteByCondition(any());
    }
    @Test
    public void testClearExpireWhitelistNoItems() {
        when(serviceWhitelistResource.getExpireWhitelist()).thenReturn(Collections.emptyList());
        final List<OwtSetWhitelistBO> owtSetWhitelistBOS = Collections.singletonList(owtSetWhitelistBO);
        when(owtSetWhitelistResource.getExpireWhitelist()).thenReturn(owtSetWhitelistBOS);
        when(owtSetWhitelistResource.deleteByCondition(Mockito.any(OwtSetWhitelistRequestBO.class))).thenReturn(false);
        crane.clearExpireWhitelist();
        verify(serviceWhitelistResource, times(0)).deleteByCondition(any());
        verify(owtSetWhitelistResource, times(1)).deleteByCondition(any());
    }
    @Test
    public void testClearExpireWhitelist_OwtSetWhitelistResourceGetExpireWhitelistReturnsNoItems() {
        final List<ServiceWhitelistBO> boList = Collections.singletonList(serviceWhitelistBO);
        when(serviceWhitelistResource.getExpireWhitelist()).thenReturn(boList);
        when(serviceWhitelistResource.deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(false);
        when(owtSetWhitelistResource.getExpireWhitelist()).thenReturn(Collections.emptyList());
        crane.clearExpireWhitelist();
        verify(serviceWhitelistResource, times(1)).deleteByCondition(any());
        verify(owtSetWhitelistResource, times(0)).deleteByCondition(any());
    }
    @Test
    public void testUpdateWhitelistOrgAndApplication() throws Exception {
        ServiceWhitelistRequestBO requestBO1 = ServiceWhitelistRequestBO.builder().build();
        ServiceWhitelistRequestBO requestBO2 = ServiceWhitelistRequestBO.builder().build();
        requestBO1.setPageSize(200);
        when(serviceWhitelistResource.queryPage(requestBO1)).thenReturn(pageResponse1);
        requestBO2.setPage(2);
        requestBO2.setPageSize(200);
        when(serviceWhitelistResource.queryPage(requestBO2)).thenReturn(pageResponse2);
        when(serviceWhitelistResource.save(Mockito.any(ServiceWhitelistBO.class))).thenReturn(true);
        when(serviceWhitelistResource.deleteByCondition(Mockito.any(ServiceWhitelistRequestBO.class))).thenReturn(true);
        when(serviceCatalogAppKey.getAppKey(Mockito.anyString())).thenReturn(scAppKey);
        crane.updateWhitelistOrgAndApplication();
        verify(serviceWhitelistResource,times(2)).queryPage(Mockito.any(ServiceWhitelistRequestBO.class));
        verify(serviceWhitelistResource, times(1)).save(Mockito.any());
        verify(serviceWhitelistResource, times(1)).deleteByCondition(Mockito.any());
        verify(serviceCatalogAppKey,times(1)).getAppKey(Mockito.anyString());
    }
    @Test
    public void testSendExpireWhitelistNotice() throws Exception {
        ServiceWhitelistRequestBO requestBO1 = ServiceWhitelistRequestBO.builder().build();
        ServiceWhitelistRequestBO requestBO2 = ServiceWhitelistRequestBO.builder().build();
        requestBO1.setPageSize(200);
        when(serviceWhitelistResource.queryPage(requestBO1)).thenReturn(pageResponse1);
        requestBO2.setPage(2);
        requestBO2.setPageSize(200);
        when(serviceWhitelistResource.queryPage(requestBO2)).thenReturn(pageResponse2);
        when(serviceCatalogAppKey.getAppKey(Mockito.anyString())).thenReturn(scAppKey);
        when(dxMessageResource.pushDxMessage(Mockito.anyList(), Mockito.anyString())).thenReturn(true);
        crane.sendExpireWhitelistNotice();
        verify(serviceWhitelistResource,times(2)).queryPage(Mockito.any(ServiceWhitelistRequestBO.class));
        verify(serviceCatalogAppKey,times(1)).getAppKey(Mockito.anyString());
        verify(dxMessageResource, times(1)).pushDxMessage(Mockito.anyList(), Mockito.anyString());
    }

}
