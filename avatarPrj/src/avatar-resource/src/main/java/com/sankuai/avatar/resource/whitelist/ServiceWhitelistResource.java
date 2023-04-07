package com.sankuai.avatar.resource.whitelist;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.whitelist.bo.ServiceWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.request.ServiceWhitelistRequestBO;

import java.util.List;

/**
 * 服务白名单资源管理
 * @author caoyang
 * @create 2022-10-27 17:09
 */
public interface ServiceWhitelistResource {


    /**
     * 分页查询
     *
     * @param requestBO 请求
     * @return {@link PageResponse}<{@link ServiceWhitelistBO}>
     */
    PageResponse<ServiceWhitelistBO> queryPage(ServiceWhitelistRequestBO requestBO);

    /**
     * 查询服务白名单
     *
     * @param requestBO 请求
     * @return {@link List}<{@link ServiceWhitelistBO}>
     */
    List<ServiceWhitelistBO> query(ServiceWhitelistRequestBO requestBO);

    /**
     * 保存服务白名单
     *
     * @param serviceWhitelistBO BO
     * @return 是否成功
     */
    boolean save(ServiceWhitelistBO serviceWhitelistBO);

    /**
     * 根据条件删除
     *
     * @param requestBO 请求
     * @return 是否成功
     */
    boolean deleteByCondition(ServiceWhitelistRequestBO requestBO);

    /**
     * appkey-set是否加白
     *
     * @param appkey  appkey
     * @param type    白名单类型
     * @param setName set名称
     * @return boolean
     */
    boolean isWhiteOfAppkeySet(String appkey, WhiteType type, String setName);

    /**
     * 获取到期白名单
     *
     * @return {@link List}<{@link ServiceWhitelistBO}>
     */
    List<ServiceWhitelistBO> getExpireWhitelist();

}
