package com.sankuai.avatar.web.service;

import com.sankuai.avatar.web.dto.PlusReleaseDTO;

import java.util.List;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/10/19 13:33
 * @version 1.0
 */
public interface PlusService {

    /**
     * 获取appkey绑定的发布项列表
     * @param appkey 服务
     * @return 发布项列表
     */
    List<PlusReleaseDTO> getBindPlusByAppkey(String appkey);

    /**
     * 获取appkey应用的发布项列表
     * @param appkey 服务
     * @return 发布项列表
     */
    List<PlusReleaseDTO> getAppliedPlusByAppkey(String appkey);

}
