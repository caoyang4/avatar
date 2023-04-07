package com.sankuai.avatar.dao.workflow.repository.impl;

import com.sankuai.avatar.dao.workflow.repository.PermissionRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.PermissionEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.PermissionDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.PermissionDO;
import com.sankuai.avatar.dao.workflow.repository.request.PermissionRequest;
import com.sankuai.avatar.dao.workflow.repository.transfer.PermissionTransfer;
import tk.mybatis.mapper.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author zhaozhifan02
 */
@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    private final PermissionDOMapper mapper;

    @Autowired
    public PermissionRepositoryImpl(PermissionDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<PermissionEntity> query(PermissionRequest request) {
        if (request == null) {
            return Collections.emptyList();
        }
        Example example = new Example(PermissionDO.class);
        example.orderBy("id").desc();

        Example.Criteria criteria = example.createCriteria();
        if (request.getTemplateName() != null) {
            criteria.andEqualTo("templateName", request.getTemplateName());
        }
        if (request.getRole() != null) {
            criteria.andEqualTo("role", request.getRole());
        }
        if (request.getIsApply() != null) {
            criteria.andEqualTo("isApply", request.getIsApply());
        }
        List<PermissionDO> permissionDOList = mapper.selectByExample(example);
        return PermissionTransfer.INSTANCE.toEntityList(permissionDOList);
    }
}
