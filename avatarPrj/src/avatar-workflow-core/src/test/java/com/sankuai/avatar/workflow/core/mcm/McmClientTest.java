package com.sankuai.avatar.workflow.core.mcm;

import com.sankuai.avatar.workflow.core.mcm.exception.McmErrorException;
import com.sankuai.avatar.workflow.core.mcm.impl.McmClientImpl;
import com.sankuai.avatar.workflow.core.mcm.request.McmEventRequest;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreAuditResponse;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreCheckResponse;
import com.sankuai.mcm.client.sdk.context.handler.client.EventChangeClient;
import com.sankuai.mcm.client.sdk.context.handler.client.PreAuditClient;
import com.sankuai.mcm.client.sdk.context.handler.client.PreCheckClient;
import com.sankuai.mcm.client.sdk.dto.request.*;
import com.sankuai.mcm.client.sdk.dto.response.PreAuditResponseDTO;
import com.sankuai.mcm.client.sdk.dto.response.PreCheckResponseDTO;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class McmClientTest {

    private static final String REQUEST_PARAM_NULL_ERROR_MESSAGE = "请求参数不能为空";

    @Mock
    private PreCheckClient preCheckClient;

    @Mock
    private PreAuditClient preAuditClient;

    @Mock
    private EventChangeClient eventChangeClient;

    @InjectMocks
    private McmClientImpl mcmClient;

    @Test
    public void testPreCheckNullRequest() {
        assertThatThrownBy(() -> mcmClient.preCheck(null))
                .isInstanceOf(NullPointerException.class).hasMessage(REQUEST_PARAM_NULL_ERROR_MESSAGE);
        verify(preCheckClient, never()).preCheck(any(PreCheckRequestDTO.class));

    }

    @Test
    public void testPreCheckNullEvent() {
        McmPreCheckResponse response = mcmClient.preCheck(mock(McmEventRequest.class));
        Assert.assertNull(response);
        verify(preCheckClient, never()).preCheck(any(PreCheckRequestDTO.class));
    }

    @Test
    public void testPreCheckException() {
        when(preCheckClient.preCheck(any(PreCheckRequestDTO.class))).thenThrow(new McmErrorException());
        McmEventRequest request = getMcmEventRequest();
        assertThatThrownBy(() -> mcmClient.preCheck(request)).isInstanceOf(McmErrorException.class);
    }

    @Test
    public void testPreCheck() {
        when(preCheckClient.preCheck(any(PreCheckRequestDTO.class))).thenReturn(new PreCheckResponseDTO());
        McmPreCheckResponse response = mcmClient.preCheck(getMcmEventRequest());
        Assert.assertNotNull(response);
        verify(preCheckClient, times(1)).preCheck(any(PreCheckRequestDTO.class));
    }

    @Test
    public void testPreCheckConfirmNullRequest() {
        assertThatThrownBy(() -> mcmClient.preCheckConfirm(null))
                .isInstanceOf(NullPointerException.class).hasMessage(REQUEST_PARAM_NULL_ERROR_MESSAGE);
        verify(preCheckClient, never()).confirm(any(PreCheckConfirmRequestDTO.class));

    }

    @Test
    public void testPreCheckConfirmException() {
        when(preCheckClient.confirm(any(PreCheckConfirmRequestDTO.class))).thenThrow(McmErrorException.class);
        McmEventRequest request = getMcmEventRequest();
        assertThatThrownBy(() -> mcmClient.preCheckConfirm(request))
                .isInstanceOf(McmErrorException.class);
    }

    @Test
    public void testPreCheckConfirm() {
        mcmClient.preCheckConfirm(getMcmEventRequest());
        verify(preCheckClient, times(1)).confirm(any(PreCheckConfirmRequestDTO.class));
    }

    @Test
    public void testPreCheckCancelNullRequest() {
        assertThatThrownBy(() -> mcmClient.preCheckCancel(null))
                .isInstanceOf(NullPointerException.class).hasMessage(REQUEST_PARAM_NULL_ERROR_MESSAGE);
        verify(preCheckClient, never()).cancel(any(PreCheckCancelRequestDTO.class));
    }

    @Test
    public void testPreCheckCancelException() {
        when(preCheckClient.cancel(any(PreCheckCancelRequestDTO.class))).thenThrow(McmErrorException.class);
        McmEventRequest request = getMcmEventRequest();
        assertThatThrownBy(() -> mcmClient.preCheckCancel(request))
                .isInstanceOf(McmErrorException.class);
    }

    @Test
    public void testPreCheckCancel() {
        mcmClient.preCheckCancel(getMcmEventRequest());
        verify(preCheckClient, times(1)).cancel(any(PreCheckCancelRequestDTO.class));
    }

    @Test
    public void testAuditNullRequest() {
        assertThatThrownBy(() -> mcmClient.audit(null))
                .isInstanceOf(NullPointerException.class).hasMessage(REQUEST_PARAM_NULL_ERROR_MESSAGE);
        verify(preAuditClient, never()).preAudit(any(PreAuditRequestDTO.class));

    }

    @Test
    public void testAuditException() {
        when(preAuditClient.preAudit(any(PreAuditRequestDTO.class))).thenThrow(McmErrorException.class);
        McmEventRequest request = getMcmEventRequest();
        assertThatThrownBy(() -> mcmClient.audit(request))
                .isInstanceOf(McmErrorException.class);
    }

    @Test
    public void testAudit() {
        when(preAuditClient.preAudit(any(PreAuditRequestDTO.class))).thenReturn(new PreAuditResponseDTO());
        McmPreAuditResponse response = mcmClient.audit(getMcmEventRequest());
        Assert.assertNotNull(response);
        verify(preAuditClient, times(1)).preAudit(any(PreAuditRequestDTO.class));
    }

    @Test
    public void testAuditCancelNullRequest() {
        assertThatThrownBy(() -> mcmClient.auditCancel(null))
                .isInstanceOf(NullPointerException.class).hasMessage(REQUEST_PARAM_NULL_ERROR_MESSAGE);
        verify(preAuditClient, never()).cancel(any(PreAuditCancelRequestDTO.class));

    }

    @Test
    public void testAuditCancelException() {
        when(preAuditClient.cancel(any(PreAuditCancelRequestDTO.class))).thenThrow(McmErrorException.class);
        McmEventRequest request = getMcmEventRequest();
        assertThatThrownBy(() -> mcmClient.auditCancel(request))
                .isInstanceOf(McmErrorException.class);
    }

    @Test
    public void testAuditCancel() {
        mcmClient.auditCancel(getMcmEventRequest());
        verify(preAuditClient, times(1)).cancel(any(PreAuditCancelRequestDTO.class));
    }

    @Test
    public void testAuditRejectNullRequest() {
        assertThatThrownBy(() -> mcmClient.auditReject(null))
                .isInstanceOf(NullPointerException.class).hasMessage(REQUEST_PARAM_NULL_ERROR_MESSAGE);
        verify(preAuditClient, never()).reject(any(PreAuditRejectRequestDTO.class));
    }

    @Test
    public void testAuditRejectException() {
        when(preAuditClient.reject(any(PreAuditRejectRequestDTO.class))).thenThrow(McmErrorException.class);
        McmEventRequest request = getMcmEventRequest();
        assertThatThrownBy(() -> mcmClient.auditReject(request))
                .isInstanceOf(McmErrorException.class);
    }

    @Test
    public void testAuditReject() {
        mcmClient.auditReject(getMcmEventRequest());
        verify(preAuditClient, times(1)).reject(any(PreAuditRejectRequestDTO.class));
    }

    @Test
    public void testAuditAcceptNullRequest() {
        assertThatThrownBy(() -> mcmClient.auditAccept(null))
                .isInstanceOf(NullPointerException.class).hasMessage(REQUEST_PARAM_NULL_ERROR_MESSAGE);
        verify(preAuditClient, never()).accept(any(PreAuditAcceptRequestDTO.class));

    }

    @Test
    public void testAuditAcceptException() {
        when(preAuditClient.accept(any(PreAuditAcceptRequestDTO.class))).thenThrow(McmErrorException.class);
        McmEventRequest request = getMcmEventRequest();
        assertThatThrownBy(() -> mcmClient.auditAccept(request))
                .isInstanceOf(McmErrorException.class);
    }

    @Test
    public void testAuditAccept() {
        mcmClient.auditAccept(getMcmEventRequest());
        verify(preAuditClient, times(1)).accept(any(PreAuditAcceptRequestDTO.class));
    }

    @Test
    public void testGetPreCheckResponseNullEventUuid() {
        assertThatThrownBy(() -> mcmClient.getEventChangeDetail(null))
                .isInstanceOf(NullPointerException.class);
        verify(eventChangeClient, never()).getChangeDetail(anyString(), anyString());
    }

    @Test
    public void testGetPreCheckResponse() {
        mcmClient.getEventChangeDetail("88fedb4b-8c44-4c02-ae1f-d22eef072450");
        verify(eventChangeClient).getChangeDetail(anyString(), anyString());
    }

    private McmEventRequest getMcmEventRequest() {
        return McmEventRequest.builder()
                .evenName("UserUnlock")
                .mcmEventContext(new McmEventContext())
                .build();
    }
}