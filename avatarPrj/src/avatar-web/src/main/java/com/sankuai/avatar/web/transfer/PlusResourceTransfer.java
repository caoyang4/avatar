package com.sankuai.avatar.web.transfer;

import com.sankuai.avatar.resource.plus.bo.PlusReleaseBO;
import com.sankuai.avatar.web.dto.PlusReleaseDTO;
import com.sankuai.avatar.web.vo.appkey.PlusAppliedReleaseVO;
import com.sankuai.avatar.web.vo.appkey.PlusBindReleaseVO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/10/19 13:15
 * @version 1.0
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface PlusResourceTransfer {

    /**
     * 转换器
     */
    PlusResourceTransfer INSTANCE = Mappers.getMapper(PlusResourceTransfer.class);

    /**
     * 转换为VO Object
     * @param plusReleaseDTO 发布项
     * @return bo
     */
    PlusBindReleaseVO toBindVO(PlusReleaseDTO plusReleaseDTO);

    /**
     * 批量转换为VO Object
     * @param plusReleaseDTOList 发布项
     * @return bo
     */
    @IterableMapping(qualifiedByName = "toBindVO")
    List<PlusBindReleaseVO> toBindVOList(List<PlusReleaseDTO> plusReleaseDTOList);

    /**
     * 转换为VO Object
     * @param plusReleaseDTO 发布项
     * @return bo
     */
    PlusAppliedReleaseVO toAppliedVO(PlusReleaseDTO plusReleaseDTO);

    /**
     * 批量转换为VO Object
     * @param plusReleaseDTOList 发布项
     * @return bo
     */
    @IterableMapping(qualifiedByName = "toAppliedVO")
    List<PlusAppliedReleaseVO> toAppliedVOList(List<PlusReleaseDTO> plusReleaseDTOList);

    /**
     * 转换为DTO Object
     * @param plusReleaseBO 发布项
     * @return bo
     */
    PlusReleaseDTO toDTO(PlusReleaseBO plusReleaseBO);

    /**
     * 转换为DTO Object
     * @param appkeyPlusReleaseBOList 发布项s
     * @return bo
     */
    List<PlusReleaseDTO> toDTOList(List<PlusReleaseBO> appkeyPlusReleaseBOList);
}
