package com.sankuai.avatar.resource.capacity;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacityBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyCapacityRequestBO;

import java.util.List;

/**
 * 服务业务容灾资源管理接口
 * @author caoyang
 * @create 2022-11-03 19:40
 */
public interface AppkeyCapacityResource {

    /**
     * 分页查询
     *
     * @param appkeyCapacityRequestBO 请求对象
     * @return {@link PageResponse}<{@link AppkeyCapacityBO}>
     */
    PageResponse<AppkeyCapacityBO> queryPage(AppkeyCapacityRequestBO appkeyCapacityRequestBO);

    /**
     * 查询
     *
     * @param appkeyCapacityRequestBO 请求对象
     * @return {@link List}<{@link AppkeyCapacityBO}>
     */
    List<AppkeyCapacityBO> query(AppkeyCapacityRequestBO appkeyCapacityRequestBO);

    /**
     * 保存
     *
     * @param appkeyCapacityBO appkeyCapacityBO
     * @return boolean
     */
    boolean save(AppkeyCapacityBO appkeyCapacityBO);

    /**
     * 根据条件删除
     *
     * @param appkeyCapacityRequestBO 请求对象
     * @return boolean
     */
    boolean deleteByCondition(AppkeyCapacityRequestBO appkeyCapacityRequestBO);

    /**
     * 更新ops容灾
     *
     * @param appkey        appkey
     * @param capacityLevel 容灾
     * @param reason        原因
     * @return boolean
     */
    boolean updateOpsCapacity(String appkey, String reason, Integer capacityLevel);

}
