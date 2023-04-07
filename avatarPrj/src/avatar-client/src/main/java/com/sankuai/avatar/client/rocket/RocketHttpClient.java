package com.sankuai.avatar.client.rocket;

import com.sankuai.avatar.client.rocket.model.HostQueryRequest;
import com.sankuai.avatar.client.rocket.model.RocketHost;
import com.sankuai.avatar.client.rocket.response.RocketHostResponseData;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

/**
 * @author qinwei05
 */
public interface RocketHttpClient {

    /**
     * 获取appkey下主机
     *
     * @param appkey 名称
     * @return {@link RocketHostResponseData}<{@link RocketHost}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    RocketHostResponseData<RocketHost> getAppkeyHosts(String appkey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 依据查询条件获取appkey下主机
     *
     * @param hostQueryRequest 主机查询请求
     * @return {@link RocketHostResponseData}<{@link RocketHost}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    RocketHostResponseData<RocketHost> getAppkeyHostsByQueryRequest(HostQueryRequest hostQueryRequest) throws SdkCallException, SdkBusinessErrorException;
}
