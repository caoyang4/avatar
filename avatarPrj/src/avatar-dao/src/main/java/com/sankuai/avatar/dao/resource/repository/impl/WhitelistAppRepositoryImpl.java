package com.sankuai.avatar.dao.resource.repository.impl;

import com.sankuai.avatar.dao.resource.repository.WhitelistAppRepository;
import com.sankuai.avatar.dao.resource.repository.mapper.WhitelistAppDOMapper;
import com.sankuai.avatar.dao.resource.repository.model.WhitelistAppDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * WhitelistAppRepository实现类
 * @author caoyang
 * @create 2022-11-02 21:52
 */
@Repository
public class WhitelistAppRepositoryImpl implements WhitelistAppRepository {

    private final WhitelistAppDOMapper mapper;
    @Autowired
    public WhitelistAppRepositoryImpl(WhitelistAppDOMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<WhitelistAppDO> query() {
        return mapper.selectAll();
    }

    @Override
    public boolean insert(WhitelistAppDO whitelistAppDO) {
        if (Objects.isNull(whitelistAppDO)) {
            return false;
        }
        return mapper.insertSelective(whitelistAppDO) == 1;
    }

    @Override
    public boolean update(WhitelistAppDO whitelistAppDO) {
        if (Objects.isNull(whitelistAppDO) || Objects.isNull(whitelistAppDO.getId())) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(whitelistAppDO) == 1;
    }
}
