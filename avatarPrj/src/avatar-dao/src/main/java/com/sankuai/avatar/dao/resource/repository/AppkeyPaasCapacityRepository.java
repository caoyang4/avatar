package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasCapacityDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasCapacityRequest;

import java.util.Date;
import java.util.List;

/**
 * Appkey Paas层容灾等级数据管理
 * @author caoyang
 * @create 2022-09-21 16:04
 */
public interface AppkeyPaasCapacityRepository {

    /**
     * 查询对象
     * @param request 查询对象
     * @return do list
     */
    List<AppkeyPaasCapacityDO> query(AppkeyPaasCapacityRequest request);

    /**
     * 容灾实体聚合查询 依赖paas 容灾
     *
     * @param date   日期
     * @param appkey appkey
     * @return {@link List}<{@link AppkeyPaasCapacityDO}>
     */
    List<AppkeyPaasCapacityDO> queryAggregatedClientAppkey(Date date, String appkey);

    /**
     * 容灾实体聚合查询 依赖paas 容灾
     *
     * @param date      日期
     * @param appkey    appkey
     * @param paasNames paas名字
     * @return {@link List}<{@link AppkeyPaasCapacityDO}>
     */
    List<AppkeyPaasCapacityDO> queryAggregatedClientAppkey(Date date, String appkey, List<String> paasNames);

    /**
     * distinct查询 paasName
     *
     * @param date   日期
     * @param appkey appkey
     * @return {@link List}<{@link String}>
     */
    List<String> queryPaasNamesByAppkey(Date date, String appkey);

    /**
     * 容灾实体聚合查询 paas 自身容灾
     *
     * @param date       日期
     * @param paasAppkey paas appkey
     * @return {@link List}<{@link AppkeyPaasCapacityDO}>
     */
    List<AppkeyPaasCapacityDO> queryAggregatedPaasAppkey(Date date, String paasAppkey);

    /**
     * 容灾实体聚合查询 paas 自身容灾
     *
     * @param date       日期
     * @param paasAppkey paas appkey
     * @param paasNames  paas名字
     * @return {@link List}<{@link AppkeyPaasCapacityDO}>
     */
    List<AppkeyPaasCapacityDO> queryAggregatedPaasAppkey(Date date, String paasAppkey, List<String> paasNames);

    /**
     * distinct查询 paasName
     *
     * @param date       日期
     * @param paasAppkey paas appkey
     * @return {@link List}<{@link String}>
     */
    List<String> queryPaasNamesByPaasAppkey(Date date, String paasAppkey);

    /**
     * 新增
     * @param appkeyPaasCapacityDO  do
     * @return 是否写入成功
     */
    boolean insert(AppkeyPaasCapacityDO appkeyPaasCapacityDO);

    /**
     * 批量新增
     * @param appkeyPaasCapacityDOList list
     * @return 生效数量
     */
    int insertBatch(List<AppkeyPaasCapacityDO> appkeyPaasCapacityDOList);

    /**
     * 更新
     * @param appkeyPaasCapacityDO do
     * @return 是否写入成功
     */
    boolean update(AppkeyPaasCapacityDO appkeyPaasCapacityDO);

    /**
     * 根据特定条件删除数据
     * @param id 主键
     * @return 是否删除成功
     */
    boolean delete(int id);

    /**
     * 查询某上报日期的所有paas appkey
     *
     * @param updateDate 上报日期
     * @return {@link List}<{@link String}>
     */
    List<String> queryPaasAppkeysByUpdateDate(Date updateDate);

    /**
     * 查询某上报日期的所有业务 appkey
     *
     * @param updateDate 上报日期
     * @return {@link List}<{@link String}>
     */
    List<String> queryClientAppkeysByUpdateDate(Date updateDate);

}
