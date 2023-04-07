package com.sankuai.avatar.web.transfer.capacity;

import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasClientBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasStandardClientBO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasClientDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasStandardClientDTO;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasClientVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 业务服务依赖的 paas 客户端转换器
 * @author caoyang
 * @create 2022-10-13 13:58
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyPaasClientDTOTransfer {

    AppkeyPaasClientDTOTransfer INSTANCE = Mappers.getMapper(AppkeyPaasClientDTOTransfer.class);

    /**
     * BO -> DTO
     * @param appkeyPaasClientBO
     * @return
     */
    @Mapping(source = "clientAppkey", target = "appkey")
    @Named("toDTO")
    AppkeyPaasClientDTO toDTO(AppkeyPaasClientBO appkeyPaasClientBO);

    /**
     * 批量转换 BO -> DTO
     * @param appkeyPaasClientBOList BOList
     * @return
     */
    @IterableMapping(qualifiedByName = "toDTO")
    List<AppkeyPaasClientDTO> toDTOList(List<AppkeyPaasClientBO> appkeyPaasClientBOList);

    /**
     * DTO -> BO
     * @param appkeyPaasClientDTO DTO
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "appkey", target = "clientAppkey")
    @Named("toBO")
    AppkeyPaasClientBO toBO(AppkeyPaasClientDTO appkeyPaasClientDTO);

    /**
     * 批量转换 DTO -> BO
     * @param appkeyPaasClientDTOList DTOList
     * @return BOList
     */
    @IterableMapping(qualifiedByName = "toBO")
    List<AppkeyPaasClientBO> toBOList(List<AppkeyPaasClientDTO> appkeyPaasClientDTOList);

    /**
     * DTO -> BO
     * @param appkeyPaasStandardClientDTO DTO
     * @return BO
     */
    @Mapping(target = "id", ignore = true)
    @Named("toStandardClientBO")
    AppkeyPaasStandardClientBO toStandardClientBO(AppkeyPaasStandardClientDTO appkeyPaasStandardClientDTO);

    /**
     * 批量 DTO -> BO
     * @param appkeyPaasStandardClientDTOList DTOList
     * @return BOList
     */
    @IterableMapping(qualifiedByName = "toStandardClientBO")
    List<AppkeyPaasStandardClientBO> toStandardClientBOList(List<AppkeyPaasStandardClientDTO> appkeyPaasStandardClientDTOList);

    /**
     * DTO -> VO
     * @param appkeyPaasClientDTO DTO
     * @return VO
     */
    @Mapping(target = "clientDesc", expression = "java(appkeyPaasClientDTO.getVersionDesc())")
    @Named("toVO")
    AppkeyPaasClientVO toVO(AppkeyPaasClientDTO appkeyPaasClientDTO);

    /**
     * 批量转换 DTO -> VO
     * @param appkeyPaasClientDTOList DTOList
     * @return VOList
     */
    @IterableMapping(qualifiedByName = "toVO")
    List<AppkeyPaasClientVO> toVOList(List<AppkeyPaasClientDTO> appkeyPaasClientDTOList);
}
