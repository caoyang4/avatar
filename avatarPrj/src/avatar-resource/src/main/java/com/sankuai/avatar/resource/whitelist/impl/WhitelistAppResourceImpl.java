package com.sankuai.avatar.resource.whitelist.impl;

import com.sankuai.avatar.dao.resource.repository.WhitelistAppRepository;
import com.sankuai.avatar.resource.whitelist.WhitelistAppResource;
import com.sankuai.avatar.resource.whitelist.bo.WhitelistAppBO;
import com.sankuai.avatar.resource.whitelist.transfer.WhitelistAppTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-15 10:29
 */
@Slf4j
@Component
public class WhitelistAppResourceImpl implements WhitelistAppResource {

    private final WhitelistAppRepository repository;

    @Autowired
    public WhitelistAppResourceImpl(WhitelistAppRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WhitelistAppBO> getAllWhitelistApp() {
        return WhitelistAppTransfer.INSTANCE.toBOList(repository.query());
    }
}
