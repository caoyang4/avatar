package com.sankuai.avatar.resource.orgRole.impl;

import com.sankuai.avatar.dao.resource.repository.DxGroupRepository;
import com.sankuai.avatar.dao.resource.repository.model.DxGroupDO;
import com.sankuai.avatar.resource.orgRole.DxGroupResource;
import com.sankuai.avatar.resource.orgRole.bo.DxGroupBO;
import com.sankuai.avatar.resource.orgRole.transfer.DxGroupTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * DxGroupResource 实现类
 * @author caoyang
 * @create 2022-11-10 16:45
 */
@Slf4j
@Component
public class DxGroupResourceImpl implements DxGroupResource {

    private final DxGroupRepository repository;

    @Autowired
    public DxGroupResourceImpl(DxGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DxGroupBO> getAllDxGroup() {
        return DxGroupTransfer.INSTANCE.toBOList(repository.queryAllGroup());
    }

    @Override
    public List<DxGroupBO> getDxGroupByGroupIds(List<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return Collections.emptyList();
        }
        return DxGroupTransfer.INSTANCE.toBOList(repository.queryByGroupId(groupIds));
    }

    @Override
    public boolean save(DxGroupBO dxGroupBO) {
        List<DxGroupDO> doList = repository.queryByGroupId(Collections.singletonList(dxGroupBO.getGroupId()));
        DxGroupDO dxGroupDO = DxGroupTransfer.INSTANCE.toDO(dxGroupBO);
        if (CollectionUtils.isEmpty(doList)) {
            return repository.insert(dxGroupDO);
        } else {
            dxGroupDO.setId(doList.get(0).getId());
            return repository.update(dxGroupDO);
        }
    }

    @Override
    public boolean batchSave(List<DxGroupBO> dxGroupBOList) {
        boolean success = true;
        for (DxGroupBO dxGroupBO : dxGroupBOList) {
            if (!save(dxGroupBO)) {
                success = false;
            }
        }
        return success;
    }
}
