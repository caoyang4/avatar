package com.sankuai.avatar.client.kapiserver;

import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.kapiserver.model.HostFeature;
import com.sankuai.avatar.client.kapiserver.model.VmHostDiskFeature;
import com.sankuai.avatar.client.kapiserver.request.VmHostDiskQueryRequest;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

import java.util.List;
import java.util.Map;

/**
 * HULK2.0集群调度系统 - APIServer模块服务
 * @author qinwei05
 */
public interface KApiServerHttpClient {

    /**
     * 获得HULK主机特性
     *
     * @param hostNameList 主机名称列表
     * @param env          env
     * @return {@link Map}<{@link String}, {@link HostFeature}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Map<String, HostFeature> getHulkHostsFeatures(List<String> hostNameList, EnvEnum env) throws SdkCallException, SdkBusinessErrorException;

    /**
     * vm主机磁盘特性
     *
     * @param request 请求
     * @return {@link List}<{@link VmHostDiskFeature}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<VmHostDiskFeature> getVmHostsDiskFeatures(VmHostDiskQueryRequest request) throws SdkCallException, SdkBusinessErrorException;
}
