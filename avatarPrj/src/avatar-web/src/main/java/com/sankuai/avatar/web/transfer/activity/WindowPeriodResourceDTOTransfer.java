package com.sankuai.avatar.web.transfer.activity;

import com.sankuai.avatar.resource.activity.bo.WindowPeriodResourceBO;
import com.sankuai.avatar.resource.activity.request.WindowPeriodRequestBO;
import com.sankuai.avatar.web.dto.activity.WindowPeriodResourceDTO;
import com.sankuai.avatar.web.request.WindowPeriodPageRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 窗口对象转换接口
 * @author caoyang
 * @create 2023-03-15 17:38
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface WindowPeriodResourceDTOTransfer {

    WindowPeriodResourceDTOTransfer INSTANCE = Mappers.getMapper(WindowPeriodResourceDTOTransfer.class);

    /**
     * 请求转换
     *
     * @param pageRequest pageRequest
     * @return {@link WindowPeriodRequestBO}
     */
    @Named("toRequestBO")
    WindowPeriodRequestBO toRequestBO(WindowPeriodPageRequest pageRequest);

    /**
     * bo -> dto
     *
     * @param bo bo
     * @return {@link WindowPeriodResourceDTO}
     */
    @Named("toDTO")
    @Mapping(target = "hasOrder", ignore = true)
    WindowPeriodResourceDTO toDTO(WindowPeriodResourceBO bo);

    /**
     * 批量转换 bo -> dto
     *
     * @param boList bo列表
     * @return {@link List}<{@link WindowPeriodResourceDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<WindowPeriodResourceDTO> toDTOList(List<WindowPeriodResourceBO> boList);

    /**
     * dto -> bo
     *
     * @param dto dto
     * @return {@link WindowPeriodResourceBO}
     */
    @Named("toBO")
    WindowPeriodResourceBO toBO(WindowPeriodResourceDTO dto);

    /**
     * 批量转换 dto -> bo
     *
     * @param dtoList dto列表
     * @return {@link List}<{@link WindowPeriodResourceBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<WindowPeriodResourceBO> toBOList(List<WindowPeriodResourceDTO> dtoList);

}
