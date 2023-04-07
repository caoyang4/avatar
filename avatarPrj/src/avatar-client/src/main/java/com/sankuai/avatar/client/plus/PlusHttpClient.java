package com.sankuai.avatar.client.plus;

import com.sankuai.avatar.client.plus.model.PlusRelease;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

import java.util.List;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/10/7 16:30
 * @version 1.0
 */
public interface PlusHttpClient {

    /**
     * 获取绑定关系的发布项
     * @param appkey 服务
     * @return list
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<PlusRelease> getBindPlusByAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取应用关系的发布项
     *
     * @param appkey 服务
     * @return list
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<PlusRelease> getAppliedPlusByAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException;
}
