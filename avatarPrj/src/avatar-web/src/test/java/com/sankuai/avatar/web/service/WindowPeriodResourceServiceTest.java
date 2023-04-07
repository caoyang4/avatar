package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.ActivityResource;
import com.sankuai.avatar.resource.activity.WindowPeriodResource;
import com.sankuai.avatar.resource.activity.bo.ActivityResourceBO;
import com.sankuai.avatar.resource.activity.bo.WindowPeriodResourceBO;
import com.sankuai.avatar.resource.activity.request.ActivityResourceRequestBO;
import com.sankuai.avatar.resource.activity.request.WindowPeriodRequestBO;
import com.sankuai.avatar.web.dto.activity.WindowPeriodResourceDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.request.WindowPeriodPageRequest;
import com.sankuai.avatar.web.service.impl.WindowPeriodResourceServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2023-03-16 11:44
 */
@RunWith(MockitoJUnitRunner.class)
public class WindowPeriodResourceServiceTest {

    private WindowPeriodResourceService service;

    @Mock
    private WindowPeriodResource periodResource;

    @Mock
    private ActivityResource activityResource;

    @Before
    public void setUp() throws Exception {
        service = new WindowPeriodResourceServiceImpl(periodResource, activityResource);
    }

    @Test
    public void queryPage() {
        PageResponse<WindowPeriodResourceBO> boPageResponse = new PageResponse<>();
        WindowPeriodResourceBO resourceBO = new WindowPeriodResourceBO();
        resourceBO.setId(1);
        boPageResponse.setItems(Collections.singletonList(resourceBO));
        when(periodResource.queryPage(Mockito.any(WindowPeriodRequestBO.class))).thenReturn(boPageResponse);
        when(activityResource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.singletonList(new ActivityResourceBO()));
        PageResponse<WindowPeriodResourceDTO> dtoPageResponse = service.queryPage(new WindowPeriodPageRequest());
        Assert.assertEquals(1, dtoPageResponse.getItems().size());
        verify(periodResource).queryPage(Mockito.any(WindowPeriodRequestBO.class));
    }

    @Test
    public void getWindowPeriodByPkNoItem() {
        when(periodResource.query(Mockito.any(WindowPeriodRequestBO.class))).thenReturn(Collections.emptyList());
        WindowPeriodResourceDTO resourceDTO = service.getWindowPeriodByPk(1);
        Assert.assertNull(resourceDTO);
        verify(periodResource).query(Mockito.any(WindowPeriodRequestBO.class));
    }

    @Test
    public void getWindowPeriodByPk() {
        WindowPeriodResourceBO resourceBO = new WindowPeriodResourceBO();
        resourceBO.setId(1);
        when(periodResource.query(Mockito.any(WindowPeriodRequestBO.class))).thenReturn(Collections.singletonList(resourceBO));
        when(activityResource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.singletonList(new ActivityResourceBO()));
        WindowPeriodResourceDTO resourceDTO = service.getWindowPeriodByPk(1);
        Assert.assertNotNull(resourceDTO);
        Assert.assertTrue(resourceDTO.getHasOrder());
        verify(periodResource).query(Mockito.any(WindowPeriodRequestBO.class));
        verify(activityResource).query(Mockito.any(ActivityResourceRequestBO.class));
    }

    @Test
    public void getMaxWindowId() {
        when(periodResource.getMaxWindowId()).thenReturn(1);
        int maxWindowId = service.getMaxWindowId();
        Assert.assertEquals(1, maxWindowId);
        verify(periodResource).getMaxWindowId();
    }

    @Test
    public void saveCaseInsert() {
        WindowPeriodResourceDTO dto = new WindowPeriodResourceDTO();
        dto.setStartTime(new Date());
        dto.setEndTime(DateUtils.localDateToDate(LocalDate.now().plusDays(1)));
        when(periodResource.save(Mockito.any(WindowPeriodResourceBO.class))).thenReturn(true);
        Boolean save = service.save(dto);
        Assert.assertTrue(save);
        verify(periodResource).save(Mockito.any(WindowPeriodResourceBO.class));
    }

    @Test
    public void saveCaseStartAfterEnd() {
        WindowPeriodResourceDTO dto = new WindowPeriodResourceDTO();
        dto.setStartTime(new Date());
        dto.setEndTime(DateUtils.localDateToDate(LocalDate.now().plusDays(-1)));
        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(SupportErrorException.class);
        verify(periodResource, times(0)).save(Mockito.any(WindowPeriodResourceBO.class));
    }

    @Test
    public void saveCaseStartAfterWindowEnd() {
        WindowPeriodResourceDTO dto = new WindowPeriodResourceDTO();
        dto.setStartTime(new Date());
        dto.setEndTime(DateUtils.localDateToDate(LocalDate.now().plusDays(1)));
        dto.setId(1);
        WindowPeriodResourceBO resourceBO = new WindowPeriodResourceBO();
        resourceBO.setId(1);
        resourceBO.setEndTime(DateUtils.localDateToDate(LocalDate.now().plusDays(-1)));
        when(periodResource.query(Mockito.any(WindowPeriodRequestBO.class))).thenReturn(Collections.singletonList(resourceBO));
        when(activityResource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.singletonList(new ActivityResourceBO()));
        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(SupportErrorException.class);
        verify(periodResource).query(Mockito.any(WindowPeriodRequestBO.class));
        verify(activityResource).query(Mockito.any(ActivityResourceRequestBO.class));
        verify(periodResource, times(0)).save(Mockito.any(WindowPeriodResourceBO.class));
    }

