package com.sankuai.avatar.web.transfer.capacity;

import com.sankuai.avatar.resource.capacity.bo.*;
import com.sankuai.avatar.web.dto.capacity.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 容灾属性信息转换器
 * @author caoyang
 * @create 2022-11-04 18:35
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyCapacityAttributeDTOTransfer {

    AppkeyCapacityAttributeDTOTransfer INSTANCE = Mappers.getMapper(AppkeyCapacityAttributeDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param bo BO
     * @return {@link AppkeyCapacityHostDTO}
     */
    @Named("toHostDTO")
    AppkeyCapacityHostDTO toHostDTO(AppkeyCapacityHostBO bo);

    /**
     * 批量转换 BO -> DTO
     *
     * @param boList BOList
     * @return {@link List}<{@link AppkeyCapacityHostDTO}>
     */
    @IterableMapping(qualifiedByName = "toHostDTO")
    @Named("toHostDTOList")
    List<AppkeyCapacityHostDTO> toHostDTOList(List<AppkeyCapacityHostBO> boList);

    /**
     * DTO -> BO
     *
     * @param dto dto
     * @return {@link AppkeyCapacityHostBO}
     */
    @Named("toHostBO")
    AppkeyCapacityHostBO toHostBO(AppkeyCapacityHostDTO dto);

    /**
     * 批量转换 DTO -> BO
     *
     * @param dtoList DTOList
     * @return {@link List}<{@link AppkeyCapacityHostBO}>
     */
    @IterableMapping(qualifiedByName = "toHostBO")
    @Named("toHostBOList")
    List<AppkeyCapacityHostBO> toHostBOList(List<AppkeyCapacityHostDTO> dtoList);

    /**
     * BO -> DTO
     *
     * @param bo BO
     * @return {@link AppkeyCapacityMiddleWareDTO}
     */
    @Mapping(source = "middleWareName", target = "middleWare")
    @Named("toMiddleDTO")
    AppkeyCapacityMiddleWareDTO toMiddleDTO(AppkeyCapacityMiddleWareBO bo);

    /**
     * 批量转换 BO -> DTO
     *
     * @param boList BOList
     * @return {@link List}<{@link AppkeyCapacityMiddleWareDTO}>
     */
    @IterableMapping(qualifiedByName = "toMiddleDTO")
    @Named("toMiddleDTOList")
    List<AppkeyCapacityMiddleWareDTO> toMiddleDTOList(List<AppkeyCapacityMiddleWareBO> boList);

    /**
     * DTO -> BO
     *
     * @param dto dto
     * @return {@link AppkeyCapacityMiddleWareBO}
     */
    @Mapping(source = "middleWare", target = "middleWareName")
    @Named("toMiddleBO")
    AppkeyCapacityMiddleWareBO toMiddleBO(AppkeyCapacityMiddleWareDTO dto);

    /**
     * 批量转换 DTO -> BO
     *
     * @param dtoList DTOList
     * @return {@link List}<{@link AppkeyCapacityMiddleWareBO}>
     */
    @IterableMapping(qualifiedByName = "toMiddleBO")
    @Named("toMiddleBOList")
    List<AppkeyCapacityMiddleWareBO> toMiddleBOList(List<AppkeyCapacityMiddleWareDTO> dtoList);

    /**
     * BO -> DTO
     *
     * @param bo BO
     * @return {@link AppkeyCapacityAccessComponentDTO}
     */
    @Named("toAccessDTO")
    AppkeyCapacityAccessComponentDTO toAccessDTO(AppkeyCapacityAccessComponentBO bo);

    /**
     * 批量转换 BO -> DTO
     *
     * @param boList BOList
     * @return {@link List}<{@link AppkeyCapacityAccessComponentDTO}>
     */
    @IterableMapping(qualifiedByName = "toAccessDTO")
    @Named("toAccessDTOList")
    List<AppkeyCapacityAccessComponentDTO> toAccessDTOList(List<AppkeyCapacityAccessComponentBO> boList);

    /**
     * DTO -> BO
     *
     * @param dto dto
     * @return {@link AppkeyCapacityAccessComponentBO}
     */
    @Named("toAccessBO")
    AppkeyCapacityAccessComponentBO toAccessBO(AppkeyCapacityAccessComponentDTO dto);

    /**
     * 批量转换 DTO -> BO
     *
     * @param dtoList DTOList
     * @return {@link List}<{@link AppkeyCapacityAccessComponentBO}>
     */
    @IterableMapping(qualifiedByName = "toAccessBO")
    @Named("toAccessBOList")
    List<AppkeyCapacityAccessComponentBO> toAccessBOList(List<AppkeyCapacityAccessComponentDTO> dtoList);

    /**
     * BO -> DTO
     *
     * @param bo BO
     * @return {@link AppkeyCapacityOctoProviderDTO}
     */
    @Named("toOctoDTO")
    AppkeyCapacityOctoProviderDTO toOctoDTO(AppkeyCapacityOctoProviderBO bo);

    /**
     * 批量转换 BO -> DTO
     *
     * @param boList BOList
     * @return {@link List}<{@link AppkeyCapacityOctoProviderDTO}>
     */
    @IterableMapping(qualifiedByName = "toOctoDTO")
    @Named("toOctoDTOList")
    List<AppkeyCapacityOctoProviderDTO> toOctoDTOList(List<AppkeyCapacityOctoProviderBO> boList);

    /**
     * DTO -> BO
     *
     * @param dto dto
     * @return {@link AppkeyCapacityOctoProviderBO}
     */
    @Named("toOctoBO")
    AppkeyCapacityOctoProviderBO toOctoBO(AppkeyCapacityOctoProviderDTO dto);

    /**
     * 批量转换 DTO -> BO
     *
     * @param dtoList DTOList
     * @return {@link List}<{@link AppkeyCapacityOctoProviderBO}>
     */
    @IterableMapping(qualifiedByName = "toOctoBO")
    @Named("toOctoBOList")
    List<AppkeyCapacityOctoProviderBO> toOctoBOList(List<AppkeyCapacityOctoProviderDTO> dtoList);

    /**
     * BO -> DTO
     *
     * @param bo BO
     * @return {@link AppkeyCapacityUtilizationDTO}
     */
    @Named("toUtilizationDTO")
    AppkeyCapacityUtilizationDTO toUtilizationDTO(AppkeyCapacityUtilizationBO bo);

    /**
     * DTO -> BO
     *
     * @param dto dto
     * @return {@link AppkeyCapacityUtilizationBO}
     */
    @Named("toUtilizationBO")
    AppkeyCapacityUtilizationBO toUtilizationBO(AppkeyCapacityUtilizationDTO dto);

    /**
     * BO -> DTO
     *
     * @param bo BO
     * @return {@link AppkeyCapacityWhiteDTO}
     */
    @Named("toWhiteDTO")
    AppkeyCapacityWhiteDTO toWhiteDTO(AppkeyCapacityWhiteBO bo);

    /**
     * 批量转换 BO -> DTO
     *
     * @param boList BOList
     * @return {@link List}<{@link AppkeyCapacityWhiteDTO}>
     */
    @IterableMapping(qualifiedByName = "toWhiteDTO")
    @Named("toWhiteDTOList")
    List<AppkeyCapacityWhiteDTO> toWhiteDTOList(List<AppkeyCapacityWhiteBO> boList);

    /**
     * DTO -> BO
     *
     * @param dto dto
     * @return {@link AppkeyCapacityWhiteBO}
     */
    @Named("toWhiteBO")
    AppkeyCapacityWhiteBO toWhiteBO(AppkeyCapacityWhiteDTO dto);

    /**
     * 批量转换 DTO -> BO
     *
     * @param dtoList DTOList
     * @return {@link List}<{@link AppkeyCapacityWhiteBO}>
     */
    @IterableMapping(qualifiedByName = "toWhiteBO")
    @Named("toWhiteBOList")
    List<AppkeyCapacityWhiteBO> toWhiteBOList(List<AppkeyCapacityWhiteDTO> dtoList);

}
