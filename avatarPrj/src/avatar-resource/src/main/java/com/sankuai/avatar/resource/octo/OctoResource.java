package com.sankuai.avatar.resource.octo;

import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.octo.model.OctoProviderGroup;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.octo.model.OctoNodeStatusResponseBO;
import com.sankuai.avatar.resource.octo.model.OctoProviderGroupBO;
import com.sankuai.avatar.resource.octo.request.OctoNodeStatusQueryRequestBO;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-13 16:25
 */
public interface OctoResource {

    /**
     * 查询服务路由分组
     *
     * @param appkey appkey
     * @param env    环境
     * @param type   http、thrift
     * @return {@link List}<{@link OctoProviderGroup}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OctoProviderGroupBO> getOctoProviderGroup(String appkey, EnvEnum env, String type) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 查询服务OCTO节点状态
     *
     * @param octoNodeStatusQueryRequestBO OCTO请求
     * @param env                          env
     * @return <{@link OctoNodeStatusResponseBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OctoNodeStatusResponseBO getAppkeyOctoNodeStatus(OctoNodeStatusQueryRequestBO octoNodeStatusQueryRequestBO,
                                                     EnvEnum env)
            throws SdkCallException, SdkBusinessErrorException;
}
