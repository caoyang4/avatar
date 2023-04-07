package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.OwtSetWhitelistDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * OwtSetWhitelistDO对象的 mapper
 * @author caoyang
 * @create 2022-10-21 15:41
 */
@Repository
public interface OwtSetWhitelistDOMapper extends Mapper<OwtSetWhitelistDO>, InsertListMapper<OwtSetWhitelistDO> {

}
