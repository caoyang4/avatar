package com.sankuai.avatar.workflow.core.engine.scheduler.event.eventInput;

import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * atom执行回调事件输入参数
 *
 * @author xk
 */
@AllArgsConstructor
public class ExecuteCallbackEvent implements EventInput {

    /**
     * atom执行结果返回状态
     */
    @Getter
    private final AtomStatus atomState;

    /**
     * atom任务结果
     */
    @Getter
    private List<AtomContext> atomContextList;

    public static ExecuteCallbackEvent of(AtomStatus atomState) {
        return new ExecuteCallbackEvent(atomState, null);
    }

    public static ExecuteCallbackEvent of(AtomStatus atomState, List<AtomContext> atomContextList) {
        return new ExecuteCallbackEvent(atomState, atomContextList);
    }
}
