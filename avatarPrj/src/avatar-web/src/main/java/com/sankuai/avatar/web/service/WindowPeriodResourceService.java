package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.activity.WindowPeriodResourceDTO;
import com.sankuai.avatar.web.request.WindowPeriodPageRequest;

/**
 * 窗口期资源管理
 * @author caoyang
 * @create 2023-03-15 17:19
 */
public interface WindowPeriodResourceService {

    /**
     * 分页查询
     *
     * @param pageRequest pageRequest
     * @return {@link PageResponse}<{@link WindowPeriodResourceDTO}>
     */
    PageResponse<WindowPeriodResourceDTO> queryPage(WindowPeriodPageRequest pageRequest);

    /**
     * 根据主键查询窗口期
     *
     * @param id id
     * @return {@link WindowPeriodResourceDTO}
     */
    WindowPeriodResourceDTO getWindowPeriodByPk(int id);

    /**
     * 获取最新窗口id
     *
     * @return {@link Integer}
     */
    Integer getMaxWindowId();

    /**
     * 保存
     *
     * @param resourceDTO resourceDTO
     * @return {@link Boolean}
     */
    Boolean save(WindowPeriodResourceDTO resourceDTO);

    /**
     * 删除pk
     *
     * @param pk pk
     * @return {@link Boolean}
     */
    Boolean deleteByPk(int pk);


    /**
     * 查询命中窗口期
     *
     * @param pk pk
     * @return {@link WindowPeriodResourceDTO}
     */
    WindowPeriodResourceDTO getHitWindowPeriod(Integer pk);

}
