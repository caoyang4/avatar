package com.sankuai.avatar.resource.octo.transfer;

import com.sankuai.avatar.client.octo.model.OctoNodeStatusProvider;
import com.sankuai.avatar.client.octo.model.OctoProviderGroup;
import com.sankuai.avatar.client.octo.request.OctoNodeStatusQueryRequest;
import com.sankuai.avatar.client.octo.response.OctoNodeStatusResponse;
import com.sankuai.avatar.resource.octo.model.OctoNodeStatusProviderBO;
import com.sankuai.avatar.resource.octo.model.OctoNodeStatusResponseBO;
import com.sankuai.avatar.resource.octo.model.OctoProviderGroupBO;
import com.sankuai.avatar.resource.octo.request.OctoNodeStatusQueryRequestBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * OCTO数据对象转换器
 *
 * @author qinwei05
 * @date 2023/02/10
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OctoTransfer {

    /**
     * 转换器实例
     */
    OctoTransfer INSTANCE = Mappers.getMapper(OctoTransfer.class);

    /**
     * 从OctoProviderGroup转换为OctoProviderGroupBO对象
     *
     * @param octoProviderGroup 服务路由分组信息
     * @return OctoProviderGroupBO
     */
    @Named("toOctoProviderGroupBO")
    OctoProviderGroupBO toOctoProviderGroupBO(OctoProviderGroup octoProviderGroup);

    /**
     * 批量从OctoProviderGroup转换为OctoProviderGroupBO对象
     *
     * @param octoProviderGroupList 服务多个路由分组信息
     * @return {@link List}<{@link OctoProviderGroupBO}>
     */
    @Named("toOctoProviderGroupBOList")
    @IterableMapping(qualifiedByName = "toOctoProviderGroupBO")
    List<OctoProviderGroupBO> toOctoProviderGroupBOList(List<OctoProviderGroup> octoProviderGroupList);

    /**
     * 转换OctoNodeStatusQueryRequest对象
     *
     * @param octoNodeStatusQueryRequestBO 请求
     * @return OctoNodeStatusQueryRequest
     */
    @Named("toOctoNodeStatusQueryRequestBO")
    OctoNodeStatusQueryRequest toOctoNodeStatusQueryRequest(OctoNodeStatusQueryRequestBO octoNodeStatusQueryRequestBO);

    /**
     * 转换为OctoNodeStatusProviderBO对象
     *
     * @param octoNodeStatusProvider octo节点状态
     * @return OctoProviderGroupBO
     */
    @Named("toOctoNodeStatusProviderBO")
    OctoNodeStatusProviderBO toOctoNodeStatusProviderBO(OctoNodeStatusProvider octoNodeStatusProvider);

    /**
     * 批量转换为OctoNodeStatusProviderBO对象
     *
     * @param octoNodeStatusProviderList 服务多个octo节点状态
     * @return {@link List}<{@link OctoProviderGroupBO}>
     */
    @Named("toOctoNodeStatusProviderBOList")
    @IterableMapping(qualifiedByName = "toOctoNodeStatusProviderBO")
    List<OctoNodeStatusProviderBO> toOctoNodeStatusProviderBOList(List<OctoNodeStatusProvider> octoNodeStatusProviderList);

    /**
     * 批量转换为OctoNodeStatusResponseBO对象
     *
     * @param octoNodeStatusResponse 服务节点状态返回
     * @return {@link List}<{@link OctoProviderGroupBO}>
     */
    @Named("toPageResponse")
    OctoNodeStatusResponseBO toPageResponse(OctoNodeStatusResponse octoNodeStatusResponse);
}
