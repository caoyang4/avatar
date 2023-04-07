package com.sankuai.avatar.resource.capacity;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacitySummaryBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasCapacityRequestBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasCapacityBO;

import java.util.Date;
import java.util.List;

/**
 * appkey paas容灾信息资源管理
 * @author Jie.li.sh
 * @create 2022-09-26
 **/
public interface AppkeyPaasCapacityResource {

    /**
     * 查询服务对应的paas容灾等级数据
     * @param appkeyPaasCapacityRequestBO BO查询对象
     * @return BOList
     */
    List<AppkeyPaasCapacityBO> query(AppkeyPaasCapacityRequestBO appkeyPaasCapacityRequestBO);

    /**
     * 查询服务对应的paas容灾等级分页数据
     * @param appkeyPaasCapacityRequestBO appkey, paasName
     * @return BOList
     */
    PageResponse<AppkeyPaasCapacityBO> queryPage(AppkeyPaasCapacityRequestBO appkeyPaasCapacityRequestBO);

    /**
     * 被appkey页面聚合
     * 容灾实体聚合查询 paas 容灾
     *
     * @param appkey    appkey
     * @param update    更新日期
     * @param page      页面
     * @param pageSize  页面大小
     * @param isPaas    是否查询 paas 自身
     * @param paasNames paas名字
     * @return {@link PageResponse}<{@link AppkeyPaasCapacityBO}>
     */
    PageResponse<AppkeyPaasCapacityBO> getPageAggregatedByAppkey(String appkey, Date update, List<String> paasNames,
                                                                 int page, int pageSize, boolean isPaas);

    /**
     * 保存(insert/update)服务的容灾等级数据
     * @param appkeyPaasCapacityBO 待新增容灾等级
     * @return 是否新增成功
     */
    boolean save(AppkeyPaasCapacityBO appkeyPaasCapacityBO);

    /**
     * 根据条件删除数据
     * @param appkeyPaasCapacityRequestBO 条件
     * @return 是否删除成功
     */
    boolean deleteByCondition(AppkeyPaasCapacityRequestBO appkeyPaasCapacityRequestBO);

    /**
     * 聚合依赖的 paas
     *
     * @param appkey appkey
     * @param date 日期
     * @param isPaas paas是
     * @return {@link List}<{@link String}>
     */
    List<String> getPaasNamesByAppkey(String appkey, Date date, boolean isPaas);

    /**
     * 查询某上报日期的所有paas appkey
     *
     * @param updateDate 上报日期
     * @return {@link List}<{@link String}>
     */
    List<String> getPaasAppkeys(Date updateDate);

    /**
     * 查询某上报日期的所有client appkey
     *
     * @param updateDate 上报日期
     * @return {@link List}<{@link String}>
     */
    List<String> getClientAppkeys(Date updateDate);

    /**
     * 缓存获取总体容灾信息
     *
     * @param appkey appkey
     * @param isPaas 是否是paas
     * @return {@link AppkeyCapacitySummaryBO}
     */
    AppkeyCapacitySummaryBO getAppkeyCapacitySummaryBO(String appkey, boolean isPaas);

    /**
     * 缓存总体容灾信息
     *
     * @param appkey    appkey
     * @param summaryBO summaryBO
     * @param isPaas    是否是paas
     * @return {@link AppkeyCapacitySummaryBO}
     */
    Boolean setAppkeyCapacitySummaryBO(String appkey, AppkeyCapacitySummaryBO summaryBO, boolean isPaas);

}
