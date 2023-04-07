package com.sankuai.avatar.resource.application.transfer;

import com.sankuai.avatar.client.soa.request.ApplicationPageRequest;
import com.sankuai.avatar.resource.application.request.ApplicationPageRequestBO;
import com.sankuai.avatar.resource.application.request.ApplicationRoleAdminRequestBO;
import com.sankuai.avatar.dao.resource.repository.request.ApplicationRoleAdminRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 应用信息分页请求转换器
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ApplicationPageRequestTransfer {
    /**
     * 实例
     */
    ApplicationPageRequestTransfer INSTANCE = Mappers.getMapper(ApplicationPageRequestTransfer.class);

    /**
     * 转换
     *
     * @param requestBO 请求对象
     * @return {@link ApplicationPageRequest}
     */
    ApplicationPageRequest convert(ApplicationPageRequestBO requestBO);

    /**
     * BO -> DO
     *
     * @param request 请求
     * @return {@link ApplicationRoleAdminRequestBO}
     */
    ApplicationRoleAdminRequest toRequest(ApplicationRoleAdminRequestBO request);
}
