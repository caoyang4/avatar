package com.sankuai.avatar.collect.appkey.process.impl;

import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.process.Processor;
import com.sankuai.avatar.collect.appkey.process.transfer.AppkeyTransfer;
import com.sankuai.avatar.dao.es.AppkeyEsRepository;
import com.sankuai.avatar.dao.es.request.AppkeyUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author qinwei05
 * @date 2022/11/3 17:28
 */
@Service
@Slf4j
public class EsProcessorImpl implements Processor {

    private final AppkeyEsRepository appkeyEsRepository;

    @Autowired
    public EsProcessorImpl(AppkeyEsRepository appkeyEsRepository) {
        this.appkeyEsRepository = appkeyEsRepository;
    }

    @Override
    public Boolean batchProcess(List<Appkey> appkeys) {
        appkeys.forEach(this::process);
        return true;
    }

    @Override
    public Boolean process(Appkey appkey) {
        AppkeyUpdateRequest appkeyUpdateRequest = AppkeyTransfer.INSTANCE.toEsRequest(appkey);
        if (Objects.isNull(appkeyUpdateRequest) || StringUtils.isBlank(appkeyUpdateRequest.getAppkey())) {
            return Boolean.FALSE;
        }
        return appkeyEsRepository.update(appkeyUpdateRequest);
    }
}
