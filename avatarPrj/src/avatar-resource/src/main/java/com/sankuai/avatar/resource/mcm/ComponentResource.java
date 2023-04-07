package com.sankuai.avatar.resource.mcm;

import com.sankuai.avatar.resource.mcm.request.CreateBusyPeriodRequestBO;

/**
 * @author zhaozhifan02
 */
public interface ComponentResource {
    /**
     * 增加高峰期解禁记录
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean createBusyPeriod(CreateBusyPeriodRequestBO request);
}
