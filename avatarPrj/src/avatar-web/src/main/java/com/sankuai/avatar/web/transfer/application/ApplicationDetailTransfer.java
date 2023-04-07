package com.sankuai.avatar.web.transfer.application;

import com.sankuai.avatar.resource.application.bo.ApplicationDetailBO;
import com.sankuai.avatar.web.dto.application.ApplicationDetailDTO;
import com.sankuai.avatar.web.vo.application.ApplicationDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 应用详细信息对象转换器
 *
 * @author zhangxiaoning07
 * @date 2022/11/24
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ApplicationDetailTransfer {
    /**
     * 转换器实例
     */
    ApplicationDetailTransfer INSTANCE = Mappers.getMapper(ApplicationDetailTransfer.class);

    /**
     * 转换为DTO
     *
     * @param applicationDetailBO 应用细节BO
     * @return {@link ApplicationDetailDTO}
     */
    ApplicationDetailDTO toDTO(ApplicationDetailBO applicationDetailBO);

    /**
     * 转换为VO
     *
     * @param applicationDetailDTO 应用细节DTO
     * @return {@link ApplicationDetailVO}
     */
    ApplicationDetailVO toVO(ApplicationDetailDTO applicationDetailDTO);
}
