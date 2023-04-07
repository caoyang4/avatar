package com.sankuai.avatar.web.transfer.host;

import com.sankuai.avatar.resource.host.bo.HostAttributesBO;
import com.sankuai.avatar.resource.host.bo.HostBO;
import com.sankuai.avatar.resource.host.bo.IdcMetaDataBO;
import com.sankuai.avatar.resource.host.request.GroupTagQueryRequestBO;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import com.sankuai.avatar.web.dto.host.HostAttributesDTO;
import com.sankuai.avatar.web.dto.host.HostDTO;
import com.sankuai.avatar.web.dto.host.IdcMetaDataDTO;
import com.sankuai.avatar.web.request.GroupTagQueryRequest;
import com.sankuai.avatar.web.request.HostQueryRequest;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author qinwei05
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface HostTransfer {

    /**
     * 转换器
     */
    HostTransfer INSTANCE = Mappers.getMapper(HostTransfer.class);

    /**
     * 转换为HostQueryRequestBO
     *
     * @param hostQueryRequest 请求
     * @return {@link HostQueryRequestBO}
     */
    HostQueryRequestBO toHostQueryRequestBO(HostQueryRequest hostQueryRequest);

    /**
     * 转换为HostAttributesDTO对象
     *
     * @param hostAttributesBO hostAttributesBO
     * @return {@link HostAttributesDTO}
     */
    HostAttributesDTO toHostAttributesDTO(HostAttributesBO hostAttributesBO);

    /**
     * 转换为HostDTO对象
     *
     * @param hostBO hostBO
     * @return {@link HostDTO}
     */
    HostDTO toHostDTO(HostBO hostBO);

    /**
     * 转换为HostAttributesDTO列表对象
     *
     * @param hostAttributesBOList 主机列表
     * @return {@link List}<{@link HostAttributesDTO}>
     */
    @IterableMapping(qualifiedByName = "toHostAttributesDTO")
    List<HostAttributesDTO> toHostAttributesDTOList(List<HostAttributesBO> hostAttributesBOList);

    /**
     * 转换为IdcMetaDataDTO对象
     *
     * @param idcMetaDataBO ecsIdc
     * @return {@link IdcMetaDataBO}
     */
    IdcMetaDataDTO toIdcDTO(IdcMetaDataBO idcMetaDataBO);

    /**
     * 批量转换为IdcMetaDataDTO对象
     *
     * @param idcMetaDataBOList ecsIdc列表
     * @return {@link List}<{@link IdcMetaDataBO}>
     */
    @IterableMapping(qualifiedByName = "toIdcDTO")
    List<IdcMetaDataDTO> batchToIdcDTO(List<IdcMetaDataBO> idcMetaDataBOList);

    /**
     * 转换为GroupTagQueryRequestBO
     *
     * @param groupTagQueryRequest 请求
     * @return {@link GroupTagQueryRequestBO}
     */
    GroupTagQueryRequestBO toGroupTagQueryRequestBO(GroupTagQueryRequest groupTagQueryRequest);
}
