package com.sankuai.avatar.workflow.core.execute;

import com.sankuai.avatar.dao.workflow.repository.*;
import com.sankuai.avatar.dao.workflow.repository.entity.*;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.transfer.FlowDisplayTransfer;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.workflow.core.execute.atom.AtomTemplate;
import com.sankuai.avatar.workflow.core.execute.listener.AtomEventImpl;
import com.sankuai.avatar.workflow.core.execute.transfer.AtomContextTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * atom编排模板加载器，根据flow对象加载atom模板, 校验状态是否合法(允许被调度执行)
 *
 * @author xk
 */
@Slf4j
@Component
public class AtomLoaderImpl implements AtomLoader {

    @Autowired
    private AtomScheduler atomScheduler;

    @Autowired
    private AtomEventImpl atomEvent;

    @Autowired
    private AtomStepRepository atomStepRepository;

    @Autowired
    private FlowDataRepository flowDataRepository;

    @Autowired
    private FlowDisplayRepository flowDisplayRepository;

    @Autowired
    private FlowAtomContextRepository flowAtomContextRepository;

    @Autowired
    private FlowAtomTemplateRepository flowAtomTemplateRepository;

    @Override
    public void loadAtomTemplate(ProcessContext processContext, FlowContext flowContext) {
        AtomTemplate atomTemplate = this.buildAtomTemplate(flowContext);
        if (this.checker(atomTemplate)) {
            // 提交任务到调度器
            atomTemplate.setProcessContext(processContext);
            atomScheduler.dispatch(atomTemplate);
        }
    }

    /**
     * 校验flow流程是否可被执行
     *
     * @param atomTemplate 原子模板
     * @return boolean
     */
    private boolean checker(AtomTemplate atomTemplate) {
        if (atomTemplate == null) {
            return false;
        }
        if (CollectionUtils.isEmpty(atomTemplate.getAtomContextList())) {
            return false;
        }
        // 判断索引位置
        AtomContext maxIndexAtom = atomTemplate.getAtomContextList().stream()
                .max(Comparator.comparing(AtomContext::getSeq)).orElse(null);
        if (maxIndexAtom == null) {
            return false;
        }
        return atomTemplate.getIndex() <= maxIndexAtom.getSeq();
    }

    /**
     * 根据flowId构建atom编排模板, 新流程创建，存量流程加载
     *
     * @param flowContext flow流程上下文
     * @return {@link AtomTemplate}
     */
    private AtomTemplate buildAtomTemplate(FlowContext flowContext) {
        // 设置 PublicData，初始化公共数据字段
        FlowDataEntity flowDataEntity = flowDataRepository.getFlowDataByFlowId(flowContext.getId());
        // 获取 FlowDisplay
        FlowDisplayEntity flowDisplayEntity = flowDisplayRepository.getFlowDisplayByFlowId(flowContext.getId());
        return AtomTemplate.builder()
                .flowContext(flowContext)
                .index(flowContext.getAtomIndex())
                .publicData(flowDataEntity == null ? null : flowDataEntity.getPublicData())
                .flowDisplay(FlowDisplayTransfer.INSTANCE.toFlowDisplay(flowDisplayEntity))
                .atomContextList(this.getAtomContextList(flowContext))
                .build();
    }

    /**
     * 获取 AtomContext 执行上下文
     *
     * @param flowContext 流程上下文
     * @return List<AtomContext>
     */
    private List<AtomContext> getAtomContextList(FlowContext flowContext) {
        // 存量加载
        List<FlowAtomContextEntity> entities = flowAtomContextRepository.getFlowAtomContextByFlowId(flowContext.getId());
        if (CollectionUtils.isNotEmpty(entities)) {
            return AtomContextTransfer.INSTANCE.entitiesToAtomContextList(entities);
        }
        // 基于模板新创建上下文
        return loadFromTemplate(flowContext);
    }

    /**
     * 从模板中加载 Atom 编排任务
     *
     * @param flowContext 流程上下文
     * @return List<AtomContext>
     */
    private List<AtomContext> loadFromTemplate(FlowContext flowContext) {
        List<AtomContext> atomContextList = new ArrayList<>();
        List<FlowAtomTemplateEntity> templateEntities = flowAtomTemplateRepository.getFlowAtomTemplate(flowContext.getTemplateName(), null);
        if (CollectionUtils.isEmpty(templateEntities)) {
            // 从数据库加载模板为空, 该流程没有任何atom步骤
            return atomContextList;
        }

        // 通过 Atom 模板初始化 AtomContext
        for (FlowAtomTemplateEntity templateEntity : templateEntities) {
            AtomStepEntity atomStepEntity = atomStepRepository.getAtomStepByName(templateEntity.getAtomName());
            AtomContext atomContext = AtomContext.builder()
                    .flowId(flowContext.getId())
                    .name(templateEntity.getAtomName())
                    .seq(templateEntity.getSeq())
                    .atomStatus(AtomStatus.NEW)
                    .retryTimes(atomStepEntity.getRetryTimes())
                    .timeout(atomStepEntity.getTimeout())
                    .build();
            atomContextList.add(atomContext);
            // 新创建atomContext, 推送new状态事件
            this.atomEvent.pushEvent(atomContext);
            log.info("init atomContext flow(id:{}), atom(name:{})", flowContext.getId(), atomContext.getName());
        }
        return atomContextList.stream().sorted(Comparator.comparing(AtomContext::getSeq)).collect(Collectors.toList());
    }
}
