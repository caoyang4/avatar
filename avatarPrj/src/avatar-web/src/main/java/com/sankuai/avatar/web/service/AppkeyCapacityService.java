package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacityDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacitySummaryDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasCapacityDTO;
import com.sankuai.avatar.web.request.AppkeyCapacityPageRequest;

import java.util.List;
import java.util.Set;

/**
 * 服务业务容灾Service接口
 * @author caoyang
 * @create 2022-09-21 15:55
 */
public interface AppkeyCapacityService {

    /**
     * 分页查询
     *
     * @param pageRequest 分页请求对象
     * @return {@link PageResponse}<{@link AppkeyCapacityDTO}>
     */
    PageResponse<AppkeyCapacityDTO> queryPage(AppkeyCapacityPageRequest pageRequest);

    /**
     * 根据 appkey 查询容灾信息
     *
     * @param appkey appkey
     * @return {@link List}<{@link AppkeyCapacityDTO}>
     */
    List<AppkeyCapacityDTO> getAppkeyCapacityByAppkey(String appkey);

    /**
     * 查询不达标容灾信息
     *
     * @return {@link List}<{@link AppkeyPaasCapacityDTO}>
     */
    List<AppkeyCapacityDTO> getUnStandardAppkeyCapacity();

    /**
     * 计算整体业务容灾
     *
     * @param appkey appkey
     * @return {@link AppkeyCapacitySummaryDTO}
     */
    AppkeyCapacitySummaryDTO getAppkeyCapacitySummary(String appkey);

    /**
     * 保存 appkey 容灾信息
     *
     * @param appkeyCapacityDTOList appkey paas dtolist能力
     * @return boolean
     */
    boolean saveAppkeyCapacity(List<AppkeyCapacityDTO> appkeyCapacityDTOList);

    /**
     * 根据给定的 set 集合，与库中appkey 的 set 对比，
     * 若给定set集合中有，而库中没有，则该 set 为废弃 set，需要将其清理
     * @param appkey appkey
     * @param setList    setList
     * @return boolean
     */
    boolean clearAppkeyCapacityInvalidSet(String appkey, Set<String> setList);


    /**
     * 清理已下线的 appkey 容灾数据
     *
     * @param appkey appkey
     * @return boolean
     */
    boolean clearAppkeyCapacityByAppkey(String appkey);


    /**
     * 获取所有容灾 appkey
     *
     * @return {@link List}<{@link String}>
     */
    Set<String> getAllCapacityAppkey();

    /**
     * 更新ops容灾
     *
     * @param appkey        appkey
     * @param reason        原因
     * @param capacityLevel 容灾等级
     * @return {@link Boolean}
     */
    Boolean updateOpsCapacity(String appkey, String reason, Integer capacityLevel);

    /**
     * 服务 set 容灾是否存在
     *
     * @param appkey appkey
     * @param set    set
     * @return {@link Boolean}
     */
    Boolean isExistAppkeySetCapacity(String appkey, String set);

}
