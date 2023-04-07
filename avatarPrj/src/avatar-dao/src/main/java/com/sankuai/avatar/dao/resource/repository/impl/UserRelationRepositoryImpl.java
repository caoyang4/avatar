package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.UserRelationRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.UserRelationDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.UserRelationDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRelationRequest;
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
 * UserRelationRepository实现类
 * @author caoyang
 * @create 2022-11-02 19:22
 */
@Slf4j
@Repository
public class UserRelationRepositoryImpl implements UserRelationRepository {

    private final UserRelationDOMapper mapper;
    @Autowired
    public UserRelationRepositoryImpl(UserRelationDOMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<UserRelationDO> query(UserRelationRequest userRelationRequest) {
        if (Objects.isNull(userRelationRequest)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(userRelationRequest));
    }

    @Override
    public boolean insert(UserRelationDO userRelationDO) {
        if (Objects.isNull(userRelationDO)) {
            return false;
        }
        return mapper.insertSelective(userRelationDO) == 1;
    }

    @Override
    public boolean update(UserRelationDO userRelationDO) {
        if (Objects.isNull(userRelationDO) || Objects.isNull(userRelationDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(userRelationDO) == 1;
    }

    @Override
    @LogRecord(success = "删除用户服务置顶：{{#id}}，结果:{{#_ret}}", prefix = "UserRelation", bizNo = "{{#id}}")
    public boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    private Example buildExample(UserRelationRequest request){
        Example example = new Example(UserRelationDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(request.getAppkey())) {
            criteria.andEqualTo("appkey", request.getAppkey());
        }
        if (StringUtils.isNotEmpty(request.getTag())) {
            criteria.andEqualTo("tag", request.getTag());
        }
        if (StringUtils.isNotEmpty(request.getLoginName())) {
            criteria.andEqualTo("loginName", request.getLoginName());
        }
        // 按照更新时间逆序排列
        example.setOrderByClause("updatetime desc");
        return example;
    }
}
