package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.capacity.AppkeyPaasCapacityResource;
import com.sankuai.avatar.resource.capacity.AppkeyPaasClientResource;
import com.sankuai.avatar.resource.capacity.AppkeyPaasStandardClientResource;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacitySummaryBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasCapacityBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasClientBO;
import com.sankuai.avatar.resource.capacity.constant.PaasCapacityType;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasCapacityRequestBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasClientRequestBO;
import com.sankuai.avatar.web.dto.capacity.*;
import com.sankuai.avatar.web.request.AppkeyPaasCapacityPageRequest;
import com.sankuai.avatar.web.service.impl.AppkeyPaasCapacityServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2022-09-23 14:56
 */
@RunWith(MockitoJUnitRunner.class)
public class AppkeyPaasCapacityServiceTest{

    private AppkeyPaasCapacityService paasCapacityService;

    @Mock
    private AppkeyPaasCapacityResource appkeyPaasCapacityResource;
    @Mock
    private AppkeyPaasClientResource appkeyPaasClientResource;
    @Mock
    private AppkeyPaasStandardClientResource appkeyPaasStandardClientResource;

    static AppkeyPaasCapacityBO appkeyPaasCapacityBO = new AppkeyPaasCapacityBO();
    static AppkeyPaasClientBO clientBO = new AppkeyPaasClientBO();
    static {
        appkeyPaasCapacityBO.setPaasName("Pike");
        appkeyPaasCapacityBO.setType(PaasCapacityType.APPKEY);
        appkeyPaasCapacityBO.setTypeName("com.sankuai.nginx.gct.web");
        appkeyPaasCapacityBO.setTypeComment("xxx");
        appkeyPaasCapacityBO.setPaasAppkey("com.sankuai.nginx.gct.web");
        appkeyPaasCapacityBO.setIsCore(false);
        appkeyPaasCapacityBO.setCapacityLevel(3);
        appkeyPaasCapacityBO.setStandardLevel(3);
        appkeyPaasCapacityBO.setStandardReason("xxx");
        appkeyPaasCapacityBO.setStandardTips("xxx");
        appkeyPaasCapacityBO.setIsCapacityStandard(true);
        appkeyPaasCapacityBO.setClientAppkey("test-appkey-bo");
        appkeyPaasCapacityBO.setClientConfig(new ArrayList<>());
        appkeyPaasCapacityBO.setStandardConfig(new ArrayList<>());
        appkeyPaasCapacityBO.setIsConfigStandard(true);
        appkeyPaasCapacityBO.setIsWhite(false);
        appkeyPaasCapacityBO.setWhiteReason("xxx");
        appkeyPaasCapacityBO.setOwner("zhangbin80,bianji,yanermeng");
        appkeyPaasCapacityBO.setUpdateBy("chenxinyi");

        clientBO.setClientAppkey("test-pike-web");
        clientBO.setPaasName("Pike");
        clientBO.setLanguage("java");
        clientBO.setVersion("3.3.5");
        clientBO.setStandardVersion("3.3.9");
        clientBO.setIsCapacityStandard(false);
        clientBO.setArtifactId("pike-client");
    }

    static AppkeyPaasCapacityReportDTO reportDTO = new AppkeyPaasCapacityReportDTO();
    static AppkeyPaasCapacityDTO appkeyPaasCapacityDTO = new AppkeyPaasCapacityDTO();
    static AppkeyPaasClientDTO clientDTO = new AppkeyPaasClientDTO();
    static AppkeyPaasStandardClientDTO standardClientDTO = new AppkeyPaasStandardClientDTO();

