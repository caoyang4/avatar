package com.sankuai.avatar.web.transfer.activity;

import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.web.dto.activity.WindowPeriodResourceDTO;
import com.sankuai.avatar.web.vo.activity.WindowPeriodHitVO;
import com.sankuai.avatar.web.vo.activity.WindowPeriodResourceVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

/**
 * @author caoyang
 * @create 2023-03-15 17:57
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface WindowPeriodResourceVOTransfer {

    WindowPeriodResourceVOTransfer INSTANCE = Mappers.getMapper(WindowPeriodResourceVOTransfer.class);

    /**
     * dto -> vo
     *
     * @param dto dto
     * @return {@link WindowPeriodResourceVO}
     */
    @Named("toVO")
    @Mapping(target = "period", expression = "java(INSTANCE.toPeriod(dto))")
    @Mapping(target = "hit", expression = "java(INSTANCE.isHit(dto))")
    WindowPeriodResourceVO toVO(WindowPeriodResourceDTO dto);

    /**
     * 批量转换 dto -> vo
     *
     * @param dtoList dtoList
     * @return {@link List}<{@link WindowPeriodResourceVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<WindowPeriodResourceVO> toVOList(List<WindowPeriodResourceDTO> dtoList);

    /**
     * vo -> dto
     *
     * @param vo vo
     * @return {@link WindowPeriodResourceDTO}
     */
    @Named("toDTO")
    WindowPeriodResourceDTO toDTO(WindowPeriodResourceVO vo);

    /**
     * 批量转换 vo -> dto
     *
     * @param voList voList
     * @return {@link List}<{@link WindowPeriodResourceDTO}>
     */
    @Named("toDTOList")
    List<WindowPeriodResourceDTO> toDTOList(List<WindowPeriodResourceVO> voList);

    /**
     * 转换为 HitVO
     *
     * @param dto dto
     * @return {@link WindowPeriodHitVO}
     */
    @Named("toHitVO")
    @Mapping(source = "id", target = "windowPeriodId")
    @Mapping(target = "period", expression = "java(INSTANCE.toPeriod(dto))")
    @Mapping(target = "hit", expression = "java(INSTANCE.isHit(dto))")
    @Mapping(target = "description", ignore = true)
    WindowPeriodHitVO toHitVO(WindowPeriodResourceDTO dto);

    /**
     * 转换为窗口期+时间范围描述
     *
     * @param dto dto
     * @return {@link String}
     */
    @Named("toPeriod")
    default String toPeriod(WindowPeriodResourceDTO dto){
        Date startTime = dto.getStartTime();
        Date endTime = dto.getEndTime();
        if (startTime != null && endTime != null) {
            String pattern = "yyyy-MM-dd";
            return String.format("%s【%s~%s】", dto.getName(), DateUtils.dateToString(startTime,pattern),  DateUtils.dateToString(endTime,pattern));
        }
        return "";
    }

    /**
     * 是否命中窗口期
     *
     * @param dto dto
     * @return boolean
     */
    @Named("isHit")
    default boolean isHit(WindowPeriodResourceDTO dto){
        Date startTime = dto.getStartTime();
        Date endTime = dto.getEndTime();
        Date date = new Date();
        if (startTime != null && endTime != null) {
            return date.after(startTime) && date.before(endTime);
        }
        return false;
    }

}
