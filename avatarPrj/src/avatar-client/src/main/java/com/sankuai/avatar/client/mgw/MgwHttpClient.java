package com.sankuai.avatar.client.mgw;

import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.mgw.request.MgwVsRequest;
import com.sankuai.avatar.client.mgw.response.MgwVs;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

import java.util.List;

/**
 * @author qinwei05
 * @date 2022/12/25 16:30
 */
public interface MgwHttpClient {

    /**
     * 获取VS
     *
     * @param mgwVsRequest 与请求
     * @param env       env
     * @return list
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<MgwVs> getVsList(MgwVsRequest mgwVsRequest, EnvEnum env) throws SdkCallException, SdkBusinessErrorException;
}
