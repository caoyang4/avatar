package com.sankuai.avatar.workflow.core.execute.atom;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.display.model.DiffDisplay;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import com.sankuai.avatar.workflow.core.display.model.OutputDisplay;
import com.sankuai.avatar.workflow.core.display.model.TextDisplay;
import com.sankuai.avatar.workflow.core.execute.atom.atomInput.JumperUserUnlockAtomInput;
import com.sankuai.avatar.workflow.core.execute.atom.atomOutput.AtomOutput;
import com.sankuai.avatar.workflow.core.execute.listener.AtomEvent;
import com.sankuai.avatar.dao.workflow.repository.FlowAtomRecordRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowDataRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowDisplayRepository;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomRecordAddRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AbstractAtomTest {

    @Mock
    private FlowContext flowContext;
    @Mock
    private AtomContext atomContext;
    @Mock
    private AtomEvent atomEvent;
    @Mock
    private FlowDisplayRepository flowDisplayRepository;
    @Mock
    private FlowDataRepository flowDataRepository;
    @Mock
    private FlowAtomRecordRepository flowAtomRecordRepository;

    private AbstractAtom abstractAtomTest;

    @Before
    public void setUp() throws Exception {
        abstractAtomTest = new AbstractAtom() {
            @Override
            protected AtomStatus doProcess() {
                return AtomStatus.SUCCESS;
            }
        };

        // mock atomTemplate
        AtomTemplate atomTemplate = mock(AtomTemplate.class);
        when(atomTemplate.getFlowContext()).thenReturn(flowContext);
        when(atomTemplate.getFlowContext().getCreateUser()).thenReturn("zhaozhifan02");

        JumperUserUnlockAtomInput input = new JumperUserUnlockAtomInput();
        input.setLoginName("zhaozhifan02");
        input.setType("user_unlock");

        Map<String, Object> publicData = new HashMap<>(JsonUtil.beanToMap(input));
        when(atomTemplate.getPublicData()).thenReturn(publicData);

        // mock atomContext
        when(atomContext.getAtomTemplate()).thenReturn(atomTemplate);
        when(atomContext.getAtomResult()).thenReturn(AtomResult.of());
        abstractAtomTest.setAtomContext(this.atomContext);

        ReflectionTestUtils.setField(abstractAtomTest, "atomEvent", atomEvent);
        ReflectionTestUtils.setField(abstractAtomTest, "flowAtomRecordRepository", flowAtomRecordRepository);
        ReflectionTestUtils.setField(abstractAtomTest, "flowDisplayRepository", flowDisplayRepository);
        ReflectionTestUtils.setField(abstractAtomTest, "flowDataRepository", flowDataRepository);
    }

    @Test
    public void testBeforeProcess() {
        // Setup
        // Run the test
        abstractAtomTest.beforeProcess();

        // Verify the results
        verify(atomContext, times(1)).setAtomStatus(AtomStatus.RUNNING);
    }

    @Test
    public void testProcess() {
        // Setup
        when(atomContext.getAtomStatus()).thenReturn(AtomStatus.SCHEDULER);

        // Run the test
        abstractAtomTest.process();

        // Verify the results
        verify(atomContext, times(1)).setAtomStatus(AtomStatus.SUCCESS);
        verify(atomEvent, times(1)).pushEvent(atomContext);
    }

    @Test
    public void testAfterProcess() {
        // Setup
        AtomResult atomResult = new AtomResult();
        atomResult.setStartTime(LocalDateTime.now());
        ReflectionTestUtils.setField(abstractAtomTest, "atomResult", atomResult);
        when(atomContext.getAtomStatus()).thenReturn(AtomStatus.SUCCESS);

        // Run the test
        abstractAtomTest.afterProcess();

        // Verify the results
        verify(flowAtomRecordRepository, times(1)).addAtomRecord(any(FlowAtomRecordAddRequest.class));
    }

    @Test
    public void testGetRetryTimes() {
        // Setup
        // Run the test
        final Integer result = abstractAtomTest.getRetryTimes();

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void testGetTimeout() {
        // Setup
        // Run the test
        final Integer result = abstractAtomTest.getTimeout();

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void testGetInput() {
        // Setup
        JumperUserUnlockAtomInput input = new JumperUserUnlockAtomInput();
        input.setLoginName("zhaozhifan02");
        input.setType("user_unlock");
        // Run the test
        JumperUserUnlockAtomInput result = abstractAtomTest.getInput(JumperUserUnlockAtomInput.class);
        // Verify the results
        assertThat(result).isEqualTo(input);
    }

    @Test
    public void testSetOutput() {
        AtomOutput atomOutput = new AtomOutput(){};

        this.abstractAtomTest.setOutput(atomOutput);

        verify(flowDataRepository, times(1)).updateFlowDataByFlowId(any());
    }

    @Test
    public void testGetFlowResource() {
        // mock resource
        FlowResource flowResource = FlowResource.builder().build();
        when(flowContext.getResource()).thenReturn(flowResource);

        // Run the test
        FlowResource resource = this.abstractAtomTest.getFlowResource();

        // verify results
        Assert.assertEquals(flowResource, resource);
    }

    @Test
    public void testGetCreateUser() {
        // Setup
        // Run the test
        String result = abstractAtomTest.getCreateUser();

        // Verify the results
        assertThat(result).isEqualTo("zhaozhifan02");
    }

    @Test
    public void testUpdateDisplayInput() {
        // mock FlowDisplay
        FlowDisplay flowDisplay = FlowDisplay.builder().input(new ArrayList<>()).build();
        ReflectionTestUtils.setField(this.abstractAtomTest, "flowDisplay", flowDisplay);
        // mock inputDisplay
        InputDisplay inputDisplay = InputDisplay.builder().build();

        // Run  the test
        this.abstractAtomTest.updateDisplayInput(inputDisplay);

        // verify the results
        verify(flowDisplayRepository, times(1)).updateFlowDisplay(any());
    }

    @Test
    public void testUpdateDisplayText() {
        // mock FlowDisplay
        FlowDisplay flowDisplay = FlowDisplay.builder().text(new ArrayList<>()).build();
        ReflectionTestUtils.setField(this.abstractAtomTest, "flowDisplay", flowDisplay);
        // mock textDisplay
        TextDisplay textDisplay = TextDisplay.builder().build();

        // Run  the test
        this.abstractAtomTest.updateDisplayText(textDisplay);

        // verify the results
        verify(flowDisplayRepository, times(1)).updateFlowDisplay(any());
    }

    @Test
    public void testUpdateDisplayDiff() {
        // mock FlowDisplay
        FlowDisplay flowDisplay = FlowDisplay.builder().build();
        ReflectionTestUtils.setField(this.abstractAtomTest, "flowDisplay", flowDisplay);
        // mock textDisplay
        DiffDisplay diffDisplay = DiffDisplay.builder().build();

        // Run  the test
        this.abstractAtomTest.updateDisplayDiff(diffDisplay);

        // verify the results
        verify(flowDisplayRepository, times(1)).updateFlowDisplay(any());
    }

    @Test
    public void testUpdateDisplayOutput() {
        // mock FlowDisplay
        FlowDisplay flowDisplay = FlowDisplay.builder().build();
        ReflectionTestUtils.setField(this.abstractAtomTest, "flowDisplay", flowDisplay);
        // mock textDisplay
        OutputDisplay outputDisplay = OutputDisplay.builder().build();

        // Run  the test
        this.abstractAtomTest.updateDisplayOutput(outputDisplay);

        // verify the results
        verify(flowDisplayRepository, times(1)).updateFlowDisplay(any());
    }
}
