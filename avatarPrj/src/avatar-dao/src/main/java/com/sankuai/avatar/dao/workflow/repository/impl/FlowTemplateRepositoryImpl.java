package com.sankuai.avatar.dao.workflow.repository.impl;

import com.sankuai.avatar.dao.workflow.repository.FlowTemplateRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowTemplateEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowTemplateDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowTemplateDO;
import com.sankuai.avatar.dao.workflow.repository.model.FlowTemplateTask;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowTemplateTransfer;
import com.sankuai.avatar.dao.workflow.repository.utils.PicklerDataUtils;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;


/**
 * 流程模版的CRUD
 * @author zhaozhifan02
 */
@Slf4j
@Repository
public class FlowTemplateRepositoryImpl implements FlowTemplateRepository {

    private final FlowTemplateDOMapper mapper;

    @Autowired
    public FlowTemplateRepositoryImpl(FlowTemplateDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public FlowTemplateEntity getFlowTemplateByName(String templateName) {
        if (StringUtils.isBlank(templateName)) {
            return null;
        }
        Example example = new Example(FlowTemplateDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("templateName", templateName);
        List<FlowTemplateDO> flowTemplateDOList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(flowTemplateDOList)) {
            return null;
        }
        FlowTemplateDO flowTemplateDO = flowTemplateDOList.get(0);
        FlowTemplateEntity flowTemplateEntity = FlowTemplateTransfer.INSTANCE.toEntity(flowTemplateDO);
        FlowTemplateTask[] tasks = PicklerDataUtils.unPicklerData(flowTemplateDO.getTasks(), FlowTemplateTask[].class);
        if (tasks != null) {
            flowTemplateEntity.setTasks(Arrays.asList(tasks));
        }
        flowTemplateEntity.setConfig(PicklerDataUtils.unPicklerData(flowTemplateDO.getConfig(), Object.class));
        return flowTemplateEntity;
    }
}
