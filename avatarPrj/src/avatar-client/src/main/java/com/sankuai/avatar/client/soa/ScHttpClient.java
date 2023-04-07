package com.sankuai.avatar.client.soa;

import com.sankuai.avatar.client.soa.model.*;
import com.sankuai.avatar.client.soa.request.IsoltAppkeyRequest;
import com.sankuai.avatar.client.soa.request.ApplicationPageRequest;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

import java.util.List;

/**
 * SC的HTTP客户端
 */
public interface ScHttpClient {

    /**
     * 获取Org信息
     *
     * @param orgIds orgIds 逗号隔开的部门节点id的字符串
     * @return 部门信息
     */
    ScOrg getOrg(String orgIds) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取用户的OrgTree
     *
     * @param user 用户的mis号
     * @return 组织树，树节点为OrgTreeNodeBO对象
     */
    List<ScOrgTreeNode> getOrgTreeByUser(String user) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 查询应用列表
     *
     * @param request 请求对象
     * @return 应用列表
     */
    ScPageResponse<ScApplication> getApplications(ApplicationPageRequest request) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取用户组内应用
     *
     * @param request 请求
     * @return {@link ScPageResponse}<{@link ScUserOwnerApplication}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    ScPageResponse<ScUserOwnerApplication> getUserOwnerApplications(ApplicationPageRequest request) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 查询检索应用信息（带结算单元信息）
     *
     * @param request 请求
     * @return {@link ScPageResponse}<{@link ScUserOwnerApplication}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    ScPageResponse<ScQueryApplication> queryApplications(ApplicationPageRequest request) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取应用程序信息
     *
     * @param name 应用名称
     * @return {@link ScApplicationDetail}
     */
    ScApplicationDetail getApplication(String name) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取v1版本 appkey信息
     *
     * @param appKey 应用关键
     * @return {@link ScV1Appkey}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    ScV1Appkey getAppkeyInfoByV1(String appKey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 查询演练服务列表
     *
     * @param request 请求对象
     * @return 服务列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    ScPageResponse<ScIsoltAppkey> getIsoltAppkeys(IsoltAppkeyRequest request) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 批量获取appkey信息
     *
     * @param appkey 名称
     * @return SC Appkey信息
     */
    List<ScAppkey> getAppkeysInfo(List<String> appkey);

    /**
     * 获取所有appkeys
     *
     * @return {@link List}<{@link String}>
     */
    List<String> getAllAppkeys();

    /**
     * 分页获取所有appkeys
     *
     * @param page     页码
     * @param pageSize 大小
     * @return {@link List}<{@link String}>
     */
    List<String> getAllAppkeysByPage(Integer page, Integer pageSize);

    /**
     * 获取所有paas应用
     *
     * @return {@link List}<{@link String}>
     */
    List<String> getAllPaasApplications();
}