package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.whitelist.OwtSetWhitelistResource;
import com.sankuai.avatar.resource.whitelist.bo.OwtSetWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.request.OwtSetWhitelistRequestBO;
import com.sankuai.avatar.web.dto.whitelist.OwtSetWhitelistDTO;
import com.sankuai.avatar.web.request.OwtSetWhitelistPageRequest;
import com.sankuai.avatar.web.service.OwtSetWhitelistService;
import com.sankuai.avatar.web.transfer.whitelist.OwtSetWhitelistDTOTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-11-15 12:04
 */
@Slf4j
@Service
public class OwtSetWhitelistServiceImpl implements OwtSetWhitelistService {

    private final OwtSetWhitelistResource owtSetWhitelistResource;

    @Autowired
    public OwtSetWhitelistServiceImpl(OwtSetWhitelistResource owtSetWhitelistResource) {
        this.owtSetWhitelistResource = owtSetWhitelistResource;
    }

    private WhiteType toWhiteType(String app){
        if (StringUtils.isEmpty(app)) {
            return null;
        }
        return Arrays.stream(WhiteType.values()).filter(
                type -> Objects.equals(app, type.getWhiteType())).findFirst().orElse(null);
    }

    @Override
    public PageResponse<OwtSetWhitelistDTO> queryPage(OwtSetWhitelistPageRequest pageRequest) {
        OwtSetWhitelistRequestBO requestBO = OwtSetWhitelistRequestBO.builder()
                .id(pageRequest.getId()).app(toWhiteType(pageRequest.getApp()))
                .owt(pageRequest.getOwt()).setName(pageRequest.getSetName())
                .endTimeGtn(new Date()).build();
        requestBO.setPage(pageRequest.getPage());
        requestBO.setPageSize(pageRequest.getPageSize());
        PageResponse<OwtSetWhitelistBO> boPageResponse = owtSetWhitelistResource.queryPage(requestBO);
        PageResponse<OwtSetWhitelistDTO> pageResponse = new PageResponse<>();
        pageResponse.setPage(boPageResponse.getPage());
        pageResponse.setPageSize(boPageResponse.getPageSize());
        pageResponse.setTotalPage(boPageResponse.getTotalPage());
        pageResponse.setTotalCount(boPageResponse.getTotalCount());
        pageResponse.setItems(OwtSetWhitelistDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
        return pageResponse;
    }

    @Override
    public boolean saveOwtSetWhitelist(OwtSetWhitelistDTO owtSetWhitelistDTO) {
        if (Objects.isNull(owtSetWhitelistDTO)) {
            return false;
        }
        return owtSetWhitelistResource.save(OwtSetWhitelistDTOTransfer.INSTANCE.toBO(owtSetWhitelistDTO));
    }

    @Override
    public List<OwtSetWhitelistDTO> getOwtSetWhitelistByOwt(String owt) {
        List<OwtSetWhitelistBO> boList = owtSetWhitelistResource.query(OwtSetWhitelistRequestBO.builder()
                .owt(owt).endTimeGtn(new Date()).build());
        return OwtSetWhitelistDTOTransfer.INSTANCE.toDTOList(boList);
    }

    @Override
    public List<OwtSetWhitelistDTO> getCapacityWhitelistByOwtAndSet(String owt, String setName) {
        List<OwtSetWhitelistBO> boList = owtSetWhitelistResource.query(OwtSetWhitelistRequestBO.builder()
                .owt(owt).setName(setName).app(WhiteType.CAPACITY).endTimeGtn(new Date()).build());
        return OwtSetWhitelistDTOTransfer.INSTANCE.toDTOList(boList);
    }

    @Override
    public Boolean deletetOwtSetWhitelistByPk(int pk) {
        return owtSetWhitelistResource.deleteByCondition(OwtSetWhitelistRequestBO.builder().id(pk).build());
    }
}
