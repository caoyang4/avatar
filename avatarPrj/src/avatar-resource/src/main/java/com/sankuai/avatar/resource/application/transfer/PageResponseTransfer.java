package com.sankuai.avatar.resource.application.transfer;

import com.sankuai.avatar.client.soa.model.ScApplication;
import com.sankuai.avatar.client.soa.model.ScPageResponse;
import com.sankuai.avatar.client.soa.model.ScQueryApplication;
import com.sankuai.avatar.client.soa.model.ScUserOwnerApplication;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.bo.ApplicationBO;
import com.sankuai.avatar.resource.application.bo.UserOwnerApplicationBO;
import com.sankuai.avatar.resource.application.request.ScQueryApplicationBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * PageResponse的转换器
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 **/
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface PageResponseTransfer {

    /**
     * 转换器实例
     */
    PageResponseTransfer INSTANCE = Mappers.getMapper(PageResponseTransfer.class);

    /**
     * 转换为PageResponse
     *
     * @param scPageResponse sc分页响应
     * @return {@link PageResponse}<{@link ApplicationBO}>
     */
    @Mapping(source = "tn", target = "totalCount")
    @Mapping(source = "cn", target = "page")
    @Mapping(source = "pn", target = "totalPage")
    @Mapping(source = "sn", target = "pageSize")
    @Mapping(source = "items", target = "items")
    PageResponse<ApplicationBO> convert(ScPageResponse<ScApplication> scPageResponse);

    /**
     * 转换为PageResponse
     *
     * @param scPageResponse sc分页响应
     * @return {@link PageResponse}<{@link ApplicationBO}>
     */
    @Mapping(source = "tn", target = "totalCount")
    @Mapping(source = "cn", target = "page")
    @Mapping(source = "pn", target = "totalPage")
    @Mapping(source = "sn", target = "pageSize")
    @Mapping(source = "items", target = "items")
    PageResponse<UserOwnerApplicationBO> toUserOwnerApplicationBOPageResponse(ScPageResponse<ScUserOwnerApplication> scPageResponse);

    /**
     * 转换为PageResponse
     *
     * @param scPageResponse sc分页响应
     * @return {@link PageResponse}<{@link ApplicationBO}>
     */
    @Mapping(source = "tn", target = "totalCount")
    @Mapping(source = "cn", target = "page")
    @Mapping(source = "pn", target = "totalPage")
    @Mapping(source = "sn", target = "pageSize")
    @Mapping(source = "items", target = "items")
    PageResponse<ScQueryApplicationBO> toScQueryApplicationBOPageResponse(ScPageResponse<ScQueryApplication> scPageResponse);

}
