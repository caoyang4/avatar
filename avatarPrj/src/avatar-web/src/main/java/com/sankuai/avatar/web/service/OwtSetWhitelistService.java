package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.whitelist.OwtSetWhitelistDTO;
import com.sankuai.avatar.web.request.OwtSetWhitelistPageRequest;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-15 11:36
 */
public interface OwtSetWhitelistService {

    /**
     * 分页查询
     *
     * @param pageRequest 请求
     * @return {@link PageResponse}<{@link OwtSetWhitelistDTO}>
     */
    PageResponse<OwtSetWhitelistDTO> queryPage(OwtSetWhitelistPageRequest pageRequest);

    /**
     * 保存owt-set白名单
     *
     * @param owtSetWhitelistDTO owt设置白名单dto
     * @return boolean
     */
    boolean saveOwtSetWhitelist(OwtSetWhitelistDTO owtSetWhitelistDTO);

    /**
     * 根据 owt 查询白名单
     *
     * @param owt owt
     * @return {@link List}<{@link OwtSetWhitelistDTO}>
     */
    List<OwtSetWhitelistDTO> getOwtSetWhitelistByOwt(String owt);

    /**
     * 根据 owt-set 查询白名单
     *
     * @param owt     owt
     * @param setName set名称
     * @return {@link List}<{@link OwtSetWhitelistDTO}>
     */
    List<OwtSetWhitelistDTO> getCapacityWhitelistByOwtAndSet(String owt, String setName);

    /**
     * 根据主键删除白名单
     *
     * @param pk pk
     * @return {@link Boolean}
     */
    Boolean deletetOwtSetWhitelistByPk(int pk);

}
