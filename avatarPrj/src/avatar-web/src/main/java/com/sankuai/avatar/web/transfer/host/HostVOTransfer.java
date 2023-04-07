package com.sankuai.avatar.web.transfer.host;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.host.*;
import com.sankuai.avatar.web.vo.host.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author qinwei05
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface HostVOTransfer {

    /**
     * 转换器
     */
    HostVOTransfer INSTANCE = Mappers.getMapper(HostVOTransfer.class);

    /**
     * 转换为HostAttributesVO对象
     *
     * @param hostAttributesDTO hostAttributesDTO
     * @return {@link HostAttributesDTO}
     */
    HostAttributesVO toHostAttributesVO(HostAttributesDTO hostAttributesDTO);

    /**
     * 转换为HostAttributesVO列表对象
     *
     * @param hostAttributesDTOList 主机信息
     * @return {@link PageResponse}<{@link HostAttributesVO}>
     */
    @IterableMapping(qualifiedByName = "toHostAttributesVO")
    PageResponse<HostAttributesVO> toHostAttributesVOList(PageResponse<HostAttributesDTO> hostAttributesDTOList);

    /**
     * 转换为HostSumAttributeVO对象
     *
     * @param hostSumAttributeDTO hostSumAttributeDTO
     * @return {@link HostSumAttributeVO}
     */
    HostSumAttributeVO toHostSumAttributeVO(HostSumAttributeDTO hostSumAttributeDTO);

    /**
     * 转换为HostCountVO对象
     *
     * @param hostCountDTO 主机数dto
     * @return {@link HostCountVO}
     */
    HostCountVO toHostCountVO(HostCountDTO hostCountDTO);


    /**
     * 转换为HostIdcDistributedVO对象
     *
     * @param hostIdcDistributedDTO hostIdcDistributedDTO
     * @return {@link HostIdcDistributedVO}
     */
    HostIdcDistributedVO toHostIdcDistributedVO(HostIdcDistributedDTO hostIdcDistributedDTO);

    /**
     * 转换为HostCellVO对象
     *
     * @param hostCellDTO hostCellDTO
     * @return {@link HostCellVO}
     */
    HostCellVO toHostCellVO(HostCellDTO hostCellDTO);

    /**
     * 转换为HostCellVO列表对象
     *
     * @param hostCellDTOList hostCellDTOList
     * @return {@link List}<{@link HostCellVO}>
     */
    @IterableMapping(qualifiedByName = "toHostCellVO")
    List<HostCellVO> toHostCellVOList(List<HostCellDTO> hostCellDTOList);

    /**
     * 转换为IdcMetaDataVO对象
     *
     * @param idcMetaDataDTO ecsIdc
     * @return {@link IdcMetaDataVO}
     */
    IdcMetaDataVO toIdcMetaDataVO(IdcMetaDataDTO idcMetaDataDTO);

    /**
     * 批量转换为IdcMetaDataVO对象
     *
     * @param idcMetaDataDTOList ecsIdc列表
     * @return {@link List}<{@link IdcMetaDataVO}>
     */
    @IterableMapping(qualifiedByName = "toIdcMetaDataVO")
    List<IdcMetaDataVO> batchToIdcMetaDataVO(List<IdcMetaDataDTO> idcMetaDataDTOList);

    /**
     * 转换为ExternalHostVO对象
     *
     * @param externalHostDTO externalHostDTO
     * @return {@link ExternalHostVO}
     */
    ExternalHostVO toExternalHostVO(ExternalHostDTO externalHostDTO);

    /**
     * 转换为HostVO对象
     *
     * @param hostDTO hostDTO
     * @return {@link HostVO}
     */
    HostVO toHostVO(HostDTO hostDTO);

    /**
     * 批量转换为HostVO对象
     *
     * @param hostDTOList hostDTOList
     * @return {@link List}<{@link HostVO}>
     */
    @IterableMapping(qualifiedByName = "toHostVO")
    List<HostVO> batchToHostVO(List<HostDTO> hostDTOList);

    /**
     * 转换为GroupTagVO对象
     *
     * @param groupTagDTO groupTagDTO
     * @return {@link GroupTagVO}
     */
    GroupTagVO toGroupTagVO(GroupTagDTO groupTagDTO);

    /**
     * 批量转换为GroupTagVO对象
     *
     * @param groupTagDTOList groupTagDTOList
     * @return {@link List}<{@link GroupTagVO}>
     */
    @IterableMapping(qualifiedByName = "toGroupTagVO")
    List<GroupTagVO> batchToGroupTagVO(List<GroupTagDTO> groupTagDTOList);
}
