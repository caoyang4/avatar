package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacitySummaryDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasCapacityDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasClientDTO;
import com.sankuai.avatar.web.service.AppkeyPaasCapacityService;
import com.sankuai.avatar.web.transfer.capacity.AppkeyPaasCapacityDTOTransfer;
import com.sankuai.avatar.web.transfer.capacity.AppkeyPaasClientDTOTransfer;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityDetailVO;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-09-21 15:55
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/v2/avatar/appkeyPaasCapacity")
public class AppkeyPaasCapacityController {

    private final AppkeyPaasCapacityService appkeyPaasCapacityService;

    @Autowired
    public AppkeyPaasCapacityController(AppkeyPaasCapacityService appkeyPaasCapacityService){
        this.appkeyPaasCapacityService = appkeyPaasCapacityService;
    }

    /**
     * 业务依赖的 paas 容灾
     * @param appkey appkey
     * @return AppkeyPaasCapacityDetailVO
     */
    @GetMapping("/{appkey}")
    public AppkeyPaasCapacityDetailVO getAppkeyPaasCapacityDetail(@PathVariable("appkey") @Valid @NotBlank String appkey,
                                                                  @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                                  @Max (value = 500, message = "每页最大限制500条") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                                  @RequestParam(value = "paasNames", required = false, defaultValue = "") String paasNames) {
        if (Objects.isNull(page) || page < 1) {page = 1;}
        if (Objects.isNull(pageSize) || pageSize < 1) {pageSize = 10;}
        List<String> paasNameList = StringUtils.isEmpty(paasNames) ? Collections.emptyList() : Arrays.asList(paasNames.split(","));
        PageResponse<AppkeyPaasCapacityDTO> dtoPageResponse = appkeyPaasCapacityService.getPageAggregatedByAppkey(appkey, paasNameList,false, page, pageSize);
        if (Objects.isNull(dtoPageResponse) || CollectionUtils.isEmpty(dtoPageResponse.getItems())) {
            return new AppkeyPaasCapacityDetailVO();
        }
        return getAppkeyPaasCapacityDetailVO(appkey, false, dtoPageResponse);
    }

    /**
     * paas 自身的容灾信息
     * @param appkey appkey
     * @return AppkeyPaasCapacityDetailVO
     */
    @GetMapping("/self/{appkey}")
    public AppkeyPaasCapacityDetailVO getAppkeyPaasCapacitySelfDetail(@PathVariable("appkey") @Valid @NotBlank String appkey,
                                                                      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                                      @Max (value = 500, message = "每页最大限制500条") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                                      @RequestParam(value = "paasNames", required = false, defaultValue = "") String paasNames) {
        if (Objects.isNull(page) || page < 1) {page = 1;}
        if (Objects.isNull(pageSize) || pageSize < 1) {pageSize = 10;}
        List<String> paasNameList = StringUtils.isEmpty(paasNames) ? Collections.emptyList() : Arrays.asList(paasNames.split(","));
        PageResponse<AppkeyPaasCapacityDTO> dtoPageResponse = appkeyPaasCapacityService.getPageAggregatedByAppkey(appkey, paasNameList,true, page, pageSize);
        if (Objects.isNull(dtoPageResponse) || CollectionUtils.isEmpty(dtoPageResponse.getItems())) {
            return new AppkeyPaasCapacityDetailVO();
        }
        return getAppkeyPaasCapacityDetailVO(appkey,true, dtoPageResponse);
    }

    /**
     * appkeyPaasCapacityDTOList 扩充客户端，并转为 detailVO
     * @param appkey appkey
     * @param dtoPageResponse  dtoPageResponse
     * @return paas 容灾详细信息 AppkeyPaasCapacityDetailVO
     */
    private AppkeyPaasCapacityDetailVO getAppkeyPaasCapacityDetailVO(String appkey, Boolean isPaas, PageResponse<AppkeyPaasCapacityDTO> dtoPageResponse){
        AppkeyPaasCapacityDetailVO capacityDetailVO = new AppkeyPaasCapacityDetailVO();
        capacityDetailVO.setAppkey(appkey);
        capacityDetailVO.setPaasCapacityLevels(toVOPageResponse(dtoPageResponse));
        for (AppkeyPaasCapacityVO appkeyPaasCapacityVO : capacityDetailVO.getPaasCapacityLevels().getItems()) {
            List<AppkeyPaasClientDTO> appkeyPaasClientDTOList = appkeyPaasCapacityService.queryPaasClientByAppkey(
                    appkeyPaasCapacityVO.getPaasName(), appkey);
            if (!appkeyPaasClientDTOList.isEmpty()) {
                appkeyPaasCapacityVO.setClientVersion(AppkeyPaasClientDTOTransfer.INSTANCE.toVO(appkeyPaasClientDTOList.get(0)));
            }
        }
        AppkeyCapacitySummaryDTO summaryDTO = isPaas
                ? appkeyPaasCapacityService.getAppkeyPaasCapacitySummaryByPaasAppkey(appkey)
                : appkeyPaasCapacityService.getAppkeyPaasCapacitySummary(appkey);
        capacityDetailVO.setTotalCapacityLevel(summaryDTO.getCapacityLevel());
        capacityDetailVO.setTotalStandardCapacityLevel(summaryDTO.getStandardCapacityLevel());
        capacityDetailVO.setIsTotalStandard(summaryDTO.getIsCapacityStandard());
        String tips = "依赖PaaS容灾治理可参考：<a href='https://km.sankuai.com/page/1352518835' target='_blank'>业务PaaS组件容灾治理手册</a>，PaaS容灾标准可参考：<a href='https://km.sankuai.com/page/109136439' target='_blank'>PaaS容灾标准及达标要求</a>";
        String msg = summaryDTO.getStandardTips();
        if (StringUtils.isNotEmpty(msg)) {
            msg = msg + "，" + tips;
        } else {
            msg = tips;
        }
        capacityDetailVO.setStandardReason(msg);
        return capacityDetailVO;
    }

    /**
     * vo 分页对象
     *
     * @param dtoPageResponse dto
     * @return {@link PageResponse}<{@link AppkeyPaasCapacityVO}>
     */
    @NotNull
    private PageResponse<AppkeyPaasCapacityVO> toVOPageResponse(PageResponse<AppkeyPaasCapacityDTO> dtoPageResponse) {
        PageResponse<AppkeyPaasCapacityVO> pageResponse = new PageResponse<>();
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setItems(AppkeyPaasCapacityDTOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @GetMapping("/paasNames")
    public List<String> getPaasNamesByAppkey(@RequestParam(value = "appkey") String appkey,
                                             @RequestParam(value = "isSelf") Boolean isSelf){

        return appkeyPaasCapacityService.getPaasNamesByAppkey(appkey, isSelf);
    }
}
