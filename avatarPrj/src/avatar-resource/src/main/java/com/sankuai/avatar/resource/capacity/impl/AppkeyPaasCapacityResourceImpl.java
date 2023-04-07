package com.sankuai.avatar.resource.capacity.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.capacity.AppkeyPaasCapacityResource;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacitySummaryBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasCapacityBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasCapacityRequestBO;
import com.sankuai.avatar.resource.capacity.transfer.AppkeyPaasCapacityRequestTransfer;
import com.sankuai.avatar.resource.capacity.transfer.AppkeyPaasCapacityTransfer;
import com.sankuai.avatar.dao.cache.AppkeyCapacityCacheRepository;
import com.sankuai.avatar.dao.resource.repository.AppkeyPaasCapacityRepository;
import com.sankuai.avatar.dao.cache.model.CapacitySummary;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasCapacityDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * AppkeyPaasCapacityResource 接口实现类
 * @author Jie.li.sh
 * @create 2022-09-26
 **/
@Slf4j
@Component
public class AppkeyPaasCapacityResourceImpl implements AppkeyPaasCapacityResource {

    private final AppkeyPaasCapacityRepository repository;
    private final OpsHttpClient opsHttpClient;
    private final AppkeyCapacityCacheRepository cacheRepository;

    @Autowired
    public AppkeyPaasCapacityResourceImpl(AppkeyPaasCapacityRepository repository,
                                          OpsHttpClient opsHttpClient,
                                          AppkeyCapacityCacheRepository cacheRepository){
        this.repository = repository;
        this.opsHttpClient = opsHttpClient;
        this.cacheRepository = cacheRepository;
    }

    @Override
    public List<AppkeyPaasCapacityBO> query(AppkeyPaasCapacityRequestBO appkeyPaasCapacityRequestBO) {
        List<AppkeyPaasCapacityDO> appkeyPaasCapacityDOList = repository.query(
                AppkeyPaasCapacityRequestTransfer.INSTANCE.toAppkeyPaasCapacityRequest(appkeyPaasCapacityRequestBO)
        );
        return AppkeyPaasCapacityTransfer.INSTANCE.toBOList(appkeyPaasCapacityDOList);
    }

