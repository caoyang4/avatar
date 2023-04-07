package com.sankuai.avatar.client.octo;

import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.octo.model.OctoProviderGroup;
import com.sankuai.avatar.client.octo.request.OctoNodeStatusQueryRequest;
import com.sankuai.avatar.client.octo.response.OctoNodeStatusResponse;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

import java.util.List;

/**
 * octo 接口 https://km.sankuai.com/page/1202105324
 * @author caoyang
 * @create 2022-12-13 14:31
 */
public interface OctoHttpClient {

    /**
     * 查询服务分组
     *
     * @param appkey appkey
     * @param env    环境
     * @param type   http、thrift
     * @return {@link List}<{@link OctoProviderGroup}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OctoProviderGroup> getOctoProviderGroup(String appkey, EnvEnum env, String type) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 查询服务OCTO节点状态
     * PS: 没有经过申请的接口一律不能使用，为申请限流值为0，会触发限流，返回604
     *
     * @param octoNodeStatusQueryRequest OCTO请求
     * @param env                        env
     * @return {@link OctoNodeStatusResponse}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OctoNodeStatusResponse getAppkeyOctoNodeStatus(OctoNodeStatusQueryRequest octoNodeStatusQueryRequest,
                                                   EnvEnum env)
            throws SdkCallException, SdkBusinessErrorException;
}
