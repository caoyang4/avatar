package com.sankuai.avatar.web.transfer.whitelist;

import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.web.dto.whitelist.OwtSetWhitelistDTO;
import com.sankuai.avatar.web.vo.whitelist.OwtSetWhitelistVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-11-10 14:30
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OwtSetWhitelistVOTransfer {

    OwtSetWhitelistVOTransfer INSTANCE = Mappers.getMapper(OwtSetWhitelistVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param owtSetWhitelistDTO owtSetWhitelistDTO
     * @return {@link OwtSetWhitelistVO}
     */
    @Mapping(source = "app", target = "app", qualifiedByName = "toWhiteApp")
    @Named("toVO")
    OwtSetWhitelistVO toVO(OwtSetWhitelistDTO owtSetWhitelistDTO);

    /**
     * 批量转换DTO -> VO
     *
     * @param owtSetWhitelistDTOList owtSetWhitelistDTOList
     * @return {@link List}<{@link OwtSetWhitelistVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<OwtSetWhitelistVO> toVOList(List<OwtSetWhitelistDTO> owtSetWhitelistDTOList);

    /**
     * VO -> DTO
     *
     * @param owtSetWhitelistVO owtSetWhitelistVO
     * @return {@link OwtSetWhitelistDTO}
     */
    @Mapping(source = "app", target = "app", qualifiedByName = "toWhiteType")
    @Named("toDTO")
    OwtSetWhitelistDTO toDTO(OwtSetWhitelistVO owtSetWhitelistVO);

    /**
     * 批量转换VO -> DTO
     *
     * @param owtSetWhitelistVOList owtSetWhitelistVOList
     * @return {@link List}<{@link OwtSetWhitelistDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<OwtSetWhitelistDTO> toDTOList(List<OwtSetWhitelistVO> owtSetWhitelistVOList);

    /**
     * 转换白名单类型枚举
     *
     * @param app 类型
     * @return {@link WhiteType}
     */
    @Named("toWhiteType")
    default WhiteType toWhiteType(String app){
        return Arrays.stream(WhiteType.values()).filter(e -> Objects.equals(e.getWhiteType(), app)).findFirst().orElse(null);
    }
    /**
     * 白名单类型枚举转字符串
     *
     * @param type 类型
     * @return {@link String}
     */
    @Named("toWhiteApp")
    default String toWhiteApp(WhiteType type){
        return Objects.nonNull(type) ? type.getWhiteType() : null;
    }
}
