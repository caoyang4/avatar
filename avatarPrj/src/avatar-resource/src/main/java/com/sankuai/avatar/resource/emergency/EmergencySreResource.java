package com.sankuai.avatar.resource.emergency;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.emergency.bo.EmergencySreBO;
import com.sankuai.avatar.resource.emergency.request.EmergencySreRequestBO;

/**
 * @author caoyang
 * @create 2023-01-20 15:36
 */
public interface EmergencySreResource {

    /**
     * 分页查询
     *
     * @param requestBO requestBO
     * @return {@link PageResponse}<{@link EmergencySreBO}>
     */
    PageResponse<EmergencySreBO> queryPage(EmergencySreRequestBO requestBO);

    /**
     * 保存
     *
     * @param emergencySreBO emergencySreBO
     * @return {@link Boolean}
     */
    Boolean save(EmergencySreBO emergencySreBO);

    /**
     * 删除
     *
     * @param requestBO requestBO
     * @return {@link Boolean}
     */
    Boolean deleteByCondition(EmergencySreRequestBO requestBO);

}
