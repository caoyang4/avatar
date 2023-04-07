package com.sankuai.avatar.web.service.impl;

import com.dianping.cat.Cat;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.PageHelperUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.ActivityResource;
import com.sankuai.avatar.resource.activity.bo.ActivityResourceBO;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.sdk.entity.servicecatalog.AppKey;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.avatar.web.dto.activity.ActivityResourceDTO;
import com.sankuai.avatar.web.dto.activity.WindowPeriodResourceDTO;
import com.sankuai.avatar.web.request.ActivityResourcePageRequest;
import com.sankuai.avatar.web.service.ActivityResourceService;
import com.sankuai.avatar.web.service.WindowPeriodResourceService;
import com.sankuai.avatar.web.transfer.activity.ActivityResourceDTOTransfer;
import com.sankuai.avatar.web.vo.activity.RegionSum;
import com.sankuai.avatar.web.vo.activity.ResourceSumResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-03-08 16:16
 */
@Service
public class ActivityResourceServiceImpl implements ActivityResourceService {

    private final ActivityResource resource;
    private final AppkeyResource appkeyResource;
    private final WindowPeriodResourceService windowService;
    private final ServiceCatalogAppKey serviceCatalogAppKey;

    @Autowired
    public ActivityResourceServiceImpl(ActivityResource resource,
                                       AppkeyResource appkeyResource,
                                       WindowPeriodResourceService windowService,
                                       ServiceCatalogAppKey serviceCatalogAppKey) {
        this.resource = resource;
        this.appkeyResource = appkeyResource;
        this.windowService = windowService;
        this.serviceCatalogAppKey = serviceCatalogAppKey;
    }

    @Override
    public PageResponse<ActivityResourceDTO> getPageActivityResource(ActivityResourcePageRequest pageRequest) {
        PageResponse<ActivityResourceBO> boPageResponse = resource.queryPage(ActivityResourceDTOTransfer.INSTANCE.toRequestBO(pageRequest));
        return PageHelperUtils.convertPageResponse(boPageResponse, boList -> ActivityResourceDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
    }

    @Override
    public List<ActivityResourceDTO> getActivityResource(ActivityResourcePageRequest pageRequest) {
        List<ActivityResourceBO> boList = resource.query(ActivityResourceDTOTransfer.INSTANCE.toRequestBO(pageRequest));
        return ActivityResourceDTOTransfer.INSTANCE.toDTOList(boList);
    }

    @Override
    public ActivityResourceDTO getActivityResourceByPk(int pk) {
        ActivityResourcePageRequest pageRequest = new ActivityResourcePageRequest();
        pageRequest.setId(pk);
        List<ActivityResourceDTO> dtoList = getActivityResource(pageRequest);
        return CollectionUtils.isNotEmpty(dtoList) ? dtoList.get(0) : null;
    }

    @Override
    public List<ActivityResourceDTO> getActivityResourceByWindowId(Integer windowId) {
        ActivityResourcePageRequest pageRequest = new ActivityResourcePageRequest();
        // 若未指定窗口 id，取最新的窗口 id
        if (Objects.isNull(windowId)) {
            windowId = windowService.getMaxWindowId();
        }
        pageRequest.setWindowPeriodId(windowId);
        return getActivityResource(pageRequest);
    }

    @Override
    public Boolean save(ActivityResourceDTO resourceDTO) {
        ActivityResourceBO resourceBO = ActivityResourceDTOTransfer.INSTANCE.toBO(resourceDTO);
        resourceBO.setCount(resourceBO.getHostConfig().getCount());
        /*  TODO 代 appkey 表的 orgId 存储全路径后，替换
        AppkeyBO appkeyBO = appkeyResource.getByAppkey(resourceBO.getAppkey());
        if (Objects.nonNull(appkeyBO)) {
            resourceBO.setOrgId(appkeyBO.getOrgId());
            resourceBO.setOrgName(appkeyBO.getOrgName());
        }
         */
        try {
            AppKey appKeyInfo = serviceCatalogAppKey.getAppKey(resourceBO.getAppkey());
            resourceBO.setOrgId(appKeyInfo.getOrgIds());
            resourceBO.setOrgName(appKeyInfo.getTeam().getDisplayName());
        } catch (Exception e) {
            Cat.logError(e);
        }
        return resource.save(resourceBO);
    }

    @Override
    public Boolean delete(int id) {
        return resource.deleteByPk(id);
    }

    @Override
    public ResourceSumResult getActivityResourceSum(Integer windowPeriodId) {
        WindowPeriodResourceDTO hitWindowPeriod = windowService.getHitWindowPeriod(null);
        List<ActivityResourceDTO> dtoList = getActivityResourceByWindowId(windowPeriodId);
        int summaryShangHaiCpu = 0;
        int summaryShangHaiMem = 0;
        int summaryShangHaiDisk = 0;
        int summaryBeiJingCpu = 0;
        int summaryBeiJingMem = 0;
        int summaryBeiJingDisk = 0;
        for (ActivityResourceDTO activityResource : dtoList) {
            if (Objects.equals(activityResource.getHostConfig().getRegion(), "shanghai")){
                summaryShangHaiCpu += activityResource.getHostConfig().getCount() * activityResource.getHostConfig().getCpu();
                summaryShangHaiMem += activityResource.getHostConfig().getCount() * activityResource.getHostConfig().getMemory();
                summaryShangHaiDisk += activityResource.getHostConfig().getCount() * activityResource.getHostConfig().getDisk();
            }
            if (Objects.equals(activityResource.getHostConfig().getRegion(), "beijing")){
                summaryBeiJingCpu += activityResource.getHostConfig().getCount() * activityResource.getHostConfig().getCpu();
                summaryBeiJingMem += activityResource.getHostConfig().getCount() * activityResource.getHostConfig().getMemory();
                summaryBeiJingDisk += activityResource.getHostConfig().getCount() * activityResource.getHostConfig().getDisk();
            }
        }
        RegionSum shanghai = RegionSum.builder()
                .city("上海").cpu(summaryShangHaiCpu).memory(summaryShangHaiMem).disk(summaryShangHaiDisk)
                .build();
        RegionSum beijing = RegionSum.builder()
                .city("北京").cpu(summaryBeiJingCpu).memory(summaryBeiJingMem).disk(summaryBeiJingDisk)
                .build();
        return ResourceSumResult.builder()
                .name(Objects.nonNull(hitWindowPeriod) ? hitWindowPeriod.getName() : "")
                .description(toDescription(hitWindowPeriod))
                .data(Arrays.asList(shanghai, beijing))
                .build();

    }
    private String toDescription(WindowPeriodResourceDTO dto){
        if (Objects.isNull(dto)) {
            return "";
        }
        Date startTime = dto.getStartTime();
        Date endTime = dto.getEndTime();
        if (startTime != null && endTime != null) {
            String pattern = "yyyy-MM-dd";
            return String.format("%s【%s~%s】", dto.getName(), DateUtils.dateToString(startTime,pattern),  DateUtils.dateToString(endTime,pattern));
        }
        return "";
    }
}
