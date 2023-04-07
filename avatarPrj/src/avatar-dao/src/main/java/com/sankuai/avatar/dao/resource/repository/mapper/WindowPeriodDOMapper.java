package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.ResourceWindowPeriodDO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author caoyang
 * @create 2023-03-15 15:54
 */
@Repository
public interface WindowPeriodDOMapper extends Mapper<ResourceWindowPeriodDO> {

    /**
     * 获取最新资源窗口id
     *
     * @return {@link Integer}
     */
    @Select("select max(id) from resource_host_period")
    Integer getMaxResourceWindowPeriodId();
}