    static {
        appkeyPaasCapacityDTO.setPaasName("Pike");
        appkeyPaasCapacityDTO.setType(PaasCapacityType.APPKEY);
        appkeyPaasCapacityDTO.setTypeName("com.sankuai.nginx.gct.web");
        appkeyPaasCapacityDTO.setTypeComment("xxx");
        appkeyPaasCapacityDTO.setPaasAppkey("com.sankuai.nginx.gct.web");
        appkeyPaasCapacityDTO.setIsCore(false);
        appkeyPaasCapacityDTO.setCapacityLevel(4);
        appkeyPaasCapacityDTO.setStandardLevel(4);
        appkeyPaasCapacityDTO.setStandardReason("xxx");
        appkeyPaasCapacityDTO.setStandardTips("xxx");
        appkeyPaasCapacityDTO.setIsCapacityStandard(true);
        appkeyPaasCapacityDTO.setAppkey("test-appkey-dto");
        appkeyPaasCapacityDTO.setClientConfig(new ArrayList<>());
        appkeyPaasCapacityDTO.setStandardConfig(new ArrayList<>());
        appkeyPaasCapacityDTO.setIsConfigStandard(true);
        appkeyPaasCapacityDTO.setIsWhite(false);
        appkeyPaasCapacityDTO.setWhiteReason("xxx");
        appkeyPaasCapacityDTO.setOwner("zhangbin80,bianji,yanermeng");
        appkeyPaasCapacityDTO.setUpdateBy("chenxinyi");

        clientDTO.setAppkey("test-pike-web");
        clientDTO.setPaasName("Pike");
        clientDTO.setLanguage("java");
        clientDTO.setVersion("3.3.5");
        clientDTO.setStandardVersion("3.3.9");
        clientDTO.setIsCapacityStandard(false);
        clientDTO.setArtifactId("pike-client");

        standardClientDTO.setPaasName("Pike");
        standardClientDTO.setLanguage("java");
        standardClientDTO.setStandardVersion("3.3.9");
        standardClientDTO.setArtifactId("pike-client");

        reportDTO.setPaasName("Pike");
        reportDTO.setAppkeyPaasCapacityDTOList(Collections.singletonList(appkeyPaasCapacityDTO));
        reportDTO.setAppkeyPaasClientDTOList(Collections.singletonList(clientDTO));
        reportDTO.setAppkeyPaasStandardClientDTOList(Collections.singletonList(standardClientDTO));
    }

    static AppkeyCapacitySummaryDTO summaryDTO = new AppkeyCapacitySummaryDTO();
    static {
        summaryDTO.setCapacityLevel(4);
        summaryDTO.setStandardCapacityLevel(5);
        summaryDTO.setStandardTips("tips");
        summaryDTO.setIsCapacityStandard(false);
    }

    @Before
    public void setUp() {
        paasCapacityService = new AppkeyPaasCapacityServiceImpl(appkeyPaasCapacityResource,
                                                                appkeyPaasClientResource,
                                                                appkeyPaasStandardClientResource);
    }

    @Test
    public void testReportAppkeyPaasCapacity() {
        when(appkeyPaasCapacityResource.save(Mockito.any())).thenReturn(true);
        when(appkeyPaasClientResource.save(Mockito.any())).thenReturn(true);
        when(appkeyPaasStandardClientResource.save(Mockito.any())).thenReturn(true);
        boolean report = paasCapacityService.reportAppkeyPaasCapacity(Collections.singletonList(reportDTO));
        Assert.assertTrue(report);
        verify(appkeyPaasCapacityResource).save(Mockito.any());
        verify(appkeyPaasClientResource).save(Mockito.any());
        verify(appkeyPaasStandardClientResource).save(Mockito.any());
    }
    @Test
    public void testReportAppkeyPaasCapacityThrowsException() {
        when(appkeyPaasCapacityResource.save(Mockito.any())).thenThrow(NullPointerException.class);
        boolean report = paasCapacityService.reportAppkeyPaasCapacity(Collections.singletonList(reportDTO));
        Assert.assertFalse(report);
        verify(appkeyPaasCapacityResource).save(Mockito.any());
        verify(appkeyPaasClientResource, times(0)).save(Mockito.any());
        verify(appkeyPaasStandardClientResource, times(0)).save(Mockito.any());
    }

    @Test
    public void testQueryUnStandardLevel() {
        when(appkeyPaasCapacityResource.query(Mockito.any(AppkeyPaasCapacityRequestBO.class))).thenReturn(Collections.singletonList(appkeyPaasCapacityBO));
        List<AppkeyPaasCapacityDTO> appkeyPaasCapacityDTOList = paasCapacityService.queryUnStandardLevel("appkey");
        assert appkeyPaasCapacityDTOList.size() > 0;
        for (AppkeyPaasCapacityDTO appkeyPaasCapacityDTO : appkeyPaasCapacityDTOList) {
            Assert.assertEquals("test-appkey-bo", appkeyPaasCapacityDTO.getAppkey());
        }
        verify(appkeyPaasCapacityResource).query(Mockito.any());
    }

