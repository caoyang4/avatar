package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowPublicDataUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowQueryRequest;

import java.util.List;

/**
 * @author zhaozhifan02
 */
public interface FlowRepository {

    /**
     * 查询
     *
     * @param request 请求
     * @return {@link List}<{@link FlowEntity}>
     */
    PageResponse<FlowEntity> queryPage(FlowQueryRequest request);

    /**
     * 根据流程UUID获取流程详情
     *
     * @param uuid UUID
     * @return {@link FlowEntity}
     */
    FlowEntity getFlowEntityByUuid(String uuid);

    /**
     * 根据流程UUID获取流程详情
     *
     * @param id UUID
     * @return {@link FlowEntity}
     */
    FlowEntity getFlowEntityById(Integer id);

    /**
     * 创建流程
     *
     * @param flowEntity {@link FlowEntity}
     * @return boolean
     */
    boolean addFlow(FlowEntity flowEntity);

    /**
     * 更新流程状态
     *
     * @param flowEntity {@link FlowEntity}
     * @return boolean
     */
    boolean updateFlow(FlowEntity flowEntity);

    /**
     * 兼容V1老数据，更新老的 Pickler 类型字段
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean updatePublicData(FlowPublicDataUpdateRequest request);
}