    @Test
    public void saveCaseStartAfterFirstOrder() {
        WindowPeriodResourceDTO dto = new WindowPeriodResourceDTO();
        dto.setStartTime(new Date());
        dto.setEndTime(DateUtils.localDateToDate(LocalDate.now().plusDays(1)));
        dto.setId(1);
        WindowPeriodResourceBO resourceBO = new WindowPeriodResourceBO();
        resourceBO.setId(1);
        resourceBO.setStartTime(DateUtils.localDateToDate(LocalDate.now().plusDays(-1)));
        resourceBO.setEndTime(DateUtils.localDateToDate(LocalDate.now().plusDays(1)));
        when(periodResource.query(Mockito.any(WindowPeriodRequestBO.class))).thenReturn(Collections.singletonList(resourceBO));
        ActivityResourceBO activityResourceBO = new ActivityResourceBO();
        activityResourceBO.setStartTime(DateUtils.localDateToDate(LocalDate.now().plusDays(-1)));
        when(activityResource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.singletonList(activityResourceBO));
        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(SupportErrorException.class);
        verify(periodResource).query(Mockito.any(WindowPeriodRequestBO.class));
        verify(activityResource, times(2)).query(Mockito.any(ActivityResourceRequestBO.class));
        verify(periodResource, times(0)).save(Mockito.any(WindowPeriodResourceBO.class));
    }

    @Test
    public void deleteByPk() {
        WindowPeriodResourceBO resourceBO = new WindowPeriodResourceBO();
        when(periodResource.query(Mockito.any(WindowPeriodRequestBO.class))).thenReturn(Collections.emptyList());
        when(periodResource.deleteByPk(Mockito.anyInt())).thenReturn(true);
        Boolean delete = service.deleteByPk(1);
        Assert.assertTrue(delete);
        verify(periodResource).deleteByPk(Mockito.anyInt());
    }

    @Test
    public void deleteCaseAfterEnd(){
        WindowPeriodResourceBO resourceBO = new WindowPeriodResourceBO();
        resourceBO.setId(1);
        resourceBO.setEndTime(DateUtils.localDateToDate(LocalDate.now().plusDays(-1)));
        when(periodResource.query(Mockito.any(WindowPeriodRequestBO.class))).thenReturn(Collections.singletonList(resourceBO));
        when(activityResource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.singletonList(new ActivityResourceBO()));
        assertThatThrownBy(() -> service.deleteByPk(1)).isInstanceOf(SupportErrorException.class);
        verify(periodResource,times(0)).deleteByPk(Mockito.anyInt());
    }

    @Test
    public void deleteAfterStart(){
        WindowPeriodResourceBO resourceBO = new WindowPeriodResourceBO();
        resourceBO.setId(1);
        resourceBO.setStartTime(DateUtils.localDateToDate(LocalDate.now().plusDays(-1)));
        resourceBO.setEndTime(DateUtils.localDateToDate(LocalDate.now().plusDays(1)));
        when(periodResource.query(Mockito.any(WindowPeriodRequestBO.class))).thenReturn(Collections.singletonList(resourceBO));
        when(activityResource.query(Mockito.any(ActivityResourceRequestBO.class))).thenReturn(Collections.singletonList(new ActivityResourceBO()));
        assertThatThrownBy(() -> service.deleteByPk(1)).isInstanceOf(SupportErrorException.class);
        verify(periodResource,times(0)).deleteByPk(Mockito.anyInt());

    }

    @Test
    public void getHitWindowPeriodWithEmpty() {
        when(periodResource.query(Mockito.any(WindowPeriodRequestBO.class))).thenReturn(Collections.emptyList());
        assertThatThrownBy(() -> service.getHitWindowPeriod(1)).isInstanceOf(SupportErrorException.class);
        verify(periodResource).query(Mockito.any(WindowPeriodRequestBO.class));
    }

    @Test
    public void getHitWindowPeriod() {
        WindowPeriodResourceBO resourceBO = new WindowPeriodResourceBO();
        resourceBO.setId(1);
        resourceBO.setStartTime(DateUtils.localDateToDate(LocalDate.now().plusDays(-1)));
        resourceBO.setEndTime(DateUtils.localDateToDate(LocalDate.now().plusDays(1)));
        when(periodResource.query(Mockito.any(WindowPeriodRequestBO.class))).thenReturn(Collections.singletonList(resourceBO));
        WindowPeriodResourceDTO hitWindowPeriod = service.getHitWindowPeriod(1);
        Assert.assertNotNull(hitWindowPeriod);
        verify(periodResource).query(Mockito.any(WindowPeriodRequestBO.class));
    }
}