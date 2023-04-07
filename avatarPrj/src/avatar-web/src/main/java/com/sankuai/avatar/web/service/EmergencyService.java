package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.emergency.EmergencyResourceDTO;
import com.sankuai.avatar.web.request.EmergencyResourcePageRequest;

/**
 * @author caoyang
 * @create 2022-12-30 16:40
 */
public interface EmergencyService {

    /**
     * 分页查询
     *
     * @param pageRequest 页面请求
     * @return {@link PageResponse}<{@link EmergencyResourceDTO}>
     */
    PageResponse<EmergencyResourceDTO> queryPage(EmergencyResourcePageRequest pageRequest);

    /**
     * 保存
     *
     * @param dto dto
     * @return {@link Boolean}
     */
    Boolean saveEmergencyResource(EmergencyResourceDTO dto);

    /**
     * 根据主键删除紧急资源
     *
     * @param pk 主键
     * @return {@link Boolean}
     */
    Boolean deleteEmergencyResourceByPk(int pk);

}
