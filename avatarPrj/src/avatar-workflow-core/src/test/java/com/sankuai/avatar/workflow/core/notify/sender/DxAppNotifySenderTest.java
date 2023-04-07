package com.sankuai.avatar.workflow.core.notify.sender;

import com.sankuai.avatar.client.dx.DxHttpClient;
import com.sankuai.avatar.workflow.core.notify.NotifyMessageType;
import com.sankuai.avatar.workflow.core.notify.NotifyReceiverType;
import com.sankuai.avatar.workflow.core.notify.NotifyResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DxAppNotifySenderTest {
    @Mock
    DxHttpClient dxHttpClient;

    @InjectMocks
    DxAppNotifySender dxAppNotifySender;

    @Before
    public void setUp() {
        // mock send
        when(dxHttpClient.pushDxMessage(any(), any())).thenReturn(true);
        when(dxHttpClient.pushDxAuditMessage(any(), any())).thenReturn(true);
        when(dxHttpClient.pushGroupMessage(any(), any())).thenReturn(true);
    }

    @Test
    public void testUser() {
        NotifyResult notifyResult = mock(NotifyResult.class);
        when(notifyResult.getReceiverList()).thenReturn(new HashSet<>(Arrays.asList("a", "b")));
        when(notifyResult.getNotifyMessageType()).thenReturn(NotifyMessageType.NORMAL);
        when(notifyResult.getReceiverType()).thenReturn(NotifyReceiverType.USER);
        dxAppNotifySender.send(notifyResult);
        verify(dxHttpClient, times(1)).pushDxMessage(any(), any());
    }

    @Test
    public void testAudit() {
        NotifyResult notifyResult = mock(NotifyResult.class);
        when(notifyResult.getReceiverList()).thenReturn(new HashSet<>(Arrays.asList("a", "b")));
        when(notifyResult.getNotifyMessageType()).thenReturn(NotifyMessageType.AUDIT);
        when(notifyResult.getReceiverType()).thenReturn(NotifyReceiverType.USER);
        dxAppNotifySender.send(notifyResult);
        verify(dxHttpClient, times(0)).pushDxMessage(any(), any());
        verify(dxHttpClient, times(1)).pushDxAuditMessage(any(), any());
    }

    @Test
    public void testGroup() {
        NotifyResult notifyResult = mock(NotifyResult.class);
        when(notifyResult.getReceiverList()).thenReturn(new HashSet<>(Arrays.asList("1", "2")));
        when(notifyResult.getReceiverType()).thenReturn(NotifyReceiverType.GROUP);
        dxAppNotifySender.send(notifyResult);
        verify(dxHttpClient, times(0)).pushDxMessage(any(), any());
        verify(dxHttpClient, times(0)).pushDxAuditMessage(any(), any());
        verify(dxHttpClient, times(notifyResult.getReceiverList().size())).pushGroupMessage(any(), any());
    }

    @Test
    public void testNoUser() {
        NotifyResult notifyResult = mock(NotifyResult.class);
        dxAppNotifySender.send(notifyResult);
        verify(dxHttpClient, times(0)).pushDxMessage(any(), any());
    }
}
