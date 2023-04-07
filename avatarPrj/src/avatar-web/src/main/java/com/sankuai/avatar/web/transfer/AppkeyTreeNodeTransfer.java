package com.sankuai.avatar.web.transfer;

import com.sankuai.avatar.resource.tree.bo.AppkeyTreeBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeSrvDetailBO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeDTO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeSrvDetailDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 服务树AppkeyTreeNode对象转换器
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyTreeNodeTransfer {

    AppkeyTreeNodeTransfer INSTANCE = Mappers.getMapper(AppkeyTreeNodeTransfer.class);

    /**
     * SrvTreeBO转SrvTreeDTO
     *
     * @param appkeyTreeBO srv树
     * @return {@link AppkeyTreeDTO}
     */
    @Mapping(source = "srv.rdAdmin", target = "srv.rdAdmin", ignore = true)
    @Mapping(source = "srv.epAdmin", target = "srv.epAdmin", ignore = true)
    @Mapping(source = "srv.opAdmin", target = "srv.opAdmin", ignore = true)
    @Mapping(target = "owt.org", ignore = true)
    @Mapping(target = "pdl.owt.org", ignore = true)
    AppkeyTreeDTO toAppkeyTreeDTO(AppkeyTreeBO appkeyTreeBO);

    /**
     * appkeyTreeSrvDetailBOList转AppkeyTreeSrvDetailDTO
     *
     * @param appkeyTreeSrvDetailBO srv详细信息
     * @return {@link AppkeyTreeSrvDetailDTO}
     */
    @Named("toAppkeyTreeSrvDetailDTO")
    @Mapping(source = "srv.rdAdmin", target = "srv.rdAdmin", ignore = true)
    @Mapping(source = "srv.epAdmin", target = "srv.epAdmin", ignore = true)
    @Mapping(source = "srv.opAdmin", target = "srv.opAdmin", ignore = true)
    AppkeyTreeSrvDetailDTO toAppkeyTreeSrvDetailDTO(AppkeyTreeSrvDetailBO appkeyTreeSrvDetailBO);

    /**
     * 批量转换：appkeyTreeSrvDetailBOList转AppkeyTreeSrvDetailDTO
     *
     * @param appkeyTreeSrvDetailBOList srv详细信息
     * @return {@link AppkeyTreeSrvDetailDTO}
     */
    @IterableMapping(qualifiedByName = "toAppkeyTreeSrvDetailDTO")
    List<AppkeyTreeSrvDetailDTO> batchToAppkeyTreeSrvDetailDTO(List<AppkeyTreeSrvDetailBO> appkeyTreeSrvDetailBOList);
}
