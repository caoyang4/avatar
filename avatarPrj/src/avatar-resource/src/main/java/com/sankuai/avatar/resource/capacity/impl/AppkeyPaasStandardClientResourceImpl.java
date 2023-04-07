package com.sankuai.avatar.resource.capacity.impl;

import com.sankuai.avatar.resource.capacity.AppkeyPaasStandardClientResource;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasStandardClientBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasStandardClientRequestBO;
import com.sankuai.avatar.dao.resource.repository.AppkeyPaasStandardClientRepository;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasStandardClientDO;
import com.sankuai.avatar.resource.capacity.transfer.AppkeyPaasStandardClientRequestTransfer;
import com.sankuai.avatar.resource.capacity.transfer.AppkeyPaasStandardClientTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AppkeyPaasStandardClientResource 接口实现类
 * @author caoyang
 * @create 2022-10-11 18:00
 */
@Slf4j
@Component
public class AppkeyPaasStandardClientResourceImpl implements AppkeyPaasStandardClientResource {

    private final AppkeyPaasStandardClientRepository repository;
    @Autowired
    public AppkeyPaasStandardClientResourceImpl(AppkeyPaasStandardClientRepository repository){
        this.repository = repository;
    }

    @Override
    public List<AppkeyPaasStandardClientBO> query(AppkeyPaasStandardClientRequestBO appkeyPaasStandardClientRequestBO) {
        List<AppkeyPaasStandardClientDO> appkeyPaasStandardClientDOList = repository.query(
                AppkeyPaasStandardClientRequestTransfer.INSTANCE.toAppkeyPaasStandardClientRequest(appkeyPaasStandardClientRequestBO)
        );
        return AppkeyPaasStandardClientTransfer.INSTANCE.toBOList(appkeyPaasStandardClientDOList);
    }

    @Override
    public boolean save(AppkeyPaasStandardClientBO appkeyPaasStandardClientBO) {
        AppkeyPaasStandardClientRequestBO request = AppkeyPaasStandardClientRequestBO.builder()
                .paasName(appkeyPaasStandardClientBO.getPaasName())
                .language(appkeyPaasStandardClientBO.getLanguage())
                .groupId(appkeyPaasStandardClientBO.getGroupId())
                .artifactId(appkeyPaasStandardClientBO.getArtifactId())
                .build();
        AppkeyPaasStandardClientDO appkeyPaasStandardClientDO = AppkeyPaasStandardClientTransfer.INSTANCE.toDO(appkeyPaasStandardClientBO);
        List<AppkeyPaasStandardClientBO> appkeyPaasStandardClientBOList = query(request);
        if (appkeyPaasStandardClientBOList.isEmpty()) {
            return repository.insert(appkeyPaasStandardClientDO);
        } else {
            appkeyPaasStandardClientDO.setId(appkeyPaasStandardClientBOList.get(0).getId());
            return repository.update(appkeyPaasStandardClientDO);
        }
    }
}
