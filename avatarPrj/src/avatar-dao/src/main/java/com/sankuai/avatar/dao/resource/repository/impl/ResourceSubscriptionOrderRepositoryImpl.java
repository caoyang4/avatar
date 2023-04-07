package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.ResourceSubscriptionOrderRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.ResourceSubscriptionOrderDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.ResourceSubscriptionOrderDO;
import com.sankuai.avatar.dao.resource.repository.request.ResourceSubscriptionOrderRequest;
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
 * @create 2023-02-10 14:56
 */
@Repository
public class ResourceSubscriptionOrderRepositoryImpl implements ResourceSubscriptionOrderRepository {

    private final ResourceSubscriptionOrderDOMapper mapper;

    @Autowired
    public ResourceSubscriptionOrderRepositoryImpl(ResourceSubscriptionOrderDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<ResourceSubscriptionOrderDO> query(ResourceSubscriptionOrderRequest request) {
        if (Objects.isNull(request)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(request));
    }

    @Override
    public Boolean insert(ResourceSubscriptionOrderDO resourceSubscriptionOrderDO) {
        if (Objects.isNull(resourceSubscriptionOrderDO)) {
            return false;
        }
        return mapper.insertSelective(resourceSubscriptionOrderDO) == 1;
    }

    @Override
    public Boolean update(ResourceSubscriptionOrderDO resourceSubscriptionOrderDO) {
        if (Objects.isNull(resourceSubscriptionOrderDO) || Objects.isNull(resourceSubscriptionOrderDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(resourceSubscriptionOrderDO) == 1;
    }

    @LogRecord(success = "删除到货订阅订单：{{#id}}，结果:{{#_ret}}", prefix = "ResourceSubscriptionOrder", bizNo = "{{#id}}")
    @Override
    public Boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    private Example buildExample(ResourceSubscriptionOrderRequest request){
        Example example = new Example(ResourceSubscriptionOrderDO.class);
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
        if (StringUtils.isNotEmpty(request.getRegion())) {
            criteria.andEqualTo("region", request.getRegion());
        }
        if (StringUtils.isNotEmpty(request.getIdc())) {
            criteria.andEqualTo("idc", request.getIdc());
        }
        if (StringUtils.isNotEmpty(request.getUnit())) {
            criteria.andEqualTo("unit", request.getUnit());
        }
        if (StringUtils.isNotEmpty(request.getStatus())) {
            criteria.andEqualTo("status", request.getStatus());
        }
        example.orderBy("expireTime").desc();
        return example;
    }

}
