package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.ActivityResourceDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author caoyang
 * @create 2023-03-06 16:57
 */
@Repository
public interface ActivityResourceDOMapper extends Mapper<ActivityResourceDO>, InsertListMapper<ActivityResourceDO> {

}
