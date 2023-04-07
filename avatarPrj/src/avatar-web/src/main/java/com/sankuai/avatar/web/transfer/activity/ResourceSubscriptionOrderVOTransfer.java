package com.sankuai.avatar.web.transfer.activity;

import com.sankuai.avatar.web.dto.activity.ResourceSubscriptionOrderDTO;
import com.sankuai.avatar.web.vo.activity.ResourceSubscriptionOrderVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-02-13 15:03
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ResourceSubscriptionOrderVOTransfer {

    ResourceSubscriptionOrderVOTransfer INSTANCE = Mappers.getMapper(ResourceSubscriptionOrderVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param orderDTO orderDTO
     * @return {@link ResourceSubscriptionOrderVO}
     */
    @Named("toVO")
    ResourceSubscriptionOrderVO toVO(ResourceSubscriptionOrderDTO orderDTO);

    /**
     * 批量转换DTO -> VO
     *
     * @param orderDTOList orderDTOList
     * @return {@link List}<{@link ResourceSubscriptionOrderVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<ResourceSubscriptionOrderVO> toVOList(List<ResourceSubscriptionOrderDTO> orderDTOList);

    /**
     * VO -> DTO
     *
     * @param orderVO orderVO
     * @return {@link ResourceSubscriptionOrderDTO}
     */
    @Named("toDTO")
    ResourceSubscriptionOrderDTO toDTO(ResourceSubscriptionOrderVO orderVO);

    /**
     * 批量转换VO -> DTO
     *
     * @param orderVOList 订单volist
     * @return {@link List}<{@link ResourceSubscriptionOrderDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<ResourceSubscriptionOrderDTO> toDTOList(List<ResourceSubscriptionOrderVO> orderVOList);

}
