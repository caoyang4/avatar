package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.whitelist.OwtSetWhitelistResource;
import com.sankuai.avatar.resource.whitelist.bo.OwtSetWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.request.OwtSetWhitelistRequestBO;
import com.sankuai.avatar.web.dto.whitelist.OwtSetWhitelistDTO;
import com.sankuai.avatar.web.request.OwtSetWhitelistPageRequest;
import com.sankuai.avatar.web.service.impl.OwtSetWhitelistServiceImpl;
import org.apache.commons.collections.CollectionUtils;
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
 * @create 2022-11-15 20:01
 */
@RunWith(MockitoJUnitRunner.class)
public class OwtSetWhitelistServiceTest {

    @Mock
    private OwtSetWhitelistResource owtSetWhitelistResource;

    private OwtSetWhitelistService service;

    static OwtSetWhitelistBO owtSetWhitelistBO = new OwtSetWhitelistBO();
    static {
        owtSetWhitelistBO.setOwt("test-owt");
        owtSetWhitelistBO.setSetName("test-set");
        owtSetWhitelistBO.setApp(WhiteType.CAPACITY);
        owtSetWhitelistBO.setReason("无可奉告");
        owtSetWhitelistBO.setApplyBy("test");
        owtSetWhitelistBO.setStartTime(new Date());
        owtSetWhitelistBO.setEndTime(new Date());
    }

    static OwtSetWhitelistDTO owtSetWhitelistDTO = new OwtSetWhitelistDTO();
    static {
        owtSetWhitelistDTO.setOwt("test-owt");
        owtSetWhitelistDTO.setSetName("test-set");
        owtSetWhitelistDTO.setApp(WhiteType.CAPACITY);
        owtSetWhitelistDTO.setReason("无可奉告");
        owtSetWhitelistDTO.setApplyBy("test");
        owtSetWhitelistDTO.setStartTime(new Date());
        owtSetWhitelistDTO.setEndTime(new Date());
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        service = new OwtSetWhitelistServiceImpl(owtSetWhitelistResource);
    }

    @Test
    public void queryPage() {
        PageResponse<OwtSetWhitelistBO> boPageResponse = new PageResponse<>();
        boPageResponse.setTotalPage(1);
        boPageResponse.setPage(1);
        boPageResponse.setPageSize(10);
        boPageResponse.setTotalCount(1);
        boPageResponse.setItems(Collections.singletonList(owtSetWhitelistBO));
        when(owtSetWhitelistResource.queryPage(Mockito.any(OwtSetWhitelistRequestBO.class))).thenReturn(boPageResponse);
        PageResponse<OwtSetWhitelistDTO> pageResponse = service.queryPage(OwtSetWhitelistPageRequest.builder().owt("test-owt").build());
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertEquals(1, pageResponse.getPage());
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
        for (OwtSetWhitelistDTO dto : pageResponse.getItems()) {
            Assert.assertEquals("test-owt", dto.getOwt());
        }
        verify(owtSetWhitelistResource,times(1)).queryPage(any());
    }

    @Test
    public void saveOwtSetWhitelist() {
        Assert.assertFalse(service.saveOwtSetWhitelist(null));
        when(owtSetWhitelistResource.save(Mockito.any(OwtSetWhitelistBO.class))).thenReturn(true);
        Assert.assertTrue(service.saveOwtSetWhitelist(owtSetWhitelistDTO));
        verify(owtSetWhitelistResource, times(1)).save(Mockito.any(OwtSetWhitelistBO.class));
    }

    @Test
    public void getOwtSetWhitelistByOwt() {
        when(owtSetWhitelistResource.query(Mockito.any(OwtSetWhitelistRequestBO.class))).thenReturn(Collections.singletonList(owtSetWhitelistBO));
        String owt = "test-owt";
        List<OwtSetWhitelistDTO> dtoList = service.getOwtSetWhitelistByOwt(owt);
        Assert.assertTrue(CollectionUtils.isNotEmpty(dtoList));
        for (OwtSetWhitelistDTO dto : dtoList) {
            Assert.assertEquals(owt, dto.getOwt());
        }
        verify(owtSetWhitelistResource,times(1)).query(any());
    }

    @Test
    public void deletetOwtSetWhitelistByPk() {
        when(owtSetWhitelistResource.deleteByCondition(Mockito.any(OwtSetWhitelistRequestBO.class))).thenReturn(true);
        Assert.assertTrue(service.deletetOwtSetWhitelistByPk(0));
        verify(owtSetWhitelistResource, times(1)).deleteByCondition(OwtSetWhitelistRequestBO.builder().id(0).build());
    }

    @Test
    public void getOwtSetWhitelistByOwtAndSet() {
        when(owtSetWhitelistResource.query(Mockito.any(OwtSetWhitelistRequestBO.class))).thenReturn(Collections.singletonList(owtSetWhitelistBO));
        String owt = "test-owt";
        String set = "test-set";
        List<OwtSetWhitelistDTO> dtoList = service.getCapacityWhitelistByOwtAndSet(owt, set);
        Assert.assertTrue(CollectionUtils.isNotEmpty(dtoList));
        for (OwtSetWhitelistDTO dto : dtoList) {
            Assert.assertEquals(owt, dto.getOwt());
        }
    }
}