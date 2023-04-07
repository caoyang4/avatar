package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * AppkeyDO对象 mapper
 *
 * @author qinwei05
 * @date 2022/10/28
 */
@Repository
public interface AppkeyDOMapper extends Mapper<AppkeyDO>, InsertListMapper<AppkeyDO> {
}
