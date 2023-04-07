package com.sankuai.avatar.resource.capacity.transfer;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.resource.capacity.bo.*;
import com.sankuai.avatar.resource.capacity.constant.UtilizationStandardType;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyCapacityDO;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * AppkeyCapacity 转换器
 * @author caoyang
 * @create 2022-11-03 16:24
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyCapacityTransfer {
    AppkeyCapacityTransfer INSTANCE = Mappers.getMapper(AppkeyCapacityTransfer.class);


    /**
     * DO -> BO
     *
     * @param appkeyCapacityDO DO
     * @return {@link AppkeyCapacityBO}
     */
    @Mapping(source = "resourceUtil", target = "utilization", qualifiedByName = "jsonToResourceUtil")
    @Mapping(source = "utilizationStandard", target = "utilizationStandard", qualifiedByName = "toUtilizationStandardType")
    @Mapping(source = "whitelists", target = "whiteList", qualifiedByName = "jsonToWhites")
    @Mapping(source = "middleware", target = "middleWareList", qualifiedByName = "jsonToMiddleWares")
    @Mapping(source = "hosts", target = "hostList", qualifiedByName = "jsonToHosts")
    @Mapping(source = "octoHttpProvider", target = "octoHttpProviderList", qualifiedByName = "jsonToOctoProviders")
    @Mapping(source = "octoThriftProvider", target = "octoThriftProviderList", qualifiedByName = "jsonToOctoProviders")
    @Mapping(source = "accessComponent", target = "accessComponentList", qualifiedByName = "jsonToAccessComponents")
    @Named("toBO")
    AppkeyCapacityBO toBO(AppkeyCapacityDO appkeyCapacityDO);

    /**
     * 批量转换 DO -> BO
     *
     * @param appkeyCapacityDOList DOList
     * @return {@link List}<{@link AppkeyCapacityBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<AppkeyCapacityBO> toBOList(List<AppkeyCapacityDO> appkeyCapacityDOList);

    /**
     * BO -> DO
     *
     * @param appkeyCapacityBO BO
     * @return {@link AppkeyCapacityDO}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(source = "utilization", target = "resourceUtil", qualifiedByName = "bean2Json")
    @Mapping(source = "utilizationStandard", target = "utilizationStandard", qualifiedByName = "java(appkeyCapacityBO.getUtilizationStandard().name())")
    @Mapping(source = "whiteList", target = "whitelists", qualifiedByName = "bean2Json")
    @Mapping(source = "middleWareList", target = "middleware", qualifiedByName = "bean2Json")
    @Mapping(source = "hostList", target = "hosts", qualifiedByName = "bean2Json")
    @Mapping(source = "octoHttpProviderList", target = "octoHttpProvider", qualifiedByName = "bean2Json")
    @Mapping(source = "octoThriftProviderList", target = "octoThriftProvider", qualifiedByName = "bean2Json")
    @Mapping(source = "accessComponentList", target = "accessComponent", qualifiedByName = "bean2Json")
    @Named("toDO")
    AppkeyCapacityDO toDO(AppkeyCapacityBO appkeyCapacityBO);

    /**
     * 批量转换 BO -> DO
     *
     * @param appkeyCapacityBOList BOList
     * @return {@link List}<{@link AppkeyCapacityDO}>
     */
    List<AppkeyCapacityDO> toDOList(List<AppkeyCapacityBO> appkeyCapacityBOList);

    /**
     * json 转 ResourceUtilizationBO对象
     *
     * @param json 资源利用率 json 串
     * @return {@link AppkeyCapacityUtilizationBO}
     */
    @Named("jsonToResourceUtil")
    default AppkeyCapacityUtilizationBO jsonToResourceUtil(String json){
        return StringUtils.isNotEmpty(json)
                ? JsonUtil.json2Bean(json, AppkeyCapacityUtilizationBO.class)
                : null;
    }

    /**
     * 转换资源利用率达标类型枚举值
     *
     * @param type 类型
     * @return {@link UtilizationStandardType}
     */
    @Named("toUtilizationStandardType")
    default UtilizationStandardType toUtilizationStandardType(String type){
        return Arrays.stream(UtilizationStandardType.values()).filter(
                e -> Objects.equals(type, e.name())
        ).findFirst().orElse(null);
    }

    /**
     * josn 转为 白名单对象
     *
     * @param json 白名单信息json串
     * @return {@link List}<{@link AppkeyCapacityWhiteBO}>
     */
    @Named("jsonToWhites")
    default List<AppkeyCapacityWhiteBO> jsonToWhites(String json){
        return StringUtils.isNotEmpty(json)
                ? Arrays.asList(Objects.requireNonNull(JsonUtil.json2Bean(json, AppkeyCapacityWhiteBO[].class)))
                : Collections.emptyList();
    }

    /**
     * json 转中间件接入名单
     *
     * @param json 中间件 json 串
     * @return {@link List}<{@link AppkeyCapacityMiddleWareBO}>
     */
    @Named("jsonToMiddleWares")
    default List<AppkeyCapacityMiddleWareBO> jsonToMiddleWares(String json){
        return StringUtils.isNotEmpty(json)
                ? Arrays.asList(Objects.requireNonNull(JsonUtil.json2Bean(json, AppkeyCapacityMiddleWareBO[].class)))
                : Collections.emptyList();
    }

    /**
     * json 转服务主机信息
     *
     * @param json json 串
     * @return {@link List}<{@link AppkeyCapacityHostBO}>
     */
    @Named("jsonToHosts")
    default List<AppkeyCapacityHostBO> jsonToHosts(String json){
        return StringUtils.isNotEmpty(json)
                ? Arrays.asList(Objects.requireNonNull(JsonUtil.json2Bean(json, AppkeyCapacityHostBO[].class)))
                : Collections.emptyList();
    }

    /**
     * json转 octo 节点信息
     *
     * @param json json
     * @return {@link List}<{@link AppkeyCapacityOctoProviderBO}>
     */
    @Named("jsonToOctoProviders")
    default List<AppkeyCapacityOctoProviderBO> jsonToOctoProviders(String json){
        return StringUtils.isNotEmpty(json)
                ? Arrays.asList(Objects.requireNonNull(JsonUtil.json2Bean(json, AppkeyCapacityOctoProviderBO[].class)))
                : Collections.emptyList();
    }


    /**
     * json转组件接入信息
     *
     * @param json json
     * @return {@link List}<{@link AppkeyCapacityAccessComponentBO}>
     */
    @Named("jsonToAccessComponents")
    default List<AppkeyCapacityAccessComponentBO> jsonToAccessComponents(String json){
        return StringUtils.isNotEmpty(json)
                ? Arrays.asList(Objects.requireNonNull(JsonUtil.json2Bean(json, AppkeyCapacityAccessComponentBO[].class)))
                : Collections.emptyList();
    }

    /**
     * bean 转 json
     *
     * @param obj obj
     * @return {@link String}
     */
    @Named("bean2Json")
    default <T> String bean2Json(T obj){
        return Objects.nonNull(obj) ? JsonUtil.bean2Json(obj) : "";
    }
}
