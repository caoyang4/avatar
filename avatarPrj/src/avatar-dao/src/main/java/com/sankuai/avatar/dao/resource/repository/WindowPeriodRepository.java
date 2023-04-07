package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.ResourceWindowPeriodDO;
import com.sankuai.avatar.dao.resource.repository.request.WindowPeriodRequest;

import java.util.List;

/**
 * 窗口期数据层
 * @author caoyang
 * @create 2023-03-15 16:03
 */
public interface WindowPeriodRepository {

    /**
     * 查询
     *
     * @param request 请求
     * @return {@link List}<{@link ResourceWindowPeriodDO}>
     */
    List<ResourceWindowPeriodDO> query(WindowPeriodRequest request);

    /**
     * 获取最新窗口 id
     *
     * @return {@link Integer}
     */
    Integer getMaxWindowId();

    /**
     * 插入
     *
     * @param resourceWindowPeriodDO 资源窗口期
     * @return {@link Boolean}
     */
    Boolean insert(ResourceWindowPeriodDO resourceWindowPeriodDO);

    /**
     * 更新
     *
     * @param resourceWindowPeriodDO 资源窗口期
     * @return {@link Boolean}
     */
    Boolean update(ResourceWindowPeriodDO resourceWindowPeriodDO);

    /**
     * 删除
     *
     * @param pk pk
     * @return {@link Boolean}
     */
    Boolean delete(int pk);

}
