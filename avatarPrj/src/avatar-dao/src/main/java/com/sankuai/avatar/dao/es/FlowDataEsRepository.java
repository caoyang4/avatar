package com.sankuai.avatar.dao.es;

import com.sankuai.avatar.dao.es.request.FlowDataUpdateRequest;

/**
 * 流程es数据管理接口
 *
 * @author zhaozhifan02
 */
public interface FlowDataEsRepository {
    /**
     * 更新流程数据
     *
     * @param request 流程更新参数
     * @return boolean
     */
    boolean update(FlowDataUpdateRequest request);
}
