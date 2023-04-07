package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.AppkeyPaasStandardClientRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.AppkeyPaasStandardClientDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasStandardClientDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasStandardClientRequest;
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
 * @create 2022-09-27 16:48
 */
@Slf4j
@Repository
public class AppkeyPaasStandardClientRepositoryImpl implements AppkeyPaasStandardClientRepository {

    private final AppkeyPaasStandardClientDOMapper mapper;

    @Autowired
    public AppkeyPaasStandardClientRepositoryImpl(AppkeyPaasStandardClientDOMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<AppkeyPaasStandardClientDO> query(AppkeyPaasStandardClientRequest request) {
        if (Objects.isNull(request)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(request));
    }

    @Override
    public boolean insert(AppkeyPaasStandardClientDO appkeyPaasStandardClientDO) {
        if (Objects.isNull(appkeyPaasStandardClientDO)) {
            return false;
        }
        return mapper.insertSelective(appkeyPaasStandardClientDO) == 1;
    }

    @Override
    public int insertBatch(List<AppkeyPaasStandardClientDO> appkeyPaasStandardClientDOList) {
        if (Objects.isNull(appkeyPaasStandardClientDOList) || appkeyPaasStandardClientDOList.isEmpty()) {
            return 0;
        }
        return mapper.insertList(appkeyPaasStandardClientDOList);
    }

    @Override
    public boolean update(AppkeyPaasStandardClientDO appkeyPaasStandardClientDO) {
        if (Objects.isNull(appkeyPaasStandardClientDO) || Objects.isNull(appkeyPaasStandardClientDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(appkeyPaasStandardClientDO) == 1;
    }

    /**
     * 构造 Example
     * @param request 请求对象
     * @return
     */
    public Example buildExample(AppkeyPaasStandardClientRequest request) {
        Example example = new Example(AppkeyPaasStandardClientDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(request.getPaasName())) {
            criteria.andEqualTo("paasName", request.getPaasName());
        }
        if (StringUtils.isNotEmpty(request.getLanguage())) {
            criteria.andEqualTo("language", request.getLanguage());
        }
        if (!Objects.isNull(request.getGroupId())) {
            criteria.andEqualTo("groupId", request.getGroupId());
        }
        if (!Objects.isNull(request.getArtifactId())) {
            criteria.andEqualTo("artifactId", request.getArtifactId());
        }
        return example;
    }
}
