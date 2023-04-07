package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.AppkeyRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.AppkeyDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyRequest;
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
 * @author caoyang
 * @create 2022-09-22 11:11
 */
@Slf4j
@Repository
public class AppkeyRepositoryImpl implements AppkeyRepository {

    private final AppkeyDOMapper appkeyDOMapper;

    @Autowired
    public AppkeyRepositoryImpl(AppkeyDOMapper appkeyDOMapper){
        this.appkeyDOMapper = appkeyDOMapper;
    }

    @Override
    public List<AppkeyDO> query(AppkeyRequest appkeyRequest) {
        if (Objects.isNull(appkeyRequest)) {
            return Collections.emptyList();
        }
        return appkeyDOMapper.selectByExample(buildExample(appkeyRequest));
    }

    @Override
    public boolean insert(AppkeyDO appkeyDO) {
        if (Objects.isNull(appkeyDO)) {
            return false;
        }
        return appkeyDOMapper.insertSelective(appkeyDO) == 1;
    }

    @Override
    public int insertBatch(List<AppkeyDO> appkeyDOList) {
        if (CollectionUtils.isEmpty(appkeyDOList)) {
            return 0;
        }
        return appkeyDOMapper.insertList(appkeyDOList);
    }

    @Override
    public boolean update(AppkeyDO appkeyDO) {
        if (Objects.isNull(appkeyDO) || Objects.isNull(appkeyDO.getId())) {
            return false;
        }
        return appkeyDOMapper.updateByPrimaryKeySelective(appkeyDO) == 1;
    }

    @Override
    public boolean delete(int id) {
        return appkeyDOMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 构建 Example
     * @param appkeyRequest 请求对象
     * @return example条件对象
     */
    private Example buildExample(AppkeyRequest appkeyRequest) {

        Example example = new Example(AppkeyDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(appkeyRequest.getSrv())) {
            criteria.andEqualTo("srv", appkeyRequest.getSrv());
        }
        if (StringUtils.isNotEmpty(appkeyRequest.getAppkey())) {
            criteria.andEqualTo("appkey", appkeyRequest.getAppkey());
        } else {
            List<String> appkeys = appkeyRequest.getAppkeys();
            if (CollectionUtils.isNotEmpty(appkeys)) {
                if (Objects.equals(1, appkeys.size())) {
                    criteria.andEqualTo("appkey", appkeys.get(0));
                } else {
                    criteria.andIn("appkey", appkeys);
                }
            }
        }
        if (StringUtils.isNotEmpty(appkeyRequest.getOwt())) {
            criteria.andEqualTo("owt", appkeyRequest.getOwt());
        }
        if (StringUtils.isNotEmpty(appkeyRequest.getPdl())) {
            criteria.andEqualTo("pdl", appkeyRequest.getPdl());
        }
        if (StringUtils.isNotEmpty(appkeyRequest.getOrgId())) {
            criteria.andEqualTo("orgId", appkeyRequest.getOrgId());
        }
        if (StringUtils.isNotEmpty(appkeyRequest.getBillingUnitId())) {
            criteria.andEqualTo("billingUnitId", appkeyRequest.getBillingUnitId());
        }
        if (Objects.nonNull(appkeyRequest.getStateful())) {
            criteria.andEqualTo("stateful", appkeyRequest.getStateful());
        }
        if (Objects.nonNull(appkeyRequest.getCompatibleIpv6())) {
            criteria.andEqualTo("compatibleIpv6", appkeyRequest.getCompatibleIpv6());
        }
        if (Objects.nonNull(appkeyRequest.getIsLiteset())) {
            criteria.andEqualTo("isLiteset", appkeyRequest.getIsLiteset());
        }
        if (Objects.nonNull(appkeyRequest.getIsSet())) {
            criteria.andEqualTo("isSet", appkeyRequest.getIsSet());
        }
        if (StringUtils.isNotEmpty(appkeyRequest.getOwner())) {
            criteria.orLike("rdAdmin", String.format("%%%s%%", appkeyRequest.getOwner()));
            criteria.orLike("epAdmin", String.format("%%%s%%", appkeyRequest.getOwner()));
        }
        return example;
    }

}
