package com.sankuai.avatar.workflow.core.execute.listener;

import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.workflow.core.execute.listener.atomListen.AtomListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AtomEventImplTest {

    private AtomEventImpl atomEventImplTest;

    @Before
    public void setUp() {
        atomEventImplTest = new AtomEventImpl();
    }

    @Test
    public void testPushEvent() {
        AtomListener atomListener = mock(AtomListener.class);
        ReflectionTestUtils.setField(atomEventImplTest, "listeners", Arrays.asList(atomListener, atomListener));
        AtomContext atomContext = mock(AtomContext.class);

        for (AtomStatus atomStatus : AtomStatus.values()) {
            when(atomContext.getAtomStatus()).thenReturn(atomStatus);

            atomEventImplTest.pushEvent(atomContext);

            switch (atomStatus) {
                case NEW:
                    verify(atomListener, times(2)).atomNew(atomContext);
                    break;
                case SCHEDULER:
                    verify(atomListener, times(2)).atomAtomScheduler(atomContext);
                    break;
                case RUNNING:
                    verify(atomListener, times(2)).atomRunning(atomContext);
                    break;
                case SUCCESS:
                    verify(atomListener, times(2)).atomSuccess(atomContext);
                    break;
                case PENDING:
                    verify(atomListener, times(2)).atomPending(atomContext);
                    break;
                case FAIL:
                    verify(atomListener, times(2)).atomFail(atomContext);
                    break;
                default:
                    throw new RuntimeException("No push events: " + atomStatus);
            }
        }
    }
}
