package com.sankuai.avatar.resource.emergency;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.emergency.bo.EmergencyResourceBO;
import com.sankuai.avatar.resource.emergency.request.EmergencyResourceRequestBO;

/**
 * 紧急资源管理对象
 * @author caoyang
 * @create 2022-12-02 22:19
 */
public interface EmergencyHostResource {

    /**
     * 分页查询紧急资源
     *
     * @param bo 薄
     * @return {@link PageResponse}<{@link EmergencyResourceBO}>
     */
    PageResponse<EmergencyResourceBO> queryPage(EmergencyResourceRequestBO bo);

    /**
     * 保存
     *
     * @param bo 薄
     * @return {@link Boolean}
     */
    Boolean save(EmergencyResourceBO bo);

    /**
     * 根据条件删除信息
     *
     * @param bo 薄
     * @return {@link Boolean}
     */
    Boolean deleteByCondition(EmergencyResourceRequestBO bo);

}
