package com.sankuai.avatar.resource.emergency.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.resource.repository.EmergencySreRepository;
import com.sankuai.avatar.dao.resource.repository.model.EmergencySreDO;
import com.sankuai.avatar.dao.resource.repository.request.EmergencySreRequest;
import com.sankuai.avatar.resource.emergency.EmergencySreResource;
import com.sankuai.avatar.resource.emergency.bo.EmergencySreBO;
import com.sankuai.avatar.resource.emergency.request.EmergencySreRequestBO;
import com.sankuai.avatar.resource.emergency.transfer.EmergencySreTransfer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-01-20 17:57
 */
@Component
public class EmergencySreResourceImpl implements EmergencySreResource {

    private final EmergencySreRepository repository;

    @Autowired
    public EmergencySreResourceImpl(EmergencySreRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageResponse<EmergencySreBO> queryPage(EmergencySreRequestBO requestBO) {
        PageResponse<EmergencySreBO> pageResponse = new PageResponse<>();
        int page = requestBO.getPage();
        int pageSize = requestBO.getPageSize();
        Page<EmergencySreDO> doPage = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(EmergencySreTransfer.INSTANCE.toRequestBO(requestBO))
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(doPage.getPages());
        pageResponse.setTotalCount(doPage.getTotal());
        pageResponse.setItems(EmergencySreTransfer.INSTANCE.toBOList(doPage));
        return pageResponse;
    }

    @Override
    public Boolean save(EmergencySreBO emergencySreBO) {
        if (Objects.isNull(emergencySreBO)) {
            return false;
        }
        EmergencySreDO emergencySreDO = EmergencySreTransfer.INSTANCE.toDO(emergencySreBO);
        List<EmergencySreDO> doList = repository.query(
                EmergencySreRequest.builder().appkey(emergencySreBO.getAppkey()).build()
        );
        if (CollectionUtils.isEmpty(doList)) {
            return repository.insert(emergencySreDO);
        } else {
            emergencySreDO.setId(doList.get(0).getId());
            return repository.update(emergencySreDO);
        }
    }

    @Override
    public Boolean deleteByCondition(EmergencySreRequestBO requestBO) {
        if (Objects.isNull(requestBO) || ObjectUtils.checkObjAllFieldsIsNull(requestBO)) {
            return false;
        }
        List<EmergencySreDO> doList = repository.query(EmergencySreTransfer.INSTANCE.toRequestBO(requestBO));
        boolean success = true;
        for (EmergencySreDO emergencySreDO : doList) {
            if (!repository.delete(emergencySreDO.getId())) {
                success = false;
            }
        }
        return success;
    }

}
