package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.UserRelationDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author caoyang
 * @create 2022-11-02 18:47
 */
@Repository
public interface UserRelationDOMapper extends Mapper<UserRelationDO>, InsertListMapper<UserRelationDO> {
}
