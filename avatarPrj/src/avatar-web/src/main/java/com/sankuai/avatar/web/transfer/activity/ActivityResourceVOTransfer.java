package com.sankuai.avatar.web.transfer.activity;

import com.sankuai.avatar.resource.activity.constant.ResourceStatusType;
import com.sankuai.avatar.web.dto.activity.ActivityHostDTO;
import com.sankuai.avatar.web.dto.activity.ActivityResourceDTO;
import com.sankuai.avatar.web.vo.activity.ActivityResourceExportVO;
import com.sankuai.avatar.web.vo.activity.ActivityResourceVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-03-08 17:28
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ActivityResourceVOTransfer {

    ActivityResourceVOTransfer INSTANCE = Mappers.getMapper(ActivityResourceVOTransfer.class);


    /**
     * DTO -> VO
     *
     * @param dto dto
     * @return {@link ActivityResourceVO}
     */
    @Named("toVO")
    @Mapping(target = "config", expression = "java(INSTANCE.toConfig(dto.getHostConfig()))")
    @Mapping(target = "idcs", expression = "java(INSTANCE.toIdcString(dto.getHostConfig()))")
    @Mapping(target = "count", expression = "java(INSTANCE.toCount(dto.getHostConfig()))")
    @Mapping(target = "statusCn", expression = "java(INSTANCE.toStatusCn(dto.getStatus()))")
    ActivityResourceVO toVO(ActivityResourceDTO dto);

    /**
     * 批量转换DTO -> VO
     *
     * @param dtoList dtoList
     * @return {@link List}<{@link ActivityResourceVO}>
     */
    @Named("toVOList")
    @IterableMapping(qualifiedByName = "toVO")
    List<ActivityResourceVO> toVOList(List<ActivityResourceDTO> dtoList);

    /**
     * VO -> DTO
     *
     * @param vo vo
     * @return {@link ActivityResourceDTO}
     */
    @Named("toDTO")
    @Mapping(target = "retainCount", ignore = true)
    ActivityResourceDTO toDTO(ActivityResourceVO vo);

    /**
     * DTO -> ExportVO
     *
     * @param dto dto
     * @return {@link ActivityResourceExportVO}
     */
    @Named("toExportVO")
    @Mapping(source = "hostConfig.cpu", target = "cpu")
    @Mapping(source = "hostConfig.memory", target = "memory")
    @Mapping(source = "hostConfig.disk", target = "disk")
    @Mapping(source = "hostConfig.city", target = "city")
    @Mapping(source = "hostConfig.count", target = "count")
    @Mapping(source = "hostConfig.deliverCount", target = "deliverCount")
    @Mapping(target = "idcs", expression = "java(INSTANCE.toIdcString(dto.getHostConfig()))")
    @Mapping(target = "status", expression = "java(INSTANCE.toStatusCn(dto.getStatus()))")
    @Mapping(target = "expectedTime", expression = "java(INSTANCE.toTimeStr(dto.getStartTime()))")
    @Mapping(target = "deliverTime", expression = "java(INSTANCE.toTimeStr(dto.getDeliverTime()))")
    @Mapping(target = "returnTime", expression = "java(INSTANCE.toTimeStr(dto.getEndTime()))")
    ActivityResourceExportVO toExportVO(ActivityResourceDTO dto);

    /**
     * 批量转换 DTO -> ExportVO
     *
     * @param dtoList dtoList
     * @return {@link List}<{@link ActivityResourceExportVO}>
     */
    @Named("toExportVOList")
    @IterableMapping(qualifiedByName = "toExportVO")
    List<ActivityResourceExportVO> toExportVOList(List<ActivityResourceDTO> dtoList);

    /**
     * 配置信息
     *
     * @param hostConfig 主机配置
     * @return {@link String}
     */
    default String toConfig(ActivityHostDTO hostConfig){
        return Objects.nonNull(hostConfig) ? String.format("%d核/%dG/%dG", hostConfig.getCpu(), hostConfig.getMemory(), hostConfig.getDisk()) : "";
    }

    /**
     * 机房信息
     *
     * @param hostConfig 主机配置
     * @return {@link String}
     */
    default String toIdcString(ActivityHostDTO hostConfig){
        return Objects.nonNull(hostConfig) ? hostConfig.getIdcs() : "";
    }

    /**
     * 机器数量
     *
     * @param hostConfig 主机配置
     * @return {@link Integer}
     */
    default Integer toCount(ActivityHostDTO hostConfig){
        return Objects.nonNull(hostConfig) ? hostConfig.getCount() : 0;
    }

    /**
     * 活动状态名称
     *
     * @param statusType 状态类型
     * @return {@link String}
     */
    default String toStatusCn(ResourceStatusType statusType){
        return Objects.nonNull(statusType) ? statusType.getName() : "";
    }

    /**
     * 格式化时间
     *
     * @param time 时间
     * @return {@link String}
     */
    default String toTimeStr(Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Objects.nonNull(time) ? sdf.format(time) : "";
    }
}
