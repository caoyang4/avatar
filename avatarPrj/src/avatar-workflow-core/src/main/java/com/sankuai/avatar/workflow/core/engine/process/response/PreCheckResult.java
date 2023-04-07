package com.sankuai.avatar.workflow.core.engine.process.response;

import com.sankuai.avatar.workflow.core.checker.CheckResult;
import com.sankuai.avatar.workflow.core.checker.CheckState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 预检结果, 返给给前端解析并展示给用户
 *
 * @author Jie.li.sh
 * @create 2022-11-15
 **/
@AllArgsConstructor
public class PreCheckResult extends Result {

    /**
     * 检查决策
     */
    @Getter
    private final CheckState checkState;

    /**
     * 检查结果
     */
    @Getter
    private final List<CheckResult> checkResults;


    /**
     * 通过
     *
     * @return {@link PreCheckResult}
     */
    public static PreCheckResult ofAccept() {
        return new PreCheckResult(CheckState.PRE_CHECK_ACCEPTED, null);
    }

    /**
     * 警告
     *
     * @param checkResults 检查结果
     * @return {@link PreCheckResult}
     */
    public static PreCheckResult ofWarn(List<CheckResult> checkResults) {
        return new PreCheckResult(CheckState.PRE_CHECK_WARNING, checkResults);
    }

    /**
     * 拒绝
     *
     * @param checkResults 检查结果
     * @return {@link PreCheckResult}
     */
    public static PreCheckResult ofReject(List<CheckResult> checkResults) {
        return new PreCheckResult(CheckState.PRE_CHECK_REJECTED, checkResults);
    }
}
