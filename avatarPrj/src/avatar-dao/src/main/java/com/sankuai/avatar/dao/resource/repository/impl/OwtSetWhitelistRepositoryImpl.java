package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.OwtSetWhitelistRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.OwtSetWhitelistDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.OwtSetWhitelistDO;
import com.sankuai.avatar.dao.resource.repository.request.OwtSetWhitelistRequest;
import com.sankuai.microplat.logrecord.sdk.starter.annotation.LogRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * OwtSetWhitelistRepository(owt-set白名单) 实现类
 * @author caoyang
 * @create 2022-10-27 16:04
 */
@Repository
public class OwtSetWhitelistRepositoryImpl implements OwtSetWhitelistRepository {

    private final OwtSetWhitelistDOMapper mapper;

    @Autowired
    public OwtSetWhitelistRepositoryImpl(OwtSetWhitelistDOMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<OwtSetWhitelistDO> query(OwtSetWhitelistRequest owtSetWhitelistRequest) {
        if (Objects.isNull(owtSetWhitelistRequest)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(owtSetWhitelistRequest));
    }

    @Override
    public boolean insert(OwtSetWhitelistDO owtSetWhitelistDO) {
        if (Objects.isNull(owtSetWhitelistDO)) {
            return false;
        }
        return mapper.insertSelective(owtSetWhitelistDO) == 1;
    }

    @Override
    public boolean update(OwtSetWhitelistDO owtSetWhitelistDO) {
        if (Objects.isNull(owtSetWhitelistDO) || Objects.isNull(owtSetWhitelistDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(owtSetWhitelistDO) == 1;
    }

    @Override
    @LogRecord(success = "删除owt-set白名单：{{#id}}，结果:{{#_ret}}", prefix = "OwtSetWhitelist", bizNo = "{{#id}}")
    public boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 构建example
     *
     * @param request 请求
     * @return {@link Example}
     */
    private Example buildExample(OwtSetWhitelistRequest request){
        Example example = new Example(OwtSetWhitelistDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andEqualTo("id", request.getId());
            return example;
        }
        if (Objects.nonNull(request.getEndTimeLtn())) {
            criteria.andLessThan("endTime", request.getEndTimeLtn());
        }
        if (Objects.nonNull(request.getEndTimeGtn())) {
            criteria.andGreaterThan("endTime", request.getEndTimeGtn());
        }
        if (StringUtils.isNotEmpty(request.getApp())) {
            criteria.andEqualTo("app", request.getApp());
        }
        if (StringUtils.isNotEmpty(request.getOwt())) {
            criteria.andEqualTo("owt", request.getOwt());
        }
        if (StringUtils.isNotEmpty(request.getSetName())) {
            criteria.andEqualTo("setName", request.getSetName());
        }
        return example;
    }
}
