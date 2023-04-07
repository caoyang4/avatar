package com.sankuai.avatar.workflow.core.display.impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import com.sankuai.avatar.workflow.core.input.jumper.PasswordResetInput;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author caoyang
 * @create 2023-03-16 15:20
 */
public class PasswordResetGeneratorTest {

    private PasswordResetGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new PasswordResetGenerator();
    }

    static PasswordResetInput input = new PasswordResetInput();
    static List<InputDisplay> inputDisplays = new ArrayList<>();
    static {
        input.setType("user_unlock");
        input.setLoginName("zhaozhifan02");

        inputDisplays.add(InputDisplay.builder().fieldName("loginName").key("登录名").value("zhaozhifan02").build());
        inputDisplays.add(InputDisplay.builder().fieldName("type").key("类型").value("user_unlock").build());
    }
    @Test
    public void doGenerate() {
        FlowContext flowContext = FlowContext.builder().flowInput(input).build();
        final FlowDisplay expectedResult = FlowDisplay.builder().input(inputDisplays).build();
        // Run the test
        FlowDisplay result = generator.doGenerate(flowContext);
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void getInputDisplay() {
        // Setup
        FlowContext flowContext = FlowContext.builder().flowInput(input).build();
        // Run the test
        List<InputDisplay> result = generator.getInputDisplay(flowContext);
        // Verify the results
        assertThat(result).isEqualTo(inputDisplays);
    }
}