package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.WindowPeriodRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.WindowPeriodDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.ResourceWindowPeriodDO;
import com.sankuai.avatar.dao.resource.repository.request.WindowPeriodRequest;
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
 * @create 2023-03-15 16:07
 */
@Repository
public class WindowPeriodRepositoryImpl implements WindowPeriodRepository {

    private final WindowPeriodDOMapper mapper;

    @Autowired
    public WindowPeriodRepositoryImpl(WindowPeriodDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<ResourceWindowPeriodDO> query(WindowPeriodRequest request) {
        if (Objects.isNull(request)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(request));
    }

    @Override
    public Integer getMaxWindowId() {
        return mapper.getMaxResourceWindowPeriodId();
    }

    @Override
    public Boolean insert(ResourceWindowPeriodDO resourceWindowPeriodDO) {
        if (Objects.isNull(resourceWindowPeriodDO)) {
            return false;
        }
        return mapper.insertSelective(resourceWindowPeriodDO) == 1;
    }

    @Override
    public Boolean update(ResourceWindowPeriodDO resourceWindowPeriodDO) {
        if (Objects.isNull(resourceWindowPeriodDO) || Objects.isNull(resourceWindowPeriodDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(resourceWindowPeriodDO) == 1;
    }

    @Override
    @LogRecord(success = "删除窗口期：{{#id}}，结果:{{#_ret}}", prefix = "WindowPeriod", bizNo = "{{#id}}")
    public Boolean delete(int pk) {
        return mapper.deleteByPrimaryKey(pk) == 1;
    }

    private Example buildExample(WindowPeriodRequest request){
        Example example = new Example(ResourceWindowPeriodDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andEqualTo("id", request.getId());
            return example;
        }
        if (StringUtils.isNotEmpty(request.getName())) {
            criteria.andEqualTo("name", request.getName());
        }
        if (StringUtils.isNotEmpty(request.getCreateUser())) {
            criteria.andEqualTo("createUser", request.getCreateUser());
        }
        example.orderBy("startTime").desc();
        return example;
    }

}
