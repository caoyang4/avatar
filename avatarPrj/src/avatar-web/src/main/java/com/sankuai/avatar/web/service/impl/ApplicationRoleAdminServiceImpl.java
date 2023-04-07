package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.ApplicationRoleAdminResource;
import com.sankuai.avatar.resource.application.bo.ApplicationRoleAdminBO;
import com.sankuai.avatar.resource.application.request.ApplicationRoleAdminRequestBO;
import com.sankuai.avatar.web.dto.application.ApplicationRoleAdminDTO;
import com.sankuai.avatar.web.request.ApplicationRoleAdminPageRequest;
import com.sankuai.avatar.web.service.ApplicationRoleAdminService;
import com.sankuai.avatar.web.transfer.application.ApplicationPageRequestTransfer;
import com.sankuai.avatar.web.transfer.application.ApplicationRoleAdminDTOTransfer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-01-16 19:00
 */
@Service
public class ApplicationRoleAdminServiceImpl implements ApplicationRoleAdminService {

    private final ApplicationRoleAdminResource resource;

    public ApplicationRoleAdminServiceImpl(ApplicationRoleAdminResource resource) {
        this.resource = resource;
    }

    @Override
    public PageResponse<ApplicationRoleAdminDTO> getPageApplicationRoleAdmin(ApplicationRoleAdminPageRequest pageRequest) {
        ApplicationRoleAdminRequestBO requestBO = ApplicationPageRequestTransfer.INSTANCE.toRequestBO(pageRequest);
        requestBO.setPage(pageRequest.getPage());
        requestBO.setPageSize(pageRequest.getPageSize());
        PageResponse<ApplicationRoleAdminBO> boPageResponse = resource.queryPage(requestBO);
        PageResponse<ApplicationRoleAdminDTO> dtoPageResponse = new PageResponse<>();
        dtoPageResponse.setPage(pageRequest.getPage());
        dtoPageResponse.setPageSize(pageRequest.getPageSize());
        dtoPageResponse.setTotalCount(boPageResponse.getTotalCount());
        dtoPageResponse.setTotalPage(boPageResponse.getTotalPage());
        dtoPageResponse.setItems(ApplicationRoleAdminDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
        return dtoPageResponse;
    }

    @Override
    public ApplicationRoleAdminDTO getByApplicationId(int applicationId) {
        ApplicationRoleAdminRequestBO requestBO = new ApplicationRoleAdminRequestBO();
        requestBO.setApplicationId(applicationId);
        List<ApplicationRoleAdminBO> boList = resource.query(requestBO);
        if (CollectionUtils.isNotEmpty(boList)) {
            return ApplicationRoleAdminDTOTransfer.INSTANCE.toDTO(boList.get(0));
        }
        return null;
    }

    @Override
    public ApplicationRoleAdminDTO getByApplicationName(String applicationName) {
        ApplicationRoleAdminRequestBO requestBO = new ApplicationRoleAdminRequestBO();
        requestBO.setApplicationName(applicationName);
        List<ApplicationRoleAdminBO> boList = resource.query(requestBO);
        if (CollectionUtils.isNotEmpty(boList)) {
            return ApplicationRoleAdminDTOTransfer.INSTANCE.toDTO(boList.get(0));
        }
        return null;
    }

    @Override
    public Boolean deleteApplicationRoleAdminByPk(int pk) {
        ApplicationRoleAdminRequestBO requestBO = new ApplicationRoleAdminRequestBO();
        requestBO.setId(pk);
        return resource.deleteByCondition(requestBO);
    }

    @Override
    public Boolean saveApplicationRoleAdmin(ApplicationRoleAdminDTO dto) {
        if (Objects.isNull(dto)) {
            return false;
        }
        return resource.save(ApplicationRoleAdminDTOTransfer.INSTANCE.toBO(dto));
    }
}
