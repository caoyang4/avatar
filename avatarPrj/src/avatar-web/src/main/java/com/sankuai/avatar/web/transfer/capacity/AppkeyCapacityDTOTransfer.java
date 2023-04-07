package com.sankuai.avatar.web.transfer.capacity;

import com.sankuai.avatar.resource.capacity.bo.*;
import com.sankuai.avatar.web.dto.capacity.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 服务容灾转换器
 * @author caoyang
 * @create 2022-11-04 18:19
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyCapacityDTOTransfer {

    AppkeyCapacityDTOTransfer INSTANCE = Mappers.getMapper(AppkeyCapacityDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param appkeyCapacityBO BO
     * @return {@link AppkeyCapacityDTO}
     */
    @Mapping(source = "utilization", target = "utilization", qualifiedByName = "toUtilizationDTO")
    @Mapping(source = "whiteList", target = "whiteList", qualifiedByName = "toWhiteDTOList")
    @Mapping(source = "middleWareList", target = "middleWareList", qualifiedByName = "toMiddleWareDTOList")
    @Mapping(source = "hostList", target = "hostList", qualifiedByName = "toHostDTOList")
    @Mapping(source = "octoHttpProviderList", target = "octoHttpProviderList", qualifiedByName = "toOctoDTOList")
    @Mapping(source = "octoThriftProviderList", target = "octoThriftProviderList", qualifiedByName = "toOctoDTOList")
    @Mapping(source = "accessComponentList", target = "accessComponentList", qualifiedByName = "toAccessComponentDTOList")
    @Named("toDTO")
    AppkeyCapacityDTO toDTO(AppkeyCapacityBO appkeyCapacityBO);

    /**
     * 批量转换 BO -> DTO
     *
     * @param appkeyCapacityBOList BOList
     * @return {@link List}<{@link AppkeyCapacityDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<AppkeyCapacityDTO> toDTOList(List<AppkeyCapacityBO> appkeyCapacityBOList);

    /**
     * DTO -> BO
     *
     * @param appkeyCapacityDTO DTO
     * @return {@link AppkeyCapacityBO}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "utilization", target = "utilization", qualifiedByName = "toUtilizationBO")
    @Mapping(source = "whiteList", target = "whiteList", qualifiedByName = "toWhiteBOList")
    @Mapping(source = "middleWareList", target = "middleWareList", qualifiedByName = "toMiddleWareBOList")
    @Mapping(source = "hostList", target = "hostList", qualifiedByName = "toHostBOList")
    @Mapping(source = "octoHttpProviderList", target = "octoHttpProviderList", qualifiedByName = "toOctoBOList")
    @Mapping(source = "octoThriftProviderList", target = "octoThriftProviderList", qualifiedByName = "toOctoBOList")
    @Mapping(source = "accessComponentList", target = "accessComponentList", qualifiedByName = "toAccessComponentBOList")
    @Named("toBO")
    AppkeyCapacityBO toBO(AppkeyCapacityDTO appkeyCapacityDTO);

    /**
     * 批量转换 DTO -> BO
     *
     * @param appkeyCapacityDTOList DTOList
     * @return {@link List}<{@link AppkeyCapacityBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<AppkeyCapacityBO> toBOList(List<AppkeyCapacityDTO> appkeyCapacityDTOList);


    /**
     * toAccessComponentBOList
     *
     * @param dtoList dto列表
     * @return {@link List}<{@link AppkeyCapacityAccessComponentBO}>
     */
    @Named("toAccessComponentBOList")
    default List<AppkeyCapacityAccessComponentBO> toAccessComponentBOList(List<AppkeyCapacityAccessComponentDTO> dtoList){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toAccessBOList(dtoList);
    }

    /**
     * toAccessComponentDTOList
     *
     * @param boList bo列表
     * @return {@link List}<{@link AppkeyCapacityAccessComponentDTO}>
     */
    @Named("toAccessComponentDTOList")
    default List<AppkeyCapacityAccessComponentDTO> toAccessComponentDTOList(List<AppkeyCapacityAccessComponentBO> boList){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toAccessDTOList(boList);
    }

    /**
     * toHostBOList
     *
     * @param dtoList dto列表
     * @return {@link List}<{@link AppkeyCapacityHostBO}>
     */
    @Named("toHostBOList")
    default List<AppkeyCapacityHostBO> toHostBOList(List<AppkeyCapacityHostDTO> dtoList){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toHostBOList(dtoList);
    }

    /**
     * toHostDTOList
     *
     * @param boList bo列表
     * @return {@link List}<{@link AppkeyCapacityHostDTO}>
     */
    @Named("toHostDTOList")
    default List<AppkeyCapacityHostDTO> toHostDTOList(List<AppkeyCapacityHostBO> boList){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toHostDTOList(boList);
    }

    /**
     * toMiddleWareBOList
     *
     * @param dtoList dto列表
     * @return {@link List}<{@link AppkeyCapacityMiddleWareBO}>
     */
    @Named("toMiddleWareBOList")
    default List<AppkeyCapacityMiddleWareBO> toMiddleWareBOList(List<AppkeyCapacityMiddleWareDTO> dtoList){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toMiddleBOList(dtoList);
    }

    /**
     * toMiddleWareDTOList
     *
     * @param boList bo列表
     * @return {@link List}<{@link AppkeyCapacityMiddleWareDTO}>
     */
    @Named("toMiddleWareDTOList")
    default List<AppkeyCapacityMiddleWareDTO> toMiddleWareDTOList(List<AppkeyCapacityMiddleWareBO> boList){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toMiddleDTOList(boList);
    }


    /**
     * toUtilizationBO
     *
     * @param dto dto
     * @return {@link AppkeyCapacityUtilizationBO}
     */
    @Named("toUtilizationBO")
    default AppkeyCapacityUtilizationBO toUtilizationBO(AppkeyCapacityUtilizationDTO dto){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toUtilizationBO(dto);
    }


    /**
     * toUtilizationDTO
     *
     * @param bo bo
     * @return {@link AppkeyCapacityUtilizationDTO}
     */
    @Named("toUtilizationDTO")
    default AppkeyCapacityUtilizationDTO toUtilizationDTO(AppkeyCapacityUtilizationBO bo){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toUtilizationDTO(bo);
    }

    /**
     * toWhiteBOList
     *
     * @param dtoList dto列表
     * @return {@link List}<{@link AppkeyCapacityWhiteBO}>
     */
    @Named("toWhiteBOList")
    default List<AppkeyCapacityWhiteBO> toWhiteBOList(List<AppkeyCapacityWhiteDTO> dtoList){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toWhiteBOList(dtoList);
    }

    /**
     * toWhiteDTOList
     *
     * @param boList bo列表
     * @return {@link List}<{@link AppkeyCapacityWhiteDTO}>
     */
    @Named("toWhiteDTOList")
    default List<AppkeyCapacityWhiteDTO> toWhiteDTOList(List<AppkeyCapacityWhiteBO> boList){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toWhiteDTOList(boList);
    }

    /**
     * toOctoBOList
     *
     * @param dtoList dto列表
     * @return {@link List}<{@link AppkeyCapacityOctoProviderBO}>
     */
    @Named("toOctoBOList")
    default List<AppkeyCapacityOctoProviderBO> toOctoBOList(List<AppkeyCapacityOctoProviderDTO> dtoList){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toOctoBOList(dtoList);
    }

    /**
     * toOctoDTOList
     *
     * @param boList bo列表
     * @return {@link List}<{@link AppkeyCapacityOctoProviderDTO}>
     */
    @Named("toOctoDTOList")
    default List<AppkeyCapacityOctoProviderDTO> toOctoDTOList(List<AppkeyCapacityOctoProviderBO> boList){
        return AppkeyCapacityAttributeDTOTransfer.INSTANCE.toOctoDTOList(boList);
    }

}
