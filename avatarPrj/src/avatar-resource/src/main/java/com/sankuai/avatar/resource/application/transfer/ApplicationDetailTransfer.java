package com.sankuai.avatar.resource.application.transfer;

import com.sankuai.avatar.client.soa.model.ScApplicationDetail;
import com.sankuai.avatar.resource.application.bo.ApplicationDetailBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * ApplicationDetail转换器
 *
 * @author zhangxiaoning07
 * @create 2022/11/23
 **/
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
     * 转换为BO对象
     *
     * @param scApplicationDetail ScApplicationDetail对象
     * @return ApplicationBO对象
     */
    @Mapping(source = "appKeyTotal", target = "appKeyCount")
    ApplicationDetailBO toBO(ScApplicationDetail scApplicationDetail);
}
