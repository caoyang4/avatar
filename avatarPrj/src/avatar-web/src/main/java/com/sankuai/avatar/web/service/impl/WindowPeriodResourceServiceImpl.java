package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.common.utils.PageHelperUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.ActivityResource;
import com.sankuai.avatar.resource.activity.WindowPeriodResource;
import com.sankuai.avatar.resource.activity.bo.ActivityResourceBO;
import com.sankuai.avatar.resource.activity.bo.WindowPeriodResourceBO;
import com.sankuai.avatar.resource.activity.request.ActivityResourceRequestBO;
import com.sankuai.avatar.resource.activity.request.WindowPeriodRequestBO;
import com.sankuai.avatar.web.dto.activity.WindowPeriodResourceDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.request.WindowPeriodPageRequest;
import com.sankuai.avatar.web.service.WindowPeriodResourceService;
import com.sankuai.avatar.web.transfer.activity.WindowPeriodResourceDTOTransfer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-03-15 19:21
 */
@Service
public class WindowPeriodResourceServiceImpl implements WindowPeriodResourceService {

    private final WindowPeriodResource periodResource;

    private final ActivityResource activityResource;

    @Autowired
    public WindowPeriodResourceServiceImpl(WindowPeriodResource periodResource,
                                           ActivityResource activityResource) {
        this.periodResource = periodResource;
        this.activityResource = activityResource;
    }

    @Override
    public PageResponse<WindowPeriodResourceDTO> queryPage(WindowPeriodPageRequest pageRequest) {
        PageResponse<WindowPeriodResourceBO> boPageResponse = periodResource.queryPage(WindowPeriodResourceDTOTransfer.INSTANCE.toRequestBO(pageRequest));
        PageResponse<WindowPeriodResourceDTO> dtoPageResponse = PageHelperUtils.convertPageResponse(boPageResponse,
                boList -> WindowPeriodResourceDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
        // 补齐窗口期是否有订单信息
        for (WindowPeriodResourceDTO item : dtoPageResponse.getItems()) {
            item.setHasOrder(existWindowOrder(item.getId()));
        }
        return dtoPageResponse;
    }

    @Override
    public WindowPeriodResourceDTO getWindowPeriodByPk(int id) {
        WindowPeriodRequestBO requestBO = new WindowPeriodRequestBO();
        requestBO.setId(id);
        List<WindowPeriodResourceBO> boList = periodResource.query(requestBO);
        if (CollectionUtils.isEmpty(boList)) {
            return null;
        }
        WindowPeriodResourceDTO resourceDTO = WindowPeriodResourceDTOTransfer.INSTANCE.toDTO(boList.get(0));
        resourceDTO.setHasOrder(existWindowOrder(resourceDTO.getId()));
        return resourceDTO;
    }

    @Override
    public Integer getMaxWindowId() {
        return periodResource.getMaxWindowId();
    }

    @Override
    public Boolean save(WindowPeriodResourceDTO resourceDTO) {
        // 时间判断
        if (resourceDTO.getStartTime().after(resourceDTO.getEndTime())) {
            throw new SupportErrorException("窗口期的截止时间需要晚于开始时间!");
        }
        // 修改窗口期
        if (Objects.nonNull(resourceDTO.getId())) {
            Date thisTime = new Date();
            WindowPeriodResourceDTO existedPeriod = getWindowPeriodByPk(resourceDTO.getId());
            // 窗口期已截止
            if (thisTime.after(existedPeriod.getEndTime())) {
                throw new SupportErrorException("窗口期已截止，不允许修改窗口期!");
            }
            // 窗口期已开始未截止，且存在订单
            if (thisTime.after(existedPeriod.getStartTime()) && Boolean.TRUE.equals(existedPeriod.getHasOrder())) {
                ActivityResourceRequestBO requestBO = new ActivityResourceRequestBO();
                requestBO.setWindowPeriodId(resourceDTO.getId());
                List<ActivityResourceBO> activityList = activityResource.query(requestBO);
                if (resourceDTO.getStartTime().after(activityList.get(0).getStartTime())) {
                    throw new SupportErrorException("窗口期的开始时间不可晚于此窗口期的首订单开始时间!");
                }
            }

        }
        return periodResource.save(WindowPeriodResourceDTOTransfer.INSTANCE.toBO(resourceDTO));
    }

    @Override
    public Boolean deleteByPk(int pk) {
        WindowPeriodResourceDTO resourceDTO = getWindowPeriodByPk(pk);
        if (Objects.nonNull(resourceDTO) && Boolean.TRUE.equals(resourceDTO.getHasOrder())) {
            Date thisTime = new Date();
            if (thisTime.after(resourceDTO.getEndTime())) {
                throw new SupportErrorException("窗口期已截止，并且存在订单，无法删除!");
            }
            if (thisTime.after(resourceDTO.getStartTime())) {
                throw new SupportErrorException("您无法删除窗口期，但可关闭当前窗口期!");
            }
        }
        return periodResource.deleteByPk(pk);
    }

    @Override
    public WindowPeriodResourceDTO getHitWindowPeriod(Integer pk) {
        WindowPeriodRequestBO requestBO = new WindowPeriodRequestBO();
        requestBO.setId(pk);
        List<WindowPeriodResourceBO> boList = periodResource.query(requestBO);
        if (CollectionUtils.isEmpty(boList)) {
            throw new SupportErrorException("窗口期不存在！");
        }
        List<WindowPeriodResourceDTO> dtoList = WindowPeriodResourceDTOTransfer.INSTANCE.toDTOList(boList);
        return dtoList.stream().filter(this::isHitPeriod).findFirst().orElse(null);
    }

    /**
     * 是否命中窗口期
     *
     * @param dto dto
     * @return boolean
     */
    private boolean isHitPeriod(WindowPeriodResourceDTO dto){
        Date startTime = dto.getStartTime();
        Date endTime = dto.getEndTime();
        Date date = new Date();
        if (startTime != null && endTime != null) {
            return date.after(startTime) && date.before(endTime);
        }
        return false;
    }

    /**
     * 窗口期是否有订单
     *
     * @param windowId 窗口id
     * @return {@link Boolean}
     */
    private Boolean existWindowOrder(int windowId) {
        ActivityResourceRequestBO requestBO = new ActivityResourceRequestBO();
        requestBO.setWindowPeriodId(windowId);
        return CollectionUtils.isNotEmpty(activityResource.query(requestBO));
    }


}
