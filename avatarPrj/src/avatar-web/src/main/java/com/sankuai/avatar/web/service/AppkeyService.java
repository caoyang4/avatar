package com.sankuai.avatar.web.service;

import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.appkey.*;
import com.sankuai.avatar.web.dto.octo.OctoRouteStrategyDTO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeSrvDTO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeSrvDetailResponseDTO;
import com.sankuai.avatar.web.request.AppkeyQueryPageRequest;
import com.sankuai.avatar.web.request.AppkeySearchPageRequest;
import com.sankuai.avatar.web.request.AppkeyTreeQueryRequest;
import com.sankuai.avatar.web.request.IsoltAppkeyPageRequest;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-14 14:26
 */

public interface AppkeyService {

    /**
     * 定时任务比对服务数据准确性: 随机获取DB中的多个Appkey信息
     *
     * @param count 数目
     * @return {@link List}<{@link AppkeyDTO}>
     */
    List<AppkeyDTO> getByAppkeyRandom(Integer count);

    /**
     * 从DOM获取appkey资源利用率信息
     *
     * @param appkey appkey
     * @return {@link AppkeyResourceUtilDTO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    AppkeyResourceUtilDTO getAppkeyUtilization(String appkey);

    /**
     * DB查询appkey详细信息
     *
     * @param appkey appkey
     * @return {@link AppkeyDetailDTO}
     */
    AppkeyDetailDTO getAppkeyDetailByRepository(String appkey);

    /**
     * 第三方(ops/sc...)实时查询appkey信息
     *
     * @param appkey appkey
     * @return {@link AppkeyDetailDTO}
     */
    AppkeyDetailDTO getAppkeyDetailInfoByHttpClient(String appkey);

    /**
     * 第三方(ops)实时查询appkey信息
     *
     * @param appkeyTreeQueryRequest appkeyTreeQueryRequest
     * @return {@link AppkeyTreeSrvDetailResponseDTO}
     */
    AppkeyTreeSrvDetailResponseDTO getOpsAppkeysByHttpClient(AppkeyTreeQueryRequest appkeyTreeQueryRequest);

    /**
     * 第三方(ops)实时查询用户负责的appkey信息
     *
     * @param appkeyTreeQueryRequest appkeyTreeQueryRequest
     * @return {@link List<AppkeyTreeSrvDTO>}
     */
    List<AppkeyTreeSrvDTO> getMineAppkeysByHttpClient(AppkeyTreeQueryRequest appkeyTreeQueryRequest);

    /**
     * 第三方(sc)实时查询演练appkey信息
     *
     * @param request IsoltAppkeyPageRequest
     * @return {@link PageResponse<AppkeyTreeSrvDTO>}
     */
    PageResponse<IsoltAppkeyDTO> getIsoltAppkeysByHttpClient(IsoltAppkeyPageRequest request);

    /**
     * 生成演练服务名称
     *
     * @param originAppkey 源appkey
     * @param soamod       模块名
     * @param soasrv       服务名
     * @return {@link IsoltAppkeyGenerateDisplayDTO}
     */
    IsoltAppkeyGenerateDisplayDTO generateIsoltAppkeyName(String originAppkey, String soamod, String soasrv);

    /**
     * 根据 srv查询
     *
     * @param srv srv
     * @return {@link AppkeyDTO}
     */
    AppkeyDTO getBySrv(String srv);

    /**
     * 精确分页查询
     *
     * @param pageRequest 页面请求
     * @return {@link PageResponse}<{@link AppkeyDTO}>
     */
    PageResponse<AppkeyDTO> queryPage(AppkeyQueryPageRequest pageRequest);

    /**
     * 搜索分页查询
     *
     * @param pageRequest 页面请求
     * @return {@link PageResponse}<{@link AppkeyDTO}>
     */
    PageResponse<AppkeyDTO> searchPage(AppkeySearchPageRequest pageRequest);

    /**
     * 通过主机查询服务
     *
     * @param host 机器
     * @return {@link String}
     */
    String getByHost(String host);

    /**
     * 我的服务，支持按照 appkey 搜索
     *
     * @param request 请求
     * @return {@link PageResponse}<{@link AppkeyDTO}>
     */
    PageResponse<AppkeyDTO> getMineAppkeysByRepository(AppkeyTreeQueryRequest request);

    /**
     * 获取用户置顶服务
     *
     * @param mis 管理信息系统
     * @return {@link List}<{@link String}>
     */
    List<String> getUserTopAppkey(String mis);

    /**
     * 全部服务
     *
     * @param request 请求
     * @return {@link PageResponse}<{@link AppkeyDTO}>
     */
    PageResponse<AppkeyDTO> getAllAppkeysByRepository(AppkeyTreeQueryRequest request);

    /**
     * 用户是否关注了Appkey
     *
     * @param user   用户
     * @param appkey appkey
     * @return {@link Boolean}
     */
    Boolean isUserFavorAppkey(String user, String appkey);

    /**
     * 我的关注，支持按照 appkey 搜索
     *
     * @param request 请求
     * @return {@link PageResponse}<{@link AppkeyDTO}>
     */
    PageResponse<AppkeyDTO> getFavorPageAppkey(AppkeyTreeQueryRequest request);

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
     * 服务接入弹性提示
     *
     * @return {@link ElasticTipDTO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    ElasticTipDTO getElasticTips() throws SdkCallException, SdkBusinessErrorException;

    /**
     * 是否为弹性灰度的owt
     *
     * @param owt owt
     * @return {@link Boolean}
     * @throws SdkCallException          sdk调用异常{}
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean getElasticGrayScale(String owt) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 批量查询：appkey运行中与待审核的流程列表
     *
     * @param appkeyList appkey列表
     * @return {@link PageResponse}<{@link AppkeyFlowDTO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    PageResponse<AppkeyFlowDTO> batchGetAppkeyRunningAndHoldingFlowList(List<String> appkeyList) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 查询服务路由分组
     *
     * @param appkey appkey
     * @param env    环境
     * @return {@link OctoRouteStrategyDTO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OctoRouteStrategyDTO getAppkeyOctoRouteStrategy(String appkey, EnvEnum env) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取服务结算单元信息
     *
     * @param appkey appkey
     * @return {@link AppkeyBillingUnitDTO}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    AppkeyBillingUnitDTO getAppkeyUnitList(String appkey) throws SdkCallException, SdkBusinessErrorException;
}
