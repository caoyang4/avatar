package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.DxGroupRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.DxGroupDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.DxGroupDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * DxGroupRepository实现类
 * @author caoyang
 * @create 2022-11-01 22:06
 */
@Slf4j
@Repository
public class DxGroupRepositoryImpl implements DxGroupRepository {

    private final DxGroupDOMapper mapper;
    @Autowired
    public DxGroupRepositoryImpl(DxGroupDOMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<DxGroupDO> queryAllGroup() {
        return mapper.selectAll();
    }

    @Override
    public List<DxGroupDO> queryByGroupId(List<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return Collections.emptyList();
        }
        Example example = new Example(DxGroupDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (groupIds.size() == 1) {
            criteria.andEqualTo("groupId", groupIds.get(0));
        } else {
            criteria.andIn("groupId", groupIds);
        }
        return mapper.selectByExample(example);
    }

    @Override
    public boolean insert(DxGroupDO dxGroupDO) {
        if (Objects.isNull(dxGroupDO)) {
            return false;
        }
        return mapper.insertSelective(dxGroupDO) == 1;
    }

    @Override
    public boolean update(DxGroupDO dxGroupDO) {
        if (Objects.isNull(dxGroupDO) || Objects.isNull(dxGroupDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(dxGroupDO) == 1;
    }
}
