package com.sankuai.avatar.web.transfer.emergency;

import com.sankuai.avatar.resource.emergency.bo.EmergencyResourceBO;
import com.sankuai.avatar.resource.emergency.bo.OfflineHostBO;
import com.sankuai.avatar.resource.emergency.bo.OnlineHostBO;
import com.sankuai.avatar.web.dto.emergency.EmergencyResourceDTO;
import com.sankuai.avatar.web.dto.emergency.OfflineHostDTO;
import com.sankuai.avatar.web.dto.emergency.OnlineHostDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-30 17:02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface EmergencyResourceDTOTransfer {

    EmergencyResourceDTOTransfer INSTANCE = Mappers.getMapper(EmergencyResourceDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param bo bo
     * @return {@link EmergencyResourceDTO}
     */
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "toOnlineHostDTO")
    @Mapping(source = "offlineHost", target = "offlineHost", qualifiedByName = "toOfflineHostDTO")
    @Named("toDTO")
    EmergencyResourceDTO toDTO(EmergencyResourceBO bo);

    /**
     * 批量转换 BO -> DTO
     *
     * @param boList boList
     * @return {@link List}<{@link EmergencyResourceDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<EmergencyResourceDTO> toDTOList(List<EmergencyResourceBO> boList);

    /**
     * DTO -> BO
     *
     * @param dto dto
     * @return {@link EmergencyResourceBO}
     */
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "toOnlineHostBO")
    @Mapping(source = "offlineHost", target = "offlineHost", qualifiedByName = "toOfflineHostBO")
    @Named("toBO")
    EmergencyResourceBO toBO(EmergencyResourceDTO dto);

    /**
     * 批量转换 DTO -> BO
     *
     * @param dtoList dto列表
     * @return {@link List}<{@link EmergencyResourceBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<EmergencyResourceBO> toBOList(List<EmergencyResourceDTO> dtoList);

    /**
     * BO -> DTO
     *
     * @param onlineHostBO onlineHostBO
     * @return {@link OnlineHostDTO}
     */
    default OnlineHostDTO toOnlineHostDTO(OnlineHostBO onlineHostBO){
        return EmergencyAttributesDTOTransfer.INSTANCE.toOnlineHostDTO(onlineHostBO);
    }

    /**
     * BO -> DTO
     *
     * @param offlineHostBO offlineHostBO
     * @return {@link OfflineHostDTO}
     */
    default OfflineHostDTO toOfflineHostDTO(OfflineHostBO offlineHostBO){
        return EmergencyAttributesDTOTransfer.INSTANCE.toOfflineHostDTO(offlineHostBO);
    }

    /**
     * DTO -> BO
     *
     * @param onlineHostDTO onlineHostDTO
     * @return {@link OnlineHostBO}
     */
    default OnlineHostBO toOnlineHostBO(OnlineHostDTO onlineHostDTO){
        return EmergencyAttributesDTOTransfer.INSTANCE.toOnlineHostBO(onlineHostDTO);
    }

    /**
     * DTO -> BO
     *
     * @param offlineHostDTO offlineHostDTO
     * @return {@link OfflineHostBO}
     */
    default OfflineHostBO toOfflineHostBO(OfflineHostDTO offlineHostDTO){
        return EmergencyAttributesDTOTransfer.INSTANCE.toOfflineHostBO(offlineHostDTO);
    }

}
