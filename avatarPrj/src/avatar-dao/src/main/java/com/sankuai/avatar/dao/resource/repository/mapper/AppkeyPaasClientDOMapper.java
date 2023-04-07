package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasClientDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * AppkeyPaasClientDO对象 mapper
 * @author caoyang
 * @create 2022-09-27 16:17
 */
@Repository
public interface AppkeyPaasClientDOMapper
        extends Mapper<AppkeyPaasClientDO>, InsertListMapper<AppkeyPaasClientDO> {
}
