package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.AppkeyBO;
import com.sankuai.avatar.resource.capacity.AppkeyCapacityResource;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacityBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyCapacityRequestBO;
import com.sankuai.avatar.resource.dxMessage.DxMessageResource;
import com.sankuai.avatar.resource.whitelist.ServiceWhitelistResource;
import com.sankuai.avatar.resource.whitelist.WhitelistAppResource;
import com.sankuai.avatar.resource.whitelist.bo.ServiceWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.request.ServiceWhitelistRequestBO;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.dto.whitelist.WhitelistAppDTO;
import com.sankuai.avatar.web.request.WhitelistPageRequest;
import com.sankuai.avatar.web.service.WhitelistService;
import com.sankuai.avatar.web.transfer.whitelist.ServiceWhitelistDTOTransfer;
import com.sankuai.avatar.web.transfer.whitelist.WhitelistAppDTOTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2022-11-15 12:05
 */
@Slf4j
@Service
public class WhitelistServiceImpl implements WhitelistService {

    private final ServiceWhitelistResource serviceWhitelistResource;
    private final WhitelistAppResource whitelistAppResource;
    private final AppkeyCapacityResource capacityResource;
    private final DxMessageResource dxMessageResource;
    private final AppkeyResource appkeyResource;

    @Autowired
    public WhitelistServiceImpl(ServiceWhitelistResource serviceWhitelistResource,
                                WhitelistAppResource whitelistAppResource,
                                AppkeyCapacityResource capacityResource,
                                DxMessageResource dxMessageResource,
                                AppkeyResource appkeyResource) {
        this.serviceWhitelistResource = serviceWhitelistResource;
        this.whitelistAppResource = whitelistAppResource;
        this.capacityResource = capacityResource;
        this.dxMessageResource= dxMessageResource;
        this.appkeyResource= appkeyResource;
    }

    private WhiteType toWhiteType(String app){
        if (StringUtils.isEmpty(app)) {
            return null;
        }
        return Arrays.stream(WhiteType.values()).filter(
                type -> Objects.equals(app, type.getWhiteType())).findFirst().orElse(null);
    }

