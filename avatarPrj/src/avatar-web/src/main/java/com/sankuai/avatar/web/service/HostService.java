package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.host.*;
import com.sankuai.avatar.web.request.GroupTagQueryRequest;
import com.sankuai.avatar.web.request.HostQueryRequest;

import java.util.List;

/**
 * @author qinwei05
 */
public interface HostService {

    /**
     * 分页获取srv机器列表（支持筛选
     *
     * @param hostQueryRequest 请求
     * @return 机器列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    PageResponse<HostAttributesDTO> getSrvHostsByQueryRequest(HostQueryRequest hostQueryRequest) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 全量获取srv机器列表
     *
     * @param hostQueryRequest 主机查询请求
     * @return {@link List}<{@link HostAttributesDTO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<HostAttributesDTO> getOriginSrvHostsByQueryRequest(HostQueryRequest hostQueryRequest) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取单个主机信息
     *
     * @param name IP/Name
     * @return 机器信息
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    HostDTO getHostInfo(String name) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获得主机汇总属性
     *
     * @param request 请求
     * @return {@link HostSumAttributeDTO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    HostSumAttributeDTO getHostSumAttribute(HostQueryRequest request) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 统计服务各个环境的机器数目
     *
     * @param request 请求
     * @return {@link HostCountDTO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    HostCountDTO getHostsCountByQueryRequest(HostQueryRequest request) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 统计服务下指定环境的SET信息
     *
     * @param request 请求
     * @return {@link List}<{@link HostCellDTO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<HostCellDTO> getHostCellByQueryRequest(HostQueryRequest request) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取服务下某个环境的机器、机房分布
     *
     * @param request 请求
     * @return {@link HostIdcDistributedDTO}
     */
    HostIdcDistributedDTO getAppkeyHostDistributed(HostQueryRequest request);

    /**
     * 获取公司机房元数据
     *
     * @return {@link List}<{@link IdcMetaDataDTO}>
     */
    List<IdcMetaDataDTO> getIdc();

    /**
     * 获得外采主机信息
     *
     * @param name 名字
     * @return {@link ExternalHostDTO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    ExternalHostDTO getExternalHostInfo(String name) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取外采机器宿主机信息
     *
     * @param name IP/Name
     * @return {@link List}<{@link HostDTO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<HostDTO> getExternalParentHostInfo(String name) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 从Dayu系统获取服务的业务分组信息（若Dayu系统异常则不展示）
     *
     * @param request 请求
     * @return {@link List}<{@link HostCellDTO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<GroupTagDTO> getGrouptags(GroupTagQueryRequest request);
}
