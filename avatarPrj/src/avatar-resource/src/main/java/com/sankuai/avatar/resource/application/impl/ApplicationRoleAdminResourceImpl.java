package com.sankuai.avatar.resource.application.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.ApplicationRoleAdminResource;
import com.sankuai.avatar.resource.application.bo.ApplicationRoleAdminBO;
import com.sankuai.avatar.resource.application.request.ApplicationRoleAdminRequestBO;
import com.sankuai.avatar.resource.application.transfer.ApplicationPageRequestTransfer;
import com.sankuai.avatar.resource.application.transfer.ApplicationRoleAdminTransfer;
import com.sankuai.avatar.dao.resource.repository.ApplicationRoleAdminRepository;
import com.sankuai.avatar.dao.resource.repository.model.ApplicationRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.ApplicationRoleAdminRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-01-16 17:48
 */
@Component
public class ApplicationRoleAdminResourceImpl implements ApplicationRoleAdminResource {

    private final ApplicationRoleAdminRepository repository;

    @Autowired
    public ApplicationRoleAdminResourceImpl(ApplicationRoleAdminRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageResponse<ApplicationRoleAdminBO> queryPage(ApplicationRoleAdminRequestBO requestBO) {
        ApplicationRoleAdminRequest request = ApplicationPageRequestTransfer.INSTANCE.toRequest(requestBO);
        int page = requestBO.getPage();
        int pageSize = requestBO.getPageSize();
        Page<ApplicationRoleAdminDO> doPage = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(request)
        );
        PageResponse<ApplicationRoleAdminBO> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(doPage.getPages());
        pageResponse.setTotalCount(doPage.getTotal());
        pageResponse.setItems(ApplicationRoleAdminTransfer.INSTANCE.toBOList(doPage));
        return pageResponse;
    }

    @Override
    public List<ApplicationRoleAdminBO> query(ApplicationRoleAdminRequestBO requestBO) {
        return ApplicationRoleAdminTransfer.INSTANCE.toBOList(repository.query(
                ApplicationPageRequestTransfer.INSTANCE.toRequest(requestBO)
        ));
    }

    @Override
    public Boolean save(ApplicationRoleAdminBO applicationRoleAdminBO) {
        if (Objects.isNull(applicationRoleAdminBO)) {
            return false;
        }
        ApplicationRoleAdminRequest request = ApplicationRoleAdminRequest.builder().applicationName(applicationRoleAdminBO.getApplicationName()).build();
        ApplicationRoleAdminDO applicationRoleAdminDO = ApplicationRoleAdminTransfer.INSTANCE.toDO(applicationRoleAdminBO);
        List<ApplicationRoleAdminDO> doList = repository.query(request);
        if (CollectionUtils.isEmpty(doList)) {
            return repository.insert(applicationRoleAdminDO);
        } else {
            applicationRoleAdminDO.setId(doList.get(0).getId());
            return repository.update(applicationRoleAdminDO);
        }
    }

    @Override
    public Boolean deleteByCondition(ApplicationRoleAdminRequestBO requestBO) {
        if (Objects.isNull(requestBO) || ObjectUtils.checkObjAllFieldsIsNull(requestBO)) {
            return false;
        }
        boolean success = true;
        for (ApplicationRoleAdminDO adminDO : repository.query(ApplicationPageRequestTransfer.INSTANCE.toRequest(requestBO))) {
            if (!repository.delete(adminDO.getId())) {
                success = false;
            }
        }
        return success;
    }
}
