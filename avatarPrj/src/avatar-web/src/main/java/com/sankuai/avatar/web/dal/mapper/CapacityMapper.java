package com.sankuai.avatar.web.dal.mapper;

import com.sankuai.avatar.web.dal.customize.MyMapper;
import com.sankuai.avatar.web.dal.entity.CapacityDO;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author chenxinli
 */
public interface CapacityMapper extends Mapper<CapacityDO>, InsertListMapper<CapacityDO>, MyMapper<CapacityDO> {
}