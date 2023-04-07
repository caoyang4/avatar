package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.DxGroupDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 大象群 mapper
 * @author caoyang
 * @create 2022-11-01 19:59
 */
@Repository
public interface DxGroupDOMapper extends Mapper<DxGroupDO>, InsertListMapper<DxGroupDO> {
}