    @Test
    public void testQueryPaasCapacityByAppkey() {
        when(appkeyPaasCapacityResource.query(Mockito.any(AppkeyPaasCapacityRequestBO.class))).thenReturn(Collections.singletonList(appkeyPaasCapacityBO));
        List<AppkeyPaasCapacityDTO> appkeyPaasCapacityDTOList = paasCapacityService.queryPaasCapacityByAppkey("appkey");
        assert appkeyPaasCapacityDTOList.size() > 0;
        for (AppkeyPaasCapacityDTO appkeyPaasCapacityDTO : appkeyPaasCapacityDTOList) {
            Assert.assertEquals("test-appkey-bo", appkeyPaasCapacityDTO.getAppkey());
        }
        verify(appkeyPaasCapacityResource).query(Mockito.any());
    }

    @Test
    public void testQueryPaasCapacityByPaasAppkey() {
        when(appkeyPaasCapacityResource.query(Mockito.any(AppkeyPaasCapacityRequestBO.class))).thenReturn(Collections.singletonList(appkeyPaasCapacityBO));
        List<AppkeyPaasCapacityDTO> appkeyPaasCapacityDTOList = paasCapacityService.queryPaasCapacityByPaasAppkey("appkey");
        assert appkeyPaasCapacityDTOList.size() > 0;
        for (AppkeyPaasCapacityDTO appkeyPaasCapacityDTO : appkeyPaasCapacityDTOList) {
            Assert.assertEquals("test-appkey-bo", appkeyPaasCapacityDTO.getAppkey());
        }
        verify(appkeyPaasCapacityResource).query(Mockito.any());
    }

