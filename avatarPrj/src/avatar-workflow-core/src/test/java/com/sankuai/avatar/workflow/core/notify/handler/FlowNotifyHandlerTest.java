package com.sankuai.avatar.workflow.core.notify.handler;

import com.sankuai.avatar.workflow.core.notify.NotifyMessageType;
import com.sankuai.avatar.workflow.core.notify.NotifyReceiverRole;
import com.sankuai.avatar.workflow.core.notify.NotifyRequest;
import com.sankuai.avatar.workflow.core.notify.NotifyResult;
import com.sankuai.avatar.workflow.core.notify.builder.CompleteNotifyBuilder;
import com.sankuai.avatar.workflow.core.notify.builder.FailedNotifyBuilder;
import com.sankuai.avatar.workflow.core.notify.builder.StartNotifyBuilder;
import com.sankuai.avatar.workflow.core.notify.sender.DxAppNotifySender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FlowNotifyHandlerTest {
    @Mock
    StartNotifyBuilder startNotifyBuilder;

    @Mock
    DxAppNotifySender dxAppNotifySender;

    @Mock
    CompleteNotifyBuilder  completeNotifyBuilder;

    @Mock
    FailedNotifyBuilder failedNotifyBuilder;

    @InjectMocks
    FlowNotifyHandler flowNotifyHandler;

    NotifyResult notifyResult;

    @Before
    public void setUp() {
        // mock send
        doNothing().when(dxAppNotifySender).send(any(NotifyResult.class));
    }

    @Test
    public void testStart() {
        NotifyRequest notifyRequest = mock(NotifyRequest.class);
        // mock create
        NotifyResult notifyResult = NotifyResult.builder()
                .notifyReceiverRole(NotifyReceiverRole.CREATE_USER)
                .notifyMessageType(NotifyMessageType.NORMAL)
                .receiverList(new HashSet<>(Arrays.asList("a", "b", "c")))
                .build();
        // mock rd admin
        NotifyResult notifyResult2 = NotifyResult.builder()
                .notifyReceiverRole(NotifyReceiverRole.RD_ADMIN)
                .notifyMessageType(NotifyMessageType.NORMAL)
                .receiverList(new HashSet<>(Arrays.asList("a", "b")))
                .build();
        when(startNotifyBuilder.build(notifyRequest)).thenReturn(Arrays.asList(notifyResult2, notifyResult));
        // do handler
        flowNotifyHandler.handle(FlowTemplateType.START, notifyRequest);
        verify(dxAppNotifySender,times(2)).send(any());
        Assert.assertEquals(0, notifyResult2.getReceiverList().size());
    }

    @Test
    public void testHandleStart() {
        NotifyRequest notifyRequest = mock(NotifyRequest.class);
        // mock result
        NotifyResult notifyResult = NotifyResult.builder()
                .notifyReceiverRole(NotifyReceiverRole.CREATE_USER)
                .notifyMessageType(NotifyMessageType.NORMAL)
                .receiverList(new HashSet<>(Arrays.asList("a", "b", "c")))
                .build();
        // mock builder result
        when(startNotifyBuilder.build(notifyRequest)).thenReturn(Collections.singletonList(notifyResult));

        // mock start
        flowNotifyHandler.handle(FlowTemplateType.START, notifyRequest);
        verify(startNotifyBuilder, times(1)).build(any());
        verify(completeNotifyBuilder, times(0)).build(any());
        verify(failedNotifyBuilder, times(0)).build(any());
    }

    @Test
    public void testHandleComplete() {
        NotifyRequest notifyRequest = mock(NotifyRequest.class);
        // mock result
        NotifyResult notifyResult = NotifyResult.builder()
                .notifyReceiverRole(NotifyReceiverRole.CREATE_USER)
                .notifyMessageType(NotifyMessageType.NORMAL)
                .receiverList(new HashSet<>(Arrays.asList("a", "b", "c")))
                .build();
        // mock builder result
        when(completeNotifyBuilder.build(notifyRequest)).thenReturn(Collections.singletonList(notifyResult));
        // mock complete
        flowNotifyHandler.handle(FlowTemplateType.COMPLETE, notifyRequest);
        verify(startNotifyBuilder, times(0)).build(any());
        verify(completeNotifyBuilder, times(1)).build(any());
        verify(failedNotifyBuilder, times(0)).build(any());
    }

    @Test
    public void testHandleFailed() {
        NotifyRequest notifyRequest = mock(NotifyRequest.class);
        // mock result
        NotifyResult notifyResult = NotifyResult.builder()
                .notifyReceiverRole(NotifyReceiverRole.CREATE_USER)
                .notifyMessageType(NotifyMessageType.NORMAL)
                .receiverList(new HashSet<>(Arrays.asList("a", "b", "c")))
                .build();
        // mock builder result
        when(failedNotifyBuilder.build(notifyRequest)).thenReturn(Collections.singletonList(notifyResult));
        // mock complete
        flowNotifyHandler.handle(FlowTemplateType.FAILED, notifyRequest);
        verify(startNotifyBuilder, times(0)).build(any());
        verify(completeNotifyBuilder, times(0)).build(any());
        verify(failedNotifyBuilder, times(1)).build(any());
    }
}
