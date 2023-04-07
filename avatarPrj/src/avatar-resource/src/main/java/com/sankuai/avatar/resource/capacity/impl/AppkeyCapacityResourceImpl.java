package com.sankuai.avatar.resource.capacity.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.client.ops.model.OpsCapacity;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.capacity.AppkeyCapacityResource;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacityBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyCapacityRequestBO;
import com.sankuai.avatar.resource.capacity.transfer.AppkeyCapacityRequestTransfer;
import com.sankuai.avatar.resource.capacity.transfer.AppkeyCapacityTransfer;
import com.sankuai.avatar.dao.resource.repository.AppkeyCapacityRepository;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyCapacityDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyCapacityRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-11-03 19:44
 */
@Slf4j
@Component
public class AppkeyCapacityResourceImpl implements AppkeyCapacityResource {

    private final AppkeyCapacityRepository repository;
    private final OpsHttpClient opsHttpClient;

    public AppkeyCapacityResourceImpl(AppkeyCapacityRepository repository, OpsHttpClient opsHttpClient) {
        this.repository = repository;
        this.opsHttpClient = opsHttpClient;
    }

    @Override
    public PageResponse<AppkeyCapacityBO> queryPage(AppkeyCapacityRequestBO appkeyCapacityRequestBO) {
        PageResponse<AppkeyCapacityBO> pageResponse = new PageResponse<>();
        int page = appkeyCapacityRequestBO.getPage();
        int pageSize = appkeyCapacityRequestBO.getPageSize();
        AppkeyCapacityRequest request = AppkeyCapacityRequestTransfer.INSTANCE.toAppkeyCapacityRequest(appkeyCapacityRequestBO);
        request.setSetName(appkeyCapacityRequestBO.getSetName());
        Page<AppkeyCapacityDO> capacityDOPage = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(request)
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(capacityDOPage.getTotal());
        pageResponse.setTotalPage(capacityDOPage.getPages());
        pageResponse.setItems(AppkeyCapacityTransfer.INSTANCE.toBOList(capacityDOPage));
        return pageResponse;
    }

    @Override
    public List<AppkeyCapacityBO> query(AppkeyCapacityRequestBO appkeyCapacityRequestBO) {
        AppkeyCapacityRequest request = AppkeyCapacityRequestTransfer.INSTANCE.toAppkeyCapacityRequest(appkeyCapacityRequestBO);
        request.setSetName(appkeyCapacityRequestBO.getSetName());
        List<AppkeyCapacityDO> appkeyCapacityDOList = repository.query(request);
        return AppkeyCapacityTransfer.INSTANCE.toBOList(appkeyCapacityDOList);
    }

    @Override
    public boolean save(AppkeyCapacityBO appkeyCapacityBO) {
        if (Objects.isNull(appkeyCapacityBO)) {
            return false;
        }
        AppkeyCapacityRequestBO requestBO = AppkeyCapacityRequestBO.builder()
                .appkey(appkeyCapacityBO.getAppkey())
                .setName(Objects.nonNull(appkeyCapacityBO.getSetName()) ? appkeyCapacityBO.getSetName() : "")
                .isFullField(false)
                .build();
        AppkeyCapacityDO appkeyCapacityDO = AppkeyCapacityTransfer.INSTANCE.toDO(appkeyCapacityBO);
        List<AppkeyCapacityBO> capacityBOList = query(requestBO);
        if (CollectionUtils.isEmpty(capacityBOList)) {
            return repository.insert(appkeyCapacityDO);
        } else {
            appkeyCapacityDO.setId(capacityBOList.get(0).getId());
            return repository.update(appkeyCapacityDO);
        }
    }

    @Override
    public boolean deleteByCondition(AppkeyCapacityRequestBO appkeyCapacityRequestBO) {
        // 禁止无条件删除
        if (ObjectUtils.checkObjAllFieldsIsNull(appkeyCapacityRequestBO)) {
            return false;
        }
        boolean success = true;
        List<AppkeyCapacityBO> capacityBOList = query(appkeyCapacityRequestBO);
        for (AppkeyCapacityBO capacityBO : capacityBOList) {
            if (!repository.delete(capacityBO.getId())) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public boolean updateOpsCapacity(String appkey,  String reason, Integer capacityLevel) {
        if (StringUtils.isEmpty(appkey) || Objects.isNull(capacityLevel)) {
            return false;
        }
        OpsCapacity opsCapacity = OpsCapacity.builder()
                .capacity(capacityLevel).reason(reason).build();
        try {
            return opsHttpClient.updateOpsCapacity(appkey, opsCapacity);
        } catch (SdkCallException | SdkBusinessErrorException ignored) {
            log.error("{}更新 ops 容灾异常: {}", appkey, ignored);
            return false;
        }
    }

}
