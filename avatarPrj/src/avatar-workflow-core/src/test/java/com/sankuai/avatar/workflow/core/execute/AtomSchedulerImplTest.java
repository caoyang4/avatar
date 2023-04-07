package com.sankuai.avatar.workflow.core.execute;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.impl.ExecuteFlowProcess;
import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.workflow.core.execute.atom.AtomTemplate;
import com.sankuai.avatar.workflow.core.execute.listener.AtomEvent;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * atom调度器测试，模拟多种调度场景
 * 由于 AtomScheduler 使用了Rhino线程池异步调度, 单测环境不稳定, 使用mock线程池
 *
 * @author xk
 */
@RunWith(MockitoJUnitRunner.class)
public class AtomSchedulerImplTest {

    @Mock
    private AtomExecute mockAtomExecute;

    @Mock
    private AtomEvent mockAtomEvent;

    @Mock
    private ExecuteFlowProcess executeProcess;

    @Mock
    private AtomTemplate atomTemplate;

    @InjectMocks
    private AtomSchedulerImpl atomSchedulerImplUnderTest;

    @Before
    public void setup() {
        // 模拟线程池, 替换掉Rhino线程池
        ThreadPoolExecutor atomSchedulerPool = mock(ThreadPoolExecutor.class);
        doAnswer(invocation -> {
            ((Runnable) invocation.getArguments()[0]).run();
            return null;
        }).when(atomSchedulerPool).execute(any(Runnable.class));
        ReflectionTestUtils.setField(atomSchedulerImplUnderTest, "atomScheduler", atomSchedulerPool);
    }

    /**
     * 第一次调度
     */
    @Test
    public void testDispatchWhenNew() {
        //mock AtomContext
        AtomContext atomContext1 = AtomContext.builder().seq(1).atomStatus(AtomStatus.NEW).build();
        AtomContext atomContext2 = AtomContext.builder().seq(1).atomStatus(AtomStatus.NEW).build();
        // mock atomTemplate
        when(atomTemplate.getIndex()).thenReturn(1);
        when(atomTemplate.getAtomContextList()).thenReturn(Arrays.asList(atomContext1, atomContext2));

        // Run the test
        atomSchedulerImplUnderTest.dispatch(atomTemplate);

        // Verify the results
        verify(executeProcess, never()).doEventProcess(any(), any());
        verify(mockAtomEvent, times(2)).pushEvent(any(AtomContext.class));
        verify(mockAtomExecute, times(2)).execute(any(AtomContext.class));
    }

    /**
     * 执行成功调度, 还有后续atom
     */
    @Test
    public void testDispatchWhenSuccess() {
        //mock AtomContext
        AtomContext atomContext1 = AtomContext.builder().seq(1).atomStatus(AtomStatus.SUCCESS).build();
        AtomContext atomContext2 = AtomContext.builder().seq(2).atomStatus(AtomStatus.NEW).build();

        when(atomTemplate.getIndex()).thenReturn(1);
        when(atomTemplate.getAtomContextList()).thenReturn(Arrays.asList(atomContext1, atomContext2));

        // Run the test
        atomSchedulerImplUnderTest.dispatch(atomTemplate);

        // Verify the results
        verify(executeProcess, never()).doEventProcess(any(), any());
        verify(mockAtomEvent, times(1)).pushEvent(atomContext2);
        verify(mockAtomExecute, times(1)).execute(atomContext2);
        Assert.assertEquals(atomContext2.getAtomStatus(), AtomStatus.SCHEDULER);
    }

    /**
     * 执行成功调度, 没有后续atom
     */
    @Test
    public void testDispatchWhenSuccess2() {
        //mock AtomContext
        AtomContext atomContext1 = AtomContext.builder().seq(1).atomStatus(AtomStatus.SUCCESS).build();
        AtomContext atomContext2 = AtomContext.builder().seq(2).atomStatus(AtomStatus.SUCCESS).build();
        ProcessContext processContext = ProcessContext.builder().flowContext(FlowContext.builder().build()).build();

        when(atomTemplate.getIndex()).thenReturn(2);
        when(atomTemplate.getAtomContextList()).thenReturn(Arrays.asList(atomContext1, atomContext2));
        when(atomTemplate.getProcessContext()).thenReturn(processContext);

        // Run the test
        atomSchedulerImplUnderTest.dispatch(atomTemplate);

        // Verify the results
        verify(executeProcess, times(1)).doEventProcess(any(), any());
        verify(mockAtomEvent, never()).pushEvent(any());
        verify(mockAtomExecute, never()).execute(any());
        Assert.assertEquals(atomContext2.getAtomStatus(), AtomStatus.SUCCESS);
    }


    /**
     * 执行挂起调度
     */
    @Test
    public void testDispatchWhenPending() {
        //mock AtomContext
        AtomContext atomContext1 = AtomContext.builder().seq(1).atomStatus(AtomStatus.PENDING).build();
        AtomContext atomContext2 = AtomContext.builder().seq(1).atomStatus(AtomStatus.SUCCESS).build();
        ProcessContext processContext = ProcessContext.builder().flowContext(FlowContext.builder().build()).build();

        when(atomTemplate.getIndex()).thenReturn(1);
        when(atomTemplate.getAtomContextList()).thenReturn(Arrays.asList(atomContext1, atomContext2));
        when(atomTemplate.getProcessContext()).thenReturn(processContext);

        // Run the test
        atomSchedulerImplUnderTest.dispatch(atomTemplate);

        // Verify the results
        verify(executeProcess, times(1)).doEventProcess(any(), any());
        verify(mockAtomEvent, never()).pushEvent(any(AtomContext.class));
        verify(mockAtomExecute, never()).execute(any(AtomContext.class));
    }


    /**
     * 执行失败调度
     */
    @Test
    public void testDispatchWhenFail() {
        //mock AtomContext
        AtomContext atomContext1 = AtomContext.builder().seq(1).atomStatus(AtomStatus.FAIL).build();
        AtomContext atomContext2 = AtomContext.builder().seq(1).atomStatus(AtomStatus.SUCCESS).build();
        ProcessContext processContext = ProcessContext.builder().flowContext(FlowContext.builder().build()).build();

        when(atomTemplate.getIndex()).thenReturn(1);
        when(atomTemplate.getAtomContextList()).thenReturn(Arrays.asList(atomContext1, atomContext2));
        when(atomTemplate.getProcessContext()).thenReturn(processContext);

        // Run the test
        atomSchedulerImplUnderTest.dispatch(atomTemplate);

        // Verify the results
        verify(executeProcess, times(1)).doEventProcess(any(), any());
        verify(mockAtomEvent, never()).pushEvent(any(AtomContext.class));
        verify(mockAtomExecute, never()).execute(any(AtomContext.class));
    }

}
