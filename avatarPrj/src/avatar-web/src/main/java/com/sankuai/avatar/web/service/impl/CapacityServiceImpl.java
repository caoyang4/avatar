package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.web.dal.entity.CapacityDO;
import com.sankuai.avatar.web.dal.mapper.CapacityMapper;
import com.sankuai.avatar.web.service.ICapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenxinli
 */
@Service
public class CapacityServiceImpl implements ICapacityService {

    @Autowired
    private CapacityMapper capacityMapper;

    @Override
    public Integer batchInsert(List<CapacityDO> capacity) {
        return capacityMapper.insertList(capacity);
    }

    @Override
    public Integer batchUpdate(List<CapacityDO> capacity) {
        return capacityMapper.batchUpdate(capacity);
    }

}
