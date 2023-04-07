package com.sankuai.avatar.resource.capacity.transfer;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasClientDO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasClientBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Paas客户端 BO 与 DO 的对象转换接口
 * @author caoyang
 * @create 2022-10-11 16:58
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyPaasClientTransfer {
    AppkeyPaasClientTransfer INSTANCE = Mappers.getMapper(AppkeyPaasClientTransfer.class);


    /**
     * BO -> DO
     * @param appkeyPaasClientBO BO
     * @return DO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Named("toDO")
    AppkeyPaasClientDO toDO(AppkeyPaasClientBO appkeyPaasClientBO);

    /**
     * 批量数据模型转换: BO -> DO
     * @param appkeyPaasClientBOList  RO列表
     * @return DO列表
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<AppkeyPaasClientDO> toDOList(List<AppkeyPaasClientBO> appkeyPaasClientBOList);

    /**
     * DO -> BO
     * @param appkeyPaasClientDO DO
     * @return BO
     */
    @Named("toBO")
    AppkeyPaasClientBO toBO(AppkeyPaasClientDO appkeyPaasClientDO);

    /**
     * 批量数据模型转换: DOList -> BOList
     * @param appkeyPaasClientDOList DO列表
     * @return BO列表
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<AppkeyPaasClientBO> toBOList(List<AppkeyPaasClientDO> appkeyPaasClientDOList);
}
