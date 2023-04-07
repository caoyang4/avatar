package com.sankuai.avatar.workflow.core.execute.listener.atomListen;

import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.workflow.core.execute.transfer.AtomContextTransfer;
import com.sankuai.avatar.dao.workflow.repository.FlowAtomContextRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 存储监听器，atom状态发生变化时, 更新atom上下文状态
 *
 * @author xk
 */
@Component
public class StorageListenImpl implements AtomListener {

    @Autowired
    private FlowAtomContextRepository flowAtomContextRepository;

    @Autowired
    private FlowRepository flowRepository;

    @Override
    public void atomNew(AtomContext atomContext) {
        FlowAtomContextAddRequest request = FlowAtomContextAddRequest.builder()
                .flowId(atomContext.getFlowId())
                .seq(atomContext.getSeq())
                .status(atomContext.getAtomStatus().getValue())
                .atomName(atomContext.getName())
                .retryTimes(atomContext.getRetryTimes())
                .timeout(atomContext.getTimeout())
                .build();
        boolean status = flowAtomContextRepository.addFlowAtomContext(request);
        if (status) {
            atomContext.setId(request.getId());
        }
    }

    /**
     * 存储atom上下文的状态, 任务状态变化都更新存储
     *
     * @param atomContext 原子上下文
     */
    @Override
    public void atomAll(AtomContext atomContext) {
        if (atomContext.getAtomStatus().equals(AtomStatus.NEW)) {
            return;
        }

        // new 是初始状态，无更新存储
        FlowAtomContextUpdateRequest request = AtomContextTransfer.INSTANCE.toUpdateRequest(atomContext);
        flowAtomContextRepository.updateFlowAtomContext(request);

        Integer flowContextIndex = atomContext.getAtomTemplate().getFlowContext().getAtomIndex();
        Integer atomContextIndex = atomContext.getAtomTemplate().getIndex();
        if (!flowContextIndex.equals(atomContextIndex)) {
            flowRepository.updateFlow(FlowEntity.builder().id(atomContext.getFlowId()).index(atomContextIndex).build());
        }
    }
}
