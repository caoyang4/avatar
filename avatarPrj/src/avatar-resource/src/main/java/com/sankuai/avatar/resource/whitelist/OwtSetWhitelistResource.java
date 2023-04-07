package com.sankuai.avatar.resource.whitelist;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.whitelist.bo.OwtSetWhitelistBO;
import com.sankuai.avatar.resource.whitelist.request.OwtSetWhitelistRequestBO;

import java.util.List;

/**
 * owt-set白名单资源管理
 * @author caoyang
 * @create 2022-10-27 17:09
 */
public interface OwtSetWhitelistResource {


    /**
     * 分页查询
     *
     * @param requestBO 请求
     * @return {@link PageResponse}<{@link OwtSetWhitelistBO}>
     */
    PageResponse<OwtSetWhitelistBO> queryPage(OwtSetWhitelistRequestBO requestBO);

    /**
     * 查询owt-set
     *
     * @param requestBO 请求
     * @return {@link List}<{@link OwtSetWhitelistBO}>
     */
    List<OwtSetWhitelistBO> query(OwtSetWhitelistRequestBO requestBO);

    /**
     * 保存owt-set
     *
     * @param owtSetWhitelistBO BO
     * @return 是否成功
     */
    boolean save(OwtSetWhitelistBO owtSetWhitelistBO);

    /**
     * 根据条件删除 owt-set
     *
     * @param requestBO 请求
     * @return 是否成功
     */
    boolean deleteByCondition(OwtSetWhitelistRequestBO requestBO);

    /**
     * owt-set是否加白
     *
     * @param owt     owt
     * @param setName set名称
     * @return boolean
     */
    boolean isWhiteOfOwtSet(String owt, String setName);

    /**
     * 获取到期白名单
     *
     * @return {@link List}<{@link OwtSetWhitelistBO}>
     */
    List<OwtSetWhitelistBO> getExpireWhitelist();

}
