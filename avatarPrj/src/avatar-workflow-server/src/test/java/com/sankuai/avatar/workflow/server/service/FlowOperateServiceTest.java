package com.sankuai.avatar.workflow.server.service;

import com.google.common.collect.ImmutableMap;
import com.sankuai.avatar.common.exception.SupportErrorException;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.workflow.core.auditer.chain.AuditState;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainNode;
import com.sankuai.avatar.workflow.core.context.FlowAudit;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.context.loader.FlowContextLoader;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContextLoader;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.Loader;
import com.sankuai.avatar.workflow.core.engine.scheduler.Submit;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.execute.AtomLoader;
import com.sankuai.avatar.workflow.server.dal.entity.CasType;
import com.sankuai.avatar.workflow.server.dal.entity.CasUser;
import com.sankuai.avatar.workflow.server.dto.flow.CreateResponseDTO;
import com.sankuai.avatar.workflow.server.dto.flow.FlowAuditOperateRequestDTO;
import com.sankuai.avatar.workflow.server.service.impl.FlowOperateServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class FlowOperateServiceTest {
    /**
     * 测试用户名
     */
    private static final String TEST_USER = "zhaozhifan02";

    @Mock
    private AtomLoader atomLoader;
    @Mock
    private Loader loader;
    @Mock
    private Submit submit;
    @Mock
    private FlowContextLoader flowContextLoader;
    @Mock
    private ProcessContextLoader processContextLoader;

    @InjectMocks
    private FlowOperateServiceImpl flowOperateService;

    @Before
    public void setUp() {
        // mock user
        CasUser casUser = new CasUser();
        casUser.setLoginName(TEST_USER);
        casUser.setCasType(CasType.MIS);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("CAS_USER")).thenReturn(casUser);
        ReflectionTestUtils.setField(flowOperateService, "avatarSuperAdmin", new String[]{TEST_USER});

    }

    @SneakyThrows
    @Test
    public void testCreateFlow() {
        // mock result
        Future mockedFuture = mock(Future.class);
        when(mockedFuture.get(anyLong(), any())).thenReturn(Response.of(FlowState.PRE_CHECK_ACCEPTED, null));
        // mock loader
        when(loader.flowTemplateName(anyString(), any())).thenReturn(mock(ProcessTemplate.class));
        // mock submit
        when(submit.submit(any(ProcessTemplate.class))).thenReturn(mockedFuture);
        String templateName = "delegate_work";
        Map<String, Object> inputParams = ImmutableMap.of(
                "agent", Collections.singletonList(TEST_USER),
                "end_time", "2023-01-13 00:00:00",
                "comment", "test",
                "user", TEST_USER);
        CreateResponseDTO flowDTO = flowOperateService.createFlow(templateName, JsonUtil.bean2Json(inputParams));
        Assert.assertNotNull(flowDTO);
        log.info("Create Flow:{} data: {}", templateName, flowDTO);
    }

    @Test
    public void testCheckConfirm() throws InterruptedException, ExecutionException, TimeoutException {
        // mock result
        Future mockedFuture = mock(Future.class);
        when(mockedFuture.get(anyLong(), any())).thenReturn(Response.of(FlowState.PRE_CHECK_WARNING, null));

        Integer flowId = 121287;
        FlowContext flowContext = FlowContext.builder()
                .id(flowId)
                .flowState(FlowState.PRE_CHECK_WARNING)
                .createUser(TEST_USER)
                .build();
        when(flowContextLoader.id(flowId)).thenReturn(flowContext);
        when(loader.loadProcessTemplate(anyList(),any())).thenReturn(mock(ProcessTemplate.class));

        when(submit.submit(any(ProcessTemplate.class), any(SchedulerEventContext.class))).thenReturn(mockedFuture);
        flowOperateService.checkConfirm(flowId);
        verify(submit, times(1)).submit(any(), any(SchedulerEventContext.class));
    }

    @Test
    public void testCheckConfirmWithSupportErrorException() {
        Integer flowId = 121287;
        FlowContext flowContext = FlowContext.builder().flowState(FlowState.EXECUTE_SUCCESS).build();
        assertThatThrownBy(() -> flowOperateService.checkConfirm(flowId))
                .isInstanceOf(SupportErrorException.class);
        verify(submit, never()).submit(any(), any(SchedulerEventContext.class));
    }

    @Test
    public void testCheckCancel() throws InterruptedException, ExecutionException, TimeoutException {
        // mock result
        Future mockedFuture = mock(Future.class);
        when(mockedFuture.get(anyLong(), any())).thenReturn(Response.of(FlowState.PRE_CHECK_ACCEPTED, null));

        Integer flowId = 121287;
        FlowContext flowContext = FlowContext.builder()
                .id(flowId)
                .flowState(FlowState.PRE_CHECK_WARNING)
                .createUser(TEST_USER)
                .build();
        when(flowContextLoader.id(flowId)).thenReturn(flowContext);
        when(loader.loadProcessTemplate(anyList(),any())).thenReturn(mock(ProcessTemplate.class));


        when(submit.submit(any(ProcessTemplate.class), any(SchedulerEventContext.class))).thenReturn(mockedFuture);
        flowOperateService.checkCancel(flowId);
        verify(submit, times(1)).submit(any(), any(SchedulerEventContext.class));
    }

    @Test
    public void testCheckCancelWithSupportErrorException() {
        Integer flowId = 121287;
        FlowContext flowContext = FlowContext.builder().flowState(FlowState.EXECUTE_SUCCESS).build();
        assertThatThrownBy(() -> flowOperateService.checkCancel(flowId))
                .isInstanceOf(SupportErrorException.class);
        verify(submit, never()).submit(any(), any(SchedulerEventContext.class));
    }

    @Test
    public void testAuditAccept() throws InterruptedException, ExecutionException, TimeoutException {
        // mock result
        Future mockedFuture = mock(Future.class);
        when(mockedFuture.get(anyLong(), any())).thenReturn(Response.of(FlowState.AUDITING, null));

        Integer flowId = 121287;
        FlowContext flowContext = buildFlowContext(flowId);
        when(flowContextLoader.id(flowId)).thenReturn(flowContext);
        when(loader.loadProcessTemplate(anyList(),any())).thenReturn(mock(ProcessTemplate.class));

        when(submit.submit(any(ProcessTemplate.class), any(SchedulerEventContext.class))).thenReturn(mockedFuture);
        flowOperateService.auditAccept(flowId, FlowAuditOperateRequestDTO.builder().auditNodeId(1).build());
        verify(submit, times(1)).submit(any(), any(SchedulerEventContext.class));
    }

    @Test
    public void testAuditAcceptWithSupportErrorException() {
        Integer flowId = 121287;
        FlowContext flowContext = FlowContext.builder().flowState(FlowState.AUDIT_ACCEPTED).build();
        assertThatThrownBy(() -> flowOperateService.auditAccept(flowId, FlowAuditOperateRequestDTO.builder().build()))
                .isInstanceOf(SupportErrorException.class);
        verify(submit, never()).submit(any(), any(SchedulerEventContext.class));
    }

    @Test
    public void testAuditReject() throws InterruptedException, ExecutionException, TimeoutException {
        // mock result
        Future mockedFuture = mock(Future.class);
        when(mockedFuture.get(anyLong(), any())).thenReturn(Response.of(FlowState.AUDITING, null));

        Integer flowId = 121287;
        FlowContext flowContext = buildFlowContext(flowId);

        when(flowContextLoader.id(flowId)).thenReturn(flowContext);
        when(loader.loadProcessTemplate(anyList(),any())).thenReturn(mock(ProcessTemplate.class));

        when(submit.submit(any(ProcessTemplate.class), any(SchedulerEventContext.class))).thenReturn(mockedFuture);
        flowOperateService.auditReject(flowId, FlowAuditOperateRequestDTO.builder().auditNodeId(1).build());
        verify(submit, times(1)).submit(any(), any(SchedulerEventContext.class));
    }

    @Test
    public void testAuditRejectWithSupportErrorException() {
        Integer flowId = 121287;
        FlowContext flowContext = FlowContext.builder().flowState(FlowState.AUDIT_ACCEPTED).build();
        assertThatThrownBy(() -> flowOperateService.auditReject(flowId, FlowAuditOperateRequestDTO.builder().build()))
                .isInstanceOf(SupportErrorException.class);
        verify(submit, never()).submit(any(), any(SchedulerEventContext.class));
    }

    @Test
    public void testAuditCancel() throws InterruptedException, ExecutionException, TimeoutException {
        // mock result
        Future mockedFuture = mock(Future.class);
        when(mockedFuture.get(anyLong(), any())).thenReturn(Response.of(FlowState.AUDITING, null));

        Integer flowId = 121287;
        FlowContext flowContext = buildFlowContext(flowId);

        when(flowContextLoader.id(flowId)).thenReturn(flowContext);
        when(loader.loadProcessTemplate(anyList(),any())).thenReturn(mock(ProcessTemplate.class));


        when(submit.submit(any(ProcessTemplate.class), any(SchedulerEventContext.class))).thenReturn(mockedFuture);
        flowOperateService.auditCancel(flowId, FlowAuditOperateRequestDTO.builder().auditNodeId(1).build());
        verify(submit, times(1)).submit(any(), any(SchedulerEventContext.class));
    }

    @Test
    public void testAuditCancelWithSupportErrorException() {
        Integer flowId = 121287;
        assertThatThrownBy(() -> flowOperateService.auditCancel(flowId, FlowAuditOperateRequestDTO.builder().build()))
                .isInstanceOf(SupportErrorException.class);
        verify(submit, never()).submit(any(), any(SchedulerEventContext.class));
    }

    private FlowContext buildFlowContext(Integer flowId) {
        FlowContext flowContext = FlowContext.builder()
                .id(flowId)
                .createUser(TEST_USER)
                .flowState(FlowState.AUDITING).build();
        List<FlowAuditChainNode> auditChainNodeList = Collections.singletonList(FlowAuditChainNode
                .builder().id(1).state(AuditState.AUDITING).auditors(Collections.singletonList(TEST_USER)).build());

        FlowAudit flowAudit = FlowAudit.builder().auditNode(auditChainNodeList).build();
        flowContext.setFlowAudit(flowAudit);
        return flowContext;
    }

}