package com.sankuai.avatar.resource.whitelist.transfer;

import com.sankuai.avatar.dao.resource.repository.model.WhitelistAppDO;
import com.sankuai.avatar.resource.whitelist.bo.WhitelistAppBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-15 10:22
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface WhitelistAppTransfer {

    WhitelistAppTransfer INSTANCE = Mappers.getMapper(WhitelistAppTransfer.class);

    /**
     * DO -> BO
     *
     * @param whitelistAppDO whitelistAppDO
     * @return {@link WhitelistAppBO}
     */
    @Named("toBO")
    WhitelistAppBO toBO(WhitelistAppDO whitelistAppDO);

    /**
     * 批量转换 DO -> BO
     *
     * @param whitelistAppDOList whitelistAppDOList
     * @return {@link List}<{@link WhitelistAppBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<WhitelistAppBO> toBOList(List<WhitelistAppDO> whitelistAppDOList);
}
