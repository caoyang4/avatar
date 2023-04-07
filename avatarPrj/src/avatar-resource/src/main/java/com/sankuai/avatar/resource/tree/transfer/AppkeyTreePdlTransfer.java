package com.sankuai.avatar.resource.tree.transfer;

import com.sankuai.avatar.client.ops.model.OpsPdl;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreePdlBO;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Pdl数据类型转换器
 *
 * @author zhangxiaoning07
 * @create 2022/11/1
 **/
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyTreePdlTransfer {

    AppkeyTreePdlTransfer INSTANCE = Mappers.getMapper(AppkeyTreePdlTransfer.class);

    /**
     * 转换为BO
     *
     * @param opsPdl OpsPdl对象
     * @return OpsPdlBO对象
     */
    @Mapping(source = "rdAdmin", target = "rdAdmin", qualifiedByName = "toAdminList")
    @Mapping(source = "opAdmin", target = "opAdmin", qualifiedByName = "toAdminList")
    @Mapping(source = "epAdmin", target = "epAdmin", qualifiedByName = "toAdminList")
    @Mapping(target = "owt", ignore = true)
    @Mapping(target = "srvCount", ignore = true)
    @Named("toBO")
    AppkeyTreePdlBO toBO(OpsPdl opsPdl);

    /**
     * 批量转换为BO列表
     *
     * @param opsPdlList OpsPdl对象列表
     * @return OpsPdlBO对象列表
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<AppkeyTreePdlBO> toBOList(List<OpsPdl> opsPdlList);

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
