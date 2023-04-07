package com.sankuai.avatar.web.service.impl;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.capacity.AppkeyPaasCapacityResource;
import com.sankuai.avatar.resource.capacity.AppkeyPaasClientResource;
import com.sankuai.avatar.resource.capacity.AppkeyPaasStandardClientResource;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacitySummaryBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasCapacityBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasClientBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasCapacityRequestBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasClientRequestBO;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacitySummaryDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasCapacityDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasCapacityReportDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasClientDTO;
import com.sankuai.avatar.web.exception.ValueValidException;
import com.sankuai.avatar.web.request.AppkeyPaasCapacityPageRequest;
import com.sankuai.avatar.web.service.AppkeyPaasCapacityService;
import com.sankuai.avatar.web.transfer.capacity.AppkeyPaasCapacityDTOTransfer;
import com.sankuai.avatar.web.transfer.capacity.AppkeyPaasClientDTOTransfer;
import com.sankuai.avatar.web.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-09-21 16:21
 */
@Slf4j
@Service
public class AppkeyPaasCapacityServiceImpl implements AppkeyPaasCapacityService {

    private final AppkeyPaasCapacityResource appkeyPaasCapacityResource;
    private final AppkeyPaasClientResource appkeyPaasClientResource;
    private final AppkeyPaasStandardClientResource appkeyPaasStandardClientResource;

    @Autowired
    public AppkeyPaasCapacityServiceImpl(AppkeyPaasCapacityResource appkeyPaasCapacityResource,
                                         AppkeyPaasClientResource appkeyPaasClientResource,
                                         AppkeyPaasStandardClientResource appkeyPaasStandardClientResource){
        this.appkeyPaasCapacityResource = appkeyPaasCapacityResource;
        this.appkeyPaasClientResource = appkeyPaasClientResource;
        this.appkeyPaasStandardClientResource = appkeyPaasStandardClientResource;
    }

    @Override
    public boolean reportAppkeyPaasCapacity(List<AppkeyPaasCapacityReportDTO> appkeyPaasCapacityReportDTOList) {
        boolean success = true;
        for (AppkeyPaasCapacityReportDTO paasCapacityReport : appkeyPaasCapacityReportDTOList) {
            if (Objects.isNull(paasCapacityReport)) {continue;}
            final Transaction t = Cat.newTransaction("PaaSCapacityReport", paasCapacityReport.getPaasName());
            try {
                if (CollectionUtils.isNotEmpty(paasCapacityReport.getAppkeyPaasCapacityDTOList())) {
                    AppkeyPaasCapacityDTOTransfer.INSTANCE.toBOList(paasCapacityReport.getAppkeyPaasCapacityDTOList()).forEach(
                            appkeyPaasCapacityBO -> {
                                if(appkeyPaasCapacityResource.save(appkeyPaasCapacityBO)){
                                    log.info("PaaS: {}, 容灾实体: {} 上报成功", paasCapacityReport.getPaasName(), appkeyPaasCapacityBO.getTypeName());
                                } else {
                                    log.error("PaaS: {}, 容灾实体: {} 上报失败", paasCapacityReport.getPaasName(), appkeyPaasCapacityBO.getTypeName());
                                }
                            });
                }
                AppkeyPaasClientDTOTransfer.INSTANCE.toBOList(paasCapacityReport.getAppkeyPaasClientDTOList()).forEach(appkeyPaasClientResource::save);
                AppkeyPaasClientDTOTransfer.INSTANCE.toStandardClientBOList(paasCapacityReport.getAppkeyPaasStandardClientDTOList()).forEach(appkeyPaasStandardClientResource::save);
                t.setSuccessStatus();
                t.addData(String.format("【%s】容灾上报容灾信息成功", paasCapacityReport.getPaasName()));
            } catch (ValueValidException e){
                throw e;
            } catch (Exception e){
                success = false;
                t.setStatus(e);
                t.addData(String.format("【%s】容灾上报容灾信息失败: %s", paasCapacityReport.getPaasName(), e.getMessage()));
                t.addData(String.format("容灾失败上报参数: %s", GsonUtils.serialization(paasCapacityReport)));
            } finally {
                t.complete();
            }
        }
        return success;
    }

