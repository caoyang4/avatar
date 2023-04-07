package com.sankuai.avatar.resource.capacity.transfer;

import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasStandardClientBO;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasStandardClientDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Paas达标客户端 BO 与 DO 的对象转换接口
 * @author caoyang
 * @create 2022-10-11 16:59
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyPaasStandardClientTransfer {
    AppkeyPaasStandardClientTransfer INSTANCE = Mappers.getMapper(AppkeyPaasStandardClientTransfer.class);

    /**
     * BO -> DO
     * @param appkeyPaasStandardClientBO BO
     * @return DO对象
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Named("toDO")
    AppkeyPaasStandardClientDO toDO(AppkeyPaasStandardClientBO appkeyPaasStandardClientBO);

    /**
     * 批量数据模型转换: BO -> DO
     * @param appkeyPaasStandardClientBOList BO 列表
     * @return DO 列表
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<AppkeyPaasStandardClientDO> toDOList(List<AppkeyPaasStandardClientBO> appkeyPaasStandardClientBOList);

    @Named("toBO")
    AppkeyPaasStandardClientBO toBO(AppkeyPaasStandardClientDO appkeyPaasStandardClientDO);

    /**
     * 批量数据模型转换: DO -> BO
     * @param appkeyPaasStandardClientDOList DO 列表
     * @return BO 列表
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<AppkeyPaasStandardClientBO> toBOList(List<AppkeyPaasStandardClientDO> appkeyPaasStandardClientDOList);
}
