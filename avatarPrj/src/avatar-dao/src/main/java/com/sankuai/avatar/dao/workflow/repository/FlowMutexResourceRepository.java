package com.sankuai.avatar.dao.workflow.repository;

import java.util.Map;

/**
 * 流程资源互斥管理(资源锁底层数据源控制)
 *
 * @author zhaozhifan02
 */
public interface FlowMutexResourceRepository {
    /**
     * 保存流程资源
     *
     * @param field 资源字段
     * @param value 资源值
     * @return 是否保存成功T
     */
    boolean save(String field, String value);

    /**
     * 删除流程资源
     *
     * @param field 资源字段
     * @return 是否删除成功
     */
    boolean delete(String field);

    /**
     * 流程资源是否存在
     *
     * @param field map 字段
     * @return 是否存在
     */
    boolean isExist(String field);

    /**
     * 基于 field 字段获取已加锁资源
     *
     * @param field map 字段
     * @return 资源
     */
    String getResourceByField(String field);

    /**
     * 获取所有流程资源
     *
     * @return 所有加锁的流程资源
     */
    Map<String, String> getTotalResource();
}
