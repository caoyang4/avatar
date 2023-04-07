package com.sankuai.avatar.web.transfer.orgRole;

import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.web.dto.orgRole.OrgRoleAdminDTO;
import com.sankuai.avatar.web.vo.orgRole.DxGroupVO;
import com.sankuai.avatar.web.vo.orgRole.OrgRoleAdminVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * OrgRoleAdmin VO转换器
 * @author caoyang
 * @create 2022-11-11 14:01
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgRoleAdminVOTransfer {

    OrgRoleAdminVOTransfer INSTANCE = Mappers.getMapper(OrgRoleAdminVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param orgRoleAdminDTO orgRoleAdminDTO
     * @return {@link OrgRoleAdminVO}
     */
    @Mapping(target = "groups", ignore = true)
    @Mapping(source = "role", target = "role", qualifiedByName = "toRole")
    @Mapping(source = "roleUsers", target = "roleUsers", defaultValue = "")
    @Mapping(source = "updateUser", target = "updateUser", defaultValue = "")
    @Mapping(source = "orgName", target = "orgName", defaultValue = "")
    @Mapping(source = "orgPath", target = "orgPath", defaultValue = "")
    @Mapping(target = "deleteAllChild", ignore = true)
    @Named("toVO")
    OrgRoleAdminVO toVO(OrgRoleAdminDTO orgRoleAdminDTO);

    /**
     * 批量转换DTO -> VO
     *
     * @param orgRoleAdminDTOList DTOList
     * @return {@link List}<{@link OrgRoleAdminVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<OrgRoleAdminVO> toVOList(List<OrgRoleAdminDTO> orgRoleAdminDTOList);

    /**
     * VO -> DTO
     *
     * @param orgRoleAdminVO orgRoleAdminVO
     * @return {@link OrgRoleAdminDTO}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "role", target = "role", qualifiedByName = "toOrgRoleType")
    @Mapping(source = "groups", target = "groupId", qualifiedByName = "toGroupIds")
    @Named("toDTO")
    OrgRoleAdminDTO toDTO(OrgRoleAdminVO orgRoleAdminVO);

    /**
     * 批量转换VO -> DTO
     *
     * @param orgRoleAdminVOList VOList
     * @return {@link List}<{@link OrgRoleAdminDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<OrgRoleAdminDTO> toDTOList(List<OrgRoleAdminVO> orgRoleAdminVOList);

    /**
     * 转换为 groupIds
     *
     * @param dxGroupVOList VOList
     * @return {@link String}
     */
    @Named("toGroupIds")
    default String toGroupIds(List<DxGroupVO> dxGroupVOList){
        if (Objects.isNull(dxGroupVOList)) {
            return null;
        }
        return dxGroupVOList.stream().map(DxGroupVO::getGroupId).collect(Collectors.joining(","));
    }

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
