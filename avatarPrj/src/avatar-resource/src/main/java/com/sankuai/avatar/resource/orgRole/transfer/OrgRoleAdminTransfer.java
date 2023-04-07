package com.sankuai.avatar.resource.orgRole.transfer;

import com.sankuai.avatar.client.org.model.Org;
import com.sankuai.avatar.dao.resource.repository.model.OrgRoleAdminDO;
import com.sankuai.avatar.resource.orgRole.bo.OrgBO;
import com.sankuai.avatar.resource.orgRole.bo.OrgRoleAdminBO;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * OrgRoleAdmin转换器
 * @author caoyang
 * @create 2022-11-10 16:00
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgRoleAdminTransfer {

    OrgRoleAdminTransfer INSTANCE = Mappers.getMapper(OrgRoleAdminTransfer.class);

    /**
     * DO -> BO
     *
     * @param orgRoleAdminDO orgRoleAdminDO
     * @return {@link OrgRoleAdminBO}
     */
    @Mapping(source = "role", target = "role", qualifiedByName = "toOrgRoleType")
    @Named("toBO")
    OrgRoleAdminBO toBO(OrgRoleAdminDO orgRoleAdminDO);

    /**
     * 批量转换 DO -> BO
     *
     * @param orgRoleAdminDOList DOList
     * @return {@link List}<{@link OrgRoleAdminBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<OrgRoleAdminBO> toBOList(List<OrgRoleAdminDO> orgRoleAdminDOList);

    /**
     * BO -> DO
     *
     * @param orgRoleAdminBO orgRoleAdminBO
     * @return {@link OrgRoleAdminDO}
     */
    @Mapping(source = "role", target = "role", qualifiedByName = "toRole")
    @Named("toDO")
    OrgRoleAdminDO toDO(OrgRoleAdminBO orgRoleAdminBO);

    /**
     * 批量转换 BO -> DO
     *
     * @param orgRoleAdminBOList BOList
     * @return {@link List}<{@link OrgRoleAdminDO}>
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<OrgRoleAdminDO> toDOList(List<OrgRoleAdminBO> orgRoleAdminBOList);

    /**
     * orgBO
     *
     * @param org org
     * @return {@link OrgBO}
     */
    @Named("toOrgBO")
    OrgBO toOrgBO(Org org);

    /**
     *  角色类型转枚举
     *
     * @param role 角色
     * @return {@link OrgRoleType}
     */
    default OrgRoleType toOrgRoleType(String role){
        return Arrays.stream(OrgRoleType.values()).filter(
                r -> Objects.equals(role, r.getRoleType())
        ).findFirst().orElse(null);
    }

    /**
     * 转换为字符串
     *
     * @param role 角色
     * @return {@link String}
     */
    @Named("toRole")
    default String toRole(OrgRoleType role){
        return role.getRoleType();
    }

}
