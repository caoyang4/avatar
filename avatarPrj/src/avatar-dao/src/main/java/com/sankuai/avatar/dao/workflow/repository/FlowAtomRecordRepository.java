package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomRecordAddRequest;

/**
 * Atom 执行记录数据管理接口
 *
 * @author zhaozhifan02
 */
public interface FlowAtomRecordRepository {

    /**
     * 添加执行记录
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean addAtomRecord(FlowAtomRecordAddRequest request);
}
