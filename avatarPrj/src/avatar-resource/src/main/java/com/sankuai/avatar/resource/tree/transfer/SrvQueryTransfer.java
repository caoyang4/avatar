package com.sankuai.avatar.resource.tree.transfer;

import com.sankuai.avatar.client.ops.request.SrvQueryRequest;
import com.sankuai.avatar.resource.tree.request.SrvQueryRequestBO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


/**
 * appkey srv转换
 *
 * @author qinwei05
 * @date 2022/12/18
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        uses = {SrvTransfer.class}
)
public interface SrvQueryTransfer {

    /**
     * 转换器
     */
    SrvQueryTransfer INSTANCE = Mappers.getMapper(SrvQueryTransfer.class);

    /**
     * 转换为Srv查询对象
     * @param srvQueryRequestBO srvQueryRequestBO
     * @return SrvQueryRequest
     */
    SrvQueryRequest toSrvQueryRequest(SrvQueryRequestBO srvQueryRequestBO);
}
