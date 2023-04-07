package com.sankuai.avatar.workflow.core.execute;

import com.sankuai.avatar.common.utils.ApplicationContextUtil;
import com.sankuai.avatar.workflow.core.execute.atom.Atom;
import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
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

import java.util.concurrent.ThreadPoolExecutor;

/**
 * atom执行器单元测试
 *
 * @author xk
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.script.*"})
@PrepareForTest(value = {ApplicationContextUtil.class})
public class AtomExecuteImplTest {
    @Mock
    private
    Atom atom;
    @Mock
    private
    AtomScheduler atomScheduler;
    @InjectMocks
    private AtomExecuteImpl atomExecuteImpTest;

    @Before
    public void setUp() throws Exception {
        // 拦截 bean 加载器, 加载模拟的atom对象
        PowerMockito.mockStatic(ApplicationContextUtil.class);
        PowerMockito.when(ApplicationContextUtil.getBean("atomName", Atom.class)).thenReturn(atom);

        // 模拟线程池, 替换掉Rhino线程池
        ThreadPoolExecutor atomExecutePool = mock(ThreadPoolExecutor.class);
        doAnswer(invocation -> {
            ((Runnable) invocation.getArguments()[0]).run();
            return null;
        }).when(atomExecutePool).execute(any(Runnable.class));
        ReflectionTestUtils.setField(atomExecuteImpTest, "atomExecute", atomExecutePool);
    }

    /**
     * 执行正常
     */
    @Test
    public void testExecute() {
        // mock atom
        when(atom.process()).thenReturn(AtomStatus.SUCCESS);

        // Run the test
        atomExecuteImpTest.execute(AtomContext.builder().name("atomName").build());

        // Verify the results
        verify(atom, times(1)).process();
        verify(atom, times(1)).beforeProcess();
        verify(atom, times(1)).afterProcess();
        verify(atomScheduler, times(1)).dispatch(null);
    }

    /**
     * 执行失败
     */
    @Test
    public void testExecuteFailure() {
        // mock atom
        when(atom.process()).thenReturn(AtomStatus.FAIL, AtomStatus.FAIL, AtomStatus.SUCCESS);
        when(atom.getRetryTimes()).thenReturn(2);

        // Run the test
        atomExecuteImpTest.execute(AtomContext.builder().name("atomName").build());

        // Verify the results
        verify(atom, times(3)).process();
        verify(atom, times(3)).afterProcess();
        verify(atom, times(3)).beforeProcess();
        verify(atomScheduler, times(1)).dispatch(null);
    }

    /**
     * 执行异常
     */
    @Test
    public void testExecuteException() {
        // mock atom
        doThrow(new RuntimeException()).when(atom).process();
        doThrow(new RuntimeException()).when(atom).afterProcess();
        when(atom.getRetryTimes()).thenReturn(1);

        // Run the test
        atomExecuteImpTest.execute(AtomContext.builder().name("atomName").build());

        // Verify the results
        verify(atom, times(2)).process();
        verify(atom, times(2)).beforeProcess();
        verify(atom, times(2)).afterProcess();
        verify(atomScheduler, times(1)).dispatch(null);
    }
}
