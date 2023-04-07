package com.sankuai.avatar.resource.mcm.transfer;

import com.sankuai.avatar.client.mcm.request.CreateBusyPeriodRequest;
import com.sankuai.avatar.resource.mcm.request.CreateBusyPeriodRequestBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface BusyPeriodTransfer {
    BusyPeriodTransfer INSTANCE = Mappers.getMapper(BusyPeriodTransfer.class);

    /**
     * CreateBusyPeriodRequestBO to  CreateBusyPeriodRequest
     *
     * @param requestBO {@link CreateBusyPeriodRequest}
     * @return {@link CreateBusyPeriodRequest}
     */
    @Mapping(target = "periodConfig", source = "requestBO.periodConfig")
    CreateBusyPeriodRequest boToRequest(CreateBusyPeriodRequestBO requestBO);
}
