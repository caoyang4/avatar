package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.ApplicationRoleAdminRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.ApplicationRoleAdminDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.ApplicationRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.ApplicationRoleAdminRequest;
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
 * @create 2023-01-11 15:59
 */
@Repository
public class ApplicationRoleAdminRepositoryImpl implements ApplicationRoleAdminRepository {

    private final ApplicationRoleAdminDOMapper mapper;

    @Autowired
    public ApplicationRoleAdminRepositoryImpl(ApplicationRoleAdminDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<ApplicationRoleAdminDO> query(ApplicationRoleAdminRequest request) {
        if (Objects.isNull(request)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(request));
    }

    @Override
    public boolean insert(ApplicationRoleAdminDO applicationRoleAdminDO) {
        if (Objects.isNull(applicationRoleAdminDO)) {
            return false;
        }
        return mapper.insertSelective(applicationRoleAdminDO) == 1;
    }

    @Override
    public boolean update(ApplicationRoleAdminDO applicationRoleAdminDO) {
        if (Objects.isNull(applicationRoleAdminDO) || Objects.isNull(applicationRoleAdminDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(applicationRoleAdminDO) == 1;
    }

    @Override
    @LogRecord(success = "应用角色信息：{{#id}}，结果:{{#_ret}}", prefix = "ApplicationRoleAdmin", bizNo = "{{#id}}")
    public boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    private Example buildExample(ApplicationRoleAdminRequest request){
        Example example = new Example(ApplicationRoleAdminDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andEqualTo("id", request.getId());
            return example;
        }
        if (Objects.nonNull(request.getApplicationId())) {
            criteria.andEqualTo("applicationId", request.getApplicationId());
        }
        if (StringUtils.isNotEmpty(request.getApplicationName())) {
            criteria.andEqualTo("applicationName", request.getApplicationName());
        }
        if (StringUtils.isNotEmpty(request.getOrgPath())) {
            criteria.andLike("orgPath", String.format("%%%s%%", request.getOrgPath()));
        }
        return example;
    }

}
