package com.sankuai.avatar.resource.capacity;

import com.sankuai.avatar.resource.capacity.request.AppkeyPaasClientRequestBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasClientBO;

import java.util.List;

/**
 * appkey paas客户端信息资源管理
 * @author caoyang
 * @create 2022-10-11 16:22
 */
public interface AppkeyPaasClientResource {

    /**
     * 查询paas客户端数据
     * @param appkeyPaasClientRequestBO 条件查询对象
     * @return 数据列表
     */
    List<AppkeyPaasClientBO> query(AppkeyPaasClientRequestBO appkeyPaasClientRequestBO);

    /**
     * 保存(insert/update)paas客户端数据
     * @param appkeyPaasClientBO BO 对象
     * @return 是否保存成功
     */
    boolean save(AppkeyPaasClientBO appkeyPaasClientBO);

    /**
     * 根据条件删除paas客户端数据
     * @param appkeyPaasClientRequestBO 条件对象
     * @return 是否删除成功
     */
    boolean deleteByCondition(AppkeyPaasClientRequestBO appkeyPaasClientRequestBO);
}
