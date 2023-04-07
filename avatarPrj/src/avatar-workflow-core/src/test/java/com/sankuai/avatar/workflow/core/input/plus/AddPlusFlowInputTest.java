package com.sankuai.avatar.workflow.core.input.plus;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

public class AddPlusFlowInputTest {
    @Test
    public void testAddPlusFlowInput() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        AddPlusFlowInput addPlusFlowInput = AddPlusFlowInput.builder().build();
        addPlusFlowInput.setRespository("");
        List<String> message = new ArrayList<>();
        validator.validate(addPlusFlowInput).forEach(
                violation -> {
                    message.add(String.format("%s: %s", violation.getPropertyPath().toString(), violation.getMessage()));
                }
        );
        Assertions.assertThat(message).isNotEmpty();
    }
}
