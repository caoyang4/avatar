package com.sankuai.avatar.workflow.core.checker.global;

import com.sankuai.avatar.workflow.core.annotation.CheckerMeta;
import com.sankuai.avatar.workflow.core.checker.CheckResult;
import com.sankuai.avatar.workflow.core.checker.CheckState;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2022-11-11
 **/
@Slf4j
@CheckerMeta(order = Integer.MIN_VALUE + 1)
@Component
public class ValidateInputGlobalChecker extends AbstractGlobalChecker {
    @Override
    public CheckResult check(FlowContext flowContext) {
        return doValidate(flowContext);
    }

    /**
     * 入参校验
     *
     * @param flowContext 流程上下文
     * @return 检查结果
     */
    private CheckResult doValidate(FlowContext flowContext) {
        CheckResult checkerResult = CheckResult.ofAccept();
        FlowInput flowInput = flowContext.getFlowInput();
        if (flowInput == null) {
            return checkerResult;
        }
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        List<CheckResult> validateResult = new ArrayList<>();
        validator.validate(flowInput).forEach(
                violation -> {
                    String fieldName = violation.getPropertyPath().toString();
                    String violationMsg = String.format("%s: %s", flowInput.displayName(fieldName),
                            violation.getMessage());
                    validateResult.add(CheckResult.ofReject(violationMsg));
                }
        );
        if (CollectionUtils.isNotEmpty(validateResult)) {
            return CheckResult.of(this.getClass().getSimpleName(), CheckState.PRE_CHECK_REJECTED, validateResult);
        }
        return checkerResult;
    }
}
