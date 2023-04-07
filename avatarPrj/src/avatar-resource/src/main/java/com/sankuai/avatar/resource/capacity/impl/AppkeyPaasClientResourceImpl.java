package com.sankuai.avatar.resource.capacity.impl;

import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.resource.capacity.AppkeyPaasClientResource;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasClientBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasClientRequestBO;
import com.sankuai.avatar.dao.resource.repository.AppkeyPaasClientRepository;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasClientDO;
import com.sankuai.avatar.resource.capacity.transfer.AppkeyPaasClientRequestTransfer;
import com.sankuai.avatar.resource.capacity.transfer.AppkeyPaasClientTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * AppkeyPaasClientResource接口实现类
 * @author caoyang
 * @create 2022-10-11 17:58
 */
@Slf4j
@Component
public class AppkeyPaasClientResourceImpl implements AppkeyPaasClientResource {

    private final AppkeyPaasClientRepository repository;

    @Autowired
    public AppkeyPaasClientResourceImpl(AppkeyPaasClientRepository repository){
        this.repository = repository;
    }

    @Override
    public List<AppkeyPaasClientBO> query(AppkeyPaasClientRequestBO appkeyPaasClientRequestBO) {
        List<AppkeyPaasClientDO> appkeyPaasClientDOList = repository.query(
                AppkeyPaasClientRequestTransfer.INSTANCE.toAppkeyPaasClientRequest(appkeyPaasClientRequestBO)
        );
        return AppkeyPaasClientTransfer.INSTANCE.toBOList(appkeyPaasClientDOList);
    }

    @Override
    public boolean save(AppkeyPaasClientBO appkeyPaasClientBO) {
        if (StringUtils.isEmpty(appkeyPaasClientBO.getPaasName()) || StringUtils.isEmpty(appkeyPaasClientBO.getClientAppkey())) {
            return false;
        }
        AppkeyPaasClientRequestBO request = AppkeyPaasClientRequestBO.builder()
                .paasName(appkeyPaasClientBO.getPaasName())
                .appkey(appkeyPaasClientBO.getClientAppkey())
                .language(appkeyPaasClientBO.getLanguage())
                .groupId(appkeyPaasClientBO.getGroupId())
                .artifactId(appkeyPaasClientBO.getArtifactId())
                .updateDate(DateUtils.localDateToDate(LocalDate.now()))
                .build();
        AppkeyPaasClientDO appkeyPaasClientDO = AppkeyPaasClientTransfer.INSTANCE.toDO(appkeyPaasClientBO);
        appkeyPaasClientDO.setUpdateDate(new Date());
        List<AppkeyPaasClientBO> appkeyPaasClientBOList = query(request);
        if (appkeyPaasClientBOList.isEmpty()) {
            return repository.insert(appkeyPaasClientDO);
        } else {
            appkeyPaasClientDO.setId(appkeyPaasClientBOList.get(0).getId());
            return repository.update(appkeyPaasClientDO);
        }
    }

    @Override
    public boolean deleteByCondition(AppkeyPaasClientRequestBO appkeyPaasClientRequestBO) {
        if (ObjectUtils.checkObjAllFieldsIsNull(appkeyPaasClientRequestBO)) {
            return false;
        }
        boolean success = true;
        List<AppkeyPaasClientBO> appkeyPaasClientBOList = query(appkeyPaasClientRequestBO);
        for (AppkeyPaasClientBO appkeyPaasClientBO : appkeyPaasClientBOList) {
            if (!repository.delete(appkeyPaasClientBO.getId()) && success) {
                success = false;
            }
        }
        return true;
    }
}
