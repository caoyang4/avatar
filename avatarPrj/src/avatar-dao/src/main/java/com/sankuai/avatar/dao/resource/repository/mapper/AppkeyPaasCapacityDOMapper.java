package com.sankuai.avatar.dao.resource.repository.mapper;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasCapacityDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.aggregation.AggregationMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.Date;
import java.util.List;

/**
 * AppkeyPaasCapacityDO对象 mapper
 *
 * @author caoyang
 * @create 2022-09-27 16:14
 */
@Repository
public interface AppkeyPaasCapacityDOMapper
        extends Mapper<AppkeyPaasCapacityDO>, InsertListMapper<AppkeyPaasCapacityDO>, AggregationMapper<AppkeyPaasCapacityDO> {

    /**
     * 聚合容灾实体查询依赖 paas 容灾
     *
     * @param updateDate   更新日期
     * @param clientAppkey 客户端appkey
     * @return {@link List}<{@link AppkeyPaasCapacityDO}>
     */
    @Select("select t.* from capacity_paas t where update_date = #{updateDate} and client_appkey = #{clientAppkey} GROUP BY paas_name,type_name,set_name ORDER BY is_capacity_standard")
    @Results({
            @Result(column = "client_appkey", property = "clientAppkey"),
            @Result(column = "paas_name", property = "paasName"),
            @Result(column = "is_core", property = "isCore"),
            @Result(column = "type_name", property = "typeName"),
            @Result(column = "type_comment", property = "typeComment"),
            @Result(column = "paas_appkey", property = "paasAppkey"),
            @Result(column = "capacity_level", property = "capacityLevel"),
            @Result(column = "standard_level", property = "standardLevel"),
            @Result(column = "is_capacity_standard", property = "isCapacityStandard"),
            @Result(column = "client_config", property = "clientConfig"),
            @Result(column = "standard_config", property = "standardConfig"),
            @Result(column = "is_config_standard", property = "isConfigStandard"),
            @Result(column = "standard_reason", property = "standardReason"),
            @Result(column = "standard_tips", property = "standardTips"),
            @Result(column = "is_white", property = "isWhite"),
            @Result(column = "white_reason", property = "whiteReason"),
            @Result(column = "is_set", property = "isSet"),
            @Result(column = "set_name", property = "setName"),
            @Result(column = "set_type", property = "setType"),
            @Result(column = "update_by", property = "updateBy"),
            @Result(column = "update_date", property = "updateDate"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<AppkeyPaasCapacityDO> selectAggregatedClientAppkeyByTypeName(@Param("updateDate") Date updateDate,
                                                                      @Param("clientAppkey") String clientAppkey);


    /**
     * 聚合容灾实体查询 paas 自身容灾
     *
     * @param updateDate 更新日期
     * @param paasAppkey paas appkey
     * @return {@link List}<{@link AppkeyPaasCapacityDO}>
     */
    @Select("select t.* from capacity_paas t where update_date = #{updateDate} and paas_appkey = #{paasAppkey} GROUP BY paas_name,type_name,set_name ORDER BY is_capacity_standard")
    @Results({
            @Result(column = "client_appkey", property = "clientAppkey"),
            @Result(column = "paas_name", property = "paasName"),
            @Result(column = "is_core", property = "isCore"),
            @Result(column = "type_name", property = "typeName"),
            @Result(column = "type_comment", property = "typeComment"),
            @Result(column = "paas_appkey", property = "paasAppkey"),
            @Result(column = "capacity_level", property = "capacityLevel"),
            @Result(column = "standard_level", property = "standardLevel"),
            @Result(column = "is_capacity_standard", property = "isCapacityStandard"),
            @Result(column = "client_config", property = "clientConfig"),
            @Result(column = "standard_config", property = "standardConfig"),
            @Result(column = "is_config_standard", property = "isConfigStandard"),
            @Result(column = "standard_reason", property = "standardReason"),
            @Result(column = "standard_tips", property = "standardTips"),
            @Result(column = "is_white", property = "isWhite"),
            @Result(column = "white_reason", property = "whiteReason"),
            @Result(column = "is_set", property = "isSet"),
            @Result(column = "set_name", property = "setName"),
            @Result(column = "set_type", property = "setType"),
            @Result(column = "update_by", property = "updateBy"),
            @Result(column = "update_date", property = "updateDate"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<AppkeyPaasCapacityDO> selectAggregatedPaasAppkeyByTypeName(@Param("updateDate") Date updateDate,
                                                                    @Param("paasAppkey") String paasAppkey);

    /**
     * 根据 paas name 聚合容灾信息
     *
     * @param updateDate   更新日期
     * @param clientAppkey 客户端appkey
     * @param paasNames    paas名字
     * @return {@link List}<{@link AppkeyPaasCapacityDO}>
     */
    @Select({
            "<script>",
                "select t.* from capacity_paas t where update_date = #{updateDate} and client_appkey = #{clientAppkey} AND paas_name in",
                    "<foreach collection='paasNames' item='item' open='(' separator=',' close=')'>",
                        "#{item}",
                    "</foreach>",
                "GROUP BY paas_name,type_name,set_name ORDER BY is_capacity_standard",
            "</script>"
    })
    @Results({
            @Result(column = "client_appkey", property = "clientAppkey"),
            @Result(column = "paas_name", property = "paasName"),
            @Result(column = "is_core", property = "isCore"),
            @Result(column = "type_name", property = "typeName"),
            @Result(column = "type_comment", property = "typeComment"),
            @Result(column = "paas_appkey", property = "paasAppkey"),
            @Result(column = "capacity_level", property = "capacityLevel"),
            @Result(column = "standard_level", property = "standardLevel"),
            @Result(column = "is_capacity_standard", property = "isCapacityStandard"),
            @Result(column = "client_config", property = "clientConfig"),
            @Result(column = "standard_config", property = "standardConfig"),
            @Result(column = "is_config_standard", property = "isConfigStandard"),
            @Result(column = "standard_reason", property = "standardReason"),
            @Result(column = "standard_tips", property = "standardTips"),
            @Result(column = "is_white", property = "isWhite"),
            @Result(column = "white_reason", property = "whiteReason"),
            @Result(column = "is_set", property = "isSet"),
            @Result(column = "set_name", property = "setName"),
            @Result(column = "set_type", property = "setType"),
            @Result(column = "update_by", property = "updateBy"),
            @Result(column = "update_date", property = "updateDate"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<AppkeyPaasCapacityDO> selectAggregatedClientAppkeyByPaasList(@Param("updateDate") Date updateDate,
                                                                      @Param("clientAppkey") String clientAppkey,
                                                                      @Param("paasNames") List<String> paasNames);

    /**
     * 根据 paas name 聚合容灾信息
     *
     * @param updateDate 更新日期
     * @param paasAppkey paas appkey
     * @param paasNames  paas名字
     * @return {@link List}<{@link AppkeyPaasCapacityDO}>
     */
    @Select({
            "<script>",
                "select t.* from capacity_paas t where update_date = #{updateDate} and paas_appkey = #{paasAppkey} AND paas_name in",
                    "<foreach collection='paasNames' item='item' open='(' separator=',' close=')'>",
                        "#{item}",
                    "</foreach>",
                "GROUP BY paas_name,type_name,set_name ORDER BY is_capacity_standard",
            "</script>"
    })
    @Results({
            @Result(column = "client_appkey", property = "clientAppkey"),
            @Result(column = "paas_name", property = "paasName"),
            @Result(column = "is_core", property = "isCore"),
            @Result(column = "type_name", property = "typeName"),
            @Result(column = "type_comment", property = "typeComment"),
            @Result(column = "paas_appkey", property = "paasAppkey"),
            @Result(column = "capacity_level", property = "capacityLevel"),
            @Result(column = "standard_level", property = "standardLevel"),
            @Result(column = "is_capacity_standard", property = "isCapacityStandard"),
            @Result(column = "client_config", property = "clientConfig"),
            @Result(column = "standard_config", property = "standardConfig"),
            @Result(column = "is_config_standard", property = "isConfigStandard"),
            @Result(column = "standard_reason", property = "standardReason"),
            @Result(column = "standard_tips", property = "standardTips"),
            @Result(column = "is_white", property = "isWhite"),
            @Result(column = "white_reason", property = "whiteReason"),
            @Result(column = "is_set", property = "isSet"),
            @Result(column = "set_name", property = "setName"),
            @Result(column = "set_type", property = "setType"),
            @Result(column = "update_by", property = "updateBy"),
            @Result(column = "update_date", property = "updateDate"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<AppkeyPaasCapacityDO> selectAggregatedPaasAppkeyByPaasList(@Param("updateDate") Date updateDate,
                                                                      @Param("paasAppkey") String paasAppkey,
                                                                      @Param("paasNames") List<String> paasNames);

    /**
     * appkey 依赖paas列表
     *
     * @param updateDate   更新日期
     * @param clientAppkey 客户端appkey
     * @return {@link List}<{@link String}>
     */
    @Results(@Result(column = "paas_name", property = "paasName"))
    @Select("SELECT DISTINCT paas_name from capacity_paas WHERE update_date = #{updateDate} and client_appkey = #{clientAppkey}")
    List<String> selectPaasListByAppkey(@Param("updateDate") Date updateDate,
                                     @Param("clientAppkey") String clientAppkey);

    /**
     * 根据paas appkey 查paas列表
     *
     * @param updateDate 更新日期
     * @param paasAppkey paas appkey
     * @return {@link List}<{@link String}>
     */
    @Results(@Result(column = "paas_name", property = "paasName"))
    @Select("SELECT DISTINCT paas_name from capacity_paas WHERE update_date = #{updateDate} and paas_appkey = #{paasAppkey}")
    List<String> selectPaasListByPaasAppkey(@Param("updateDate") Date updateDate,
                                     @Param("paasAppkey") String paasAppkey);

    /**
     * 查询某上报日期的所有paas appkey
     *
     * @param updateDate 上报日期
     * @return {@link List}<{@link String}>
     */
    @Results(@Result(column = "paas_appkey", property = "paasAppkey"))
    @Select("SELECT DISTINCT paas_appkey from capacity_paas WHERE update_date = #{updateDate}")
    List<String> selectAllPaasAppkey(@Param("updateDate") Date updateDate);

    /**
     * 查询某上报日期的所有业务 appkey
     *
     * @param updateDate 更新日期
     * @return {@link List}<{@link String}>
     */
    @Results(@Result(column = "client_appkey", property = "clientAppkey"))
    @Select("SELECT DISTINCT client_appkey from capacity_paas WHERE update_date = #{updateDate}")
    List<String> selectAllClientAppkey(@Param("updateDate") Date updateDate);
}
