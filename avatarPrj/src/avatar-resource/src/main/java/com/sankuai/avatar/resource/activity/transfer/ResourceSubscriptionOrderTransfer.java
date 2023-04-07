package com.sankuai.avatar.resource.activity.transfer;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.resource.activity.bo.OrderHostBO;
import com.sankuai.avatar.resource.activity.bo.ResourceSubscriptionOrderBO;
import com.sankuai.avatar.resource.activity.constant.OrderStateType;
import com.sankuai.avatar.resource.activity.request.SubscriptionOrderRequestBO;
import com.sankuai.avatar.dao.resource.repository.model.ResourceSubscriptionOrderDO;
import com.sankuai.avatar.dao.resource.repository.request.ResourceSubscriptionOrderRequest;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-02-13 11:28
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ResourceSubscriptionOrderTransfer {

    ResourceSubscriptionOrderTransfer INSTANCE = Mappers.getMapper(ResourceSubscriptionOrderTransfer.class);

    /**
     * 请求转换
     *
     * @param requestBO 请求
     * @return {@link ResourceSubscriptionOrderRequest}
     */
    @Named("toRequest")
    ResourceSubscriptionOrderRequest toRequest(SubscriptionOrderRequestBO requestBO);

    /**
     * DO -> BO
     *
     * @param orderDO 订单
     * @return {@link ResourceSubscriptionOrderBO}
     */
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "jsonToOrder")
    @Mapping(source = "status", target = "status", qualifiedByName = "jsonToType")
    @Named("toBO")
    ResourceSubscriptionOrderBO toBO(ResourceSubscriptionOrderDO orderDO);

    /**
     * 批量DO -> BO
     *
     * @param orderDOList orderDOList
     * @return {@link List}<{@link ResourceSubscriptionOrderBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<ResourceSubscriptionOrderBO> toBOList(List<ResourceSubscriptionOrderDO> orderDOList);

    /**
     * BO -> DO
     *
     * @param orderBO 订单
     * @return {@link ResourceSubscriptionOrderDO}
     */
    @Mapping(source = "hostConfig", target = "hostConfig", qualifiedByName = "toOrderHostJson")
    @Mapping(source = "status", target = "status", qualifiedByName = "typeToJson")
    @Named("toDO")
    ResourceSubscriptionOrderDO toDO(ResourceSubscriptionOrderBO orderBO);

    /**
     * 批量BO -> DO
     *
     * @param boList boList
     * @return {@link List}<{@link ResourceSubscriptionOrderDO}>
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<ResourceSubscriptionOrderDO> toDOList(List<ResourceSubscriptionOrderBO> boList);

    /**
     * json转对象
     *
     * @param json json
     * @return {@link OrderHostBO}
     */
    default OrderHostBO jsonToOrder(String json){
        return StringUtils.isNotEmpty(json) ? JsonUtil.json2Bean(json, OrderHostBO.class) : null;
    }

    /**
     * 对象转json
     *
     * @param obj obj
     * @return {@link String}
     */
    default String toOrderHostJson(OrderHostBO obj){
        return JsonUtil.bean2Json(obj);
    }

    /**
     * json转枚举
     *
     * @param json json
     * @return {@link OrderStateType}
     */
    default OrderStateType jsonToType(String json){
        return OrderStateType.getInstance(json);
    }

    /**
     * 枚举转json
     *
     * @param type 类型
     * @return {@link String}
     */
    default String typeToJson(OrderStateType type){
        return Objects.nonNull(type) ? type.name() : null;
    }
}