    @Test
    public void getAppkeyPaasCapacitySummaryWithCache(){
        AppkeyCapacitySummaryBO summaryBO = new AppkeyCapacitySummaryBO();
        summaryBO.setCapacityLevel(3);
        when(appkeyPaasCapacityResource.getAppkeyCapacitySummaryBO(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(summaryBO);
        AppkeyCapacitySummaryDTO summary = paasCapacityService.getAppkeyPaasCapacitySummary("appkey");
        Assert.assertEquals(summaryBO.getCapacityLevel(), summary.getCapacityLevel());
        verify(appkeyPaasCapacityResource).getAppkeyCapacitySummaryBO(Mockito.anyString(),Mockito.anyBoolean());
        verify(appkeyPaasCapacityResource,times(0)).query(Mockito.any());
    }

    @Test
    public void getAppkeyPaasCapacitySummary(){
        when(appkeyPaasCapacityResource.getAppkeyCapacitySummaryBO(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(null);
        when(appkeyPaasCapacityResource.query(Mockito.any())).thenReturn(Collections.singletonList(appkeyPaasCapacityBO));
        AppkeyCapacitySummaryDTO summary = paasCapacityService.getAppkeyPaasCapacitySummary("appkey");
        Assert.assertEquals(appkeyPaasCapacityBO.getCapacityLevel(), summary.getCapacityLevel());
        verify(appkeyPaasCapacityResource).getAppkeyCapacitySummaryBO(Mockito.anyString(),Mockito.anyBoolean());
        verify(appkeyPaasCapacityResource).query(Mockito.any());
    }

    @Test
    public void getAppkeyPaasCapacitySummaryNoCache(){
        AppkeyPaasCapacityBO bo1 = JsonUtil.json2Bean(JsonUtil.bean2Json(appkeyPaasCapacityBO), AppkeyPaasCapacityBO.class);
        bo1.setCapacityLevel(4);
        bo1.setStandardLevel(5);
        bo1.setIsCapacityStandard(false);
        bo1.setIsWhite(false);
        AppkeyPaasCapacityBO bo2 = JsonUtil.json2Bean(JsonUtil.bean2Json(appkeyPaasCapacityBO), AppkeyPaasCapacityBO.class);
        bo2.setCapacityLevel(2);
        bo2.setIsWhite(true);
        when(appkeyPaasCapacityResource.query(Mockito.any(AppkeyPaasCapacityRequestBO.class))).thenReturn(Arrays.asList(appkeyPaasCapacityBO, bo1, bo2));
        AppkeyCapacitySummaryDTO summaryDTO = paasCapacityService.getAppkeyPaasCapacitySummaryNoCache("x");
        Assert.assertEquals(4, summaryDTO.getCapacityLevel().intValue());
        Assert.assertEquals(5, summaryDTO.getStandardCapacityLevel().intValue());
        Assert.assertFalse(summaryDTO.getIsCapacityStandard());
        verify(appkeyPaasCapacityResource).query(Mockito.any());
    }

    @Test
    public void getAppkeyPaasCapacitySummaryByPaasAppkey(){
        when(appkeyPaasCapacityResource.getAppkeyCapacitySummaryBO(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(null);
        when(appkeyPaasCapacityResource.query(Mockito.any())).thenReturn(Collections.singletonList(appkeyPaasCapacityBO));
        AppkeyCapacitySummaryDTO summary = paasCapacityService.getAppkeyPaasCapacitySummaryByPaasAppkey("appkey");
        Assert.assertEquals(appkeyPaasCapacityBO.getCapacityLevel(), summary.getCapacityLevel());
        verify(appkeyPaasCapacityResource).getAppkeyCapacitySummaryBO(Mockito.anyString(),Mockito.anyBoolean());
        verify(appkeyPaasCapacityResource).query(Mockito.any());
    }

    @Test
    public void getAppkeyPaasCapacitySummaryByPaasAppkeyWithCache(){
        AppkeyCapacitySummaryBO summaryBO = new AppkeyCapacitySummaryBO();
        summaryBO.setCapacityLevel(3);
        when(appkeyPaasCapacityResource.getAppkeyCapacitySummaryBO(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(summaryBO);
        AppkeyCapacitySummaryDTO summary = paasCapacityService.getAppkeyPaasCapacitySummaryByPaasAppkey("appkey");
        Assert.assertEquals(summaryBO.getCapacityLevel(), summary.getCapacityLevel());
        verify(appkeyPaasCapacityResource).getAppkeyCapacitySummaryBO(Mockito.anyString(),Mockito.anyBoolean());
        verify(appkeyPaasCapacityResource,times(0)).query(Mockito.any());
    }

    @Test
    public void getAppkeyPaasCapacitySummaryByPaasAppkeyNoCache(){
        when(appkeyPaasCapacityResource.query(Mockito.any(AppkeyPaasCapacityRequestBO.class))).thenReturn(Collections.singletonList(appkeyPaasCapacityBO));
        AppkeyCapacitySummaryDTO summaryDTO = paasCapacityService.getAppkeyPaasCapacitySummaryByPaasAppkeyNoCache("x");
        Assert.assertEquals(3, summaryDTO.getCapacityLevel().intValue());
        Assert.assertEquals(3, summaryDTO.getStandardCapacityLevel().intValue());
        Assert.assertEquals("容灾已达标", summaryDTO.getStandardTips());
        verify(appkeyPaasCapacityResource).query(Mockito.any());
    }

    @Test
    public void cacheAppkeyCapacitySummary(){
        when(appkeyPaasCapacityResource.setAppkeyCapacitySummaryBO(Mockito.anyString(),Mockito.any(),Mockito.anyBoolean())).thenReturn(true);
        Boolean cache = paasCapacityService.cacheAppkeyCapacitySummary("x", false);
        Assert.assertTrue(cache);
        verify(appkeyPaasCapacityResource).query(Mockito.any());
        verify(appkeyPaasCapacityResource,times(2)).setAppkeyCapacitySummaryBO(Mockito.anyString(),Mockito.any(),Mockito.anyBoolean());
    }

    @Test
    public void testQueryPaasClientByAppkey() {
        when(appkeyPaasClientResource.query(Mockito.any(AppkeyPaasClientRequestBO.class))).thenReturn(Collections.singletonList(clientBO));
        List<AppkeyPaasClientDTO> appkeyPaasClientDTOList = paasCapacityService.queryPaasClientByAppkey("paas", "appkey");
        Assert.assertNotNull(appkeyPaasClientDTOList);
        for (AppkeyPaasClientDTO appkeyPaasClientDTO : appkeyPaasClientDTOList) {
            Assert.assertEquals("test-pike-web", appkeyPaasClientDTO.getAppkey());
            Assert.assertEquals("Pike", appkeyPaasClientDTO.getPaasName());
        }
        verify(appkeyPaasClientResource).query(Mockito.any());
    }

    @Test
    public void testQueryPaasClientByAppkeyAndDate() {
        when(appkeyPaasClientResource.query(Mockito.any(AppkeyPaasClientRequestBO.class))).thenReturn(Collections.singletonList(clientBO));
        List<AppkeyPaasClientDTO> appkeyPaasClientDTOList = paasCapacityService.queryPaasClientByAppkey("paas", "appkey", new Date());
        Assert.assertNotNull(appkeyPaasClientDTOList);
        for (AppkeyPaasClientDTO appkeyPaasClientDTO : appkeyPaasClientDTOList) {
            Assert.assertEquals("test-pike-web", appkeyPaasClientDTO.getAppkey());
            Assert.assertEquals("Pike", appkeyPaasClientDTO.getPaasName());
        }
        verify(appkeyPaasClientResource).query(Mockito.any());
    }

    @Test
    public void testQueryPage() {
        PageResponse<AppkeyPaasCapacityBO> pageResponse = new PageResponse<>();
        pageResponse.setPage(1);
        pageResponse.setPageSize(10);
        pageResponse.setTotalPage(1);
        pageResponse.setTotalCount(1);
        pageResponse.setItems(Collections.singletonList(appkeyPaasCapacityBO));
        when(appkeyPaasCapacityResource.queryPage(Mockito.any())).thenReturn(pageResponse);
        PageResponse<AppkeyPaasCapacityDTO> response = paasCapacityService.queryPage(AppkeyPaasCapacityPageRequest.builder()
                        .paasName("paas")
                        .build());
        Assert.assertEquals(10, response.getPageSize());
        assert response.getItems().size() > 0;
        for (AppkeyPaasCapacityDTO item : response.getItems()) {
            Assert.assertEquals("Pike", item.getPaasName());
        }
        verify(appkeyPaasCapacityResource).queryPage(Mockito.any());
    }

    @Test
    public void testDeleteAppkeyPaasCapacityByUpdateDate() {
        when(appkeyPaasCapacityResource.deleteByCondition(Mockito.any())).thenReturn(true);
        when(appkeyPaasClientResource.deleteByCondition(Mockito.any())).thenReturn(true);
        boolean delete = paasCapacityService.deleteAppkeyPaasCapacityByUpdateDate(LocalDate.now().plusDays(-30));
        Assert.assertTrue(delete);
        verify(appkeyPaasCapacityResource).deleteByCondition(Mockito.any());
        verify(appkeyPaasClientResource).deleteByCondition(Mockito.any());
    }

    @Test
    public void getPageAggregatedByAppkey() {
        PageResponse<AppkeyPaasCapacityBO> pageResponse = new PageResponse<>();
        pageResponse.setPage(1);
        pageResponse.setPageSize(10);
        pageResponse.setTotalPage(1);
        pageResponse.setTotalCount(1);
        pageResponse.setItems(Collections.singletonList(appkeyPaasCapacityBO));
        when(appkeyPaasCapacityResource.getPageAggregatedByAppkey(Mockito.anyString(),
                Mockito.any(Date.class), Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(pageResponse);
        PageResponse<AppkeyPaasCapacityDTO> pageAggregated = paasCapacityService.getPageAggregatedByAppkey("appkey", new ArrayList<>(),false, 1, 10);
        Assert.assertEquals(10, pageAggregated.getPageSize());
        assert pageAggregated.getItems().size() > 0;
        for (AppkeyPaasCapacityDTO item : pageAggregated.getItems()) {
            Assert.assertEquals("com.sankuai.nginx.gct.web", item.getPaasAppkey());
        }
        verify(appkeyPaasCapacityResource).getPageAggregatedByAppkey(Mockito.anyString(),
                Mockito.any(Date.class), Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean());
    }


    @Test
    public void getPaasNamesByAppkey() {
        when(appkeyPaasCapacityResource.getPaasNamesByAppkey(Mockito.anyString(), Mockito.any(Date.class), Mockito.anyBoolean())).thenReturn(Arrays.asList("Cellar", "Eagle"));
        List<String> paasNames = paasCapacityService.getPaasNamesByAppkey("appkey", false);
        Assert.assertTrue(CollectionUtils.isNotEmpty(paasNames));
        verify(appkeyPaasCapacityResource).getPaasNamesByAppkey(Mockito.anyString(), Mockito.any(Date.class), Mockito.anyBoolean());
    }

    @Test
    public void getValidPaasAppkeys() {
        when(appkeyPaasCapacityResource.getPaasAppkeys(Mockito.any(Date.class))).thenReturn(Collections.singletonList("paas-appkey"));
        List<String> paasAppkeys = paasCapacityService.getValidPaasAppkeys();
        Assert.assertTrue(CollectionUtils.isNotEmpty(paasAppkeys));
        verify(appkeyPaasCapacityResource).getPaasAppkeys(Mockito.any(Date.class));
    }

    @Test
    public void getValidClientAppkeys(){
        when(appkeyPaasCapacityResource.getClientAppkeys(Mockito.any(Date.class))).thenReturn(Collections.singletonList("client-appkey"));
        List<String> clientAppkeys = paasCapacityService.getValidClientAppkeys();
        Assert.assertTrue(CollectionUtils.isNotEmpty(clientAppkeys));
        verify(appkeyPaasCapacityResource).getClientAppkeys(Mockito.any(Date.class));
    }

}