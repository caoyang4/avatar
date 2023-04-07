package com.sankuai.avatar.resource.emergency.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.resource.repository.EmergencyResourceRepository;
import com.sankuai.avatar.dao.resource.repository.model.EmergencyResourceDO;
import com.sankuai.avatar.resource.emergency.EmergencyHostResource;
import com.sankuai.avatar.resource.emergency.bo.EmergencyResourceBO;
import com.sankuai.avatar.resource.emergency.request.EmergencyResourceRequestBO;
import com.sankuai.avatar.resource.emergency.transfer.EmergencyResourceRequestTransfer;
import com.sankuai.avatar.resource.emergency.transfer.EmergencyResourceTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-12-02 23:16
 */
@Slf4j
@Component
public class EmergencyHostResourceImpl implements EmergencyHostResource {

    private final EmergencyResourceRepository repository;

    @Autowired
    public EmergencyHostResourceImpl(EmergencyResourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageResponse<EmergencyResourceBO> queryPage(EmergencyResourceRequestBO bo) {
        PageResponse<EmergencyResourceBO> pageResponse = new PageResponse<>();
        int page = bo.getPage();
        int pageSize = bo.getPageSize();
        Page<EmergencyResourceDO> pageDO = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(EmergencyResourceRequestTransfer.INSTANCE.toEmergencyResourceRequest(bo))
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(pageDO.getPages());
        pageResponse.setTotalCount(pageDO.getTotal());
        pageResponse.setItems(EmergencyResourceTransfer.INSTANCE.toBOList(pageDO));
        return pageResponse;
    }

    @Override
    public Boolean save(EmergencyResourceBO bo) {
        if (Objects.isNull(bo)) {
            return false;
        }
        return repository.insert(EmergencyResourceTransfer.INSTANCE.toDO(bo));
    }

    @Override
    public Boolean deleteByCondition(EmergencyResourceRequestBO bo) {
        if (Objects.isNull(bo) || ObjectUtils.checkObjAllFieldsIsNull(bo)) {
            return false;
        }
        boolean success = true;
        List<EmergencyResourceDO> doList = repository.query(EmergencyResourceRequestTransfer.INSTANCE.toEmergencyResourceRequest(bo));
        for (EmergencyResourceDO resourceDO : doList) {
            if (!repository.delete(resourceDO.getId())) {
                success = false;
            }
        }
        return success;
    }
}