    @Override
    public List<AppkeyPaasCapacityDTO> queryUnStandardLevel(String appkey) {
        AppkeyPaasCapacityRequestBO queryRequest = AppkeyPaasCapacityRequestBO.builder()
                .appkey(appkey).isCapacityStandard(false)
                .build();
        List<AppkeyPaasCapacityBO> appkeyPaasCapacityBOList = appkeyPaasCapacityResource.query(queryRequest);
        return AppkeyPaasCapacityDTOTransfer.INSTANCE.toDTOList(appkeyPaasCapacityBOList);
    }

    @Override
    public List<AppkeyPaasCapacityDTO> queryPaasCapacityByAppkey(String appkey) {
        AppkeyPaasCapacityRequestBO queryRequest = AppkeyPaasCapacityRequestBO.builder()
                .appkey(appkey).updateDate(DateUtils.localDateToDate(LocalDate.now()))
                .build();
        List<AppkeyPaasCapacityBO> paasCapacityBOList = appkeyPaasCapacityResource.query(queryRequest);
        return  AppkeyPaasCapacityDTOTransfer.INSTANCE.toDTOList(paasCapacityBOList);
    }

    @Override
    public List<AppkeyPaasCapacityDTO> queryPaasCapacityByPaasAppkey(String appkey) {
        AppkeyPaasCapacityRequestBO queryRequest = AppkeyPaasCapacityRequestBO.builder()
                .paasAppkey(appkey).updateDate(DateUtils.localDateToDate(LocalDate.now()))
                .build();
        List<AppkeyPaasCapacityBO> paasCapacityBOList = appkeyPaasCapacityResource.query(queryRequest);
        return  AppkeyPaasCapacityDTOTransfer.INSTANCE.toDTOList(paasCapacityBOList);
    }

    @Override
    public AppkeyCapacitySummaryDTO getAppkeyPaasCapacitySummary(String appkey) {
        AppkeyCapacitySummaryBO summaryBO = appkeyPaasCapacityResource.getAppkeyCapacitySummaryBO(appkey, false);
        if (Objects.nonNull(summaryBO)) {
            return AppkeyPaasCapacityDTOTransfer.INSTANCE.toSummaryDTO(summaryBO);
        }
        return getAppkeyPaasCapacitySummaryNoCache(appkey);
    }

    @Override
    public AppkeyCapacitySummaryDTO getAppkeyPaasCapacitySummaryNoCache(String appkey) {
        List<AppkeyPaasCapacityDTO> appkeyPaasCapacityDTOList = queryPaasCapacityByAppkey(appkey);
        AppkeyCapacitySummaryDTO summaryDTO = toAppkeyCapacitySummaryDTO(appkeyPaasCapacityDTOList);
        appkeyPaasCapacityResource.setAppkeyCapacitySummaryBO(appkey,
                AppkeyPaasCapacityDTOTransfer.INSTANCE.toSummaryBO(summaryDTO), false);
        return summaryDTO;
    }

    @Override
    public AppkeyCapacitySummaryDTO getAppkeyPaasCapacitySummaryByPaasAppkey(String appkey) {
        AppkeyCapacitySummaryBO summaryBO = appkeyPaasCapacityResource.getAppkeyCapacitySummaryBO(appkey, true);
        if (Objects.nonNull(summaryBO)) {
            return AppkeyPaasCapacityDTOTransfer.INSTANCE.toSummaryDTO(summaryBO);
        }
        return getAppkeyPaasCapacitySummaryByPaasAppkeyNoCache(appkey);
    }

    @Override
    public AppkeyCapacitySummaryDTO getAppkeyPaasCapacitySummaryByPaasAppkeyNoCache(String appkey) {
        List<AppkeyPaasCapacityDTO> appkeyPaasCapacityDTOList = queryPaasCapacityByPaasAppkey(appkey);
        AppkeyCapacitySummaryDTO summaryDTO = toAppkeyCapacitySummaryDTO(appkeyPaasCapacityDTOList);
        appkeyPaasCapacityResource.setAppkeyCapacitySummaryBO(appkey,
                AppkeyPaasCapacityDTOTransfer.INSTANCE.toSummaryBO(summaryDTO), true);
        return summaryDTO;
    }

