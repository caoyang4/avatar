package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.response.ExecuteResult;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomResult;
import com.sankuai.avatar.workflow.core.notify.NotifyRequest;
import com.sankuai.avatar.workflow.core.notify.handler.FlowTemplateType;
import com.sankuai.avatar.workflow.core.notify.handler.NotifyHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * process相关通知的单测
 * @author Jie.li.sh
 * @create 2023-03-20
 **/
@RunWith(MockitoJUnitRunner.class)
public class NotifyFlowListenTest {
    @Mock
    private NotifyHandler notifyHandler;

    @InjectMocks
    NotifyFlowListen notifyFlowListen;



    @Test
    public void testExecuteFlowProcessFailed() {
        AtomResult atomResult = AtomResult.of();
        atomResult.setException(new Exception());

        AtomContext atomContext = AtomContext.builder().name("atom").atomResult(atomResult).build();
        ProcessContext processContext = ProcessContext.builder()
                .response(Response.of(FlowState.PRE_CHECK_ACCEPTED, new ExecuteResult(Arrays.asList(atomContext, atomContext))))
                .build();

        FlowContext flowContext = FlowContext.builder().currentProcessContext(processContext).build();
        // mock context
        doNothing().when(notifyHandler).handle(any(), any(NotifyRequest.class));

        // mock handler
        notifyFlowListen.receiveEvents(flowContext, FlowState.EXECUTE_FAILED);

        ArgumentCaptor<FlowTemplateType> argument = ArgumentCaptor.forClass(FlowTemplateType.class);
        verify(notifyHandler, times(1)).handle(argument.capture(), any(NotifyRequest.class));
        Assert.assertEquals(FlowTemplateType.FAILED, argument.getValue());
    }

    @Test
    public void testPreCheckSuccess() {
        // mock context
        FlowContext flowContext = mock(FlowContext.class);
        // mock handler
        doNothing().when(notifyHandler).handle(any(), any(NotifyRequest.class));

        // mock handler
        notifyFlowListen.receiveEvents(flowContext, FlowState.PRE_CHECK_ACCEPTED);

        // verify
        ArgumentCaptor<FlowTemplateType> argument = ArgumentCaptor.forClass(FlowTemplateType.class);
        verify(notifyHandler, times(1)).handle(argument.capture(), any(NotifyRequest.class));
        Assert.assertEquals(FlowTemplateType.START, argument.getValue());
    }
}
