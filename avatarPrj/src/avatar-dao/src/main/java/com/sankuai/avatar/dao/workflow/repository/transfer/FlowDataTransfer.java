package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowDataEntity;
import com.sankuai.avatar.dao.workflow.repository.model.FlowDataDO;
import com.sankuai.avatar.dao.workflow.repository.model.FlowDisplayDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowCheckResultAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataUpdateRequest;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.Map;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowDataTransfer {
    FlowDataTransfer INSTANCE = Mappers.getMapper(FlowDataTransfer.class);

    /**
     * FlowDataAddRequest to FlowDataDO
     *
     * @param request {@link FlowDataAddRequest}
     * @return {@link FlowDataDO}
     */
    FlowDataDO addRequestToDO(FlowDataAddRequest request);

    /**
     * FlowDataUpdateRequest to FlowDataDO
     *
     * @param request {@link FlowDataUpdateRequest}
     * @return {@link FlowDataDO}
     */
    FlowDataDO updateRequestToDO(FlowDataUpdateRequest request);

    /**
     * FlowDataDO to FlowDataEntity
     *
     * @param flowDataDO {@link FlowDataDO}
     * @return {@link FlowDataEntity}
     */
    @Mapping(source = "publicData", target = "publicData", qualifiedByName = "toPublicDataMap")
    FlowDataEntity toEntity(FlowDataDO flowDataDO);


    /**
     * 预检结果
     *
     * @param request {@link FlowCheckResultAddRequest}
     * @return {@link FlowDisplayDO}
     */
    FlowDataDO checkResultRequestToDO(FlowCheckResultAddRequest request);

    /**
     * PublicData string to Map
     *
     * @param publicData 公共数据
     * @return Map<String, Object>
     */
    @Named("toPublicDataMap")
    static Map<String, Object> toPublicDataMap(String publicData) {
        if (StringUtils.isEmpty(publicData)) {
            return Collections.emptyMap();
        }
        return JsonUtil.json2Map(publicData, String.class, Object.class);
    }
}
