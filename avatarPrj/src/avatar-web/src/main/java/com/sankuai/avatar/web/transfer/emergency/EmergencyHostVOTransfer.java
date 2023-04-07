package com.sankuai.avatar.web.transfer.emergency;

import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.web.dto.emergency.EmergencyHostDTO;
import com.sankuai.avatar.web.dto.emergency.EmergencyResourceDTO;
import com.sankuai.avatar.web.dto.emergency.OfflineHostDTO;
import com.sankuai.avatar.web.vo.emergency.EmergencyOfflineVO;
import com.sankuai.avatar.web.vo.emergency.EmergencyOnlineVO;
import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-01-03 16:48
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public abstract class EmergencyHostVOTransfer {

    public static EmergencyHostVOTransfer INSTANCE = Mappers.getMapper(EmergencyHostVOTransfer.class);

    public List<EmergencyOfflineVO> toEmergencyOfflineVOList(List<EmergencyResourceDTO> dtoList){
        List<EmergencyOfflineVO> list = new ArrayList<>();
        for (EmergencyResourceDTO dto : dtoList) {
            OfflineHostDTO offlineHost = dto.getOfflineHost();
            if (CollectionUtils.isNotEmpty(offlineHost.getHosts())) {
                list.addAll(toEmergencyOfflineVOList(offlineHost.getHosts(), dto));
            }
        }
        return list;
    }

    /**
     * 转换 voList
     *
     * @param hostDTOList hostDTOList
     * @param dto         dto
     * @return {@link List}<{@link EmergencyOfflineVO}>
     */
    public abstract List<EmergencyOfflineVO> toEmergencyOfflineVOList(List<EmergencyHostDTO> hostDTOList, @Context EmergencyResourceDTO dto);

    public EmergencyOfflineVO toEmergencyOfflineVO(EmergencyHostDTO hostDTO, @Context EmergencyResourceDTO dto){
        EmergencyOfflineVO emergencyOfflineVO = toOfflineVO(dto);
        if (Objects.nonNull(hostDTO)) {
            emergencyOfflineVO.setCell(ObjectUtils.null2Empty(hostDTO.getCell()));
            emergencyOfflineVO.setIp(ObjectUtils.null2Empty(hostDTO.getIpLan()));
            emergencyOfflineVO.setName(ObjectUtils.null2Empty(hostDTO.getName()));
            emergencyOfflineVO.setCpu(ObjectUtils.null2zero(hostDTO.getCpu()));
            emergencyOfflineVO.setDisk(ObjectUtils.null2zero(hostDTO.getDisk()));
            emergencyOfflineVO.setMemory(ObjectUtils.null2zero(hostDTO.getMemory()));
            emergencyOfflineVO.setKind(ObjectUtils.null2Empty(hostDTO.getKind()));
            emergencyOfflineVO.setOs(ObjectUtils.null2Empty(hostDTO.getOs()));
            emergencyOfflineVO.setIdc(ObjectUtils.null2Empty(hostDTO.getIdc()));
        }
        return emergencyOfflineVO;
    }

    /**
     * EmergencyResourceDTO -> EmergencyOfflineVO
     *
     * @param dto dto
     * @return {@link EmergencyOfflineVO}
     */
    @Mapping(source = "flowUuid", target = "flowUuid", defaultValue = "")
    @Mapping(source = "flowId", target = "flowId")
    @Mapping(source = "endTime", target = "offlineDate", defaultValue = "")
    @Mapping(source = "template", target = "template",defaultValue = "")
    @Mapping(source = "dto.offlineHost.env", target = "env", defaultValue = "")
    @Mapping(source = "dto.offlineHost.appkey", target = "appkey", defaultValue = "")
    @Mapping(source = "dto.offlineHost.displaySrv", target = "displaySrv", defaultValue = "")
    @Mapping(target = "cell", ignore = true)
    @Mapping(target = "ip", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "cpu", ignore = true)
    @Mapping(target = "disk", ignore = true)
    @Mapping(target = "memory", ignore = true)
    @Mapping(target = "kind", ignore = true)
    @Mapping(target = "os", ignore = true)
    @Mapping(target = "idc", ignore = true)
    public abstract  EmergencyOfflineVO toOfflineVO(EmergencyResourceDTO dto);

    /**
     * EmergencyResourceDTO -> EmergencyOnlineVO
     *
     * @param dto dto
     * @return {@link EmergencyOnlineVO}
     */
    @Mapping(source = "env", target = "env", defaultValue = "")
    @Mapping(source = "appkey", target = "appkey", defaultValue = "")
    @Mapping(source = "count", target = "count")
    @Mapping(source = "flowUuid", target = "flowUuid", defaultValue = "")
    @Mapping(source = "flowId", target = "flowId")
    @Mapping(source = "endTime", target = "onlineDate", defaultValue = "")
    @Mapping(source = "template", target = "template")
    @Mapping(source = "dto.hostConfig.set", target = "set", defaultValue = "")
    @Mapping(source = "dto.hostConfig.channel", target = "channel", defaultValue = "")
    @Mapping(source = "dto.hostConfig.channelCn", target = "channelCn", defaultValue = "")
    @Mapping(source = "dto.hostConfig.cluster", target = "cluster", defaultValue = "")
    @Mapping(source = "dto.hostConfig.clusterCn", target = "clusterCn", defaultValue = "")
    @Mapping(source = "dto.hostConfig.city", target = "city", defaultValue = "")
    @Mapping(source = "dto.hostConfig.region", target = "region", defaultValue = "")
    @Mapping(source = "dto.hostConfig.cpu", target = "cpu")
    @Mapping(source = "dto.hostConfig.memory", target = "memory")
    @Mapping(source = "dto.hostConfig.disk", target = "disk")
    @Mapping(source = "dto.hostConfig.diskType", target = "diskType", defaultValue = "")
    @Mapping(source = "dto.hostConfig.diskTypeCn", target = "diskTypeCn", defaultValue = "")
    @Mapping(source = "dto.hostConfig.os", target = "os", defaultValue = "")
    @Mapping(source = "dto.hostConfig.configExtraInfo", target = "configExtraInfo", defaultValue = "")
    @Mapping(source = "dto.hostConfig.idcs", target = "idcs")
    @Named("toEmergencyOnlineVO")
    public abstract EmergencyOnlineVO toEmergencyOnlineVO(EmergencyResourceDTO dto);

    /**
     * 批量转换 EmergencyResourceDTO -> EmergencyOnlineVO
     *
     * @param dtoList dtoList
     * @return {@link List}<{@link EmergencyOnlineVO}>
     */
    @IterableMapping(qualifiedByName = "toEmergencyOnlineVO")
    public abstract List<EmergencyOnlineVO> toEmergencyOnlineVOList(List<EmergencyResourceDTO> dtoList);
}
