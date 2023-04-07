package com.sankuai.avatar.web.transfer.capacity;

import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacitySummaryBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasCapacityBO;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacitySummaryDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasCapacityDTO;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityReportVO;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityVO;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.*;

/**
 * paas 容灾 dto 对象转换器
 * @author caoyang
 * @create 2022-10-11 20:55
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyPaasCapacityDTOTransfer {

    AppkeyPaasCapacityDTOTransfer INSTANCE = Mappers.getMapper(AppkeyPaasCapacityDTOTransfer.class);

    /**
     * BO -> DTO
     * @param appkeyPaasCapacityBO BO
     * @return DTO
     */
    @Mapping(source = "clientAppkey", target = "appkey")
    @Named("toDTO")
    AppkeyPaasCapacityDTO toDTO(AppkeyPaasCapacityBO appkeyPaasCapacityBO);

    /**
     * 批量转换 BO -> DTO
     * @param appkeyPaasCapacityBOList BOList
     * @return DTOList
     */
    @IterableMapping(qualifiedByName = "toDTO")
    List<AppkeyPaasCapacityDTO> toDTOList(List<AppkeyPaasCapacityBO> appkeyPaasCapacityBOList);

    /**
     * DTO -> BO
     * @param appkeyPaasCapacityDTO DTO
     * @return BO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "appkey", target = "clientAppkey")
    @Named("toBO")
    AppkeyPaasCapacityBO toBO(AppkeyPaasCapacityDTO appkeyPaasCapacityDTO);

    /**
     * 批量转换 DTO -> BO
     * @param appkeyPaasCapacityDTOList DTOList
     * @return BOList
     */
    @IterableMapping(qualifiedByName = "toBOList")
    List<AppkeyPaasCapacityBO> toBOList(List<AppkeyPaasCapacityDTO> appkeyPaasCapacityDTOList);

    /**
     * DTO -> BO
     *
     * @param bo 薄
     * @return {@link AppkeyCapacitySummaryDTO}
     */
    @Named("toSummaryDTO")
    AppkeyCapacitySummaryDTO toSummaryDTO(AppkeyCapacitySummaryBO bo);

    /**
     * BO -> DTO
     *
     * @param dto dto
     * @return {@link AppkeyCapacitySummaryBO}
     */
    @Named("toSummaryBO")
    AppkeyCapacitySummaryBO toSummaryBO(AppkeyCapacitySummaryDTO dto);

    /**
     * DTO -> VO
     * @param appkeyPaasCapacityDTO DTO
     * @return VO
     */
    @Named("toVO")
    @Mapping(target = "clientVersion", ignore = true)
    @Mapping(source = "isWhite", target = "white")
    AppkeyPaasCapacityVO toVO(AppkeyPaasCapacityDTO appkeyPaasCapacityDTO);

    /**
     * 批量转换 DTO -> VO
     * @param appkeyPaasCapacityDTOList DTOList
     * @return VOList
     */
    @IterableMapping(qualifiedByName = "toVO")
    List<AppkeyPaasCapacityVO> toVOList(List<AppkeyPaasCapacityDTO> appkeyPaasCapacityDTOList);

    /**
     * dto 转换为上报vo
     * @param appkeyPaasCapacityDTO DTO
     * @return ReportVO
     */
    @Named("toReportVO")
    @Mapping(target = "clientSdkVersion", ignore = true)
    @Mapping(target = "standardVersion", ignore = true)
    @Mapping(source = "appkey", target = "clientAppkey", qualifiedByName = "toClientAppkey")
    @Mapping(source = "clientConfig", target = "clientConfig", qualifiedByName = "toConfig")
    @Mapping(source = "standardConfig", target = "standardConfig", qualifiedByName = "toConfig")
    AppkeyPaasCapacityReportVO toReportVO(AppkeyPaasCapacityDTO appkeyPaasCapacityDTO);


    /**
     * 批量将 dto 转换为上报vo
     * @param appkeyPaasCapacityDTOList DTOList
     * @return VOList
     */
    @IterableMapping(qualifiedByName = "toReportVO")
    List<AppkeyPaasCapacityReportVO> toReportVOList(List<AppkeyPaasCapacityDTO> appkeyPaasCapacityDTOList);


    @Named("toClientAppkey")
    default List<String> toClientAppkey(String appkey)  {
        return StringUtils.isEmpty(appkey) ? Collections.emptyList() : Collections.singletonList(appkey);
    }

    @Named("toConfig")
    default List<AppkeyPaasCapacityReportVO.ClientConfig> toConfig(List<Map<String,String>> configs)  {
        List<AppkeyPaasCapacityReportVO.ClientConfig> list = new ArrayList<>();
        for (Map<String, String> config : configs) {
            config.keySet().forEach(key -> {
                AppkeyPaasCapacityReportVO.ClientConfig clientConfig = new AppkeyPaasCapacityReportVO.ClientConfig();
                clientConfig.setKey(key);
                clientConfig.setValue(config.get(key));
                list.add(clientConfig);
            });
        }
        return list;
    }
}
