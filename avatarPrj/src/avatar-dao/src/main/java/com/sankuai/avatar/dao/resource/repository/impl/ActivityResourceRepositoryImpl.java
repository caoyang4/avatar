package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.ActivityResourceRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.ActivityResourceDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.ActivityResourceDO;
import com.sankuai.avatar.dao.resource.repository.request.ActivityResourceRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 活动资源管理对象
 * @author caoyang
 * @create 2023-03-07 16:20
 */
@Repository
public class ActivityResourceRepositoryImpl implements ActivityResourceRepository {

    private final ActivityResourceDOMapper mapper;

    @Autowired
    public ActivityResourceRepositoryImpl(ActivityResourceDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<ActivityResourceDO> query(ActivityResourceRequest activityResourceRequest) {
        if (Objects.isNull(activityResourceRequest)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(activityResourceRequest));
    }

    @Override
    public boolean insert(ActivityResourceDO activityResourceDO) {
        if (Objects.isNull(activityResourceDO)) {
            return false;
        }
        return mapper.insertSelective(activityResourceDO) == 1;
    }

    @Override
    public boolean update(ActivityResourceDO activityResourceDO) {
        if (Objects.isNull(activityResourceDO) || Objects.isNull(activityResourceDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(activityResourceDO) == 1;
    }

    @Override
    public boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    private Example buildExample(ActivityResourceRequest request){
        Example example = new Example(ActivityResourceDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andEqualTo("id", request.getId());
            return example;
        }
        if (StringUtils.isNotEmpty(request.getAppkey())) {
            criteria.andEqualTo("appkey", request.getAppkey());
        }
        if (StringUtils.isNotEmpty(request.getCreateUser())) {
            criteria.andEqualTo("createUser", request.getCreateUser());
        }
        if (Objects.nonNull(request.getWindowPeriodId())) {
            criteria.andEqualTo("windowPeriodId", request.getWindowPeriodId());
        }
        if (StringUtils.isNotEmpty(request.getOrgId())) {
            criteria.andEqualTo("orgId", request.getOrgId());
        }
        if (StringUtils.isNotEmpty(request.getOrgName())) {
            criteria.andEqualTo("orgName", request.getOrgName());
        }
        if (StringUtils.isNotEmpty(request.getStatus())) {
            criteria.andEqualTo("status", request.getStatus());
        }
        example.orderBy("createTime").desc();
        return example;
    }
}
