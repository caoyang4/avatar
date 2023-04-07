package com.sankuai.avatar.web.transfer.activity;

import com.sankuai.avatar.resource.activity.bo.OrderHostBO;
import com.sankuai.avatar.resource.activity.bo.ResourceSubscriptionOrderBO;
import com.sankuai.avatar.resource.activity.request.SubscriptionOrderRequestBO;
import com.sankuai.avatar.web.dto.activity.OrderHostDTO;
import com.sankuai.avatar.web.dto.activity.ResourceSubscriptionOrderDTO;
import com.sankuai.avatar.web.request.ResourceSubscriptionOrderPageRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-02-13 15:02
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ResourceSubscriptionOrderDTOTransfer {

    ResourceSubscriptionOrderDTOTransfer INSTANCE = Mappers.getMapper(ResourceSubscriptionOrderDTOTransfer.class);

    /**
     * 请求转换
     *
     * @param pageRequest 请求
     * @return {@link SubscriptionOrderRequestBO}
     */
    @Named("toRequestBO")
    SubscriptionOrderRequestBO toRequestBO(ResourceSubscriptionOrderPageRequest pageRequest);

    /**
     * BO -> DTO
     *
     * @param orderHostBO orderHostBO
     * @return {@link OrderHostDTO}
     */
    @Named("toOrderHostDTO")
    OrderHostDTO toOrderHostDTO(OrderHostBO orderHostBO);

    /**
     * DTO -> BO
     *
     * @param orderHostDTO orderHostDTO
     * @return {@link OrderHostBO}
     */
    @Named("toOrderHostBO")
    OrderHostBO toOrderHostBO(OrderHostDTO orderHostDTO);

    /**
     * BO -> DTO
     *
     * @param orderBO orderBO
     * @return {@link ResourceSubscriptionOrderDTO}
     */
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "toOrderHostDTO")
    @Named("toDTO")
    ResourceSubscriptionOrderDTO toDTO(ResourceSubscriptionOrderBO orderBO);

    /**
     * 批量转换BO -> DTO
     *
     * @param boList boList
     * @return {@link List}<{@link ResourceSubscriptionOrderDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<ResourceSubscriptionOrderDTO> toDTOList(List<ResourceSubscriptionOrderBO> boList);

    /**
     * DTO -> BO
     *
     * @param orderDTO orderDTO
     * @return {@link ResourceSubscriptionOrderBO}
     */
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "toOrderHostBO")
    @Named("toBO")
    ResourceSubscriptionOrderBO toBO(ResourceSubscriptionOrderDTO orderDTO);

    /**
     * 批量转换DTO -> BO
     *
     * @param dtoList dtoList
     * @return {@link List}<{@link ResourceSubscriptionOrderBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<ResourceSubscriptionOrderBO> toBOList(List<ResourceSubscriptionOrderDTO> dtoList);
}
