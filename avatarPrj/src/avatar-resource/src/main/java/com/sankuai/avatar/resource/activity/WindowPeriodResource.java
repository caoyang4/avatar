package com.sankuai.avatar.resource.activity;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.bo.WindowPeriodResourceBO;
import com.sankuai.avatar.resource.activity.request.WindowPeriodRequestBO;

import java.util.List;

/**
 * 窗口期 resource 管理
 * @author caoyang
 * @create 2023-03-15 16:45
 */
public interface WindowPeriodResource {

    /**
     * 分页查询
     *
     * @param requestBO 请求
     * @return {@link PageResponse}<{@link WindowPeriodResourceBO}>
     */
    PageResponse<WindowPeriodResourceBO> queryPage(WindowPeriodRequestBO requestBO);

    /**
     * 获取最大窗口 id
     *
     * @return {@link Integer}
     */
    Integer getMaxWindowId();

    /**
     * 查询
     *
     * @param requestBO 请求
     * @return {@link List}<{@link WindowPeriodResourceBO}>
     */
    List<WindowPeriodResourceBO> query(WindowPeriodRequestBO requestBO);


    /**
     * 保存
     *
     * @param resourceBO resourceBO
     * @return {@link Boolean}
     */
    Boolean save(WindowPeriodResourceBO resourceBO);

    /**
     * 删除
     *
     * @param pk pk
     * @return {@link Boolean}
     */
    Boolean deleteByPk(int pk);

}
