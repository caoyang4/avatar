package com.sankuai.avatar.resource.capacity;

import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasStandardClientBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasStandardClientRequestBO;

import java.util.List;

/**
 * appkey paas容灾达标客户端信息资源管理
 * @author caoyang
 * @create 2022-10-11 16:23
 */
public interface AppkeyPaasStandardClientResource {

    /**
     * 条件查询 paas 容灾达标客户端信息
     * @param appkeyPaasStandardClientRequestBO BO
     * @return 数据列表
     */
    List<AppkeyPaasStandardClientBO> query(AppkeyPaasStandardClientRequestBO appkeyPaasStandardClientRequestBO);

    /**
     * 保存(insert/update) paas 容灾达标客户端信息
     * @param appkeyPaasStandardClientBO BO
     * @return 是否保存成功
     */
    boolean save(AppkeyPaasStandardClientBO appkeyPaasStandardClientBO);

}
