package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowAtomTemplateEntity;

import java.util.List;

/**
 * 流程Atom任务编排模板
 *
 * @author zhaozhifa2
 */
public interface FlowAtomTemplateRepository {

    /**
     * 获取流程 Atom 任务模板
     *
     * @param templateName 流程模板名称
     * @param version      版本号
     * @return {@link FlowAtomTemplateEntity}
     */
    List<FlowAtomTemplateEntity> getFlowAtomTemplate(String templateName, Integer version);
}
