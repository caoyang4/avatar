package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.emergency.EmergencySreDTO;
import com.sankuai.avatar.web.request.EmergencySrePageRequest;

/**
 * @author caoyang
 * @create 2023-02-01 17:32
 */
public interface EmergencySreService {

    /**
     * 分页查询
     *
     * @param pageRequest pageRequest
     * @return {@link PageResponse}<{@link EmergencySreDTO}>
     */
    PageResponse<EmergencySreDTO> getPageEmergencySre(EmergencySrePageRequest pageRequest);

    /**
     * 主键获取EmergencySre
     *
     * @param pk 主键
     * @return {@link EmergencySreDTO}
     */
    EmergencySreDTO getEmergencySreByPk(int pk);

    /**
     * 保存
     *
     * @param dto dto
     * @return {@link Boolean}
     */
    Boolean saveEmergencySre(EmergencySreDTO dto);

    /**
     * 删除
     *
     * @param pk pk
     * @return {@link Boolean}
     */
    Boolean deleteEmergencySreByPk(int pk);

}
