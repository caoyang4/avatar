package com.sankuai.avatar.resource.orgRole.transfer;

import com.sankuai.avatar.dao.resource.repository.model.DxGroupDO;
import com.sankuai.avatar.resource.orgRole.bo.DxGroupBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * DxGroup转换器
 * @author caoyang
 * @create 2022-11-10 16:00
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface DxGroupTransfer {

    DxGroupTransfer INSTANCE = Mappers.getMapper(DxGroupTransfer.class);

    /**
     * BO -> DO
     *
     * @param dxGroupDO DxGroupBO
     * @return {@link DxGroupBO}
     */
    @Named("toBO")
    DxGroupBO toBO(DxGroupDO dxGroupDO);

    /**
     * 批量转换 BO -> DO
     *
     * @param dxGroupDOList DOList
     * @return {@link List}<{@link DxGroupBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<DxGroupBO> toBOList(List<DxGroupDO> dxGroupDOList);

    /**
     * BO -> DO
     *
     * @param dxGroupBO DxGroupDO
     * @return {@link DxGroupDO}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Named("toDO")
    DxGroupDO toDO(DxGroupBO dxGroupBO);

    /**
     * 批量转换BO -> DO
     *
     * @param dxGroupBOList BOList
     * @return {@link List}<{@link DxGroupDO}>
     */
    @IterableMapping(qualifiedByName = "toDo")
    @Named("toDOList")
    List<DxGroupDO> toDOList(List<DxGroupBO> dxGroupBOList);
}
