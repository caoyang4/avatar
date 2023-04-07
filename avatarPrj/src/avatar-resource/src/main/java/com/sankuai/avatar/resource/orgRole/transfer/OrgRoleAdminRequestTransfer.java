package com.sankuai.avatar.resource.orgRole.transfer;

import com.sankuai.avatar.dao.resource.repository.request.OrgRoleAdminRequest;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.resource.orgRole.request.OrgRoleAdminRequestBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

/**
 *  OrgRoleAdminRequest转换器
 * @author caoyang
 * @create 2022-11-10 16:53
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgRoleAdminRequestTransfer {

    OrgRoleAdminRequestTransfer INSTANCE = Mappers.getMapper(OrgRoleAdminRequestTransfer.class);

    /**
     * toOrgRoleAdminRequest
     *
     * @param requestBO 请求BO
     * @return {@link OrgRoleAdminRequest}
     */
    @Mapping(source = "role", target = "role", qualifiedByName = "toRole")
    @Named("toOrgRoleAdminRequest")
    OrgRoleAdminRequest toOrgRoleAdminRequest(OrgRoleAdminRequestBO requestBO);

    /**
     * 转字符串
     *
     * @param role 角色
     * @return {@link String}
     */
    @Named("toRole")
    default String toRole(OrgRoleType role){
        return Objects.nonNull(role) ? role.getRoleType() : null;
    }

}
