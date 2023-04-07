package com.sankuai.avatar.dao.es;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.es.exception.EsException;
import com.sankuai.avatar.dao.es.request.AppkeyQueryRequest;
import com.sankuai.avatar.dao.es.request.AppkeyTreeRequest;
import com.sankuai.avatar.dao.es.request.AppkeyUpdateRequest;
import com.sankuai.avatar.dao.es.request.UserAppkeyRequest;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;

import java.util.List;

/**
 * ES 客户端
 *
 * @author qinwei05
 */
public interface AppkeyEsRepository {

    /**
     * 搜索数据
     *
     * @param queryRequest 查询参数
     * @return SearchResponse
     * @throws EsException es异常
     */
    PageResponse<AppkeyDO> search(AppkeyQueryRequest queryRequest) throws EsException;

    /**
     * 我的服务
     *
     * @param request 查询条件
     * @return {@link List}<{@link AppkeyDO}>
     * @throws EsException es异常
     */
    List<AppkeyDO> getOwnAppkey(UserAppkeyRequest request) throws EsException;

    /**
     * 我的服务(默认按照置顶排序)
     *
     * @param request    查询条件
     * @param topAppkeys 用户置顶appkeys
     * @return {@link List}<{@link AppkeyDO}>
     * @throws EsException es异常
     */
    PageResponse<AppkeyDO> getOwnAppkey(UserAppkeyRequest request, List<String> topAppkeys) throws EsException;

    /**
     * 服务树-全部服务
     *
     * @param request 请求
     * @return {@link PageResponse}<{@link AppkeyDO}>
     * @throws EsException es异常
     */
    PageResponse<AppkeyDO> getPageAppkey(AppkeyTreeRequest request) throws EsException;

    /**
     * 精确查询
     *
     * @param queryRequest 查询请求
     * @return {@link List}<{@link AppkeyDO}>
     * @throws EsException es异常
     */
    PageResponse<AppkeyDO> query(AppkeyQueryRequest queryRequest) throws EsException;

    /**
     * 保存和更新
     *
     * @param appkeyUpdateRequest 数据内容
     * @return boolean
     * @throws EsException es异常
     */
    Boolean update(AppkeyUpdateRequest appkeyUpdateRequest) throws EsException;
}
