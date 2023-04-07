package com.sankuai.avatar.resource.activity.transfer;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.resource.activity.bo.ActivityHostBO;
import com.sankuai.avatar.resource.activity.bo.ActivityResourceBO;
import com.sankuai.avatar.resource.activity.constant.ResourceStatusType;
import com.sankuai.avatar.resource.activity.request.ActivityResourceRequestBO;
import com.sankuai.avatar.dao.resource.repository.model.ActivityResourceDO;
import com.sankuai.avatar.dao.resource.repository.request.ActivityResourceRequest;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-03-08 14:09
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ActivityResourceTransfer {

    ActivityResourceTransfer INSTANCE = Mappers.getMapper(ActivityResourceTransfer.class);

    /**
     * BO -> DO
     *
     * @param requestBO 请求
     * @return {@link ActivityResourceRequest}
     */
    @Named("toRequest")
    ActivityResourceRequest toRequest(ActivityResourceRequestBO requestBO);

    /**
     * DO -> BO
     *
     * @param resourceDO resourceDO
     * @return {@link ActivityResourceBO}
     */
    @Named("toBO")
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "toHostBean")
    @Mapping(source = "status", target = "status", qualifiedByName = "toTypeEnum")
    ActivityResourceBO toBO(ActivityResourceDO resourceDO);

    /**
     * 批量DO -> BO
     *
     * @param doList doList
     * @return {@link List}<{@link ActivityResourceBO}>
     */
    @Named("toBOList")
    @IterableMapping(qualifiedByName = "toBO")
    List<ActivityResourceBO> toBOList(List<ActivityResourceDO> doList);

    /**
     * BO -> DO
     *
     * @param resourceBO resourceBO
     * @return {@link ActivityResourceDO}
     */
    @Named("toDO")
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "toHostJson")
    @Mapping(source = "status", target = "status", qualifiedByName = "toType")
    ActivityResourceDO toDO(ActivityResourceBO resourceBO);

    /**
     * 批量BO -> DO
     *
     * @param boList boList
     * @return {@link List}<{@link ActivityResourceDO}>
     */
    @Named("toDOList")
    @IterableMapping(qualifiedByName = "toDO")
    List<ActivityResourceDO> toDOList(List<ActivityResourceBO> boList);


    /**
     * json -> bean
     *
     * @param json json
     * @return {@link ActivityHostBO}
     */
    @Named("toHostBean")
    default ActivityHostBO toHostBean(String json){
        return StringUtils.isNotEmpty(json) ? JsonUtil.json2Bean(json, ActivityHostBO.class) : null;
    }

    /**
     * bean -> json
     *
     * @param hostBO hostBO
     * @return {@link String}
     */
    @Named("toHostJson")
    default String toHostJson(ActivityHostBO hostBO){
        return JsonUtil.bean2Json(hostBO);
    }

    /**
     * 字符串转枚举
     *
     * @param status 状态
     * @return {@link ResourceStatusType}
     */
    @Named("toTypeEnum")
    default ResourceStatusType toTypeEnum(String status){
        return ResourceStatusType.getResourceStatusType(status);
    }

    /**
     * 枚举转字符串
     *
     * @param type 状态枚举
     * @return {@link String}
     */
    @Named("toType")
    default String toType(ResourceStatusType type){
        return Objects.nonNull(type) ? type.name() : "";
    }
}
