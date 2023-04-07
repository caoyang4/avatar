package com.sankuai.avatar.resource.tree.transfer;

import com.sankuai.avatar.client.ops.model.OpsSrvDetail;
import com.sankuai.avatar.client.ops.model.OpsTree;
import com.sankuai.avatar.client.ops.response.OpsAvatarSrvsResponse;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeSrvDetailBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeSrvDetailResponseBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;


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
public interface AppkeyTreeTransfer {

    /**
     * 转换器
     * uses = {SrvTransfer.class}
     */
    AppkeyTreeTransfer INSTANCE = Mappers.getMapper(AppkeyTreeTransfer.class);

    /**
     * OpsTree转换为BO
     * @param opsTree opsTree对象
     * @return AppkeyTreeBO
     */
    AppkeyTreeBO toAppkeyTreeBO(OpsTree opsTree);

    /**
     * opsAvatarSrvsResponse转BO
     * @param opsAvatarSrvsResponse opsTree对象
     * @return AppkeyTreeSrvsResponseBO
     */
    AppkeyTreeSrvDetailResponseBO toAppkeyTreeSrvsResponseBO(OpsAvatarSrvsResponse opsAvatarSrvsResponse);

    /**
     * OpsSrvDetail转换为BO
     * @param opsSrvDetail opsSrvDetail对象
     * @return AppkeyTreeSrvDetailBO
     */
    @Named("toAppkeyTreeSrvDetailBO")
    AppkeyTreeSrvDetailBO toAppkeyTreeSrvDetailBO(OpsSrvDetail opsSrvDetail);

    /**
     * opsSrvDetailList批量转换为BO
     * @param opsSrvDetailList opsSrvDetail对象
     * @return AppkeyTreeSrvDetailBO
     */
    @IterableMapping(qualifiedByName = "toAppkeyTreeSrvDetailBO")
    List<AppkeyTreeSrvDetailBO> batchToAppkeyTreeSrvDetailBO(List<OpsSrvDetail> opsSrvDetailList);
}
