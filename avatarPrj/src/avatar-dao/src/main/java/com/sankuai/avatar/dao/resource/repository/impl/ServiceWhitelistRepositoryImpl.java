package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.ServiceWhitelistRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.ServiceWhitelistDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.ServiceWhitelistDO;
import com.sankuai.avatar.dao.resource.repository.request.ServiceWhitelistRequest;
import com.sankuai.microplat.logrecord.sdk.starter.annotation.LogRecord;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * ServiceWhitelistRepository(服务白名单) 实现类
 * @author caoyang
 * @create 2022-10-27 16:55
 */
@Repository
public class ServiceWhitelistRepositoryImpl implements ServiceWhitelistRepository {

    final ServiceWhitelistDOMapper mapper;
    @Autowired
    public ServiceWhitelistRepositoryImpl(ServiceWhitelistDOMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<ServiceWhitelistDO> query(ServiceWhitelistRequest serviceWhitelistRequest) {
        if (Objects.isNull(serviceWhitelistRequest)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(serviceWhitelistRequest));
    }

    @Override
    public boolean insert(ServiceWhitelistDO serviceWhitelistDO) {
        if (Objects.isNull(serviceWhitelistDO)) {
            return false;
        }
        return mapper.insertSelective(serviceWhitelistDO) == 1;
    }

    @Override
    public boolean update(ServiceWhitelistDO serviceWhitelistDO) {
        if (Objects.isNull(serviceWhitelistDO) || Objects.isNull(serviceWhitelistDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(serviceWhitelistDO) == 1;
    }

    @Override
    @LogRecord(success = "删除服务白名单：{{#id}}，结果:{{#_ret}}", prefix = "ServiceWhitelist", bizNo = "{{#id}}")
    public boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 构建 example
     *
     * @param request 请求
     * @return {@link Example}
     */
    private Example buildExample(ServiceWhitelistRequest request){
        Example example = new Example(ServiceWhitelistDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andEqualTo("id", request.getId());
            return example;
        }
        if (StringUtils.isNotEmpty(request.getApp())) {
            criteria.andEqualTo("app", request.getApp());
        }
        if (Objects.nonNull(request.getEndTimeGtn())) {
            criteria.andGreaterThan("endTime", request.getEndTimeGtn());
        }
        if (Objects.nonNull(request.getEndTimeLtn())) {
            criteria.andLessThan("endTime", request.getEndTimeLtn());
        }
        if (CollectionUtils.isNotEmpty(request.getAppkeys())) {
            List<String> appkeys = request.getAppkeys();
            if (appkeys.size() == 1 && StringUtils.isEmpty(appkeys.get(0))) {
                criteria.andEqualTo("appkey", appkeys.get(0));
            } else {
                criteria.andIn("appkey", appkeys);
            }
        }
        if (StringUtils.isNotEmpty(request.getApplication())) {
            criteria.andEqualTo("application", request.getApplication());
        }
        if (StringUtils.isNotEmpty(request.getOrgIds())) {
            criteria.andLike("orgIds", String.format("%%%s%%", request.getOrgIds()));
        }
        if (StringUtils.isNotEmpty(request.getQuery())) {
            criteria.andLike("appkey", String.format("%%%s%%", request.getQuery()));
        }
        return example;
    }
}
