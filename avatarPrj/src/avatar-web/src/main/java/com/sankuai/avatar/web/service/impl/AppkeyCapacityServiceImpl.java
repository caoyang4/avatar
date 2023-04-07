package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.capacity.AppkeyCapacityResource;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacityBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyCapacityRequestBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacityDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacitySummaryDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacityWhiteDTO;
import com.sankuai.avatar.web.request.AppkeyCapacityPageRequest;
import com.sankuai.avatar.web.service.AppkeyCapacityService;
import com.sankuai.avatar.web.transfer.capacity.AppkeyCapacityDTOTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2022-09-21 16:22
 */
@Slf4j
@Service
public class AppkeyCapacityServiceImpl implements AppkeyCapacityService {

    private final AppkeyCapacityResource appkeyCapacityResource;

    @Autowired
    public AppkeyCapacityServiceImpl(AppkeyCapacityResource appkeyCapacityResource) {
        this.appkeyCapacityResource = appkeyCapacityResource;
    }

    @Override
    public PageResponse<AppkeyCapacityDTO> queryPage(AppkeyCapacityPageRequest pageRequest) {
        PageResponse<AppkeyCapacityDTO> pageResponse = new PageResponse<>();
        AppkeyCapacityRequestBO requestBO = AppkeyCapacityRequestBO.builder()
                .appkey(pageRequest.getAppkey())
                .setName(pageRequest.getSetName())
                .isCapacityStandard(pageRequest.getIsCapacityStandard())
                .org(pageRequest.getOrg())
                .isFullField(Boolean.TRUE.equals(pageRequest.getIsFullField()))
                .build();
        requestBO.setPage(pageRequest.getPage());
        requestBO.setPageSize(pageRequest.getPageSize());
        PageResponse<AppkeyCapacityBO> boPageResponse = appkeyCapacityResource.queryPage(requestBO);
        pageResponse.setPage(pageRequest.getPage());
        pageResponse.setPageSize(pageRequest.getPageSize());
        pageResponse.setTotalCount(boPageResponse.getTotalCount());
        pageResponse.setTotalPage(boPageResponse.getTotalPage());
        pageResponse.setItems(AppkeyCapacityDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
        return pageResponse;
    }

    @Override
    public List<AppkeyCapacityDTO> getAppkeyCapacityByAppkey(String appkey) {
        return AppkeyCapacityDTOTransfer.INSTANCE.toDTOList(
                appkeyCapacityResource.query(AppkeyCapacityRequestBO.builder().appkey(appkey).build())
        );
    }

    @Override
    public List<AppkeyCapacityDTO> getUnStandardAppkeyCapacity() {
        return AppkeyCapacityDTOTransfer.INSTANCE.toDTOList(
                appkeyCapacityResource.query(AppkeyCapacityRequestBO.builder().isCapacityStandard(false).build())
        );
    }

    @Override
    public boolean saveAppkeyCapacity(List<AppkeyCapacityDTO> appkeyCapacityDTOList) {
        if (Objects.isNull(appkeyCapacityDTOList)) {
            return false;
        }
        boolean success = true;
        for (AppkeyCapacityDTO appkeyCapacityDTO : appkeyCapacityDTOList) {
            if (!appkeyCapacityResource.save(AppkeyCapacityDTOTransfer.INSTANCE.toBO(appkeyCapacityDTO))) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public boolean clearAppkeyCapacityInvalidSet(String appkey, Set<String> setList) {
        if (Objects.isNull(setList)) {
            return false;
        }
        List<AppkeyCapacityBO> boList = appkeyCapacityResource.query(AppkeyCapacityRequestBO.builder().appkey(appkey).isFullField(false).build());
        Set<String> appkeySetNames = boList.stream().map(AppkeyCapacityBO::getSetName).filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
        appkeySetNames.removeAll(setList);
        boolean success = true;
        for (String setName : appkeySetNames) {
            if (StringUtils.isEmpty(setName)) { continue; }
            if (!appkeyCapacityResource.deleteByCondition(AppkeyCapacityRequestBO.builder().appkey(appkey).setName(setName).build())){
                success = false;
            } else {
                log.info("清理appkey: {},set:{}容灾信息成功", appkey, setName);
            }
        }
        return success;
    }

    @Override
    public boolean clearAppkeyCapacityByAppkey(String appkey) {
        if (StringUtils.isEmpty(appkey)) {
            return false;
        }
        return appkeyCapacityResource.deleteByCondition( AppkeyCapacityRequestBO.builder().appkey(appkey).build());
    }

    @Override
    public AppkeyCapacitySummaryDTO getAppkeyCapacitySummary(String appkey) {
        List<AppkeyCapacityDTO> appkeyCapacityDTOList = getAppkeyCapacityByAppkey(appkey);
        AppkeyCapacitySummaryDTO summaryDTO = new AppkeyCapacitySummaryDTO();
        if (CollectionUtils.isEmpty(appkeyCapacityDTOList)) {
            return summaryDTO;
        }
        appkeyCapacityDTOList.sort((v1, v2) -> {
            boolean v1White = isWhiteAppkeyCapacity(v1);
            boolean v2White = isWhiteAppkeyCapacity(v2);
            if (Objects.equals(-1, v1.getCapacityLevel()) || Objects.equals(-1, v2.getCapacityLevel())){
                return Objects.equals(-1, v1.getCapacityLevel()) ? 1 : -1;
            } else if (v1White != v2White) {
                return v1White ? 1 : -1;
            } else if (!v1.getIsCapacityStandard().equals(v2.getIsCapacityStandard())) {
                return Boolean.TRUE.equals(v1.getIsCapacityStandard()) ? 1 : -1;
            } else {
                return v1.getCapacityLevel() - v2.getCapacityLevel();
            }
        });
        AppkeyCapacityDTO minDTO = appkeyCapacityDTOList.get(0);
        summaryDTO.setCapacityLevel(minDTO.getCapacityLevel());
        summaryDTO.setStandardCapacityLevel(minDTO.getStandardLevel());
        summaryDTO.setIsCapacityStandard(minDTO.getIsCapacityStandard());
        boolean white = isWhiteAppkeyCapacity(minDTO);
        summaryDTO.setStandardTips(white
                ? "服务全链路容灾均已加白，免达标"
                : (Boolean.TRUE.equals(minDTO.getIsCapacityStandard())
                    ? "容灾已达标"
                    : (StringUtils.isNotEmpty(minDTO.getStandardTips()) ? minDTO.getStandardTips() : minDTO.getStandardReason())
                ));
        return summaryDTO;
    }

    /**
     * 容灾是否加白
     *
     * @param dto dto
     * @return boolean
     */
    private boolean isWhiteAppkeyCapacity(AppkeyCapacityDTO dto){
        if (CollectionUtils.isEmpty(dto.getWhiteList())) {
            return false;
        }
        AppkeyCapacityWhiteDTO whiteDTO = dto.getWhiteList().stream().filter(
                white -> WhiteType.CAPACITY.equals(white.getWhiteApp())).findFirst().orElse(null);
        return Objects.nonNull(whiteDTO);
    }

    @Override
    public Set<String> getAllCapacityAppkey() {
        List<AppkeyCapacityBO> boList = appkeyCapacityResource.query(AppkeyCapacityRequestBO.builder().onlyAppkey(true).build());
        return boList.stream().map(AppkeyCapacityBO::getAppkey).collect(Collectors.toSet());
    }

    @Override
    public Boolean updateOpsCapacity(String appkey, String reason, Integer capacityLevel) {
        return appkeyCapacityResource.updateOpsCapacity(appkey, reason, capacityLevel);
    }

    @Override
    public Boolean isExistAppkeySetCapacity(String appkey, String set) {
        if (StringUtils.isEmpty(set)) {
            set = "";
        }
        List<AppkeyCapacityBO> boList = appkeyCapacityResource.query(AppkeyCapacityRequestBO.builder().appkey(appkey).setName(set).build());
        return CollectionUtils.isNotEmpty(boList);
    }
}
