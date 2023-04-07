package com.sankuai.avatar.resource.appkey.transfer;

import com.sankuai.avatar.client.banner.response.ElasticTip;
import com.sankuai.avatar.client.dom.model.AppkeyResourceUtil;
import com.sankuai.avatar.client.ecs.model.BillingUnit;
import com.sankuai.avatar.client.ops.model.OpsSrv;
import com.sankuai.avatar.client.soa.model.ScAppkey;
import com.sankuai.avatar.client.soa.model.ScIsoltAppkey;
import com.sankuai.avatar.client.soa.model.ScPageResponse;
import com.sankuai.avatar.client.soa.model.ScV1Appkey;
import com.sankuai.avatar.client.workflow.model.AppkeyFlow;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.appkey.bo.*;
import com.sankuai.avatar.resource.application.bo.ApplicationBO;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-14 10:06
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyTransfer {

    AppkeyTransfer INSTANCE = Mappers.getMapper(AppkeyTransfer.class);

    /**
     * DO -> BO
     *
     * @param appkeyDO appkey做
     * @return {@link AppkeyBO}
     */
    @Named("toBO")
    AppkeyBO toBO(AppkeyDO appkeyDO);

    /**
     * 批量转换 DO -> BO
     *
     * @param doList doList
     * @return {@link List}<{@link AppkeyBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<AppkeyBO> toBOList(List<AppkeyDO> doList);

    /**
     * opsSrv -> OpsSrvBO
     *
     * @param opsSrv OPS服务树对象
     * @return {@link OpsSrvBO}
     */
    @Named("toOpsSrvBO")
    OpsSrvBO toOpsSrvBO(OpsSrv opsSrv);

    /**
     * sc appkey v2
     *
     * @param scAppkey sc appkey
     * @return {@link ScAppkeyBO}
     */
    @Named("toScAppkeyBOV2")
    ScAppkeyBO toScAppkeyBOV2(ScAppkey scAppkey);

    /**
     * 批量转换: sc appkey v2
     *
     * @param scAppkeyList scAppkeyList
     * @return {@link ScAppkeyBO}
     */
    @IterableMapping(qualifiedByName = "toScAppkeyBOV2")
    List<ScAppkeyBO> batchToScAppkeyBO(List<ScAppkey> scAppkeyList);

    /**
     * sc appkey v1
     *
     * @param scAppkey sc appkey
     * @return {@link ScAppkeyBO}
     */
    @Named("toScAppkeyBOV1")
    ScAppkeyBO toScAppkeyBOV1(ScV1Appkey scAppkey);

    /**
     * dom appkeyResourceUtil
     *
     * @param appkeyResourceUtil appkeyResourceUtil
     * @return {@link ScAppkeyBO}
     */
    @Named("toAppkeyResourceUtilBO")
    AppkeyResourceUtilBO toAppkeyResourceUtilBO(AppkeyResourceUtil appkeyResourceUtil);

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
    PageResponse<ScIsoltAppkeyBO> toScIsoltAppkeyBOPageResponse(ScPageResponse<ScIsoltAppkey> scPageResponse);

    /**
     * 转换为ElasticTipBO
     *
     * @param elasticTip 弹性提示
     * @return {@link ElasticTipBO}
     */
    ElasticTipBO toElasticTipBO(ElasticTip elasticTip);

    /**
     * 转换为AppkeyFlowBO PageResponse
     *
     * @param appkeyFlowPageResponse appkeyFlowPageResponse
     * @return {@link PageResponse}<{@link AppkeyFlowBO}>
     */
    PageResponse<AppkeyFlowBO> toAppkeyFlowBOPageResponse(PageResponse<AppkeyFlow> appkeyFlowPageResponse);

    /**
     * 转换为BillingUnitBO
     *
     * @param billingUnit 计费单位
     * @return {@link BillingUnitBO}
     */
    BillingUnitBO toBillingUnit(BillingUnit billingUnit);
}