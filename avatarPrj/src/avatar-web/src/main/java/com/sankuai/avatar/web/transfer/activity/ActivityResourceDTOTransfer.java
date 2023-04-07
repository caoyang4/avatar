package com.sankuai.avatar.web.transfer.activity;

import com.sankuai.avatar.resource.activity.bo.ActivityHostBO;
import com.sankuai.avatar.resource.activity.bo.ActivityResourceBO;
import com.sankuai.avatar.resource.activity.request.ActivityResourceRequestBO;
import com.sankuai.avatar.web.dto.activity.ActivityHostDTO;
import com.sankuai.avatar.web.dto.activity.ActivityResourceDTO;
import com.sankuai.avatar.web.request.ActivityResourcePageRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-03-08 15:48
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ActivityResourceDTOTransfer {

    ActivityResourceDTOTransfer INSTANCE = Mappers.getMapper(ActivityResourceDTOTransfer.class);

    /**
     * DTO -> BO
     *
     * @param pageRequest 请求
     * @return {@link ActivityResourceRequestBO}
     */
    ActivityResourceRequestBO toRequestBO(ActivityResourcePageRequest pageRequest);

    /**
     * BO -> DTO
     *
     * @param hostBO hostBO
     * @return {@link ActivityHostDTO}
     */
    @Named("toHostDTO")
    ActivityHostDTO toHostDTO(ActivityHostBO hostBO);

    /**
     * DTO -> BO
     *
     * @param hostDTO 主机dto
     * @return {@link ActivityHostBO}
     */
    @Named("toHostBO")
    ActivityHostBO toHostBO(ActivityHostDTO hostDTO);

    /**
     * BO -> DTO
     *
     * @param resourceBO resourceBO
     * @return {@link ActivityResourceDTO}
     */
    @Named("toDTO")
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "toHostDTO")
    ActivityResourceDTO toDTO(ActivityResourceBO resourceBO);

    /**
     * 批量BO -> DTO
     *
     * @param boList boList
     * @return {@link List}<{@link ActivityResourceDTO}>
     */
    @Named("toDTOList")
    @IterableMapping(qualifiedByName = "toDTO")
    List<ActivityResourceDTO> toDTOList(List<ActivityResourceBO> boList);

    /**
     * DTO -> BO
     *
     * @param resourceDTO resourceDTO
     * @return {@link ActivityResourceBO}
     */
    @Named("toBO")
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "toHostBO")
    ActivityResourceBO toBO(ActivityResourceDTO resourceDTO);

}
