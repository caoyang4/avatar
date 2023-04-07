package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.OrgRoleAdminRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.OrgRoleAdminDOMaper;
import com.sankuai.avatar.dao.resource.repository.model.OrgRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.OrgRoleAdminRequest;
import com.sankuai.microplat.logrecord.sdk.starter.annotation.LogRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * OrgRoleAdminRepository实现类
 * @author caoyang
 * @create 2022-11-01 22:08
 */
@Slf4j
@Repository
public class OrgRoleAdminRepositoryImpl implements OrgRoleAdminRepository {

    private final OrgRoleAdminDOMaper mapper;
    @Autowired
    public OrgRoleAdminRepositoryImpl(OrgRoleAdminDOMaper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<OrgRoleAdminDO> query(OrgRoleAdminRequest orgRoleAdminRequest) {
        if (Objects.isNull(orgRoleAdminRequest)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(orgRoleAdminRequest));
    }

    @Override
    public boolean insert(OrgRoleAdminDO orgRoleAdminDO) {
        if (Objects.isNull(orgRoleAdminDO)) {
            return false;
        }
        return mapper.insertSelective(orgRoleAdminDO) == 1;
    }

    @Override
    public boolean update(OrgRoleAdminDO orgRoleAdminDO) {
        if (Objects.isNull(orgRoleAdminDO) || Objects.isNull(orgRoleAdminDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(orgRoleAdminDO) == 1;
    }

    @Override
    @LogRecord(success = "删除部门角色信息：{{#id}}，结果:{{#_ret}}", prefix = "OrgRoleAdmin", bizNo = "{{#id}}")
    public boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    private Example buildExample(OrgRoleAdminRequest request){
        Example example = new Example(OrgRoleAdminDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getIds())) {
            if (request.getIds().size() == 1) {
                criteria.andEqualTo("id", request.getIds().get(0));
            } else {
                criteria.andIn("id", request.getIds());
            }
            return example;
        }
        if (CollectionUtils.isNotEmpty(request.getOrgIds())) {
            if (request.getOrgIds().size() == 1) {
                criteria.andEqualTo("orgId", request.getOrgIds().get(0));
            } else {
                criteria.andIn("orgId", request.getOrgIds());
            }
        }
        if (StringUtils.isNotEmpty(request.getRole())) {
            criteria.andEqualTo("role", request.getRole());
        }
        if (StringUtils.isNotEmpty(request.getRoleUser())) {
            criteria.andLike("roleUsers", String.format("%%%s%%", request.getRoleUser()));
        }
        if (StringUtils.isNotEmpty(request.getOrgPath())) {
            criteria.andLike("orgPath", String.format("%%%s%%", request.getOrgPath()));
        }
        if (StringUtils.isNotEmpty(request.getOrgName())) {
            criteria.andLike("orgName", String.format("%%%s%%", request.getOrgName()));
        }
        return example;
    }
}
