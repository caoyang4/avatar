package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.ServiceWhitelistDO;
import com.sankuai.avatar.dao.resource.repository.request.ServiceWhitelistRequest;

import java.util.List;

/**
 * 服务白名单数据管理
 * @author caoyang
 * @create 2022-10-21 15:39
 */
public interface ServiceWhitelistRepository {

    /**
     * 根据条件查询服务白名单
     * @param serviceWhitelistRequest 条件对象
     * @return DOList
     */
    List<ServiceWhitelistDO> query(ServiceWhitelistRequest serviceWhitelistRequest);

    /**
     * 新增服务白名单
     * @param serviceWhitelistDO DO
     * @return 是否成功
     */
    boolean insert(ServiceWhitelistDO serviceWhitelistDO);

    /**
     * 更新服务白名单
     * @param serviceWhitelistDO DO
     * @return 是否成功
     */
    boolean update(ServiceWhitelistDO serviceWhitelistDO);

    /**
     * 删除服务白名单
     * @param id 主键 id
     * @return 是否成功
     */
    boolean delete(int id);
}
