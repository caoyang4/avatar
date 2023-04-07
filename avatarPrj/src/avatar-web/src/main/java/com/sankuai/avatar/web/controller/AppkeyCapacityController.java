package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.capacity.service.ICapacityCollectService;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacityDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacitySummaryDTO;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.service.AppkeyCapacityService;
import com.sankuai.avatar.web.service.AppkeyPaasCapacityService;
import com.sankuai.avatar.web.service.WhitelistService;
import com.sankuai.avatar.web.transfer.capacity.AppkeyCapacityVOTransfer;
import com.sankuai.avatar.web.vo.capacity.AppKeyCapacityDetailVO;
import com.sankuai.avatar.web.vo.capacity.AppkeyCapacitySummaryVO;
import com.sankuai.avatar.web.vo.capacity.AppkeySetCapacityVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

/**
 * 容灾等级
 *
 * @author chenxinli
 */
@Slf4j
@RestController
@RequestMapping("/api/v2/avatar/capacity")
public class AppkeyCapacityController {

    private final ICapacityCollectService capacityCollect;
    private final AppkeyCapacityService appkeyCapacityService;
    private final AppkeyPaasCapacityService appkeyPaasCapacityService;
    private final WhitelistService whitelistService;

    @Autowired
    public AppkeyCapacityController(ICapacityCollectService capacityCollect,
                                    AppkeyCapacityService appkeyCapacityService,
                                    AppkeyPaasCapacityService appkeyPaasCapacityService,
                                    WhitelistService whitelistService) {
        this.capacityCollect = capacityCollect;
        this.appkeyCapacityService = appkeyCapacityService;
        this.appkeyPaasCapacityService = appkeyPaasCapacityService;
        this.whitelistService =  whitelistService;
    }

    @GetMapping("/isPaas/{appkey}")
    public boolean isPaas(@PathVariable("appkey") @Valid @NotBlank String appKey){
        return capacityCollect.isPaas(appKey);
    }

    @GetMapping("/{appkey}/summary")
    public AppkeyCapacitySummaryVO getAppkeyPaasCapacitySummary(@PathVariable("appkey") @Valid @NotBlank String appkey) {
        AppkeyCapacitySummaryVO capacitySummaryVO = new AppkeyCapacitySummaryVO();
        boolean isPaas = capacityCollect.isPaas(appkey);
        AppkeyCapacitySummaryDTO capacitySummary;
        if (isPaas) {
            capacitySummary = appkeyPaasCapacityService.getAppkeyPaasCapacitySummaryByPaasAppkey(appkey);
        } else {
            capacitySummary = appkeyCapacityService.getAppkeyCapacitySummary(appkey);
        }
        capacitySummaryVO.setCapacityLevel(capacitySummary.getCapacityLevel());
        capacitySummaryVO.setStandardCapacityLevel(capacitySummary.getStandardCapacityLevel());
        capacitySummaryVO.setIsStandard(capacitySummary.getIsCapacityStandard());
        capacitySummaryVO.setStandardTips(capacitySummary.getStandardTips());
        AppkeyCapacitySummaryDTO paasCapacitySummary = appkeyPaasCapacityService.getAppkeyPaasCapacitySummary(appkey);
        capacitySummaryVO.setPaasCapacityLevel(paasCapacitySummary.getCapacityLevel());
        capacitySummaryVO.setPaasStandardCapacityLevel(paasCapacitySummary.getStandardCapacityLevel());
        capacitySummaryVO.setIsPaasStandard(paasCapacitySummary.getIsCapacityStandard());
        capacitySummaryVO.setIsPaas(isPaas);
        return capacitySummaryVO;
    }

    @GetMapping("/{appkey}")
    public AppKeyCapacityDetailVO getAppKeyCapacityDetail(@PathVariable("appkey") String appkey){
        List<AppkeyCapacityDTO> dtoList = appkeyCapacityService.getAppkeyCapacityByAppkey(appkey);
        if (CollectionUtils.isEmpty(dtoList)) {return null;}
        AppKeyCapacityDetailVO detailVO = new AppKeyCapacityDetailVO();
        List<AppkeySetCapacityVO> setCapacityVOList = AppkeyCapacityVOTransfer.INSTANCE.toAppkeySetCapacityVOList(dtoList);
        detailVO.setCapacityLevels(setCapacityVOList);
        for (AppkeySetCapacityVO capacityVO : setCapacityVOList) {
            if (Objects.isNull(capacityVO.getExpireTime())) {continue;}
            List<ServiceWhitelistDTO> whitelist = whitelistService.getServiceWhitelistByAppkey(appkey, WhiteType.CAPACITY);
            if (CollectionUtils.isNotEmpty(whitelist)) {
                capacityVO.setWhiteStartTime(whitelist.get(0).getAddTime());
            }
        }
        detailVO.setHasSet(dtoList.size() > 1);
        // 计算整体容灾：
        // 无加白或全部加白所有整体取小；有加白剔除加白，然后所有未加白的整体取小
        setCapacityVOList.sort((v1, v2) -> {
            if (Objects.equals(-1, v1.getCapacityLevel()) || Objects.equals(-1, v2.getCapacityLevel())) {
                return Objects.equals(-1, v1.getCapacityLevel()) ? 1 : -1;
            } else if (!v1.getIsWhite().equals(v2.getIsWhite())) {
                return Boolean.TRUE.equals(v1.getIsWhite()) ? 1 : -1;
            } else if (!v1.getIsCapacityStandard().equals(v2.getIsCapacityStandard())) {
                return Boolean.TRUE.equals(v1.getIsCapacityStandard()) ? 1 : -1;
            } else {
                return v1.getCapacityLevel() - v2.getCapacityLevel();
            }
        });
        // 判断加白是否可续期：生效时间长度小于3天可续期
        AppkeySetCapacityVO vo = setCapacityVOList.stream().filter(setCapacity ->
                        Boolean.TRUE.equals(setCapacity.getIsWhite())
                        && Objects.nonNull(setCapacity.getExpireTime())
                        && (int) ((setCapacity.getExpireTime().getTime() - System.currentTimeMillis())/(1000*3600*24)) < 3
                ).findFirst().orElse(null);
        detailVO.setCanExpireApply(Objects.nonNull(vo));
        AppkeySetCapacityVO minSetCapacity = setCapacityVOList.get(0);
        detailVO.setDefaultCapacityLevel(minSetCapacity.getCapacityLevel());
        detailVO.setStandardCapacityLevel(minSetCapacity.getStandardLevel());
        detailVO.setIsStandard(minSetCapacity.getIsCapacityStandard());
        String tips = "业务容灾治理可参考：<a href='https://km.sankuai.com/page/114022614' target='_blank'>应用服务容灾等级定义与达标要求</a>，每天4:00、9:00、13:00、17:00、21:00 发起容灾计算，持续时间大概一个小时";
        detailVO.setStandardReason(tips);
        detailVO.setStandardTips(minSetCapacity.getStandardTips());
        detailVO.setCapacityWhiteList(AppkeyCapacityVOTransfer.INSTANCE.toAppkeyCapaictyWhiteVOList(dtoList));
        return detailVO;
    }
}
