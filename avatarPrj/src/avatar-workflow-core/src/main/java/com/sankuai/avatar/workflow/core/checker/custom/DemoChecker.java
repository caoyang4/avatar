package com.sankuai.avatar.workflow.core.checker.custom;

import com.sankuai.avatar.workflow.core.annotation.CheckerMeta;
import com.sankuai.avatar.workflow.core.checker.AbstractChecker;
import com.sankuai.avatar.workflow.core.checker.CheckResult;
import com.sankuai.avatar.workflow.core.checker.CheckState;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import org.springframework.stereotype.Component;

/**
 * 预检demo
 *
 * @author xk
 */
@CheckerMeta({"add_service", "add_plus", "unlock_deploy"})
@Component
public class DemoChecker extends AbstractChecker {

    @Override
    public CheckResult check(FlowContext flowContext) {
        // 完成一些列预检业务逻辑, 返回预检结果
        return CheckResult.of("变更风险过高, 请谨慎操作", CheckState.PRE_CHECK_WARNING);
    }
}
