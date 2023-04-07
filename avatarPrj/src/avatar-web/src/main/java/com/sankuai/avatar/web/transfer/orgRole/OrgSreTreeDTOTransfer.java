package com.sankuai.avatar.web.transfer.orgRole;

import com.sankuai.avatar.web.dto.orgRole.OrgSreTreeDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-11-14 11:32
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgSreTreeDTOTransfer {

    OrgSreTreeDTOTransfer INSTANCE = Mappers.getMapper(OrgSreTreeDTOTransfer.class);

    /**
     * dto转换
     *
     * @param scOrg scOrg
     * @return {@link OrgSreTreeDTO}
     */
    @Mapping(source = "id", target = "id", qualifiedByName = "convertId")
    @Mapping(target = "roleUsers")
    @Mapping(target = "opAdmins")
    @Mapping(target = "appkeyCount")
    @Named("toOrgSreTreeDTO")
    OrgSreTreeDTO toOrgSreTreeDTO(com.sankuai.avatar.sdk.entity.servicecatalog.Org scOrg);

    /**
     * 批量dto转换
     *
     * @param scOrgList scOrgList
     * @return {@link List}<{@link OrgSreTreeDTO}>
     */
    @IterableMapping(qualifiedByName = "toOrgSreTreeDTO")
    @Named("toOrgSreTreeDTOList")
    List<OrgSreTreeDTO> toOrgSreTreeDTOList(List<com.sankuai.avatar.sdk.entity.servicecatalog.Org> scOrgList);

    /**
     * 将id转为字符串
     *
     * @param id id
     * @return {@link String}
     */
    @Named("convertId")
    default String convertId(Integer id){
        return Objects.nonNull(id) ? String.valueOf(id) : "";
    }
}