    private AppkeyCapacitySummaryDTO toAppkeyCapacitySummaryDTO(List<AppkeyPaasCapacityDTO> appkeyPaasCapacityDTOList){
        AppkeyCapacitySummaryDTO summaryDTO = new AppkeyCapacitySummaryDTO();
        if (CollectionUtils.isEmpty(appkeyPaasCapacityDTOList)) {
            return summaryDTO;
        }
        appkeyPaasCapacityDTOList.sort((v1, v2) -> {
            if (!v1.getIsWhite().equals(v2.getIsWhite())) {
                return Boolean.TRUE.equals(v1.getIsWhite()) ? 1 : -1;
            } else if (!v1.getIsCapacityStandard().equals(v2.getIsCapacityStandard())) {
                return Boolean.TRUE.equals(v1.getIsCapacityStandard()) ? 1 : -1;
            } else {
                return v1.getCapacityLevel() - v2.getCapacityLevel();
            }
        });
        AppkeyPaasCapacityDTO minPaasDTO = appkeyPaasCapacityDTOList.get(0);
        summaryDTO.setCapacityLevel(minPaasDTO.getCapacityLevel());
        summaryDTO.setStandardCapacityLevel(minPaasDTO.getStandardLevel());
        summaryDTO.setIsCapacityStandard(minPaasDTO.getIsCapacityStandard());
        summaryDTO.setStandardTips(getStandardTips(minPaasDTO));
        return summaryDTO;
    }

    /**
     * 标准化paas容灾建议
     *
     * @param minPaasDTO minPaasDTO
     * @return {@link String}
     */
    private String getStandardTips(AppkeyPaasCapacityDTO minPaasDTO){
        if (Boolean.TRUE.equals(minPaasDTO.getIsWhite())) {
            return "容灾已加白，免达标";
        }
        if (Boolean.TRUE.equals(minPaasDTO.getIsCapacityStandard())) {
            return "容灾已达标";
        }
        if (StringUtils.isNotEmpty(minPaasDTO.getStandardReason()) && StringUtils.isNotEmpty(minPaasDTO.getStandardTips())) {
            return minPaasDTO.getStandardReason() + "，" + minPaasDTO.getStandardTips();
        }
        return StringUtils.isNotEmpty(minPaasDTO.getStandardReason()) ? minPaasDTO.getStandardReason() : minPaasDTO.getStandardTips();
    }

    @Override
    public List<AppkeyPaasClientDTO> queryPaasClientByAppkey(String paasName, String appkey) {
        AppkeyPaasClientRequestBO queryRequest = AppkeyPaasClientRequestBO.builder()
                .paasName(paasName)
                .appkey(appkey)
                .updateDate(DateUtils.localDateToDate(LocalDate.now()))
                .build();
        List<AppkeyPaasClientBO> appkeyPaasClientBOList = appkeyPaasClientResource.query(queryRequest);
        return AppkeyPaasClientDTOTransfer.INSTANCE.toDTOList(appkeyPaasClientBOList);
    }

    @Override
    public List<AppkeyPaasClientDTO> queryPaasClientByAppkey(String paasName, String appkey, Date updateDate) {
        AppkeyPaasClientRequestBO queryRequest = AppkeyPaasClientRequestBO.builder()
                .paasName(paasName)
                .appkey(appkey)
                .updateDate(updateDate)
                .build();
        List<AppkeyPaasClientBO> appkeyPaasClientBOList = appkeyPaasClientResource.query(queryRequest);
        return AppkeyPaasClientDTOTransfer.INSTANCE.toDTOList(appkeyPaasClientBOList);
    }

