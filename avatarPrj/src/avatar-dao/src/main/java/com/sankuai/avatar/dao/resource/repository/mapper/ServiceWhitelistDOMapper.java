package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.ServiceWhitelistDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * ServiceWhitelistDO对象的 mapper
 * @author caoyang
 * @create 2022-10-21 15:41
 */
@Repository
public interface ServiceWhitelistDOMapper extends Mapper<ServiceWhitelistDO>, InsertListMapper<ServiceWhitelistDO> {

}

