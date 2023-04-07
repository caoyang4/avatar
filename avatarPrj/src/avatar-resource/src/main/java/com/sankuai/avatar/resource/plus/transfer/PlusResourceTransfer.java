package com.sankuai.avatar.resource.plus.transfer;

import com.sankuai.avatar.client.plus.model.PlusRelease;
import com.sankuai.avatar.resource.plus.bo.PlusReleaseBO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/10/19 13:15
 * @version 1.0
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface PlusResourceTransfer {

    /**
     * 转换器
     */
    PlusResourceTransfer INSTANCE = Mappers.getMapper(PlusResourceTransfer.class);
    /**
     * 转换为Business Object
     * @param plusRelease 发布项
     * @return bo
     */
    PlusReleaseBO toBO(PlusRelease plusRelease);
    /**
     * 转换为Business Object
     * @param plusReleaseList 发布项s
     * @return bo
     */
    List<PlusReleaseBO> toBOList(List<PlusRelease> plusReleaseList);
}
