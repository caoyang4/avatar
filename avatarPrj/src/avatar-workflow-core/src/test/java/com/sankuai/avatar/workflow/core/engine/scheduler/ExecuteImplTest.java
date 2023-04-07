package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.common.utils.ApplicationContextUtil;
import com.sankuai.avatar.workflow.core.engine.process.FlowProcess;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

import static org.mockito.Mockito.*;


@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.script.*"})
@PrepareForTest(value = {ApplicationContextUtil.class})
public class ExecuteImplTest {

    @Mock
    private FlowProcess flowProcess;
    @InjectMocks
    private ExecuteImpl executeTest;

    @Before
    public void setUp() throws Exception {
        // 拦截 bean 加载器, 加载模拟的atom对象
        PowerMockito.mockStatic(ApplicationContextUtil.class);
        PowerMockito.when(ApplicationContextUtil.getBean("name", FlowProcess.class)).thenReturn(flowProcess);

        // 使用模拟线程池, 单元测试更加稳定
        ThreadPoolExecutor processPool = mock(ThreadPoolExecutor.class);
        doAnswer(invocation -> {
            ((Callable) invocation.getArguments()[0]).call();
            return null;
        }).when(processPool).submit((Callable) any());
        ReflectionTestUtils.setField(executeTest, "processExecutor", processPool);
    }

    /**
     * 执行成功场景
     */
    @Test
    public void testExecuteOk()  {
        // Setup
        ProcessContext processContext = ProcessContext.builder().name("name").build();

        // Run the test
        executeTest.execute(processContext, null);

        // Verify the results
        verify(flowProcess, times(1)).process(processContext, null);
    }


    /**
     * 事件调度，执行成功场景
     */
    @Test
    public void testExecuteOk2() {
        // Setup
        ProcessContext processContext = ProcessContext.builder().name("name").build();
        SchedulerEventContext eventContext = mock(SchedulerEventContext.class);

        // Run the test
        executeTest.execute(processContext, eventContext);

        // Verify the results
        verify(flowProcess, never()).process(processContext, null);
    }


    /**
     * 执行失败场景
     */
    @Test
    public void testExecuteFail() {
        // Setup
        ProcessContext processContext = ProcessContext.builder().name("name").build();
        //when(flowProcess.process(processContext)).thenThrow(new RuntimeException());

        // Run the test
        executeTest.execute(processContext, null);

        // Verify the results;
        verify(flowProcess, times(1)).process(processContext, null);
    }


    /**
     * 调度事件，执行失败场景
     */
    @Test
    public void testExecuteFail2() {
        // Setup
        ProcessContext processContext = ProcessContext.builder().name("name").build();
        SchedulerEventContext eventContext = mock(SchedulerEventContext.class);
        //when(flowProcess.eventProcess(processContext, eventContext)).thenThrow(new RuntimeException());

        // Run the test
        executeTest.execute(processContext, eventContext);

        // Verify the results
        verify(flowProcess, never()).process(processContext, null);
    }
}
