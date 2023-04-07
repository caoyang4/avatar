package com.sankuai.avatar.workflow.core.execute.atom;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * atom 原子任务编排对象，描述atom编排关系, 状态和结果
 *
 * @author xk
 */
@Data
@Builder
public class AtomTemplate {
    /**
     * 流程上下文
     */
    FlowContext flowContext;

    /**
     * atom公共数据, 用于个atom之间交换数据, 数据来源
     * 1, 流程入参, 创建流程时的input数据
     * 2, 各atom的执行结果
     */
    private Map<String, Object> publicData;

    /**
     * 流程的显示数据
     */
    private FlowDisplay flowDisplay;

    /**
     * 流程模板上下文
     */
    private ProcessContext processContext;

    /**
     * Atom指针, 表示当前执行的atom对象列表
     */
    private Integer index;

    /**
     * atom任务编排，描述执行关系
     */
    private List<AtomContext> atomContextList;
}
