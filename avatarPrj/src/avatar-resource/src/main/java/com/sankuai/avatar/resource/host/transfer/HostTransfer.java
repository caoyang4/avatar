package com.sankuai.avatar.resource.host.transfer;

import com.sankuai.avatar.client.dayu.model.DayuGroupTag;
import com.sankuai.avatar.client.dayu.model.GroupTagQueryRequest;
import com.sankuai.avatar.client.ecs.model.EcsIdc;
import com.sankuai.avatar.client.kapiserver.model.HostFeature;
import com.sankuai.avatar.client.kapiserver.model.VmHostDiskFeature;
import com.sankuai.avatar.client.kapiserver.request.VmHostDiskQueryRequest;
import com.sankuai.avatar.client.ops.model.OpsHost;
import com.sankuai.avatar.resource.host.bo.*;
import com.sankuai.avatar.resource.host.request.GroupTagQueryRequestBO;
import com.sankuai.avatar.resource.host.request.VmHostDiskQueryRequestBO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface HostTransfer {
    /**
     * 转换器实例
     */
    HostTransfer INSTANCE = Mappers.getMapper(HostTransfer.class);

    /**
     * 转换为BO对象
     *
     * @param opsHost OpsHost
     * @return {@link HostBO}
     */
    HostBO toBO(OpsHost opsHost);

    /**
     * 转换为BO 列表对象
     *
     * @param opsHosts List<OpsHost>
     * @return List<HostBO>
     */
    @IterableMapping(qualifiedByName = "toBO")
    List<HostBO> toBOList(List<OpsHost> opsHosts);

    /**
     * 转换为完整的HostAttributesBO主机对象
     *
     * @param hostBO hostBO
     * @return {@link HostAttributesBO}
     */
    HostAttributesBO toHostAttributesBO(OpsHost hostBO);

    /**
     * 批量转换为完整的HostAttributesBO主机对象
     *
     * @param opsHostList List<OpsHost>
     * @return List<HostAttributesBO>
     */
    @IterableMapping(qualifiedByName = "toHostAttributesBO")
    List<HostAttributesBO> toHostAttributesBOList(List<OpsHost> opsHostList);

    /**
     * 转换为IdcMetaDataBO对象
     *
     * @param ecsIdc ecsIdc
     * @return {@link IdcMetaDataBO}
     */
    IdcMetaDataBO toIdcMetaDataBO(EcsIdc ecsIdc);

    /**
     * 批量转换为IdcMetaDataBO对象
     *
     * @param ecsIdc ecs idc
     * @return {@link List}<{@link IdcMetaDataBO}>
     */
    @IterableMapping(qualifiedByName = "toIdcMetaDataBO")
    List<IdcMetaDataBO> batchToIdcMetaDataBO(List<EcsIdc> ecsIdc);

    /**
     * 转换为HostFeatureBO对象
     *
     * @param hostFeatureMap hostFeatureMap
     * @return {@link Map}<{@link String}, {@link HostFeatureBO}>
     */
    Map<String, HostFeatureBO> toHostFeatureBOMap(Map<String, HostFeature> hostFeatureMap);

    /**
     * 转换为底层Client VmHostDiskQueryRequest请求对象
     *
     * @param requestBO requestBO
     * @return {@link VmHostFeatureBO}
     */
    VmHostDiskQueryRequest toVmHostDiskQueryRequestBO(VmHostDiskQueryRequestBO requestBO);

    /**
     * 转换为VmHostFeatureBO对象
     *
     * @param vmHostDiskFeature vmHostDiskFeature
     * @return {@link VmHostFeatureBO}
     */
    VmHostFeatureBO toVmHostFeatureBO(VmHostDiskFeature vmHostDiskFeature);

    /**
     * 批量转换为VmHostFeatureBO对象
     *
     * @param vmHostDiskFeatureList vmHostDiskFeatureList
     * @return {@link List}<{@link IdcMetaDataBO}>
     */
    @IterableMapping(qualifiedByName = "toVmHostFeatureBO")
    List<VmHostFeatureBO> batchToVmHostFeatureBO(List<VmHostDiskFeature> vmHostDiskFeatureList);

    /**
     * 转换为GroupTagQueryRequest
     *
     * @param groupTagQueryRequest 请求
     * @return {@link GroupTagQueryRequest}
     */
    GroupTagQueryRequest toGroupTagQueryRequest(GroupTagQueryRequestBO groupTagQueryRequest);

    /**
     * 转换为DayuGroupTagBO对象
     *
     * @param dayuGroupTag 业务分组
     * @return {@link DayuGroupTagBO}
     */
    DayuGroupTagBO toDayuGroupTagBO(DayuGroupTag dayuGroupTag);

    /**
     * 批量转换为DayuGroupTagBO对象
     *
     * @param dayuGroupTagList 业务分组列表
     * @return {@link List}<{@link DayuGroupTagBO}>
     */
    @IterableMapping(qualifiedByName = "toDayuGroupTagBO")
    List<DayuGroupTagBO> batchToDayuGroupTagBO(List<DayuGroupTag> dayuGroupTagList);
}
