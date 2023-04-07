package com.sankuai.avatar.workflow.server.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.es.FlowSearchEsRepository;
import com.sankuai.avatar.dao.es.entity.FlowSearchEntity;
import com.sankuai.avatar.dao.es.request.FlowSearchQueryRequest;
import com.sankuai.avatar.dao.workflow.repository.FlowRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowQueryRequest;
import com.sankuai.avatar.workflow.server.dto.flow.FlowDTO;
import com.sankuai.avatar.workflow.server.exception.EsException;
import com.sankuai.avatar.workflow.server.request.FlowPageRequest;
import com.sankuai.avatar.workflow.server.service.impl.FlowServiceImpl;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class FlowServiceTest {

    /**
     * 测试用户名
     */
    private static final String TEST_USER = "zhaozhifan02";

    @Mock
    private FlowRepository flowRepository;

    @Mock
    private FlowSearchEsRepository esRepository;

    @InjectMocks
    private FlowServiceImpl flowService;

    @Test
    public void testGetFlowByUuid() {
        String uuid = "cf06c96f-926d-40fc-bcd9-ba512109e903";
        FlowDTO flowDTO = flowService.getFlowByUuid(uuid);
        verify(flowRepository, times(1)).getFlowEntityByUuid(any(String.class));
    }

    @Test
    public void getPageFlowDbSource() {
        FlowPageRequest pageRequest = new FlowPageRequest();
        pageRequest.setPageSize(1);
        pageRequest.setDbSource(true);
        PageResponse<FlowEntity> entityPage = new PageResponse<>();
        entityPage.setItems(Collections.singletonList(new FlowEntity()));
        when(flowRepository.queryPage(Mockito.any(FlowQueryRequest.class))).thenReturn(entityPage);
        PageResponse<FlowDTO> pageFlow = flowService.getPageFlow(pageRequest);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageFlow.getItems()));
        verify(flowRepository).queryPage(Mockito.any(FlowQueryRequest.class));
        verify(esRepository, times(0)).pageQuery(Mockito.any(FlowSearchQueryRequest.class), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void getPageFlowEsSource() {
        FlowPageRequest pageRequest = new FlowPageRequest();
        pageRequest.setPageSize(1);
        PageResponse<FlowSearchEntity> doPageResponse = new PageResponse<>();
        doPageResponse.setItems(Collections.singletonList(new FlowSearchEntity()));
        when(esRepository.pageQuery(Mockito.any(FlowSearchQueryRequest.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(doPageResponse);
        PageResponse<FlowDTO> pageFlow = flowService.getPageFlow(pageRequest);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageFlow.getItems()));
        verify(flowRepository, times(0)).queryPage(Mockito.any(FlowQueryRequest.class));
        verify(esRepository).pageQuery(Mockito.any(FlowSearchQueryRequest.class), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void getPageFlowThrowEsException() {
        FlowPageRequest pageRequest = new FlowPageRequest();
        pageRequest.setPageSize(1);
        PageResponse<FlowEntity> entityPage = new PageResponse<>();
        entityPage.setItems(Collections.singletonList(new FlowEntity()));
        when(flowRepository.queryPage(Mockito.any(FlowQueryRequest.class))).thenReturn(entityPage);
        when(esRepository.pageQuery(Mockito.any(FlowSearchQueryRequest.class), Mockito.anyInt(), Mockito.anyInt())).thenThrow(EsException.class);
        PageResponse<FlowDTO> pageFlow = flowService.getPageFlow(pageRequest);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageFlow.getItems()));
        verify(flowRepository).queryPage(Mockito.any(FlowQueryRequest.class));
        verify(esRepository).pageQuery(Mockito.any(FlowSearchQueryRequest.class), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void getPageFlowByDb() {
        PageResponse<FlowEntity> entityPage = new PageResponse<>();
        entityPage.setItems(Collections.singletonList(new FlowEntity()));
        when(flowRepository.queryPage(Mockito.any(FlowQueryRequest.class))).thenReturn(entityPage);
        FlowPageRequest pageRequest = new FlowPageRequest();
        pageRequest.setPageSize(1);
        PageResponse<FlowDTO> pageFlow = flowService.getPageFlowByDb(pageRequest);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageFlow.getItems()));
        verify(flowRepository).queryPage(Mockito.any(FlowQueryRequest.class));
    }

    @Test
    public void getPageFlowByEs() {
        PageResponse<FlowSearchEntity> doPageResponse = new PageResponse<>();
        doPageResponse.setItems(Collections.singletonList(new FlowSearchEntity()));
        when(esRepository.pageQuery(Mockito.any(FlowSearchQueryRequest.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(doPageResponse);
        FlowPageRequest pageRequest = new FlowPageRequest();
        PageResponse<FlowDTO> pageFlow = flowService.getPageFlowByEs(pageRequest);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageFlow.getItems()));
        verify(esRepository).pageQuery(Mockito.any(FlowSearchQueryRequest.class), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void testGetFlowByEmptyUuidWithException() {
        when(flowRepository.getFlowEntityByUuid(any())).thenThrow(new NullPointerException("UUID is null"));
        assertThatThrownBy(() -> flowService.getFlowByUuid(null))
                .isInstanceOf(NullPointerException.class);
    }
}
