package com.sankuai.avatar.resource.tree.transfer;

import com.sankuai.avatar.client.ops.model.OpsOrg;
import com.sankuai.avatar.client.ops.model.OpsOwt;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeOwtBO;
import com.sankuai.avatar.resource.tree.bo.OwtOrgBO;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Owt数据类型转换接口
 * @author zhangxiaoning
 * @create 2022-10-24
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyTreeOwtTransfer {
    /**
     * Owt数据类型转换器
     */
    AppkeyTreeOwtTransfer INSTANCE = Mappers.getMapper(AppkeyTreeOwtTransfer.class);

    /**
     * DO -> BO
     *
     * @param opsOwt opsOwt
     * @return {@link AppkeyTreeOwtBO}
     */
    @Mapping(source = "rdAdmin", target = "rdAdmin", qualifiedByName = "toAdminList")
    @Mapping(source = "opAdmin", target = "opAdmin", qualifiedByName = "toAdminList")
    @Mapping(source = "epAdmin", target = "epAdmin", qualifiedByName = "toAdminList")
    @Mapping(target = "org", ignore = true)
    @Named("toBO")
    AppkeyTreeOwtBO toBO(OpsOwt opsOwt);

    /**
     * 批量转换 DO -> BO
     *
     * @param opsOwtList opsOwtList
     * @return {@link List}<{@link AppkeyTreeOwtBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<AppkeyTreeOwtBO> toBOList(List<OpsOwt> opsOwtList);

    /**
     * DO -> BO
     *
     * @param opsOrg opsOrg
     * @return {@link OwtOrgBO}
     */
    @Named("toOwtOrgBO")
    OwtOrgBO toOwtOrgBO(OpsOrg opsOrg);

    /**
     * 批量转换 DO -> BO
     *
     * @param opsOrgList opsOrgList
     * @return {@link List}<{@link OwtOrgBO}>
     */
    @IterableMapping(qualifiedByName = "toOwtOrgBO")
    @Named("toOwtOrgBOList")
    List<OwtOrgBO> toOwtOrgBOList(List<OpsOrg> opsOrgList);

    /**
     * 字符串转列表
     *
     * @param admin admin
     * @return {@link List}<{@link String}>
     */
    default List<String> toAdminList(String admin){
        if (StringUtils.isEmpty(admin)) {
            return Collections.emptyList();
        }
        return Arrays.asList(admin.split(","));
    }
}
