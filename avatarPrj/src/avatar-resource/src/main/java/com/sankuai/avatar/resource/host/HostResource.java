package com.sankuai.avatar.resource.host;

import com.sankuai.avatar.client.dayu.model.DayuGroupTag;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.host.bo.*;
import com.sankuai.avatar.resource.host.request.GroupTagQueryRequestBO;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import com.sankuai.avatar.resource.host.request.VmHostDiskQueryRequestBO;

import java.util.List;
import java.util.Map;

/**
 * @author zhaozhifan02
 */
public interface HostResource {
    /**
     * 获取 appKey 机器列表
     *
     * @param appKey 服务 appKey
     * @return 机器列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<HostBO> getHostsByAppKey(String appKey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取 srv 机器列表
     *
     * @param srv 服务树节点
     * @return 机器列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<HostBO> getHostsBySrv(String srv) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 筛选获取srv机器列表
     *
     * @param hostQueryRequestBO 请求
     * @return 机器列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<HostAttributesBO> getSrvHostsByQueryRequest(HostQueryRequestBO hostQueryRequestBO) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取单个主机信息
     *
     * @param name IP/Name
     * @return 机器信息
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    HostBO getHostInfo(String name) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 补充主机标签与特性
     *
     * @param hostList 主机列表
     * @param env      env
     * @return {@link List}<{@link HostAttributesBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<HostAttributesBO> patchHostTagsAndFeatures(List<HostAttributesBO> hostList, String env)  throws SdkCallException, SdkBusinessErrorException;

    /**
     * 补充VM主机磁盘标签
     *
     * @param hostList 主机列表
     * @param appkey   appkey
     * @param env      env
     * @return {@link List}<{@link HostAttributesBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<HostAttributesBO> patchVmHostDiskType(List<HostAttributesBO> hostList, String appkey, String env)  throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取机房信息
     *
     * @return {@link List}<{@link IdcMetaDataBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<IdcMetaDataBO> getIdcList() throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取机房映射关系
     *
     * @return {@link Map}<{@link String}, {@link String}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Map<String, String> getIdcMap() throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获得HULK主机特性
     *
     * @param hostNameList 主机名称列表
     * @param env          env
     * @return {@link Map}<{@link String}, {@link HostFeatureBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Map<String, HostFeatureBO> getHulkHostsFeatures(List<String> hostNameList, EnvEnum env) throws SdkCallException, SdkBusinessErrorException;

    /**
     * vm主机磁盘特性
     *
     * @param request 请求
     * @return {@link List}<{@link VmHostFeatureBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<VmHostFeatureBO> getVmHostsDiskFeatures(VmHostDiskQueryRequestBO request) throws SdkCallException, SdkBusinessErrorException;

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

    /**
     * 获取Dayu系统业务分组信息
     *
     * @param request 请求
     * @return {@link DayuGroupTag}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<DayuGroupTagBO> getGrouptags(GroupTagQueryRequestBO request) throws SdkCallException, SdkBusinessErrorException;
}
