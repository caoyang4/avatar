package com.sankuai.avatar.dao.workflow.repository.impl;

import com.sankuai.avatar.dao.workflow.repository.FlowAtomTemplateRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowAtomTemplateEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowAtomTemplateDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowAtomTemplateDO;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowAtomTemplateTransfer;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Repository
public class FlowAtomTemplateRepositoryImpl implements FlowAtomTemplateRepository {
    /**
     * 默认版本号
     */
    private static final Integer DEFAULT_VERSION = 0;

    private final FlowAtomTemplateDOMapper mapper;

    @Autowired
    public FlowAtomTemplateRepositoryImpl(FlowAtomTemplateDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<FlowAtomTemplateEntity> getFlowAtomTemplate(String templateName, Integer version) {
        if (StringUtils.isBlank(templateName)) {
            return Collections.emptyList();
        }
        if (version == null) {
            version = DEFAULT_VERSION;
        }
        Example example = new Example(FlowAtomTemplateDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("templateName", templateName);
        criteria.andEqualTo("version", version);
        List<FlowAtomTemplateDO> flowAtomTemplateDOList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(flowAtomTemplateDOList)) {
            return Collections.emptyList();
        }
        return FlowAtomTemplateTransfer.INSTANCE.toEntityList(flowAtomTemplateDOList);
    }
}
