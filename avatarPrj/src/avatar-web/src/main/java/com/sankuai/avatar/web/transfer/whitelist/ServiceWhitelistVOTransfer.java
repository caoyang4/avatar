package com.sankuai.avatar.web.transfer.whitelist;

import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.vo.whitelist.ServiceWhitelistVO;
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
public interface ServiceWhitelistVOTransfer {

    ServiceWhitelistVOTransfer INSTANCE = Mappers.getMapper(ServiceWhitelistVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param serviceWhitelistDTO serviceWhitelistDTO
     * @return {@link ServiceWhitelistVO}
     */
    @Mapping(source = "app", target = "app", qualifiedByName = "toWhiteApp")
    @Mapping(source = "setName", target = "setName", defaultValue = "")
    @Mapping(source = "app", target = "cname", qualifiedByName = "toWhiteName")
    @Named("toVO")
    ServiceWhitelistVO toVO(ServiceWhitelistDTO serviceWhitelistDTO);

    /**
     * 批量转换DTO -> VO
     *
     * @param serviceWhitelistDTOList serviceWhitelistDTOList
     * @return {@link List}<{@link ServiceWhitelistVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOLIst")
    List<ServiceWhitelistVO> toVOList(List<ServiceWhitelistDTO> serviceWhitelistDTOList);

    /**
     * VO -> DTO
     *
     * @param serviceWhitelistVO serviceWhitelistVO
     * @return {@link ServiceWhitelistDTO}
     */
    @Mapping(source = "app", target = "app", qualifiedByName = "toWhiteType")
    @Mapping(target = "id", ignore = true)
    @Named("toDTO")
    ServiceWhitelistDTO toDTO(ServiceWhitelistVO serviceWhitelistVO);

    /**
     * 批量转换VO -> DTO
     *
     * @param serviceWhitelistVOList serviceWhitelistVOList
     * @return {@link List}<{@link ServiceWhitelistDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<ServiceWhitelistDTO> toDTOList(List<ServiceWhitelistVO> serviceWhitelistVOList);

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

    /**
     * 白名单类型名称
     *
     * @param type 类型
     * @return {@link String}
     */
    @Named("toWhiteName")
    default String toWhiteName(WhiteType type){
        return Objects.nonNull(type) ? type.getCname() : "";
    }
}
