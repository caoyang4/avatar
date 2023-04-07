package com.sankuai.avatar.resource.tree.transfer;

import com.sankuai.avatar.client.ops.model.OpsSrv;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeSrvBO;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * appkey srv转换
 *
 * @author qinwei05
 * @date 2022/12/18
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface SrvTransfer {

    /**
     * 转换器
     */
    SrvTransfer INSTANCE = Mappers.getMapper(SrvTransfer.class);

    /**
     * 转换为BO
     * @param opsSrv opsSrv对象
     * @return bo
     */
    @Mapping(source = "rdAdmin", target = "rdAdmin", qualifiedByName = "toAdminList")
    @Mapping(source = "opAdmin", target = "opAdmin", qualifiedByName = "toAdminList")
    @Mapping(source = "epAdmin", target = "epAdmin", qualifiedByName = "toAdminList")
    AppkeyTreeSrvBO toAppkeyTreeSrvBO(OpsSrv opsSrv);

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
