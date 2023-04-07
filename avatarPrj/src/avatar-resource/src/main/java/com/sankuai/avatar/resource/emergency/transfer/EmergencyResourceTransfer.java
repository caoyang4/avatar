package com.sankuai.avatar.resource.emergency.transfer;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.resource.repository.model.EmergencyResourceDO;
import com.sankuai.avatar.resource.emergency.bo.EmergencyResourceBO;
import com.sankuai.avatar.resource.emergency.bo.OfflineHostBO;
import com.sankuai.avatar.resource.emergency.bo.OnlineHostBO;
import com.sankuai.avatar.resource.emergency.constant.OperationType;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

/**
 * 紧急资源转换器
 * @author caoyang
 * @create 2022-12-02 22:24
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface EmergencyResourceTransfer {

    EmergencyResourceTransfer INSTANCE = Mappers.getMapper(EmergencyResourceTransfer.class);

    /**
     * DO -> BO
     *
     * @param resourceDO resourceDO
     * @return {@link EmergencyResourceBO}
     */
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "jsonToOnline")
    @Mapping(source = "offlineHost", target = "offlineHost", qualifiedByName = "jsonToOffline")
    @Mapping(source = "operationType", target = "operationType", qualifiedByName = "jsonToType")
    @Named("toBO")
    EmergencyResourceBO toBO(EmergencyResourceDO resourceDO);

    /**
     * 批量转换DO -> BO
     *
     * @param doList doList
     * @return {@link List}<{@link EmergencyResourceBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<EmergencyResourceBO> toBOList(List<EmergencyResourceDO> doList);

    /**
     * BO -> DO
     *
     * @param resourceBO resourceBO
     * @return {@link EmergencyResourceDO}
     */
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "toOnlineHostJson")
    @Mapping(source = "offlineHost", target = "offlineHost", qualifiedByName = "toOfflineHostJson")
    @Mapping(source = "operationType", target = "operationType", qualifiedByName = "typeToJson")
    @Named("toDO")
    EmergencyResourceDO toDO(EmergencyResourceBO resourceBO);

    /**
     * 批量转换BO -> DO
     *
     * @param boList boList
     * @return {@link List}<{@link EmergencyResourceDO}>
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<EmergencyResourceDO> toDOList(List<EmergencyResourceBO> boList);

    /**
     * 转OnlineHostBO对象
     *
     * @param json json
     * @return {@link OnlineHostBO}
     */
    default OnlineHostBO jsonToOnline(String json){
        return StringUtils.isNotEmpty(json) ? JsonUtil.json2Bean(json, OnlineHostBO.class) : null;
    }

    /**
     * 转OfflineHostBO对象
     *
     * @param json json
     * @return {@link OfflineHostBO}
     */
    default OfflineHostBO jsonToOffline(String json){
        return StringUtils.isNotEmpty(json) ? JsonUtil.json2Bean(json, OfflineHostBO.class) : null;
    }

    /**
     * 转枚举
     *
     * @param json json
     * @return {@link OperationType}
     */
    default OperationType jsonToType(String json){
        return OperationType.getInstance(json);
    }

    /**
     * 转json
     *
     * @param obj obj
     * @return {@link String}
     */
    default String toOnlineHostJson(OnlineHostBO obj){
        return JsonUtil.bean2Json(obj);
    }

    /**
     * 转json
     *
     * @param obj obj
     * @return {@link String}
     */
    default String toOfflineHostJson(OfflineHostBO obj){
        return JsonUtil.bean2Json(obj);
    }

    /**
     * 转字符串
     *
     * @param type 类型
     * @return {@link String}
     */
    default String typeToJson(OperationType type){
        return Objects.nonNull(type) ? type.name() : null;
    }
}
