package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasStandardClientDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasStandardClientRequest;

import java.util.List;

/**
 * Appkey Paas容灾达标客户端数据管理
 * @author caoyang
 * @create 2022-09-27 11:36
 */
public interface AppkeyPaasStandardClientRepository {
    /**
     * 查询对象
     * @param request 查询对象
     * @return DOList
     */
    List<AppkeyPaasStandardClientDO> query(AppkeyPaasStandardClientRequest request);

    /**
     * 新增
     * @param appkeyPaasStandardClientDO  do
     * @return 是否写入成功
     */
    boolean insert(AppkeyPaasStandardClientDO appkeyPaasStandardClientDO);

    /**
     * 批量新增
     * @param appkeyPaasStandardClientDOList DOList
     * @return 生效数量
     */
    int insertBatch(List<AppkeyPaasStandardClientDO> appkeyPaasStandardClientDOList);

    /**
     * 更新
     * @param appkeyPaasStandardClientDO DO
     * @return 是否写入成功
     */
    boolean update(AppkeyPaasStandardClientDO appkeyPaasStandardClientDO);
}
