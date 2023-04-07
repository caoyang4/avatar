package com.sankuai.avatar.web.transfer.capacity;

import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacityDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacityWhiteDTO;
import com.sankuai.avatar.web.vo.capacity.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.*;

/**
 * 服务容灾 VO 转换器
 * @author caoyang
 * @create 2022-11-04 19:59
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyCapacityVOTransfer {

    AppkeyCapacityVOTransfer INSTANCE = Mappers.getMapper(AppkeyCapacityVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param appkeyCapacityDTO DTO
     * @return {@link AppkeyCapacityVO}
     */
    @Mapping(source = "setName", target = "cellName", defaultValue = "")
    @Mapping(source = "standardReason", target = "standardReason", defaultValue = "")
    @Mapping(source = "standardTips", target = "standardTips", defaultValue = "")
    @Mapping(source = "whiteList", target = "isWhite", qualifiedByName = "isWhite")
    @Mapping(source = "whiteList", target = "whiteReason", qualifiedByName = "getWhiteReason")
    @Mapping(source = "setName", target = "set", qualifiedByName = "isSet")
    @Mapping(source = "whiteList", target = "whitelists")
    @Mapping(source = "hostList", target = "hosts")
    @Named("toVO")
    AppkeyCapacityVO toVO(AppkeyCapacityDTO appkeyCapacityDTO);

    /**
     * toVOList
     *
     * @param appkeyCapacityDTOList DTOList
     * @return {@link List}<{@link AppkeyCapacityVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<AppkeyCapacityVO> toVOList(List<AppkeyCapacityDTO> appkeyCapacityDTOList);

    /**
     * 部分数据返回 vo
     *
     * @param appkeyCapacityDTO appkey dto能力
     * @return {@link AppkeyCapacityVO}
     */
    @Mapping(source = "setName", target = "cellName", defaultValue = "")
    @Mapping(source = "standardReason", target = "standardReason", defaultValue = "")
    @Mapping(source = "standardTips", target = "standardTips", defaultValue = "")
    @Mapping(source = "whiteList", target = "isWhite", qualifiedByName = "isWhite")
    @Mapping(source = "whiteList", target = "whiteReason", qualifiedByName = "getWhiteReason")
    @Mapping(source = "setName", target = "set", qualifiedByName = "isSet")
    @Mapping(target = "whiteList", ignore = true)
    @Mapping(target = "whitelists", ignore = true)
    @Mapping(target = "middleWareList", ignore = true)
    @Mapping(target = "hostList", ignore = true)
    @Mapping(target = "hosts", ignore = true)
    @Mapping(target = "octoHttpProviderList", ignore = true)
    @Mapping(target = "octoThriftProviderList", ignore = true)
    @Mapping(target = "accessComponentList", ignore = true)
    @Named("toPartVO")
    AppkeyCapacityVO toPartVO(AppkeyCapacityDTO appkeyCapacityDTO);

    /**
     *批量转 vo
     *
     * @param appkeyCapacityDTOList appkey dtolist能力
     * @return {@link List}<{@link AppkeyCapacityVO}>
     */
    @IterableMapping(qualifiedByName = "toPartVO")
    @Named("toPartVOList")
    List<AppkeyCapacityVO> toPartVOList(List<AppkeyCapacityDTO> appkeyCapacityDTOList);

    /**
     * VO -> DTO
     *
     * @param appkeyCapacityVO VO
     * @return {@link AppkeyCapacityDTO}
     */
    @Mapping(source = "cellName", target = "setName", defaultValue = "")
    @Named("toDTO")
    AppkeyCapacityDTO toDTO(AppkeyCapacityVO appkeyCapacityVO);

    /**
     * toDTOList
     *
     * @param appkeyCapacityVOList VOList
     * @return {@link List}<{@link AppkeyCapacityDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<AppkeyCapacityDTO> toDTOList(List<AppkeyCapacityVO> appkeyCapacityVOList);

    /**
     * 是否 set 链路
     *
     * @param setName setName
     * @return boolean
     */
    @Named("isSet")
    default boolean isSet(String setName){
        return StringUtils.isNotEmpty(setName);
    }

    /**
     * 加白原因
     *
     * @param whiteList whiteList
     * @return {@link String}
     */
    @Named("getWhiteReason")
    default String getWhiteReason(List<AppkeyCapacityWhiteDTO> whiteList){
        if (CollectionUtils.isEmpty(whiteList)) {
            return "";
        }
        AppkeyCapacityWhiteDTO whiteDTO = whiteList.stream().filter(white -> WhiteType.CAPACITY.equals(white.getWhiteApp())).findFirst().orElse(null);
        return Objects.nonNull(whiteDTO) ? whiteDTO.getReason() : "";
    }

    /**
     * 是否加白
     *
     * @param whiteList whiteList
     * @return boolean
     */
    @Named("isWhite")
    default boolean isWhite(List<AppkeyCapacityWhiteDTO> whiteList){
        if (CollectionUtils.isEmpty(whiteList)) {
            return false;
        }
        AppkeyCapacityWhiteDTO whiteDTO = whiteList.stream().filter(white -> WhiteType.CAPACITY.equals(white.getWhiteApp())).findFirst().orElse(null);
        return Objects.nonNull(whiteDTO);
    }

    /**
     * 获取加白到期时间
     *
     * @param whiteList 白名单
     * @return {@link Date}
     */
    @Named("getWhiteExpireTime")
    default Date getWhiteExpireTime(List<AppkeyCapacityWhiteDTO> whiteList){
        if (CollectionUtils.isEmpty(whiteList)) {
            return null;
        }
        AppkeyCapacityWhiteDTO whiteDTO = whiteList.stream().filter(white -> WhiteType.CAPACITY.equals(white.getWhiteApp())).findFirst().orElse(null);
        return Objects.nonNull(whiteDTO) ? whiteDTO.getEndTime() : null;
    }

    /**
     * 转换AppkeySetCapacityVO
     *
     * @param appkeyCapacityDTO d't'o
     * @return {@link AppkeySetCapacityVO}
     */
    @Mapping(source = "setName", target = "cellName", defaultValue = "")
    @Mapping(source = "whiteList", target = "isWhite", qualifiedByName = "isWhite")
    @Mapping(source = "whiteList", target = "whiteReason", qualifiedByName = "getWhiteReason")
    @Mapping(source = "whiteList", target = "expireTime", qualifiedByName = "getWhiteExpireTime")
    @Mapping(target = "whiteStartTime", ignore = true)
    @Named("toAppkeySetCapacityVO")
    AppkeySetCapacityVO toAppkeySetCapacityVO(AppkeyCapacityDTO appkeyCapacityDTO);

    /**
     * 批量转换AppkeySetCapacityVO
     *
     * @param appkeyCapacityDTOList dtoList
     * @return {@link List}<{@link AppkeySetCapacityVO}>
     */
    @IterableMapping(qualifiedByName = "toAppkeySetCapacityVO")
    @Named("toAppkeySetCapacityVOList")
    List<AppkeySetCapacityVO> toAppkeySetCapacityVOList(List<AppkeyCapacityDTO> appkeyCapacityDTOList);

    @Named("toAppkeyCapaictyWhiteVOList")
    default List<AppkeyCapacityWhiteVO> toAppkeyCapaictyWhiteVOList(List<AppkeyCapacityDTO> dtoList){
        List<AppkeyCapacityWhiteDTO> whiteList = new ArrayList<>();
        dtoList.stream().filter(dto -> CollectionUtils.isNotEmpty(dto.getWhiteList())).forEach(dto -> whiteList.addAll(dto.getWhiteList()));
        List<AppkeyCapacityWhiteVO> appkeyCapacityWhiteVOList = new ArrayList<>();
        List<WhiteType> whiteTypeList = Arrays.asList(WhiteType.CAPACITY, WhiteType.AUTO_MIGRATE);
        for (WhiteType whiteType : whiteTypeList) {
            AppkeyCapacityWhiteVO whiteVO = new AppkeyCapacityWhiteVO();
            WhiteApplyVO applyVO = new WhiteApplyVO();
            applyVO.setApp(whiteType.getWhiteType());
            applyVO.setCname(whiteType.getCname());
            applyVO.setDesc(whiteType.getDesc());
            AppkeyCapacityWhiteDTO whiteDTO = whiteList.stream().filter(white -> whiteType.equals(white.getWhiteApp())).findFirst().orElse(null);
            whiteVO.setIsWhite(Objects.nonNull(whiteDTO));
            whiteVO.setCanApply(Objects.isNull(whiteDTO));
            if (Objects.nonNull(whiteDTO)) {
                CapacityWhiteVO capacityWhiteVO = new CapacityWhiteVO();
                capacityWhiteVO.setApp(whiteType.getWhiteType());
                capacityWhiteVO.setCname(whiteType.getCname());
                capacityWhiteVO.setReason(whiteDTO.getReason());
                capacityWhiteVO.setEndTime(whiteDTO.getEndTime());
                whiteVO.setWhite(capacityWhiteVO);
            }
            whiteVO.setApp(whiteType.getWhiteType());
            whiteVO.setApply(applyVO);
            appkeyCapacityWhiteVOList.add(whiteVO);
        }
        return appkeyCapacityWhiteVOList;
    }

}
