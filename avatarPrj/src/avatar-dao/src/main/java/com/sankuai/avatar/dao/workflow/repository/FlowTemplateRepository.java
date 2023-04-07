package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowTemplateEntity;

/**
 * @author zhaozhifan02
 */
public interface FlowTemplateRepository {

    /**
     * 根据流程模板名称查询模板详情
     *
     * @param templateName 模板名称
     * @return {@link FlowTemplateEntity}
     */
    FlowTemplateEntity getFlowTemplateByName(String templateName);
}
