package com.sankuai.avatar.client.dayu;

import com.sankuai.avatar.client.dayu.model.DayuGroupTag;
import com.sankuai.avatar.client.dayu.model.GroupTagQueryRequest;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

import java.util.List;

/**
 * WIKI：<a href="https://km.sankuai.com/page/141081850">Dayu接口文档</a>
 * @author qinwei05
 */
public interface DayuHttpClient {

    /**
     * 获取Dayu系统业务分组信息
     *
     * @param groupTagQueryRequest 请求
     * @return {@link DayuGroupTag}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<DayuGroupTag> getGrouptags(GroupTagQueryRequest groupTagQueryRequest) throws SdkCallException, SdkBusinessErrorException;
}
