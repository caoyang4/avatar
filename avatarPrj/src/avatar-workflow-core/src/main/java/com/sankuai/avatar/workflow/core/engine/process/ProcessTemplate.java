package com.sankuai.avatar.workflow.core.engine.process;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author xk
 */
@Data
@Builder
@AllArgsConstructor
public class ProcessTemplate {
    /**
     * processes指针
     */
    private Integer index;

    /**
     * processes编排列表
     */
    @JsonIgnore
    private List<ProcessContext> processes;

    /**
     * 获取当前步骤
     *
     * @return {@link ProcessContext}
     */
    public ProcessContext getCurrentProcesses(){
        return this.getProcesses().get(this.index);
    }

    /**
     * 获取流程上下文
     *
     * @return {@link FlowContext}
     */
    public FlowContext getFlowContext() {
        return this.getCurrentProcesses().getFlowContext();
    }
}
