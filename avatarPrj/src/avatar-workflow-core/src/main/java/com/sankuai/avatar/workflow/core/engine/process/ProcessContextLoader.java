package com.sankuai.avatar.workflow.core.engine.process;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.engine.process.impl.AuditFlowProcess;
import com.sankuai.avatar.workflow.core.engine.process.impl.ExecuteFlowProcess;
import com.sankuai.avatar.workflow.core.engine.process.impl.PreCheckFlowProcess;
import com.sankuai.avatar.dao.workflow.repository.FlowProcessRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * process 上下文生成
 *
 * @author xk
 * @date 2023/02/22
 */
@Data
@Component
public class ProcessContextLoader {
    @Autowired
    PreCheckFlowProcess preCheckFlowProcess;

    @Autowired
    AuditFlowProcess auditFlowProcess;

    @Autowired
    ExecuteFlowProcess executeFlowProcess;

    @Autowired
    FlowProcessRepository flowProcessRepository;

    @Autowired
    ProcessNameLoader processNameLoader;

    /**
     * 初始化process编排模版, 目前所有流程是固定步骤: 预检->审核->执行, 生成固定模板即可
     *
     * @param flowContext {@link FlowContext}
     * @return {@link List}<{@link ProcessContext}>
     */
    public List<ProcessContext> buildByFlowContext(FlowContext flowContext) {
        return Arrays.asList(
                buildProcess(preCheckFlowProcess, 0, flowContext),
                buildProcess(auditFlowProcess, 1, flowContext),
                buildProcess(executeFlowProcess, 2, flowContext)
        );
    }

    /**
     * process构造
     */
    private ProcessContext buildProcess(FlowProcess flowProcess, int seq, FlowContext flowContext) {
        return ProcessContext.builder().flowContext(flowContext).seq(seq).name(processNameLoader.getName(flowProcess)).build();
    }

}
