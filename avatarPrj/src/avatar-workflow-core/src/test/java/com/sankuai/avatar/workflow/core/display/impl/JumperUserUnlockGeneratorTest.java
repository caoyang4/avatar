package com.sankuai.avatar.workflow.core.display.impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import com.sankuai.avatar.workflow.core.input.jumper.JumperUserUnlockInput;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class JumperUserUnlockGeneratorTest {

    private JumperUserUnlockGenerator jumperUserUnlockGenerator;

    @Before
    public void setUp() {
        jumperUserUnlockGenerator = new JumperUserUnlockGenerator();
    }

    @Test
    public void testDoGenerate() {
        // Setup
        FlowContext flowContext = FlowContext.builder().flowInput(getFlowInput()).build();
        List<InputDisplay> inputDisplays = getInputDisplays();
        final FlowDisplay expectedResult = FlowDisplay.builder().input(inputDisplays).build();
        // Run the test
        FlowDisplay result = jumperUserUnlockGenerator.doGenerate(flowContext);
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetInputDisplay() {
        // Setup
        FlowContext flowContext = FlowContext.builder().flowInput(getFlowInput()).build();
        List<InputDisplay> inputDisplays = getInputDisplays();
        // Run the test
        List<InputDisplay> result = jumperUserUnlockGenerator.getInputDisplay(flowContext);
        // Verify the results
        assertThat(result).isEqualTo(inputDisplays);
    }

    private List<InputDisplay> getInputDisplays() {
        List<InputDisplay> inputDisplays = new ArrayList<>();
        inputDisplays.add(InputDisplay.builder().fieldName("loginName").key("登录名").value("zhaozhifan02").build());
        inputDisplays.add(InputDisplay.builder().fieldName("type").key("类型").value("user_unlock").build());
        return inputDisplays;
    }

    private JumperUserUnlockInput getFlowInput() {
        JumperUserUnlockInput userUnlockInput = new JumperUserUnlockInput();
        userUnlockInput.setType("user_unlock");
        userUnlockInput.setLoginName("zhaozhifan02");
        return userUnlockInput;
    }
}