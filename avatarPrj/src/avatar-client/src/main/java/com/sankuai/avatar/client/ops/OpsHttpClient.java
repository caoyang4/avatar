package com.sankuai.avatar.client.ops;

import com.sankuai.avatar.client.ops.model.*;
import com.sankuai.avatar.client.ops.request.SrvQueryRequest;
import com.sankuai.avatar.client.ops.response.OpsAvatarSrvsResponse;
import com.sankuai.avatar.client.ops.response.OpsPdlResponse;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

import java.util.List;

/**
 * @author xk
 */
public interface OpsHttpClient {

    /**
     * 获取appkey信息
     *
     * @param appkey 名称
     * @return {@link OpsAppkey}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OpsAppkey getAppkeyInfo(String appkey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取appkey关联的srv服务树节点信息
     *
     * @param appkey 名称
     * @return OpsSrv信息
     */
    OpsSrv getSrvInfoByAppkey(String appkey);

    /**
     * 获取host关联的srv服务树节点信息
     *
     * @param host 名称
     * @return {@link OpsSrv}
     */
    OpsSrv getSrvInfoByHost(String host) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取srv服务树节点信息
     *
     * @param srv srv
     * @return {@link OpsSrv}
     */
    OpsSrv getSrvInfoBySrvKey(String srv);

    /**
     * 获取srv服务树信息（包含pdl、owt信息）
     *
     * @param srv 名称
     * @return {@link OpsTree}
     */
    OpsTree getSrvTreeByKey(String srv);

    /**
     * 根据查询条件获取srv
     *
     * @param srvQueryRequest srv查询条件
     * @return {@link OpsSrvDetail}
     */
    OpsAvatarSrvsResponse getSrvsByQueryRequest(SrvQueryRequest srvQueryRequest);

    /**
     * 获取srv服务树信息（包含plus信息）
     *
     * @param srv 名称
     * @return {@link OpsTree}
     */
    OpsSrvDetail getSrvDetailByKey(String srv);

    /**
     * 获取srv服务树节点关注人
     *
     * @param srv 名称
     * @return {@link List<String>}
     */
    List<String> getSrvSubscribers(String srv);

    /**
     * 获取全部appkey信息
     *
     * @return OpsSrv信息
     */
    List<OpsSrv> getAllAppkeyInfo();

    /**
     * 从ops获取主机
     *
     * @param hostname 主机名 或 ip
     * @return 主机对象
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OpsHost getHostInfo(String hostname) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取服务树节点下的所有主机
     *
     * @param srv 服务树节点
     * @return {@link List}<{@link OpsHost}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OpsHost> getHostsBySrv(String srv) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取appkey的所有主机
     *
     * @param appkey appkey
     * @return 主机列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OpsHost> getHostsByAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取所有Owt
     *
     * @return Owt列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OpsOwt> getOwtList() throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取Owt
     *
     * @param key Owt的key字段，如服务运维部的是dianping.tbd
     * @return Owt信息
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OpsOwt getOwtByKey(String key) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 通过服务树的key获取Org数据
     *
     * @param key 如服务运维部的owt是dianping.tbd
     * @return Org信息
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OpsOrg> getOrgListByKey(String key) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取所有BG
     *
     * @return 所有BG列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OpsBg> getBgList() throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取用户Bg
     *
     * @param user 用户名
     * @return 用户名的BG名称列表
     */
    List<String> getBgListByUser(String user) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取Owt下的Pdl列表
     *
     * @param owtKey Owt的key字段，如服务运维部的是dianping.tbd
     * @return Owt下的Pdl列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OpsPdl> getPdlListByOwtKey(String owtKey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取所有Pdl列表
     *
     * @return 所有Pdl列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OpsPdl> getPdlList() throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取pdl信息
     *
     * @param key Pdl的key字段，如变更管理工具是dianping.tbd.change
     * @return Pdl信息
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OpsPdlResponse getPdlByKey(String key) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取pdl下的服务列表
     *
     * @param pdlKey Pdl的key字段，如变更管理工具是dianping.tbd.change
     * @return Srv列表
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OpsSrv> getSrvListByPdl(String pdlKey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取 appkey 服务树节点名称
     *
     * @param appkey appkey
     * @return {@link String}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    String getSrvKeyByAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 是否存在appkey
     *
     * @param appkey appkey
     * @return {@link Boolean}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean isExistAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 更新容灾
     *
     * @param opsCapacity ops 容灾信息
     * @param appkey      服务名称
     * @return {@link Boolean}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean updateOpsCapacity(String appkey, OpsCapacity opsCapacity) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 通过机器查询 appkey
     *
     * @param host 机器
     * @return {@link String}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    String getAppkeyByHost(String host) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 关注appkey
     *
     * @param appkey appkey
     * @param mis    管理信息系统
     * @return {@link Boolean}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean favorAppkey(String appkey, String mis) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 取消关注appkey
     *
     * @param appkey appkey
     * @param mis    管理信息系统
     * @return {@link Boolean}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean cancelFavorAppkey(String appkey, String mis) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取用户关注的appkey
     *
     * @param request 查询请求
     * @return {@link List}<{@link String}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<String> getUserFavorAppkey(SrvQueryRequest request) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取用户appkey信息
     *
     * @param mis mis
     * @return {@link List}<{@link OpsSrv}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<OpsSrv> getUserAppkeyInfo(String mis) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取 ops 用户信息
     *
     * @param mis 管理信息系统
     * @return {@link OpsUser}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OpsUser getOpsUser(String mis) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 用户是否是 sre
     *
     * @param mis mis
     * @return boolean
     */
    boolean isUserOpsSre(String mis);



}