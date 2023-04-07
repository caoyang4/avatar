package com.sankuai.avatar.dao.es;

import com.sankuai.avatar.dao.es.request.FlowAuditUpdateRequest;

/**
 * 流程审核数据ES数据管理接口
 *
 * @author zhaozhifan02
 */
public interface FlowAuditEsRepository {
    /**
     * 更新流程数据
     *
     * @param request 流程更新参数
     * @return boolean
     */
    boolean update(FlowAuditUpdateRequest request);
}
