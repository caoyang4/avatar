package com.sankuai.avatar.resource.capacity.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacitySummaryBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasCapacityBO;
import com.sankuai.avatar.resource.capacity.constant.PaasCapacityType;
import com.sankuai.avatar.dao.cache.model.CapacitySummary;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasCapacityDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.*;

/**
 * Paas容灾数据 BO 与 DO 的对象转换接口
 * @author caoyang
 * @create 2022-09-28 17:24
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyPaasCapacityTransfer {

    AppkeyPaasCapacityTransfer INSTANCE = Mappers.getMapper(AppkeyPaasCapacityTransfer.class);

    /**
     * Paas容灾数据对象 BO 转为 DO
     * @param appkeyPaasCapacityBO BO 对象
     * @return DO对象
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(source = "clientConfig", target = "clientConfig", qualifiedByName = "clientConfigToJson")
    @Mapping(source = "standardConfig", target = "standardConfig", qualifiedByName = "clientConfigToJson")
    @Mapping(source = "type", target = "type", qualifiedByName = "java(appkeyPaasCapacityBO.getType().getCapacityType())")
    @Named("toDO")
    AppkeyPaasCapacityDO toDO(AppkeyPaasCapacityBO appkeyPaasCapacityBO);

    /**
     * 批量数据模型转换: BO -> DO
     * @param appkeyPaasCapacityBOList BOList
     * @return DOList
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<AppkeyPaasCapacityDO> toDOList(List<AppkeyPaasCapacityBO> appkeyPaasCapacityBOList);

    /**
     * Paas容灾数据对象 DO 转为 BO
     * @param appkeyPaasCapacityDO DO 对象
     * @return BO 对象
     */
    @Mapping(source = "clientConfig", target = "clientConfig", qualifiedByName = "jsonToClientConfig")
    @Mapping(source = "standardConfig", target = "standardConfig", qualifiedByName = "jsonToClientConfig")
    @Mapping(source = "type", target = "type", qualifiedByName = "toPaasCapacityType")
    @Named("toBO")
    AppkeyPaasCapacityBO toBO(AppkeyPaasCapacityDO appkeyPaasCapacityDO);

    /**
     * 批量数据模型转换: DO -> BO
     * @param appkeyPaasCapacityDOList DOList
     * @return BOList
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<AppkeyPaasCapacityBO> toBOList(List<AppkeyPaasCapacityDO> appkeyPaasCapacityDOList);

    /**
     * DO -> BO
     *
     * @param capacitySummary capacitySummary
     * @return {@link AppkeyCapacitySummaryBO}
     */
    @Named("toSummaryBO")
    AppkeyCapacitySummaryBO toSummaryBO(CapacitySummary capacitySummary);

    /**
     * BO -> DO
     *
     * @param bo bo
     * @return {@link CapacitySummary}
     */
    @Named("toSummary")
    CapacitySummary toSummary(AppkeyCapacitySummaryBO bo);

    /**
     * 容灾配置 List<Map> 对象转为 json 字符串
     * @param clientConfig 容灾配置 List<Map> 对象
     * @return json 字符串
     * @throws JsonProcessingException exception
     */
    @Named("clientConfigToJson")
    default String clientConfigToJson(List<Map<String,String>> clientConfig) throws JsonProcessingException {
        if (Objects.nonNull(clientConfig)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(clientConfig);
        }
        return "";
    }

    /**
     * json 转 List<Map<String,String>>
     * @param configInfo
     * @return
     * @throws IOException
     */
    @Named("jsonToClientConfig")
    default List<Map<String,String>> jsonToClientConfig(String configInfo) throws IOException {
        if (Objects.nonNull(configInfo)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(configInfo, List.class);
        }
        return Collections.emptyList();
    }

    /**
     * 字符串转容灾类型枚举对象
     * @param type
     * @return
     */
    @Named("toPaasCapacityType")
    default PaasCapacityType toPaasCapacityType(String type){
        return Arrays.stream(PaasCapacityType.values()).filter(
                e -> Objects.equals(type, e.getCapacityType())
        ).findFirst().orElse(null);
    }

}
