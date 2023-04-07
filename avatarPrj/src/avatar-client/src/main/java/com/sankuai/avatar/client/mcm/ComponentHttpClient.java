package com.sankuai.avatar.client.mcm;

import com.sankuai.avatar.client.mcm.request.CreateBusyPeriodRequest;

/**
 * @author zhaozhifan02
 */
public interface ComponentHttpClient {
    /**
     * 增加高峰期解禁记录
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean createBusyPeriod(CreateBusyPeriodRequest request);
}
