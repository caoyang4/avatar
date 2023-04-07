package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.dao.resource.repository.UserRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.UserDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.UserDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRequest;
import com.sankuai.microplat.logrecord.sdk.starter.annotation.LogRecord;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * UserRepository 接口实现类
 * @author caoyang
 * @create 2022-10-19 14:27
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDOMapper mapper;

    @Autowired
    public UserRepositoryImpl(UserDOMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<UserDO> query(UserRequest userRequest) {
        if (Objects.isNull(userRequest)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(userRequest));
    }

    @Override
    public boolean insert(UserDO userDO) {
        return mapper.insertSelective(userDO) == 1;
    }

    @Override
    public UserDO insertAndQuery(UserDO userDO) {
        mapper.insertSelective(userDO);
        if (Objects.isNull(userDO.getId())) {
            return null;
        }
        return mapper.selectByPrimaryKey(userDO.getId());
    }

    @Override
    public boolean update(UserDO userDO) {
        if (Objects.isNull(userDO) || Objects.isNull(userDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(userDO) == 1;
    }

    @Override
    @LogRecord(success = "删除用户：{{#id}}，结果:{{#_ret}}", prefix = "User", bizNo = "{{#id}}")
    public boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 构建 Example
     * @param request 请求对象
     * @return example条件对象
     */
    private Example buildExample(UserRequest request){
        Example example = new Example(UserDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andEqualTo("id", request.getId());
        }
        // 默认返回近两年登录 avatar 系统的人员信息，避免全表扫描
        criteria.andGreaterThanOrEqualTo("loginTime", DateUtils.localDateToDate(LocalDate.now().plusDays(-365 * 2)));
        List<String> misList = request.getMisList();
        if (CollectionUtils.isNotEmpty(misList)) {
            if (misList.size() == 1) {
                criteria.andEqualTo("loginName", misList.get(0));
            } else {
                criteria.andIn("loginName", misList);
            }
        }
        if (StringUtils.isNotEmpty(request.getOrgId())) {
            criteria.andEqualTo("orgId", request.getOrgId());
        }
        if (StringUtils.isNotEmpty(request.getSearch())) {
            Example.Criteria nameCriteria = example.createCriteria();
            nameCriteria.orLike("name", String.format("%%%s%%", request.getSearch()));
            nameCriteria.orLike("loginName", String.format("%%%s%%", request.getSearch()));
            example.and(nameCriteria);
        }

        if (StringUtils.isNotEmpty(request.getOrgPath())) {
            criteria.andLike("orgPath", String.format("%%%s%%", request.getOrgPath()));
        }
        return example;
    }
}
