package com.sankuai.avatar.resource.activity.transfer;

import com.sankuai.avatar.resource.activity.bo.WindowPeriodResourceBO;
import com.sankuai.avatar.resource.activity.request.WindowPeriodRequestBO;
import com.sankuai.avatar.dao.resource.repository.model.ResourceWindowPeriodDO;
import com.sankuai.avatar.dao.resource.repository.request.WindowPeriodRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-03-15 16:38
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface WindowPeriodTransfer {

    WindowPeriodTransfer INSTANCE = Mappers.getMapper(WindowPeriodTransfer.class);

    /**
     * 请求对象转换
     *
     * @param requestBO 请求
     * @return {@link WindowPeriodRequest}
     */
    @Named("toRequest")
    WindowPeriodRequest toRequest(WindowPeriodRequestBO requestBO);

    /**
     * DO -> BO
     *
     * @param periodDO periodDO
     * @return {@link WindowPeriodResourceBO}
     */
    @Named("toBO")
    WindowPeriodResourceBO toBO(ResourceWindowPeriodDO periodDO);

    /**
     * 批量转换 DO -> BO
     *
     * @param periodDOList periodDOList
     * @return {@link List}<{@link WindowPeriodResourceBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<WindowPeriodResourceBO> toBOList(List<ResourceWindowPeriodDO> periodDOList);

    /**
     * BO -> DO
     *
     * @param bo bo
     * @return {@link ResourceWindowPeriodDO}
     */
    @Named("toDO")
    ResourceWindowPeriodDO toDO(WindowPeriodResourceBO bo);

    /**
     * 批量转换 BO -> DO
     *
     * @param boList boList
     * @return {@link List}<{@link ResourceWindowPeriodDO}>
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<ResourceWindowPeriodDO> toDOList(List<WindowPeriodResourceBO> boList);

}
