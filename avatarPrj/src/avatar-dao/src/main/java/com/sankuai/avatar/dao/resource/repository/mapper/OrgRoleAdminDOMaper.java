package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.OrgRoleAdminDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * OrgRoleAdmin mapper
 * @author caoyang
 * @create 2022-11-01 19:58
 */
@Repository
public interface OrgRoleAdminDOMaper extends Mapper<OrgRoleAdminDO>, InsertListMapper<OrgRoleAdminDO> {
}