    @Override
    public PageResponse<AppkeyPaasCapacityDTO> queryPage(AppkeyPaasCapacityPageRequest request) {
        int page = request.getPage();
        int pageSize = request.getPageSize();
        AppkeyPaasCapacityRequestBO queryRequest = AppkeyPaasCapacityRequestBO.builder()
                .idGtn(request.getIdGtn())
                .appkey(request.getAppkey())
                .paasName(request.getPaasName())
                .paasAppkey(request.getPaasAppkey())
                .typeName(request.getTypeName())
                .clientRole(request.getClientRole())
                .isCapacityStandard(request.getIsCapacityStandard())
                .isWhite(request.getIsWhite())
                .isSet(request.getIsSet())
                .setName(request.getSetName())
                .setType(request.getSetType())
                .updateDate(request.getUpdateDate())
                .build();
        queryRequest.setPage(page);
        queryRequest.setPageSize(pageSize);
        PageResponse<AppkeyPaasCapacityBO> boPageResponse = appkeyPaasCapacityResource.queryPage(queryRequest);
        return toDTOPageResponse(page, pageSize, boPageResponse);
    }

    @Override
    public PageResponse<AppkeyPaasCapacityDTO> getPageAggregatedByAppkey(String appkey, List<String> paasNames, boolean isPaas, int page, int pageSize) {
        PageResponse<AppkeyPaasCapacityBO> bOPageResponse = appkeyPaasCapacityResource.getPageAggregatedByAppkey(
                appkey, DateUtils.localDateToDate(LocalDate.now()), paasNames, page, pageSize, isPaas
        );
        return toDTOPageResponse(page, pageSize, bOPageResponse);
    }

    @NotNull
    private PageResponse<AppkeyPaasCapacityDTO> toDTOPageResponse(int page, int pageSize, PageResponse<AppkeyPaasCapacityBO> bOPageResponse) {
        PageResponse<AppkeyPaasCapacityDTO> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(bOPageResponse.getTotalCount());
        pageResponse.setTotalPage(bOPageResponse.getTotalPage());
        pageResponse.setItems(AppkeyPaasCapacityDTOTransfer.INSTANCE.toDTOList(bOPageResponse.getItems()));
        return pageResponse;
    }

    @Override
    public boolean deleteAppkeyPaasCapacityByUpdateDate(LocalDate date) {
        AppkeyPaasCapacityRequestBO appkeyPaasCapacityRequestBO = AppkeyPaasCapacityRequestBO.builder()
                .updateDate(DateUtils.localDateToDate(date)).build();
        AppkeyPaasClientRequestBO appkeyPaasClientRequestBO = AppkeyPaasClientRequestBO.builder()
                .updateDate(DateUtils.localDateToDate(date)).build();
        return appkeyPaasCapacityResource.deleteByCondition(appkeyPaasCapacityRequestBO)
                && appkeyPaasClientResource.deleteByCondition(appkeyPaasClientRequestBO);
    }

    @Override
    public List<String> getPaasNamesByAppkey(String appkey, boolean isPaas) {
        return appkeyPaasCapacityResource.getPaasNamesByAppkey(appkey, DateUtils.localDateToDate(LocalDate.now()), isPaas);
    }

    @Override
    public List<String> getValidPaasAppkeys() {
        return appkeyPaasCapacityResource.getPaasAppkeys(DateUtils.localDateToDate(LocalDate.now()));
    }

    @Override
    public List<String> getValidClientAppkeys() {
        return appkeyPaasCapacityResource.getClientAppkeys(DateUtils.localDateToDate(LocalDate.now()));
    }

    @Override
    public Boolean cacheAppkeyCapacitySummary(String appkey, boolean isPaas) {
        AppkeyCapacitySummaryDTO summary = isPaas
                ? getAppkeyPaasCapacitySummaryByPaasAppkeyNoCache(appkey)
                : getAppkeyPaasCapacitySummaryNoCache(appkey);
        return appkeyPaasCapacityResource.setAppkeyCapacitySummaryBO(appkey, AppkeyPaasCapacityDTOTransfer.INSTANCE.toSummaryBO(summary), isPaas);
    }
}
