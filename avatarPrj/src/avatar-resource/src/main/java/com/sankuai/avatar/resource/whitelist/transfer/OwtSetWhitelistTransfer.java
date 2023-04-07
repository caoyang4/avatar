package com.sankuai.avatar.resource.whitelist.transfer;

import com.sankuai.avatar.dao.resource.repository.model.OwtSetWhitelistDO;
import com.sankuai.avatar.resource.whitelist.bo.OwtSetWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * OwtSetWhitelist DO - BO 转换器
 * @author caoyang
 * @create 2022-10-27 19:15
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OwtSetWhitelistTransfer {
    OwtSetWhitelistTransfer INSTANCE = Mappers.getMapper(OwtSetWhitelistTransfer.class);

    /**
     * BO -> DO
     *
     * @param owtSetWhitelistBO BO
     * @return {@link OwtSetWhitelistDO}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addTime", ignore = true)
    @Mapping(source = "app", target = "app", qualifiedByName = "toWhiteApp")
    @Named("toDO")
    OwtSetWhitelistDO toDO(OwtSetWhitelistBO owtSetWhitelistBO);

    /**
     * 批量转换 BO -> DO
     *
     * @param owtSetWhitelistBOList BOList
     * @return {@link List}<{@link OwtSetWhitelistDO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toDOList")
    List<OwtSetWhitelistDO> toDOList(List<OwtSetWhitelistBO> owtSetWhitelistBOList);

    /**
     * DO -> BO
     *
     * @param owtSetWhitelistDO DO
     * @return {@link OwtSetWhitelistBO}
     */
    @Mapping(source = "app", target = "app", qualifiedByName = "toWhiteType")
    @Named("toBO")
    OwtSetWhitelistBO toBO(OwtSetWhitelistDO owtSetWhitelistDO);

    /**
     * 批量转换 DO -> BO
     *
     * @param owtSetWhitelistBOList BOList
     * @return {@link List}<{@link OwtSetWhitelistBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<OwtSetWhitelistBO> toBOList(List<OwtSetWhitelistDO> owtSetWhitelistBOList);


    /**
     * 转换白名单类型枚举
     *
     * @param app 类型
     * @return {@link WhiteType}
     */
    @Named("toWhiteType")
    default WhiteType toWhiteType(String app){
        return Arrays.stream(WhiteType.values()).filter(e -> Objects.equals(e.getWhiteType(), app)).findFirst().orElse(null);
    }
    /**
     * 白名单类型枚举转字符串
     *
     * @param type 类型
     * @return {@link String}
     */
    @Named("toWhiteApp")
    default String toWhiteApp(WhiteType type){
        return Objects.nonNull(type) ? type.getWhiteType() : null;
    }
}
