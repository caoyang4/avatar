package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.emergency.EmergencyHostResource;
import com.sankuai.avatar.resource.emergency.bo.EmergencyResourceBO;
import com.sankuai.avatar.resource.emergency.request.EmergencyResourceRequestBO;
import com.sankuai.avatar.web.dto.emergency.EmergencyResourceDTO;
import com.sankuai.avatar.web.request.EmergencyResourcePageRequest;
import com.sankuai.avatar.web.service.EmergencyService;
import com.sankuai.avatar.web.transfer.emergency.EmergencyResourceDTOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author caoyang
 * @create 2023-01-03 10:00
 */
@Service
public class EmergencyServiceImpl implements EmergencyService {

    private final EmergencyHostResource resource;

    @Autowired
    public EmergencyServiceImpl(EmergencyHostResource resource) {
        this.resource = resource;
    }

    @Override
    public PageResponse<EmergencyResourceDTO> queryPage(EmergencyResourcePageRequest pageRequest) {
        int page = pageRequest.getPage();
        int pageSize = pageRequest.getPageSize();
        EmergencyResourceRequestBO requestBO = EmergencyResourceRequestBO.builder()
                .env(pageRequest.getEnv()).appkey(pageRequest.getAppkey())
                .operationType(pageRequest.getOperationType())
                .flowUuid(pageRequest.getFlowUuid()).template(pageRequest.getTemplate())
                .endTimeGtn(pageRequest.getStartTime()).endTimeLtn(pageRequest.getEndTime())
                .build();
        requestBO.setPage(page);
        requestBO.setPageSize(pageSize);
        PageResponse<EmergencyResourceDTO> pageResponse = new PageResponse<>();
        PageResponse<EmergencyResourceBO> boPageResponse = resource.queryPage(requestBO);
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(boPageResponse.getTotalPage());
        pageResponse.setTotalCount(boPageResponse.getTotalCount());
        pageResponse.setItems(EmergencyResourceDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
        return pageResponse;
    }

    @Override
    public Boolean saveEmergencyResource(EmergencyResourceDTO dto) {
        return resource.save(EmergencyResourceDTOTransfer.INSTANCE.toBO(dto));
    }

    @Override
    public Boolean deleteEmergencyResourceByPk(int pk) {
        EmergencyResourceRequestBO requestBO = EmergencyResourceRequestBO.builder().id(pk).build();
        return resource.deleteByCondition(requestBO);
    }
}