    @Override
    public PageResponse<AppkeyPaasCapacityBO> queryPage(AppkeyPaasCapacityRequestBO appkeyPaasCapacityRequestBO) {
        PageResponse<AppkeyPaasCapacityBO> pageResponse = new PageResponse<>();
        int page = appkeyPaasCapacityRequestBO.getPage();
        int pageSize = appkeyPaasCapacityRequestBO.getPageSize();
        // 是否查询总数，标签id不返回总数
        boolean queryCount = Objects.isNull(appkeyPaasCapacityRequestBO.getIdGtn());
        Page<AppkeyPaasCapacityDO> paasCapacityDOPage = PageHelper.startPage(page, pageSize, queryCount).doSelectPage(
                () -> repository.query(AppkeyPaasCapacityRequestTransfer.INSTANCE.toAppkeyPaasCapacityRequest(appkeyPaasCapacityRequestBO))
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(paasCapacityDOPage.getTotal());
        pageResponse.setTotalPage(paasCapacityDOPage.getPages());
        pageResponse.setItems(AppkeyPaasCapacityTransfer.INSTANCE.toBOList(paasCapacityDOPage));
        return pageResponse;
    }

    @Override
    public PageResponse<AppkeyPaasCapacityBO> getPageAggregatedByAppkey(String appkey, Date update, List<String> paasNames,
                                                                        int page, int pageSize, boolean isPaas) {
        Page<AppkeyPaasCapacityDO> doPage;
        if (CollectionUtils.isEmpty(paasNames)) {
            doPage = isPaas
                    ? PageHelper.startPage(page, pageSize).doSelectPage(
                        () -> repository.queryAggregatedPaasAppkey(update, appkey))
                    : PageHelper.startPage(page, pageSize).doSelectPage(
                        () -> repository.queryAggregatedClientAppkey(update, appkey));
        } else {
            doPage = isPaas
                    ? PageHelper.startPage(page, pageSize).doSelectPage(
                        () -> repository.queryAggregatedPaasAppkey(update, appkey, paasNames))
                    : PageHelper.startPage(page, pageSize).doSelectPage(
                        () -> repository.queryAggregatedClientAppkey(update, appkey, paasNames));
        }
        PageResponse<AppkeyPaasCapacityBO> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(doPage.getTotal());
        pageResponse.setTotalPage(doPage.getPages());
        pageResponse.setItems(AppkeyPaasCapacityTransfer.INSTANCE.toBOList(doPage));
        return pageResponse;
    }

    @Override
    public boolean save(AppkeyPaasCapacityBO appkeyPaasCapacityBO) {
        if (Objects.isNull(appkeyPaasCapacityBO)) {
            return false;
        }
        AppkeyPaasCapacityRequestBO request = AppkeyPaasCapacityRequestBO.builder()
                .paasName(appkeyPaasCapacityBO.getPaasName())
                .paasAppkey(appkeyPaasCapacityBO.getPaasAppkey())
                .appkey(appkeyPaasCapacityBO.getClientAppkey())
                .type(appkeyPaasCapacityBO.getType())
                .typeName(appkeyPaasCapacityBO.getTypeName())
                .clientRole(appkeyPaasCapacityBO.getClientRole())
                .setName(appkeyPaasCapacityBO.getSetName())
                .updateDate(DateUtils.localDateToDate(LocalDate.now()))
                .build();
        AppkeyPaasCapacityDO appkeyPaasCapacityDO = AppkeyPaasCapacityTransfer.INSTANCE.toDO(appkeyPaasCapacityBO);
        appkeyPaasCapacityDO.setUpdateDate(new Date());
        List<AppkeyPaasCapacityBO> appkeyPaasCapacityBOList = query(request);
        if (appkeyPaasCapacityBOList.isEmpty()) {
            return repository.insert(appkeyPaasCapacityDO);
        } else {
            AppkeyPaasCapacityBO ro = appkeyPaasCapacityBOList.get(0);
            appkeyPaasCapacityDO.setId(ro.getId());
            appkeyPaasCapacityDO.setUpdateTime(new Date());
            return repository.update(appkeyPaasCapacityDO);
        }
    }

    @Override
    public boolean deleteByCondition(AppkeyPaasCapacityRequestBO appkeyPaasCapacityRequestBO) {
        // 禁止无条件删除
        if (ObjectUtils.checkObjAllFieldsIsNull(appkeyPaasCapacityRequestBO)) {
            return false;
        }
        boolean success = true;
        List<AppkeyPaasCapacityBO> appkeyPaasCapacityBOList = query(appkeyPaasCapacityRequestBO);
        for (AppkeyPaasCapacityBO appkeyPaasCapacityBO : appkeyPaasCapacityBOList) {
            if (!repository.delete(appkeyPaasCapacityBO.getId()) && success) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public List<String> getPaasNamesByAppkey(String appkey, Date date, boolean isPaas) {
        try {
            List<String> paasNames = cacheRepository.getPaasNamesByAppkey(appkey, isPaas);
            if (CollectionUtils.isNotEmpty(paasNames)) {
                return paasNames;
            }
        } catch (CacheException ignored) {
        }
        List<String> paasNames = isPaas
                ? repository.queryPaasNamesByPaasAppkey(date, appkey)
                : repository.queryPaasNamesByAppkey(date, appkey);
        cacheRepository.setPaasNames(appkey, paasNames, isPaas, 4*3600);
        return paasNames;
    }

    @Override
    public AppkeyCapacitySummaryBO getAppkeyCapacitySummaryBO(String appkey, boolean isPaas) {
        CapacitySummary summary;
        try {
            summary = cacheRepository.getAppkeySummary(appkey, isPaas);
        } catch (CacheException ignored) {
            summary = null;
        }
        return AppkeyPaasCapacityTransfer.INSTANCE.toSummaryBO(summary);
    }

    @Override
    public Boolean setAppkeyCapacitySummaryBO(String appkey, AppkeyCapacitySummaryBO summaryBO, boolean isPaas) {
        try {
            return cacheRepository.setAppkeySummary(appkey, AppkeyPaasCapacityTransfer.INSTANCE.toSummary(summaryBO), isPaas, 24 * 3600);
        } catch (CacheException ignored) {
            return false;
        }
    }

    @Override
    public List<String> getPaasAppkeys(Date updateDate) {
        List<String> paasAppkeys = repository.queryPaasAppkeysByUpdateDate(updateDate);
        List<String> validAppkeys = new ArrayList<>();
        for (String appkey : paasAppkeys) {
            try {
                if (opsHttpClient.isExistAppkey(appkey)) {
                    validAppkeys.add(appkey);
                }
            } catch (SdkCallException | SdkBusinessErrorException ignored) {
            }
        }
        return validAppkeys;
    }

    @Override
    public List<String> getClientAppkeys(Date updateDate) {
        List<String> clientAppkeys = repository.queryClientAppkeysByUpdateDate(updateDate);
        List<String> validAppkeys = new ArrayList<>();
        for (String appkey : clientAppkeys) {
            try {
                if (opsHttpClient.isExistAppkey(appkey)) {
                    validAppkeys.add(appkey);
                }
            } catch (SdkCallException | SdkBusinessErrorException ignored) {
            }
        }
        return validAppkeys;
    }
}
