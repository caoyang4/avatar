package com.sankuai.avatar.dao.es;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.es.entity.FlowSearchEntity;
import com.sankuai.avatar.dao.es.exception.EsException;
import com.sankuai.avatar.dao.es.request.FlowSearchQueryRequest;
import com.sankuai.avatar.dao.es.request.FlowSearchUpdateRequest;

/**
 * 流程查询类ES数据管理接口
 *
 * @author zhaozhifan02
 */
public interface FlowSearchEsRepository {
    /**
     * 更新流程查询类数据到ES
     *
     * @param request {@link FlowSearchUpdateRequest}
     * @return boolean
     */
    boolean update(FlowSearchUpdateRequest request);

    /**
     * 页面查询
     * 获取流程查询分页数据
     *
     * @param request  流程查询参数
     * @param page     页码
     * @param pageSize 每页大小
     * @return PageResponse<FlowSearchEntity>
     * @throws EsException es异常
     */
    PageResponse<FlowSearchEntity> pageQuery(FlowSearchQueryRequest request, int page, int pageSize) throws EsException;

}
