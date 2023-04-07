package com.sankuai.avatar.workflow.core.engine.listener;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;


@RunWith(MockitoJUnitRunner.class)
public class PushEventImplTest {

    private final List<FlowListen> flowListens = new ArrayList<>();

    private PushEventImpl pushEventImplUnderTest;

    @Mock
    private
    ThreadPoolExecutor poolExecutor;
    @Before
    public void setUp() throws Exception {
        pushEventImplUnderTest = new PushEventImpl();
        ReflectionTestUtils.setField(pushEventImplUnderTest, "taskThreadPool", poolExecutor);
        ReflectionTestUtils.setField(pushEventImplUnderTest, "flowListens", flowListens);
    }

    /**
     * Flow事件推送, 异步执行
     */
    @Test
    public void testPushEvent() {
        // Setup
        FlowListen flowListen = mock(FlowListen.class);
        flowListens.add(flowListen);
        final FlowContext flowContext = FlowContext.builder().build();

        // Run the test
        pushEventImplUnderTest.pushEvent(flowContext, FlowState.NEW);

        // Verify the results
        verify(poolExecutor, times(1)).execute(any(Runnable.class));
    }

    /**
     * flow事件推送, 同步执行
     */
    @Test
    public void testPushEvent2() {
        flowListens.add(new AsyncFlowListenTest());
        pushEventImplUnderTest.pushEvent(FlowContext.builder().build(), FlowState.NEW);
        verify(poolExecutor).execute(any(Runnable.class));
    }

    /**
     * flow事件推送, 注解订阅
     */
    @Test
    public void testPushEvent3() {
        // mock AsyncFlowListenTest, 添加异步监听器到列表
        flowListens.add(new AsyncFlowListenTest());

        // Run the test
        pushEventImplUnderTest.pushEvent(FlowContext.builder().build(), FlowState.NEW);
        pushEventImplUnderTest.pushEvent(FlowContext.builder().build(), FlowState.PRE_CHECK_ACCEPTED);

        // Verify the results
        verify(poolExecutor, times(1)).execute(any(Runnable.class));
    }

    /**
     * flow模拟异步监听器, mock无法模拟注解
     *
     * @author xk
     */
    @ListenFlowState({FlowState.NEW, FlowState.AUDIT_CANCELED})
    public static class AsyncFlowListenTest implements FlowListen {
        @Override
        public void receiveEvents(FlowContext flowContext, FlowState flowState) {

        }
    }
}
