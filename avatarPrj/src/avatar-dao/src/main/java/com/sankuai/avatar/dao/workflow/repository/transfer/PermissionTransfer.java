package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.entity.PermissionEntity;
import com.sankuai.avatar.dao.workflow.repository.model.PermissionDO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface PermissionTransfer {
    PermissionTransfer INSTANCE = Mappers.getMapper(PermissionTransfer.class);

    /**
     * PermissionDO 转 PermissionEntity
     *
     * @param permissionDO PermissionDO
     * @return PermissionEntity
     */
    PermissionEntity toEntity(PermissionDO permissionDO);


    /**
     * List<PermissionDO> 转 List<PermissionEntity>
     *
     * @param permissionDOList List<PermissionDO>
     * @return List<PermissionEntity>
     */
    @IterableMapping(qualifiedByName = "toEntity")
    List<PermissionEntity> toEntityList(List<PermissionDO> permissionDOList);
}
