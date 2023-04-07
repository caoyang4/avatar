package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacitySummaryDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasCapacityDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasCapacityReportDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasClientDTO;
import com.sankuai.avatar.web.request.AppkeyPaasCapacityPageRequest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * paas 容灾业务逻辑Service接口
 * @author caoyang
 * @create 2022-09-21 15:55
 */
public interface AppkeyPaasCapacityService {

    /**
     * paas 上报的容灾数据对象 dto
     * @param appkeyPaasCapacityReportDTOList DTOList
     * @return 上报结果
     */
    boolean reportAppkeyPaasCapacity(List<AppkeyPaasCapacityReportDTO> appkeyPaasCapacityReportDTOList);

    /**
     * 查询服务paas容灾不达标的数据
     * @param appkey appkey
     * @return list
     */

    List<AppkeyPaasCapacityDTO> queryUnStandardLevel(String appkey);

    /**
     * 根据服务查询其所依赖的 paas 容灾信息
     * 查询的信息为前一天上报的数据
     * @param appkey appkey
     * @return dto list
     */
    List<AppkeyPaasCapacityDTO> queryPaasCapacityByAppkey(String appkey);

    /**
     * 根据 paas appkey查询其容灾信息
     * 查询的信息为前一天上报的数据
     * @param appkey appkey
     * @return DTOList
     */
    List<AppkeyPaasCapacityDTO> queryPaasCapacityByPaasAppkey(String appkey);

    /**
     * 获取 paas 容灾的汇总信息
     * @param appkey key
     * @return 汇总信息
     */
    AppkeyCapacitySummaryDTO getAppkeyPaasCapacitySummary(String appkey);

    /**
     * 获取 paas 容灾的汇总信息，不查缓存
     *
     * @param appkey appkey
     * @return {@link AppkeyCapacitySummaryDTO}
     */
    AppkeyCapacitySummaryDTO getAppkeyPaasCapacitySummaryNoCache(String appkey);

    /**
     * paas自身容灾汇总信息
     *
     * @param appkey appkey
     * @return {@link AppkeyCapacitySummaryDTO}
     */
    AppkeyCapacitySummaryDTO getAppkeyPaasCapacitySummaryByPaasAppkey(String appkey);

    /**
     * paas自身容灾汇总信息，不查缓存
     *
     * @param appkey appkey
     * @return {@link AppkeyCapacitySummaryDTO}
     */
    AppkeyCapacitySummaryDTO getAppkeyPaasCapacitySummaryByPaasAppkeyNoCache(String appkey);

    /**
     * 根据paas和服务查询其所依赖的 paas 客户端信息, 只获取前一天的数据
     * @param paasName paas 名称
     * @param appkey appkey
     * @return dto list
     */
    List<AppkeyPaasClientDTO> queryPaasClientByAppkey(String paasName, String appkey);

    /**
     * 根据paas和服务以及更新日期查询其所依赖的 paas 客户端信息
     * @param paasName paas 名称
     * @param appkey appkey
     * @param updateDate 上报日期
     * @return DYOList
     */
    List<AppkeyPaasClientDTO> queryPaasClientByAppkey(String paasName, String appkey, Date updateDate);

    /**
     * 分页查询接口
     * @param request 分页查询参数
     * @return 分页信息
     */
    PageResponse<AppkeyPaasCapacityDTO> queryPage(AppkeyPaasCapacityPageRequest request);

    /**
     * 被appkey页面聚合
     * 分页查询paas上报信息
     *
     * @param appkey    appkey
     * @param isPaas    是否查 paas 自身容灾
     * @param page      页面
     * @param pageSize  页面大小
     * @param paasNames paas名字
     * @return {@link PageResponse}<{@link AppkeyPaasCapacityDTO}>
     */
    PageResponse<AppkeyPaasCapacityDTO> getPageAggregatedByAppkey(String appkey, List<String> paasNames, boolean isPaas, int page, int pageSize);

    /**
     * 清理指定更新日期的 paas 上报数据，包括 paas 容灾与 appkey应用的paas客户端
     * @param date 指定日期
     * @return 收否删除成功
     */
    boolean deleteAppkeyPaasCapacityByUpdateDate(LocalDate date);

    /**
     * 聚合依赖的 paas
     *
     * @param appkey appkey
     * @param isPaas paas是
     * @return {@link List}<{@link String}>
     */
    List<String> getPaasNamesByAppkey(String appkey, boolean isPaas);

    /**
     * 得到有效paas appkeys
     *
     * @return {@link List}<{@link String}>
     */
    List<String> getValidPaasAppkeys();

    /**
     * 得到有效业务 appkeys
     *
     * @return {@link List}<{@link String}>
     */
    List<String> getValidClientAppkeys();

    /**
     * 缓存appkey整体容灾
     *
     * @param appkey appkey
     * @param isPaas 是否是 paas appkey
     * @return {@link Boolean}
     */
    Boolean cacheAppkeyCapacitySummary(String appkey, boolean isPaas);

}
