package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.AppkeyCapacityRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.AppkeyCapacityDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyCapacityDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyCapacityRequest;
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
 * @create 2022-11-03 14:32
 */
@Slf4j
@Repository
public class AppkeyCapacityRepositoryImpl implements AppkeyCapacityRepository {

    private final AppkeyCapacityDOMapper mapper;

    @Autowired
    public AppkeyCapacityRepositoryImpl(AppkeyCapacityDOMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<AppkeyCapacityDO> query(AppkeyCapacityRequest appkeyCapacityRequest) {
        if (Objects.isNull(appkeyCapacityRequest)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(appkeyCapacityRequest));
    }

    @Override
    public boolean insert(AppkeyCapacityDO appkeyCapacityDO) {
        if (Objects.isNull(appkeyCapacityDO)) {
            return false;
        }
        return mapper.insertSelective(appkeyCapacityDO) == 1;
    }

    @Override
    public boolean update(AppkeyCapacityDO appkeyCapacityDO) {
        if (Objects.isNull(appkeyCapacityDO) || Objects.isNull(appkeyCapacityDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(appkeyCapacityDO) == 1;
    }

    @Override
    @LogRecord(success = "删除服务容灾信息：{{#id}}，结果:{{#_ret}}", prefix = "AppkeyCapacity", bizNo = "{{#id}}")
    public boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    private Example buildExample(AppkeyCapacityRequest request){
        Example example = new Example(AppkeyCapacityDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (Boolean.TRUE.equals(request.isOnlyAppkey())) {
            example.selectProperties("appkey");
            example.setDistinct(true);
            return example;
        }
        if (StringUtils.isNotEmpty(request.getAppkey())) {
            criteria.andEqualTo("appkey", request.getAppkey());
        }
        if (Objects.nonNull(request.getSetName())) {
            criteria.andEqualTo("setName", request.getSetName());
        }
        if (Objects.nonNull(request.getIsCapacityStandard())) {
            criteria.andEqualTo("isCapacityStandard", request.getIsCapacityStandard());
        }
        if (StringUtils.isNotEmpty(request.getOrg())) {
            criteria.orLike("orgPath", String.format("%%%s%%", request.getOrg()));
            criteria.orLike("orgDisplayName", String.format("%%%s%%", request.getOrg()));
        }
        if (Boolean.FALSE.equals(request.getIsFullField())) {
            example.selectProperties("id", "appkey", "setName", "capacityLevel", "standardLevel", "isCapacityStandard", "standardReason", "standardTips", "whitelists", "updateTime");
        }
        example.setOrderByClause("id");
        return example;
    }
}
