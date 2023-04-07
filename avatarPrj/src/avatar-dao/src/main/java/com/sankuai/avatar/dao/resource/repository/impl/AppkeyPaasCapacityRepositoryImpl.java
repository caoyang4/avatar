package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.AppkeyPaasCapacityRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.AppkeyPaasCapacityDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasCapacityDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasCapacityRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-09-22 11:11
 */
@Slf4j
@Repository
public class AppkeyPaasCapacityRepositoryImpl implements AppkeyPaasCapacityRepository {

    private final AppkeyPaasCapacityDOMapper mapper;

    @Autowired
    public AppkeyPaasCapacityRepositoryImpl(AppkeyPaasCapacityDOMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<AppkeyPaasCapacityDO> query(AppkeyPaasCapacityRequest request) {
        if (Objects.isNull(request)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(request));
    }

    @Override
    public List<String> queryPaasNamesByAppkey(Date date, String appkey) {
        return mapper.selectPaasListByAppkey(date, appkey);
    }

    @Override
    public List<AppkeyPaasCapacityDO> queryAggregatedClientAppkey(Date date, String appkey) {
        return mapper.selectAggregatedClientAppkeyByTypeName(date, appkey);
    }

    @Override
    public List<AppkeyPaasCapacityDO> queryAggregatedClientAppkey(Date date, String appkey, List<String> paasNameList) {
        return mapper.selectAggregatedClientAppkeyByPaasList(date, appkey, paasNameList);
    }

    @Override
    public List<String> queryPaasNamesByPaasAppkey(Date date, String paasAppkey) {
        return mapper.selectPaasListByPaasAppkey(date, paasAppkey);
    }

    @Override
    public List<AppkeyPaasCapacityDO> queryAggregatedPaasAppkey(Date date, String paasAppkey) {
        return mapper.selectAggregatedPaasAppkeyByTypeName(date, paasAppkey);
    }

    @Override
    public List<AppkeyPaasCapacityDO> queryAggregatedPaasAppkey(Date date, String paasAppkey, List<String> paasNameList) {
        return mapper.selectAggregatedPaasAppkeyByPaasList(date, paasAppkey, paasNameList);
    }

    @Override
    public List<String> queryPaasAppkeysByUpdateDate(Date updateDate) {
        return mapper.selectAllPaasAppkey(updateDate);
    }

    @Override
    public List<String> queryClientAppkeysByUpdateDate(Date updateDate) {
        return mapper.selectAllClientAppkey(updateDate);
    }

    @Override
    public boolean insert(AppkeyPaasCapacityDO appkeyPaasCapacityDO) {
        if (Objects.isNull(appkeyPaasCapacityDO)) {
            return false;
        }
        return mapper.insertSelective(appkeyPaasCapacityDO) == 1;
    }

    @Override
    public int insertBatch(List<AppkeyPaasCapacityDO> appkeyPaasCapacityDOList) {
        if (Objects.isNull(appkeyPaasCapacityDOList) || appkeyPaasCapacityDOList.isEmpty()) {
            return 0;
        }
        return mapper.insertList(appkeyPaasCapacityDOList);
    }

    @Override
    public boolean update(AppkeyPaasCapacityDO appkeyPaasCapacityDO) {
        if (Objects.isNull(appkeyPaasCapacityDO) || Objects.isNull(appkeyPaasCapacityDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(appkeyPaasCapacityDO) == 1;
    }

    @Override
    public boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 构建 Example
     * @param request 请求对象
     * @return example条件对象
     */
    private Example buildExample(AppkeyPaasCapacityRequest request) {
        Example example = new Example(AppkeyPaasCapacityDO.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("id").asc();
        if (Objects.nonNull(request.getIdGtn())) {
            criteria.andGreaterThan("id", request.getIdGtn());
        }
        if (StringUtils.isNotEmpty(request.getClientAppkey())) {
            criteria.andEqualTo("clientAppkey", request.getClientAppkey());
        }
        if (StringUtils.isNotEmpty(request.getPaasName())) {
            criteria.andEqualTo("paasName", request.getPaasName());
        }
        if (StringUtils.isNotEmpty(request.getTypeName())) {
            criteria.andEqualTo("typeName", request.getTypeName());
        }
        if (StringUtils.isNotEmpty(request.getClientRole())) {
            criteria.andEqualTo("clientRole", request.getClientRole());
        }
        if (StringUtils.isNotEmpty(request.getPaasAppkey())) {
            criteria.andEqualTo("paasAppkey", request.getPaasAppkey());
        }
        if (StringUtils.isNotEmpty(request.getSetName())) {
            criteria.andEqualTo("setName", request.getSetName());
        }
        if (StringUtils.isNotEmpty(request.getSetType())) {
            criteria.andEqualTo("setType", request.getSetType());
        }
        if (StringUtils.isNotEmpty(request.getType())) {
            criteria.andEqualTo("type", request.getType());
        }
        if (!Objects.isNull(request.getIsSet())) {
            criteria.andEqualTo("isSet", request.getIsSet());
        }
        if (!Objects.isNull(request.getIsWhite())) {
            criteria.andEqualTo("isWhite", request.getIsWhite());
        }
        if (!Objects.isNull(request.getIsCore())) {
            criteria.andEqualTo("isCore", request.getIsCore());
        }
        if (!Objects.isNull(request.getIsCapacityStandard())) {
            criteria.andEqualTo("isCapacityStandard", request.getIsCapacityStandard());
        }
        if (!Objects.isNull(request.getUpdateDate())) {
            criteria.andEqualTo("updateDate", request.getUpdateDate());
        }
        return example;
    }

}
