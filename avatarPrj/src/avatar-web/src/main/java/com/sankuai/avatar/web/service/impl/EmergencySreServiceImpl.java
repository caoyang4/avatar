package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.emergency.EmergencySreResource;
import com.sankuai.avatar.resource.emergency.bo.EmergencySreBO;
import com.sankuai.avatar.resource.emergency.request.EmergencySreRequestBO;
import com.sankuai.avatar.web.dto.emergency.EmergencySreDTO;
import com.sankuai.avatar.web.request.EmergencySrePageRequest;
import com.sankuai.avatar.web.service.EmergencySreService;
import com.sankuai.avatar.web.transfer.emergency.EmergencySreDTOTransfer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author caoyang
 * @create 2023-02-01 18:02
 */
@Service
public class EmergencySreServiceImpl implements EmergencySreService {

    private final EmergencySreResource resource;

    @Autowired
    public EmergencySreServiceImpl(EmergencySreResource resource) {
        this.resource = resource;
    }

    @Override
    public PageResponse<EmergencySreDTO> getPageEmergencySre(EmergencySrePageRequest pageRequest) {
        PageResponse<EmergencySreBO> boPageResponse = resource.queryPage(EmergencySreDTOTransfer.INSTANCE.toRequestBO(pageRequest));
        PageResponse<EmergencySreDTO> pageResponse = new PageResponse<>();
        pageResponse.setPage(pageRequest.getPage());
        pageResponse.setPageSize(pageRequest.getPageSize());
        pageResponse.setTotalPage(boPageResponse.getTotalPage());
        pageResponse.setTotalCount(boPageResponse.getTotalCount());
        pageResponse.setItems(EmergencySreDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
        return pageResponse;
    }

    @Override
    public EmergencySreDTO getEmergencySreByPk(int pk) {
        EmergencySreRequestBO requestBO = new EmergencySreRequestBO();
        requestBO.setId(pk);
        PageResponse<EmergencySreBO> response = resource.queryPage(requestBO);
        if (CollectionUtils.isNotEmpty(response.getItems())) {
            return EmergencySreDTOTransfer.INSTANCE.toDTO(response.getItems().get(0));
        }
        return null;
    }

    @Override
    public Boolean saveEmergencySre(EmergencySreDTO dto) {
        return resource.save(EmergencySreDTOTransfer.INSTANCE.toBO(dto));
    }

    @Override
    public Boolean deleteEmergencySreByPk(int pk) {
        EmergencySreRequestBO requestBO = new EmergencySreRequestBO();
        requestBO.setId(pk);
        return resource.deleteByCondition(requestBO);
    }
}
