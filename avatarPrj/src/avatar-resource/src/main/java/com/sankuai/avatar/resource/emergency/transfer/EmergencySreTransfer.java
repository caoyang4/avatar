package com.sankuai.avatar.resource.emergency.transfer;

import com.sankuai.avatar.dao.resource.repository.model.EmergencySreDO;
import com.sankuai.avatar.dao.resource.repository.request.EmergencySreRequest;
import com.sankuai.avatar.resource.emergency.bo.EmergencySreBO;
import com.sankuai.avatar.resource.emergency.request.EmergencySreRequestBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-20 17:51
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface EmergencySreTransfer {

    EmergencySreTransfer INSTANCE = Mappers.getMapper(EmergencySreTransfer.class);

    /**
     * DO -> BO
     *
     * @param emergencySreDO emergencySreDO
     * @return {@link EmergencySreBO}
     */
    @Named("toBO")
    EmergencySreBO toBO(EmergencySreDO emergencySreDO);

    /**
     * 批量转换DO -> BO
     *
     * @param doList doList
     * @return {@link List}<{@link EmergencySreBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<EmergencySreBO> toBOList(List<EmergencySreDO> doList);

    /**
     * BO -> DO
     *
     * @param emergencySreBO emergencySreBO
     * @return {@link EmergencySreDO}
     */
    @Named("toDO")
    EmergencySreDO toDO(EmergencySreBO emergencySreBO);

    /**
     * 批量转换BO -> DO
     *
     * @param boList boList
     * @return {@link List}<{@link EmergencySreDO}>
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<EmergencySreDO> toDOList(List<EmergencySreBO> boList);

    /**
     * DO -> BO
     *
     * @param request request
     * @return {@link EmergencySreRequestBO}
     */
    @Named("toRequestBO")
    EmergencySreRequest toRequestBO(EmergencySreRequestBO request);
}