    @Override
    public PageResponse<ServiceWhitelistDTO> queryPage(WhitelistPageRequest pageRequest) {
        ServiceWhitelistRequestBO requestBO = ServiceWhitelistRequestBO.builder()
                .id(pageRequest.getId())
                .app(toWhiteType(pageRequest.getApp())).query(pageRequest.getQuery())
                .appkeys(pageRequest.getAppkeys()).application(pageRequest.getApplication())
                .orgIds(pageRequest.getOrgIds()).endTimeGtn(new Date())
                .build();
        requestBO.setPage(pageRequest.getPage());
        requestBO.setPageSize(pageRequest.getPageSize());
        PageResponse<ServiceWhitelistBO> boPageResponse = serviceWhitelistResource.queryPage(requestBO);
        PageResponse<ServiceWhitelistDTO> pageResponse = new PageResponse<>();
        pageResponse.setPage(boPageResponse.getPage());
        pageResponse.setPageSize(boPageResponse.getPageSize());
        pageResponse.setTotalPage(boPageResponse.getTotalPage());
        pageResponse.setTotalCount(boPageResponse.getTotalCount());
        pageResponse.setItems(ServiceWhitelistDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
        return pageResponse;
    }

    @Override
    public List<WhitelistAppDTO> getAllWhitelistType() {
        return WhitelistAppDTOTransfer.INSTANCE.toDTOList(whitelistAppResource.getAllWhitelistApp());
    }

    @Override
    public List<ServiceWhitelistDTO> getServiceWhitelistByAppkey(String appkey) {
        List<ServiceWhitelistBO> boList = serviceWhitelistResource.query(ServiceWhitelistRequestBO.builder()
                                            .appkeys(Collections.singletonList(appkey)).endTimeGtn(new Date()).build());
        return ServiceWhitelistDTOTransfer.INSTANCE.toDTOList(boList);
    }

    @Override
    public List<ServiceWhitelistDTO> getServiceWhitelistByAppkey(String appkey, WhiteType whiteType) {
        List<ServiceWhitelistBO> boList = serviceWhitelistResource.query(ServiceWhitelistRequestBO.builder()
                .appkeys(Collections.singletonList(appkey)).app(whiteType).endTimeGtn(new Date()).build());
        return ServiceWhitelistDTOTransfer.INSTANCE.toDTOList(boList);
    }

    @Override
    public Boolean saveServiceWhitelist(ServiceWhitelistDTO serviceWhitelistDTO) {
        if (Objects.isNull(serviceWhitelistDTO)) {
            return false;
        }
        return serviceWhitelistResource.save(ServiceWhitelistDTOTransfer.INSTANCE.toBO(serviceWhitelistDTO));
    }

    @Override
    public Boolean deleteWhitelistByPk(int pk) {
        return serviceWhitelistResource.deleteByCondition(ServiceWhitelistRequestBO.builder().id(pk).build());
    }

    @Override
    public Boolean deleteWhitelistByAppkey(String appkey) {
        if (StringUtils.isEmpty(appkey)) {
            return false;
        }
        return serviceWhitelistResource.deleteByCondition(ServiceWhitelistRequestBO.builder()
                .appkeys(Collections.singletonList(appkey)).build());
    }

    @Override
    public Boolean deleteWhitelistByAppkeyAndType(String appkey, WhiteType type) {
        if (StringUtils.isEmpty(appkey)) {
            return false;
        }
        return serviceWhitelistResource.deleteByCondition(ServiceWhitelistRequestBO.builder()
                .appkeys(Collections.singletonList(appkey)).app(type).build());
    }

    @Override
    public Boolean cancelWhitelist(String appkey, WhiteType type, List<String> cellNames) {
        // 未传 set 信息，或白名单类型不为容灾白名单
        if (CollectionUtils.isEmpty(cellNames) || !WhiteType.CAPACITY.equals(type)) {
            return deleteWhitelistByAppkeyAndType(appkey, type);
        } else {
            List<ServiceWhitelistDTO> dtoList = getServiceWhitelistByAppkey(appkey, type);
            if (CollectionUtils.isEmpty(dtoList)) {return false;}
            ServiceWhitelistDTO serviceWhitelistDTO = dtoList.get(0);
            String setName = serviceWhitelistDTO.getSetName();
            Set<String> whiteSet = new HashSet<>();
            if (StringUtils.isNotEmpty(setName)) {
                whiteSet.addAll(Arrays.asList(setName.split(",")));
            } else {
                List<AppkeyCapacityBO> capacityBOList = capacityResource.query(AppkeyCapacityRequestBO.builder().appkey(appkey).build());
                whiteSet.addAll(capacityBOList.stream().map(AppkeyCapacityBO::getSetName).filter(StringUtils::isNotEmpty).collect(Collectors.toSet()));
                whiteSet.add("default");
            }
            Set<String> cancelSet = new HashSet<>(cellNames);
            whiteSet.removeAll(cancelSet);
            if (CollectionUtils.isEmpty(whiteSet)) {
                return deleteWhitelistByAppkeyAndType(appkey, type);
            } else {
                String newWhiteSet = String.join(",", whiteSet);
                serviceWhitelistDTO.setSetName(newWhiteSet);
                return saveServiceWhitelist(serviceWhitelistDTO);
            }
        }
    }

    @Override
    public void sendCancelWhiteNotice(String user, String appkey, WhiteType type, List<String> cellNames, ServiceWhitelistDTO whitelistDTO) {
        String body;
        if (!WhiteType.CAPACITY.equals(type)) {
            body = String.format("您的服务%s已从：%s服务白名单中移除，如有疑问请咨询%s", appkey, type.getCname(), user);
        } else {
            String detail;
            if (CollectionUtils.isEmpty(cellNames)) {
                detail = String.format("您所负责的%s容灾等级已取消白名单", appkey);
            } else {
                Set<String> cells = cellNames.stream().map(cell -> "default".equals(cell) ? "主干道" : cell).collect(Collectors.toSet());
                detail = String.format("您所负责的%s的链路：%s，容灾等级已取消白名单", appkey, String.join(",", cells));
            }
            String org = String.format("https://avatar.mws.sankuai.com/#/owt/operation?ids=%s", whitelistDTO.getOrgIds());
            String appkeyLink = String.format("https://avatar.mws.sankuai.com/#/service/detail/info?appkey=%s&env=prod", appkey);
            body = String.format("【Avatar 服务容灾白名单通知】\n【内容】%s\n【生效时间】-\n【部门链接】[前往查看|%s]\n【服务详情】[前往查看|%s]\n【操作说明】[白名单说明|https://km.sankuai.com/page/1274227259]", detail, org, appkeyLink);
        }
        AppkeyBO appkeyBO = appkeyResource.getByAppkey(appkey);
        try {
            if (Objects.nonNull(appkeyBO) && StringUtils.isNotEmpty(appkeyBO.getRdAdmin())) {
                dxMessageResource.pushDxMessage(Arrays.asList(appkeyBO.getRdAdmin().split(",")), body);
            }
        } catch (SdkBusinessErrorException ignored) {
        }
    }
}
