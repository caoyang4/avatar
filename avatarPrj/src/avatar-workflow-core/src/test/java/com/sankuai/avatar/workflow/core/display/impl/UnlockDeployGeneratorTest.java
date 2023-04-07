package com.sankuai.avatar.workflow.core.display.impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import com.sankuai.avatar.workflow.core.input.solution.UnlockDeployInput;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UnlockDeployGeneratorTest {

    private static final String TEST_APP_KEY = "com.sankuai.avatar.workflow.server";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private UnlockDeployGenerator unlockDeployGenerator;

    @Before
    public void setUp() {
        unlockDeployGenerator = new UnlockDeployGenerator();
    }

    @Test
    public void testDoGenerate() {
        // Setup
        FlowContext flowContext = FlowContext.builder().flowInput(getFlowInput()).build();
        List<InputDisplay> inputDisplays = getInputDisplays();
        final FlowDisplay expectedResult = FlowDisplay.builder().input(inputDisplays).build();
        // Run the test
        FlowDisplay result = unlockDeployGenerator.doGenerate(flowContext);
        result.getInput().remove(3);
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetInputDisplay() {
        // Setup
        FlowContext flowContext = FlowContext.builder().flowInput(getFlowInput()).build();
        List<InputDisplay> inputDisplays = getInputDisplays();
        final FlowDisplay expectedResult = FlowDisplay.builder().input(inputDisplays).build();
        // Run the test
        FlowDisplay result = unlockDeployGenerator.doGenerate(flowContext);
        result.getInput().remove(3);
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    private List<InputDisplay> getInputDisplays() {
        List<InputDisplay> inputDisplays = new ArrayList<>();
        inputDisplays.add(InputDisplay.builder().fieldName("appkey").key("来源").value(TEST_APP_KEY).build());
        inputDisplays.add(InputDisplay.builder().fieldName("reason").key("说明").value("紧急Bug修复及服务扩容").build());
        inputDisplays.add(InputDisplay.builder().key("变更类型").value("新增").build());
        inputDisplays.add(InputDisplay.builder().key("类型").value("高峰期临时解禁").build());
        return inputDisplays;
    }

    private UnlockDeployInput getFlowInput() {
        UnlockDeployInput unlockDeployInput = new UnlockDeployInput();
        unlockDeployInput.setAppkey(TEST_APP_KEY);
        unlockDeployInput.setReason("紧急Bug修复及服务扩容");
        unlockDeployInput.setComment("测试");
        return unlockDeployInput;
    }
}