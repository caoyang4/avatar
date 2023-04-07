package com.sankuai.avatar.dao.workflow.repository.impl;

import com.google.common.base.Preconditions;
import com.sankuai.avatar.dao.workflow.repository.AtomStepRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.AtomStepEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.AtomStepDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.AtomStepDO;
import com.sankuai.avatar.dao.workflow.repository.transfer.AtomStepTransfer;
import tk.mybatis.mapper.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author zhaozhifan02
 */
@Repository
public class AtomStepRepositoryImpl implements AtomStepRepository {

    private final AtomStepDOMapper mapper;

    @Autowired
    public AtomStepRepositoryImpl(AtomStepDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public AtomStepEntity getAtomStepByName(String name) {
        Preconditions.checkNotNull(name, "name must not be null");
        Example example = new Example(AtomStepDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", name);
        AtomStepDO atomStepDO = mapper.selectOneByExample(example);
        return AtomStepTransfer.INSTANCE.toEntity(atomStepDO);
    }
}
