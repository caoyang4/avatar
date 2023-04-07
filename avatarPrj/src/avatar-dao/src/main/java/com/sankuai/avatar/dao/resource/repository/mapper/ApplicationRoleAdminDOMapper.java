package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.ApplicationRoleAdminDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author caoyang
 * @create 2023-01-11 14:30
 */
@Repository
public interface ApplicationRoleAdminDOMapper
        extends Mapper<ApplicationRoleAdminDO>, InsertListMapper<ApplicationRoleAdminDO> {

}
