package com.sankuai.avatar.resource.capacity;

import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.capacity.bo.*;
import com.sankuai.avatar.resource.capacity.constant.IdcRegionType;
import com.sankuai.avatar.resource.capacity.constant.MiddleWareType;
import com.sankuai.avatar.resource.capacity.constant.UtilizationStandardType;
import com.sankuai.avatar.resource.capacity.impl.AppkeyCapacityResourceImpl;
import com.sankuai.avatar.resource.capacity.request.AppkeyCapacityRequestBO;
import com.sankuai.avatar.dao.resource.repository.AppkeyCapacityRepository;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyCapacityDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyCapacityRequest;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
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
 * @create 2022-11-03 20:08
 */
public class AppkeyCapacityResourceTest extends TestBase {

    @Mock
    private AppkeyCapacityRepository repository;
    @Mock
    private OpsHttpClient client;

    private AppkeyCapacityResource resource;

    static AppkeyCapacityDO appkeyCapacityDO = new AppkeyCapacityDO();
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

        appkeyCapacityWhiteBO.setWhiteApp(WhiteType.CAPACITY);
        appkeyCapacityWhiteBO.setCName(WhiteType.CAPACITY.getCname());
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

        appkeyCapacityDO.setId(1);
        appkeyCapacityDO.setAppkey("unit");
        appkeyCapacityDO.setSetName("unit");
        appkeyCapacityDO.setCapacityLevel(4);
        appkeyCapacityDO.setStandardLevel(5);
        appkeyCapacityDO.setIsCapacityStandard(false);
        appkeyCapacityDO.setStandardReason("");
        appkeyCapacityDO.setStandardTips("");
        appkeyCapacityDO.setResourceUtil(JsonUtil.bean2Json(utilizationBO));
        appkeyCapacityDO.setUtilizationStandard(UtilizationStandardType.STANDARD.name());
        appkeyCapacityDO.setWhitelists(JsonUtil.bean2Json(Collections.singletonList(appkeyCapacityWhiteBO)));
        appkeyCapacityDO.setMiddleware(JsonUtil.bean2Json(Collections.singletonList(middleWareBO)));
        appkeyCapacityDO.setHosts(JsonUtil.bean2Json(Collections.singletonList(hostBO)));
        appkeyCapacityDO.setOctoHttpProvider(JsonUtil.bean2Json(Collections.singletonList(appkeyCapacityOctoProviderBO)));
        appkeyCapacityDO.setOctoThriftProvider(JsonUtil.bean2Json(Collections.emptyList()));
        appkeyCapacityDO.setAccessComponent(JsonUtil.bean2Json(Collections.singletonList(appkeyCapacityAccessComponentBO)));
        appkeyCapacityDO.setStateful(true);
        appkeyCapacityDO.setCanSingleHostRestart(true);
        appkeyCapacityDO.setIsPaas(false);
        appkeyCapacityDO.setOrgPath("100046,150042,1817,40001501");
        appkeyCapacityDO.setOrgDisplayName("基础研发平台/基础技术部/数据库研发中心/Blade内核技术组");
        appkeyCapacityDO.setUpdateBy("unit");

        appkeyCapacityBO.setAppkey("unit2");
        appkeyCapacityBO.setSetName("set");
        appkeyCapacityBO.setCapacityLevel(4);
        appkeyCapacityBO.setStandardLevel(4);
        appkeyCapacityBO.setIsCapacityStandard(true);
        appkeyCapacityBO.setStandardReason("");
        appkeyCapacityBO.setStandardTips("");
        appkeyCapacityBO.setUtilization(utilizationBO);
        appkeyCapacityBO.setUtilizationStandard(UtilizationStandardType.SKIP_STANDARD);
        appkeyCapacityBO.setWhiteList(Collections.singletonList(appkeyCapacityWhiteBO));
        appkeyCapacityBO.setMiddleWareList(Collections.singletonList(middleWareBO));
        appkeyCapacityBO.setHostList(Collections.singletonList(hostBO));
        appkeyCapacityBO.setOctoHttpProviderList(Collections.emptyList());
        appkeyCapacityBO.setOctoThriftProviderList(Collections.singletonList(appkeyCapacityOctoProviderBO));
        appkeyCapacityBO.setAccessComponentList(Collections.singletonList(appkeyCapacityAccessComponentBO));
        appkeyCapacityBO.setIsPaas(false);
        appkeyCapacityBO.setCanSingleHostRestart(true);
        appkeyCapacityBO.setStateful(false);
        appkeyCapacityBO.setOrgPath("123");
        appkeyCapacityBO.setOrgDisplayName("xxx");
        appkeyCapacityBO.setUpdateBy("zzz");
    }

    @Override
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        EntityHelper.initEntityNameMap(AppkeyCapacityDO.class, new Config());
        resource = new AppkeyCapacityResourceImpl(repository, client);
    }

    @Test
    public void queryPage() {
        when(repository.query(Mockito.any(AppkeyCapacityRequest.class))).thenReturn(Collections.singletonList(appkeyCapacityDO));
        PageResponse<AppkeyCapacityBO> pageResponse = resource.queryPage(AppkeyCapacityRequestBO.builder().build());
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(1,pageResponse.getPage());
    }

    @Test
    public void query() {
        when(repository.query(Mockito.any(AppkeyCapacityRequest.class))).thenReturn(Collections.singletonList(appkeyCapacityDO));
        List<AppkeyCapacityBO> query = resource.query(AppkeyCapacityRequestBO.builder().build());
        Assert.assertEquals(1, query.size());
        AppkeyCapacityBO appkeyCapacityBO = query.get(0);
        Assert.assertEquals(1, appkeyCapacityBO.getHostList().size());
        Assert.assertEquals("100046,150042,1817,40001501", appkeyCapacityBO.getOrgPath());
    }

    @Test
    public void save() {
        Assert.assertFalse(resource.save(null));
        when(repository.query(Mockito.any(AppkeyCapacityRequest.class))).thenReturn(Collections.emptyList());
        when(repository.insert(Mockito.any(AppkeyCapacityDO.class))).thenReturn(false);
        Assert.assertFalse(resource.save(appkeyCapacityBO));
        when(repository.query(Mockito.any(AppkeyCapacityRequest.class))).thenReturn(Collections.singletonList(appkeyCapacityDO));
        when(repository.update(Mockito.any(AppkeyCapacityDO.class))).thenReturn(true);
        Assert.assertTrue(resource.save(appkeyCapacityBO));
    }

    @Test
    public void deleteByCondition() {
        Assert.assertFalse(resource.deleteByCondition(null));
        when(repository.query(Mockito.any(AppkeyCapacityRequest.class))).thenReturn(Collections.singletonList(appkeyCapacityDO));
        when(repository.delete(Mockito.anyInt())).thenReturn(true);
        Assert.assertTrue(resource.deleteByCondition(AppkeyCapacityRequestBO.builder().appkey("xxx").build()));
    }

    @Test
    public void updateOpsCapacity() {
        when(client.updateOpsCapacity(Mockito.anyString(), Mockito.any())).thenReturn(true);
        boolean update = resource.updateOpsCapacity("x", "y", 5);
        Assert.assertTrue(update);
    }
    @Test
    public void updateOpsCapacityThrowsException() {
        when(client.updateOpsCapacity(Mockito.anyString(), Mockito.any())).thenThrow(SdkCallException.class);
        boolean update = resource.updateOpsCapacity("x", "y", 5);
        Assert.assertFalse(update);
    }
}