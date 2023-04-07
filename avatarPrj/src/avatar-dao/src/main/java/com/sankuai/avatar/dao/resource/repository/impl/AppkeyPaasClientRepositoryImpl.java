package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.AppkeyPaasClientRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.AppkeyPaasClientDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasClientDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasClientRequest;
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
 * @create 2022-09-27 16:54
 */
@Slf4j
@Repository
public class AppkeyPaasClientRepositoryImpl implements AppkeyPaasClientRepository {

    private final AppkeyPaasClientDOMapper mapper;

    @Autowired
    public AppkeyPaasClientRepositoryImpl(AppkeyPaasClientDOMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<AppkeyPaasClientDO> query(AppkeyPaasClientRequest request) {
        if (Objects.isNull(request)) {
            return Collections.emptyList();
        }
        return mapper.selectByExample(buildExample(request));
    }

    @Override
    public boolean insert(AppkeyPaasClientDO appkeyPaasClientDO) {
        if (Objects.isNull(appkeyPaasClientDO)) {
            return false;
        }
        return mapper.insertSelective(appkeyPaasClientDO) == 1;
    }

    @Override
    public int insertBatch(List<AppkeyPaasClientDO> appkeyPaasClientDOList) {
        if (Objects.isNull(appkeyPaasClientDOList) || appkeyPaasClientDOList.isEmpty()) {
            return 0;
        }
        return mapper.insertList(appkeyPaasClientDOList);
    }

    @Override
    public boolean update(AppkeyPaasClientDO appkeyPaasClientDO) {
        if (Objects.isNull(appkeyPaasClientDO) || Objects.isNull(appkeyPaasClientDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(appkeyPaasClientDO) == 1;
    }

    @Override
    public boolean delete(int id) {
        return mapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 构建 Example
     * @param request 请求对象
     * @return
     */
    private Example buildExample(AppkeyPaasClientRequest request) {
        Example example = new Example(AppkeyPaasClientDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(request.getClientAppkey())) {
            criteria.andEqualTo("clientAppkey", request.getClientAppkey());
        }
        if (StringUtils.isNotEmpty(request.getPaasName())) {
            criteria.andEqualTo("paasName", request.getPaasName());
        }
        if (StringUtils.isNotEmpty(request.getLanguage())) {
            criteria.andEqualTo("language", request.getLanguage());
        }
        if (!Objects.isNull(request.getIsCapacityStandard())) {
            criteria.andEqualTo("isCapacityStandard", request.getIsCapacityStandard());
        }
        if (!Objects.isNull(request.getGroupId())) {
            criteria.andEqualTo("groupId", request.getGroupId());
        }
        if (!Objects.isNull(request.getArtifactId())) {
            criteria.andEqualTo("artifactId", request.getArtifactId());
        }
        if (!Objects.isNull(request.getUpdateDate())) {
            criteria.andEqualTo("updateDate", request.getUpdateDate());
        }
        return example;
    }
}
