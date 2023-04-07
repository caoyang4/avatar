package com.sankuai.avatar.resource.application.transfer;

import com.sankuai.avatar.resource.application.bo.ApplicationRoleAdminBO;
import com.sankuai.avatar.dao.resource.repository.model.ApplicationRoleAdminDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-16 17:39
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ApplicationRoleAdminTransfer {

    ApplicationRoleAdminTransfer INSTANCE = Mappers.getMapper(ApplicationRoleAdminTransfer.class);

    /**
     * DO -> BO
     *
     * @param applicationRoleAdminDO applicationRoleAdminDO
     * @return {@link ApplicationRoleAdminBO}
     */
    @Named("toBO")
    ApplicationRoleAdminBO toBO(ApplicationRoleAdminDO applicationRoleAdminDO);

    /**
     * 批量转换DO -> BO
     *
     * @param doList doList
     * @return {@link List}<{@link ApplicationRoleAdminBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<ApplicationRoleAdminBO> toBOList(List<ApplicationRoleAdminDO> doList);

    /**
     * BO -> DO
     *
     * @param bo bo
     * @return {@link ApplicationRoleAdminDO}
     */
    @Named("toDO")
    ApplicationRoleAdminDO toDO(ApplicationRoleAdminBO bo);

    /**
     * 批量转换BO -> DO
     *
     * @param boList boList
     * @return {@link List}<{@link ApplicationRoleAdminDO}>
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<ApplicationRoleAdminDO> toDOList(List<ApplicationRoleAdminBO> boList);

}
