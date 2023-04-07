package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.UserDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 操作UserDao的 mapper
 * @author caoyang
 * @create 2022-10-19 12:02
 */
@Repository
public interface UserDOMapper extends Mapper<UserDO>, InsertListMapper<UserDO> {
}

