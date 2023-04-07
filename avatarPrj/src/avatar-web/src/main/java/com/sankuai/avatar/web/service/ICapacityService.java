package com.sankuai.avatar.web.service;

import com.sankuai.avatar.web.dal.entity.CapacityDO;

import java.util.List;

/**
 * @author chenxinli
 */
public interface ICapacityService {

    /**
     * 批量插入或更新
     * @param capacity capacity
     * @return int
     * @throws Exception exception
     */
    Integer batchUpdate(List<CapacityDO> capacity);
    /**
     * 批量新增
     * @param capacity list
     * @return count
     */
    Integer batchInsert(List<CapacityDO> capacity);


}
