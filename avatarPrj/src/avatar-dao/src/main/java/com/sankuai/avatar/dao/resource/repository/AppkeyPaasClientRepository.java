package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasClientDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasClientRequest;

import java.util.List;

/**
 * Appkey Paas客户端数据管理
 * @author caoyang
 * @create 2022-09-27 11:22
 */
public interface AppkeyPaasClientRepository {
    /**
     * 查询对象
     * @param request 查询对象
     * @return do list
     */
    List<AppkeyPaasClientDO> query(AppkeyPaasClientRequest request);

    /**
     * 新增
     * @param appkeyPaasClientDO  do
     * @return 是否写入成功
     */
    boolean insert(AppkeyPaasClientDO appkeyPaasClientDO);

    /**
     * 批量新增
     * @param appkeyPaasClientDOList list
     * @return 生效数量
     */
    int insertBatch(List<AppkeyPaasClientDO> appkeyPaasClientDOList);

    /**
     * 更新
     * @param appkeyPaasClientDO do
     * @return 是否写入成功
     */
    boolean update(AppkeyPaasClientDO appkeyPaasClientDO);

    /**
     * 根据特定条件删除数据
     * @param id 主键
     * @return 是否删除成功
     */
    boolean delete(int id);
}
