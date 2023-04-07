package com.sankuai.avatar.resource.plus;

import com.sankuai.avatar.resource.plus.bo.PlusReleaseBO;

import java.util.List;

/**
 * 服务发布项信息接口
 * @author qinwei05
 * @date 2022/10/19 11:56
 * @version 1.0
 */

public interface PlusResource {

    /**
     * 获取appkey绑定的发布项列表
     * @param appkey 服务
     * @return 发布项列表
     */
    List<PlusReleaseBO> getBindPlusByAppkey(String appkey);

    /**
     * 获取appkey应用的发布项列表
     * @param appkey 服务
     * @return 发布项列表
     */
    List<PlusReleaseBO> getAppliedPlusByAppkey(String appkey);

}
