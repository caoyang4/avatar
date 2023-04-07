package com.sankuai.avatar.resource.whitelist.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.resource.repository.ServiceWhitelistRepository;
import com.sankuai.avatar.dao.resource.repository.model.ServiceWhitelistDO;
import com.sankuai.avatar.dao.resource.repository.request.ServiceWhitelistRequest;
import com.sankuai.avatar.resource.whitelist.ServiceWhitelistResource;
import com.sankuai.avatar.resource.whitelist.bo.ServiceWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.request.ServiceWhitelistRequestBO;
import com.sankuai.avatar.resource.whitelist.transfer.ServiceWhitelistRequestTransfer;
import com.sankuai.avatar.resource.whitelist.transfer.ServiceWhitelistTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ServiceWhitelistResource（服务白名单资源）实现类
 * @author caoyang
 * @create 2022-10-27 20:43
 */
@Slf4j
@Component
public class ServiceWhitelistResourceImpl implements ServiceWhitelistResource {

    private final ServiceWhitelistRepository repository;
    @Autowired
    public ServiceWhitelistResourceImpl(ServiceWhitelistRepository repository){
        this.repository = repository;
    }

    @Override
    public PageResponse<ServiceWhitelistBO> queryPage(ServiceWhitelistRequestBO requestBO) {
        PageResponse<ServiceWhitelistBO> pageResponse = new PageResponse<>();
        int page = requestBO.getPage();
        int pageSize = requestBO.getPageSize();
        Page<ServiceWhitelistDO> pageDO = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(ServiceWhitelistRequestTransfer.INSTANCE.toServiceWhitelistRequest(requestBO))
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(pageDO.getPages());
        pageResponse.setTotalCount(pageDO.getTotal());
        pageResponse.setItems(ServiceWhitelistTransfer.INSTANCE.toBOList(pageDO));
        return pageResponse;
    }

    @Override
    public List<ServiceWhitelistBO> query(ServiceWhitelistRequestBO requestBO) {
        ServiceWhitelistRequest request = ServiceWhitelistRequestTransfer.INSTANCE.toServiceWhitelistRequest(requestBO);
        return ServiceWhitelistTransfer.INSTANCE.toBOList(repository.query(request));
    }

    @Override
    public boolean save(ServiceWhitelistBO serviceWhitelistBO) {
        if (Objects.isNull(serviceWhitelistBO)) {
            return false;
        }
        ServiceWhitelistDO serviceWhitelistDO = ServiceWhitelistTransfer.INSTANCE.toDO(serviceWhitelistBO);
        List<ServiceWhitelistBO> serviceWhitelistBOList = query(ServiceWhitelistRequestBO.builder()
                .appkeys(Collections.singletonList(serviceWhitelistBO.getAppkey()))
                .app(serviceWhitelistBO.getApp())
                .build());
        if (CollectionUtils.isEmpty(serviceWhitelistBOList)) {
            return repository.insert(serviceWhitelistDO);
        } else {
            int id = serviceWhitelistBOList.get(0).getId();
            serviceWhitelistDO.setId(id);
            return repository.update(serviceWhitelistDO);
        }
    }

    @Override
    public boolean deleteByCondition(ServiceWhitelistRequestBO requestBO) {
        if (ObjectUtils.checkObjAllFieldsIsNull(requestBO)) {
            return false;
        }
        List<ServiceWhitelistBO> serviceWhitelistBOList = query(requestBO);
        boolean success = true;
        for (ServiceWhitelistBO bo : serviceWhitelistBOList) {
            if (!repository.delete(bo.getId())) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public boolean isWhiteOfAppkeySet(String appkey, WhiteType type, String setName) {
        final String comma = ",";
        List<ServiceWhitelistDO> doList = repository.query(ServiceWhitelistRequest.builder()
                        .appkeys(Collections.singletonList(appkey)).app(type.getWhiteType())
                        .build());
        if (CollectionUtils.isEmpty(doList)) {
            return false;
        }
        String set = doList.get(0).getSetName();
        if (StringUtils.isEmpty(set)) {
            return true;
        }
        List<String> setList = Arrays.asList(set.split(comma));
        return setList.contains(setName);
    }

    @Override
    public List<ServiceWhitelistBO> getExpireWhitelist() {
        List<ServiceWhitelistDO> doList = repository.query(ServiceWhitelistRequest.builder().endTimeLtn(new Date()).build());
        return ServiceWhitelistTransfer.INSTANCE.toBOList(doList);
    }
}
