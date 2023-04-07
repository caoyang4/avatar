package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.EmergencySreRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.EmergencySreDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.EmergencySreDO;
import com.sankuai.avatar.dao.resource.repository.request.EmergencySreRequest;
import com.sankuai.microplat.logrecord.sdk.starter.annotation.LogRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-01-17 15:48
 */
@Repository
public class EmergencySreRepositoryImpl implements EmergencySreRepository {

    private final EmergencySreDOMapper mapper;
    @Autowired
    public EmergencySreRepositoryImpl(EmergencySreDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<EmergencySreDO> query(EmergencySreRequest request) {
        if (Objects.isNull(request)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(request));
    }

    @Override
    public Boolean insert(EmergencySreDO emergencySreDO) {
        if (Objects.isNull(emergencySreDO)) {
            return false;
        }
        return mapper.insertSelective(emergencySreDO) == 1;
    }

    @Override
    public Boolean update(EmergencySreDO emergencySreDO) {
        if (Objects.isNull(emergencySreDO) || Objects.isNull(emergencySreDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(emergencySreDO) == 1;
    }

    @Override
    @LogRecord(success = "删除临时sre信息：{{#id}}，结果:{{#_ret}}", prefix = "EmergencySre", bizNo = "{{#id}}")
    public Boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    private Example buildExample(EmergencySreRequest request){
        Example example = new Example(EmergencySreDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andEqualTo("id", request.getId());
            return example;
        }
        if (Objects.nonNull(request.getSourceId())) {
            criteria.andEqualTo("sourceId", request.getSourceId());
        }
        if (StringUtils.isNotEmpty(request.getAppkey())) {
            criteria.andEqualTo("appkey", request.getAppkey());
        }
        if (StringUtils.isNotEmpty(request.getState())) {
            criteria.andEqualTo("state", request.getState());
        }
        return example;
    }
}
