package com.sankuai.avatar.web.transfer.emergency;

import com.sankuai.avatar.resource.emergency.bo.EmergencyHostBO;
import com.sankuai.avatar.resource.emergency.bo.HostIdcBO;
import com.sankuai.avatar.resource.emergency.bo.OfflineHostBO;
import com.sankuai.avatar.resource.emergency.bo.OnlineHostBO;
import com.sankuai.avatar.web.dto.emergency.EmergencyHostDTO;
import com.sankuai.avatar.web.dto.emergency.HostIdcDTO;
import com.sankuai.avatar.web.dto.emergency.OfflineHostDTO;
import com.sankuai.avatar.web.dto.emergency.OnlineHostDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-03 10:49
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface EmergencyAttributesDTOTransfer {

    EmergencyAttributesDTOTransfer INSTANCE = Mappers.getMapper(EmergencyAttributesDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param hostIdcBO hostIdcBO
     * @return {@link HostIdcDTO}
     */
    @Named("toHostIdcDTO")
    HostIdcDTO toHostIdcDTO(HostIdcBO hostIdcBO);

    /**
     * 批量转换 BO -> DTO
     *
     * @param hostIdcBOList 主机idc bolist
     * @return {@link List}<{@link HostIdcDTO}>
     */
    @IterableMapping(qualifiedByName = "toHostIdcDTO")
    @Named("toHostIdcDTOList")
    List<HostIdcDTO> toHostIdcDTOList(List<HostIdcBO> hostIdcBOList);

    /**
     * DTO -> BO
     *
     * @param hostIdcDTO hostIdcDTO
     * @return {@link HostIdcBO}
     */
    @Named("toHostIdcBO")
    HostIdcBO toHostIdcBO(HostIdcDTO hostIdcDTO);

    /**
     * 批量转换DTO -> BO
     *
     * @param hostIdcDTOList hostIdcDTOList
     * @return {@link List}<{@link HostIdcBO}>
     */
    @IterableMapping(qualifiedByName = "toHostIdcBO")
    @Named("toHostIdcBOList")
    List<HostIdcBO> toHostIdcBOList(List<HostIdcDTO> hostIdcDTOList);

    /**
     * BO -> DTO
     *
     * @param emergencyHostBO emergencyHostBO
     * @return {@link EmergencyHostDTO}
     */
    @Named("toHostDTO")
    EmergencyHostDTO toHostDTO(EmergencyHostBO emergencyHostBO);

    /**
     * 批量转换BO -> DTO
     *
     * @param emergencyHostBOList emergencyHostBOList
     * @return {@link List}<{@link EmergencyHostDTO}>
     */
    @IterableMapping(qualifiedByName = "toHostDTO")
    @Named("toHostDTOList")
    List<EmergencyHostDTO> toHostDTOList(List<EmergencyHostBO> emergencyHostBOList);

    /**
     * DTO -> BO
     *
     * @param emergencyHostDTO emergencyHostDTO
     * @return {@link EmergencyHostBO}
     */
    @Named("toHostBO")
    EmergencyHostBO toHostBO(EmergencyHostDTO emergencyHostDTO);

    /**
     * 批量转换DTO -> BO
     *
     * @param emergencyHostBOList emergencyHostBOList
     * @return {@link List}<{@link EmergencyHostDTO}>
     */
    @IterableMapping(qualifiedByName = "toHostBO")
    @Named("toHostBOList")
    List<EmergencyHostDTO> toHostBOList(List<EmergencyHostBO> emergencyHostBOList);

    /**
     * 批量转换BO -> DTO
     *
     * @param onlineHostBO onlineHostBO
     * @return {@link OnlineHostDTO}
     */
    @Named("toOnlineHostDTO")
    OnlineHostDTO toOnlineHostDTO(OnlineHostBO onlineHostBO);

    /**
     * DTO -> BO
     *
     * @param onlineHostDTO onlineHostDTO
     * @return {@link OnlineHostBO}
     */
    @Mapping(source = "idcs", target = "idcs", qualifiedByName = "toHostIdcBOList")
    @Named("toOnlineHostBO")
    OnlineHostBO toOnlineHostBO(OnlineHostDTO onlineHostDTO);

    /**
     * BO -> DTO
     *
     * @param offlineHostBO offlineHostBO
     * @return {@link OfflineHostDTO}
     */
    @Mapping(source = "hosts", target = "hosts", qualifiedByName = "toHostDTOList")
    @Named("toOfflineHostDTO")
    OfflineHostDTO toOfflineHostDTO(OfflineHostBO offlineHostBO);

    /**
     * DTO -> BO
     *
     * @param offlineHostDTO offlineHostDTO
     * @return {@link OfflineHostBO}
     */
    @Mapping(source = "hosts", target = "hosts", qualifiedByName = "toHostBOList")
    @Named("toOfflineHostBO")
    OfflineHostBO toOfflineHostBO(OfflineHostDTO offlineHostDTO);

}
