package com.sankuai.avatar.resource.appkey;

import com.sankuai.avatar.client.banner.response.ElasticTip;
import com.sankuai.avatar.common.exception.ResourceNotFoundErrorException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.appkey.bo.*;
import com.sankuai.avatar.resource.appkey.request.*;

import java.util.List;

/**
 * Appkey 资源管理
 *
 * @author caoyang
 * @create 2022-12-12 13:34
 */
public interface AppkeyResource {

    /**
     * 随机获取DB中的多个Appkey信息
     *
     * @param count 数目
     * @return {@link List}<{@link AppkeyBO}>
     */
    List<AppkeyBO> getByAppkeyRandom(Integer count);

    /**
     * 得到appkey
     *
     * @param appkey appkey
     * @return {@link AppkeyBO}
     */
    AppkeyBO getByAppkey(String appkey);

    /**
     * OPS获取appkey
     *
     * @param appkey appkey
     * @return {@link String}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    String getSrvKeyByAppkey(String appkey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取appkey关联的srv信息
     *
     * @param appkey appkey
     * @return {@link OpsSrvBO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OpsSrvBO getAppkeyRelatedSrvInfo(String appkey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * OPS获取appkey (服务基础属性信息)
     *
     * @param appkey appkey
     * @return {@link OpsSrvBO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OpsSrvBO getAppkeyByOps(String appkey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * SC V2新版接口获取appkey (服务运营属性信息)
     *
     * @param appkey appkey
     * @return {@link ScAppkeyBO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    ScAppkeyBO getAppkeyBySc(String appkey) throws SdkCallException, SdkBusinessErrorException;

    /**
     * SC 接口批量获取appkey信息 (服务运营属性信息)
     *
     * @param appkeyList appkeyList
     * @return {@link List<ScAppkeyBO> }
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<ScAppkeyBO> batchGetAppkeyBySc(List<String> appkeyList) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 查询演练服务列表
     *
     * @param request 请求对象
     * @return {@link PageResponse}<{@link ScIsoltAppkeyBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    PageResponse<ScIsoltAppkeyBO> getIsoltAppkeys(IsoltAppkeyRequestBO request) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取appkey资源利用率信息
     *
     * @param appkey appkey
     * @return {@link AppkeyResourceUtilBO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    AppkeyResourceUtilBO getAppkeyResourceUtil(String appkey) throws SdkCallException, SdkBusinessErrorException, ResourceNotFoundErrorException;

    /**
     * 根据srv查询appkey
     *
     * @param srv srv
     * @return {@link AppkeyBO}
     */
    AppkeyBO getBySrv(String srv);

    /**
     * 精确查询
     *
     * @param requestBO 请求
     * @return {@link PageResponse}<{@link AppkeyBO}>
     */
    PageResponse<AppkeyBO> queryPage(AppkeyRequestBO requestBO);

    /**
     * 搜索查询
     *
     * @param searchRequestBO searchRequestBO
     * @return {@link PageResponse}<{@link AppkeyBO}>
     */
    PageResponse<AppkeyBO> searchAppkey(AppkeySearchRequestBO searchRequestBO);

    /**
     * 通过主机查询服务
     *
     * @param host 机器
     * @return {@link String}
     */
    String getByHost(String host);

    /**
     * 我的服务，支持 appkey 名称搜索
     *
     * @param request 如果查询请求
     * @return {@link List}<{@link AppkeyBO}>
     */
    PageResponse<AppkeyBO> getOwnAppkey(AppkeySrvsQueryRequest request);

    /**
     * 分页查询全部服务
     *
     * @param request 请求
     * @return {@link PageResponse}<{@link AppkeyBO}>
     */
    PageResponse<AppkeyBO> getPageAppkey(AppkeyTreeQueryRequestBO request);

    /**
     * 根据 vip 查询 appkey
     *
     * @param vip 负载均衡虚ip
     * @return {@link List}<{@link String}>
     */
    List<String> getByVip(String vip);

    /**
     * 关注appkey
     *
     * @param appkey appkey
     * @param mis    mis
     * @return {@link Boolean}
     */
    Boolean favorAppkey(String appkey, String mis);

    /**
     * 取消关注appkey
     *
     * @param appkey appkey
     * @param mis    mis
     * @return {@link Boolean}
     */
    Boolean cancelFavorAppkey(String appkey, String mis);

    /**
     * 获取关注的appkey
     *
     * @param request 请求
     * @return {@link List}<{@link AppkeyBO}>
     */
    List<AppkeyBO> getFavorAppkey(AppkeySrvsQueryRequest request);

    /**
     * 服务接入弹性提示
     *
     * @return {@link ElasticTip}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    ElasticTipBO getElasticTips() throws SdkCallException, SdkBusinessErrorException;

    /**
     * 是否为弹性灰度的owt
     *
     * @param owt owt
     * @return {@link Boolean}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean getElasticGrayScale(String owt) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 批量查询：appkey运行中与待审核的流程列表
     *
     * @param appkeyList appkey列表
     * @return {@link PageResponse}<{@link AppkeyFlowBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    PageResponse<AppkeyFlowBO> batchGetAppkeyRunningAndHoldingFlowList(List<String> appkeyList) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取服务结算单元信息
     *
     * @param appkey appkey
     * @return {@link BillingUnitBO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    BillingUnitBO getAppkeyUnitList(String appkey) throws SdkCallException, SdkBusinessErrorException;
}