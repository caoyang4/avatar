package com.sankuai.avatar.client.nodemanager;

import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

import java.util.List;
import java.util.Map;

/**
 * HULK 宿主机生命周期管理系统（支持通用集群以及云原生集群宿主机）
 * @author qinwei05
 */
public interface NodeManagerHttpClient {

    /**
     * 获取容器宿主机特性信息
     *
     * @param parentHostsName 父母主机名称
     * @param env             env
     * @return {@link Map}<{@link String}, {@link List}<{@link String}>>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Map<String, List<String>> getHostsParentFeatures(List<String> parentHostsName, EnvEnum env) throws SdkCallException, SdkBusinessErrorException;
}
