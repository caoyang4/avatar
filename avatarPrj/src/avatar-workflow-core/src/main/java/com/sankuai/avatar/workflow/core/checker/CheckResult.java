package com.sankuai.avatar.workflow.core.checker;

import lombok.Getter;

import java.util.List;

/**
 * 预检结果
 *
 * @author xk
 * @date 2023/02/23
 */
public class CheckResult {
    /**
     * 名称
     */
    @Getter
    private String name;

    /**
     * 预检结果说明
     */
    @Getter
    private String msg = null;

    /**
     * 预检结果为多条
     */
    @Getter
    private List<CheckResult> resultItems = null;

    /**
     * 预检状态
     */
    @Getter
    private final CheckState checkState;

    private CheckResult(String msg, CheckState checkState) {
        this.msg = msg;
        this.checkState = checkState;
    }

    private CheckResult(String name, CheckState checkState, String msg) {
        this.name = name;
        this.msg = msg;
        this.checkState = checkState;
    }

    private CheckResult(String name, CheckState checkState, List<CheckResult> resultItems) {
        this.resultItems = resultItems;
        this.checkState = checkState;
        this.name = name;
    }

    public static CheckResult of(String msg, CheckState state) {
        return new CheckResult(msg, state);
    }

    public static CheckResult of(String name, CheckState state, String msg) {
        return new CheckResult(name, state, msg);
    }

    public static CheckResult of(String name, CheckState state, List<CheckResult> resultItems) {
        return new CheckResult(name , state, resultItems);
    }


    public static CheckResult ofAccept() {
        return new CheckResult("检查通过", CheckState.PRE_CHECK_ACCEPTED);
    }
    public static CheckResult ofReject(String msg) {
        return new CheckResult(msg, CheckState.PRE_CHECK_REJECTED);
    }
}
