package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyCapacityDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author caoyang
 * @create 2022-11-03 14:30
 */
@Repository
public interface AppkeyCapacityDOMapper extends Mapper<AppkeyCapacityDO>, InsertListMapper<AppkeyCapacityDO> {

}
