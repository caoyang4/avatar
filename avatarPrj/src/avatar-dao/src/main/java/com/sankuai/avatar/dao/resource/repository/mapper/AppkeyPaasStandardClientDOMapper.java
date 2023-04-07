package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasStandardClientDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 *  AppkeyPaasStandardClientDO对象的 mapper
 * @author caoyang
 * @create 2022-09-27 16:19
 */
@Repository
public interface AppkeyPaasStandardClientDOMapper
        extends Mapper<AppkeyPaasStandardClientDO>, InsertListMapper<AppkeyPaasStandardClientDO> {
}
