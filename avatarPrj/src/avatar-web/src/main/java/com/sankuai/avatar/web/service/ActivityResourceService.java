package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.activity.ActivityResourceDTO;
import com.sankuai.avatar.web.request.ActivityResourcePageRequest;
import com.sankuai.avatar.web.vo.activity.ResourceSumResult;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-03-08 15:41
 */
public interface ActivityResourceService {

    /**
     * 查询活动资源
     *
     * @param pageRequest 请求
     * @return {@link PageResponse}<{@link ActivityResourceDTO}>
     */
    PageResponse<ActivityResourceDTO> getPageActivityResource(ActivityResourcePageRequest pageRequest);

    /**
     * 查询活动资源
     *
     * @param pageRequest 请求
     * @return {@link List}<{@link ActivityResourceDTO}>
     */
    List<ActivityResourceDTO> getActivityResource(ActivityResourcePageRequest pageRequest);

    /**
     * 主键获取活动资源
     *
     * @param pk 主键
     * @return {@link ActivityResourceDTO}
     */
    ActivityResourceDTO getActivityResourceByPk(int pk);

    /**
     * 根据活动窗口id查询活动资源
     *
     * @param windowId 窗口id
     * @return {@link List}<{@link ActivityResourceDTO}>
     */
    List<ActivityResourceDTO> getActivityResourceByWindowId(Integer windowId);

    /**
     * 保存
     *
     * @param resourceDTO resourceDTO
     * @return {@link Boolean}
     */
    Boolean save(ActivityResourceDTO resourceDTO);

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    Boolean delete(int id);

    /**
     * 汇总资源数据
     *
     * @param windowPeriodId 窗口期id
     * @return {@link ResourceSumResult}
     */
    ResourceSumResult getActivityResourceSum(Integer windowPeriodId);

}
