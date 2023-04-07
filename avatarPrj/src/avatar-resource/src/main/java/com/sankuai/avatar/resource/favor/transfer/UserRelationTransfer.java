package com.sankuai.avatar.resource.favor.transfer;

import com.sankuai.avatar.dao.resource.repository.model.UserRelationDO;
import com.sankuai.avatar.resource.favor.bo.UserRelationBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-27 16:43
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface UserRelationTransfer {

    UserRelationTransfer INSTANCE = Mappers.getMapper(UserRelationTransfer.class);

    /**
     * BO -> DO
     *
     * @param userRelationBO userRelationBO
     * @return {@link UserRelationDO}
     */
    @Named("toDO")
    UserRelationDO toDO(UserRelationBO userRelationBO);

    /**
     * 批量转换BO -> DO
     *
     * @param userRelationBOList userRelationBOList
     * @return {@link List}<{@link UserRelationDO}>
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<UserRelationDO> toDOList(List<UserRelationBO> userRelationBOList);
}
