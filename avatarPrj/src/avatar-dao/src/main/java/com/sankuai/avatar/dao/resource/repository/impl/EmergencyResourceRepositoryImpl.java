package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.EmergencyResourceRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.EmergencyResourceDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.EmergencyResourceDO;
import com.sankuai.avatar.dao.resource.repository.request.EmergencyResourceRequest;
import com.sankuai.microplat.logrecord.sdk.starter.annotation.LogRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-11-25 23:26
 */
@Slf4j
@Repository
public class EmergencyResourceRepositoryImpl implements EmergencyResourceRepository {

    private final EmergencyResourceDOMapper mapper;

    @Autowired
    public EmergencyResourceRepositoryImpl(EmergencyResourceDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<EmergencyResourceDO> query(EmergencyResourceRequest request) {
        if (Objects.isNull(request)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(request));
    }

    @Override
    public Boolean insert(EmergencyResourceDO emergencyResourceDO) {
        return mapper.insertSelective(emergencyResourceDO) == 1;
    }

    @Override
    @LogRecord(success = "删除紧急资源信息：{{#id}}，结果:{{#_ret}}", prefix = "EmergencyResource", bizNo = "{{#id}}")
    public Boolean delete(int pk) {
        return mapper.deleteByPrimaryKey(pk) == 1;
    }

    private Example buildExample(EmergencyResourceRequest request){
        Example example = new Example(EmergencyResourceDO.class);
        example.setOrderByClause("end_time desc");
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andEqualTo("id", request.getId());
            return example;
        }
        if (Objects.nonNull(request.getFlowId())) {
            criteria.andEqualTo("flowId", request.getFlowId());
        }
        if (StringUtils.isNotEmpty(request.getFlowUuid())) {
            criteria.andEqualTo("flowUuid", request.getFlowUuid());
        }
        if (StringUtils.isNotEmpty(request.getAppkey())) {
            criteria.andEqualTo("appkey", request.getAppkey());
        }
        if (StringUtils.isNotEmpty(request.getEnv())) {
            criteria.andEqualTo("env", request.getEnv());
        }
        if (StringUtils.isNotEmpty(request.getTemplate())) {
            criteria.andEqualTo("template", request.getTemplate());
        }
        if (StringUtils.isNotEmpty(request.getOperationType())) {
            criteria.andEqualTo("operationType", request.getOperationType());
        }
        if (Objects.nonNull(request.getEndTimeGtn())) {
            criteria.andGreaterThan("endTime", request.getEndTimeGtn());
        }
        if (Objects.nonNull(request.getEndTimeLtn())) {
            criteria.andLessThan("endTime", request.getEndTimeLtn());
        }
        return example;
    }
}
