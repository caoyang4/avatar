package com.sankuai.avatar.web.transfer.appkey;

import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.appkey.*;
import com.sankuai.avatar.web.dto.octo.OctoRouteStrategyDTO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeSrvDTO;
import com.sankuai.avatar.web.vo.appkey.*;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-14 14:17
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyVOTransfer {

    AppkeyVOTransfer INSTANCE = Mappers.getMapper(AppkeyVOTransfer.class);

    /**
     * appkey -> VO
     *
     * @param appkey appkey
     * @return {@link AppkeyCollectVO}
     */
    AppkeyCollectVO toVO(Appkey appkey);

    /**
     * DTO -> VO
     *
     * @param appkeyDetailDTO appkeyDetailDTO
     * @return {@link AppkeyDetailVO}
     */
    AppkeyDetailVO toAppkeyDetailVO(AppkeyDetailDTO appkeyDetailDTO);

    /**
     * DTO -> VO
     *
     * @param appkeyDTO appkeyDTO
     * @return {@link AppkeyCollectVO}
     */
    @Named("toVO")
    AppkeyCollectVO toVO(AppkeyDTO appkeyDTO);

    /**
     * 批量转换DTO -> VO
     *
     * @param dtoList dto列表
     * @return {@link List}<{@link AppkeyCollectVO}>
     */
    @Named("toVOList")
    List<AppkeyCollectVO> toVOList(List<AppkeyDTO> dtoList);

    /**
     * AppkeyResourceUtilDTO -> AppkeyResourceUtilVO
     *
     * @param appkeyResourceUtilDTO appkeyResourceUtilDTO
     * @return {@link AppkeyResourceUtilVO}
     */
    @Named("toAppkeyResourceUtilVO")
    AppkeyResourceUtilVO toAppkeyResourceUtilVO(AppkeyResourceUtilDTO appkeyResourceUtilDTO);

    /**
     * appkey vo用户相关
     *
     * @param appkeyTreeSrvDTO appkeyTreeSrvDTO
     * @return {@link AppkeyVO}
     */
    @Named("toAppkeyVO")
    @Mapping(source = "key", target = "srv")
    AppkeyVO toAppkeyVO(AppkeyTreeSrvDTO appkeyTreeSrvDTO);

    /**
     * 批量转换
     *
     * @param appkeyTreeSrvDTOList appkeyTreeSrvDTOList
     * @return {@link List<AppkeyVO>}
     */
    @IterableMapping(qualifiedByName = "toAppkeyVO")
    List<AppkeyVO> batchToAppkeyVO(List<AppkeyTreeSrvDTO> appkeyTreeSrvDTOList);

    /**
     * 批量转换至演练Appkey对象
     *
     * @param isoltAppkeyDTOPageResponse isoltAppkeyDTOPageResponse
     * @return {@link PageResponse<IsoltAppkeyVO>}
     */
    @IterableMapping(qualifiedByName = "toIsoltAppkeyVO")
    PageResponse<IsoltAppkeyVO> toIsoltAppkeyVO(PageResponse<IsoltAppkeyDTO> isoltAppkeyDTOPageResponse);

    /**
     * 批量转换至AppkeyHomeVO对象
     *
     * @param appkeyVOPageResponse appkeyVOPageResponse
     * @return {@link PageResponse<AppkeyHomeVO>}
     */
    PageResponse<AppkeyHomeVO> toAppkeyHomeVO(PageResponse<AppkeyVO> appkeyVOPageResponse);

    /**
     * 批量转换至AppkeyFlowVO对象
     *
     * @param appkeyFlowDTOPageResponse appkeyFlowDTOPageResponse
     * @return {@link PageResponse<AppkeyFlowVO>}
     */
    PageResponse<AppkeyFlowVO> toAppkeyFlowVO(PageResponse<AppkeyFlowDTO> appkeyFlowDTOPageResponse);

    /**
     * 转换至生成的演练Appkey名称
     *
     * @param isoltAppkeyGenerateDisplayDTO isolt appkey生成显示dto
     * @return {@link IsoltAppkeyGenerateDisplayVO}
     */
    IsoltAppkeyGenerateDisplayVO toIsoltAppkeyGenerateDisplayVO(IsoltAppkeyGenerateDisplayDTO isoltAppkeyGenerateDisplayDTO);

    /**
     * DTO -> VO
     *
     * @param appkeyDTO appkeyDTO
     * @return {@link AppkeyVO}
     */
    @Mapping(source = "description", target = "comment")
    @Mapping(source = "weekResourceUtil", target = "utilization")
    @Mapping(source = "createTime", target = "createAt")
    @Mapping(source = "rdAdmin", target = "rdAdmin", qualifiedByName = "toUserSimpleVO")
    @Mapping(source = "epAdmin", target = "epAdmin", qualifiedByName = "toUserSimpleVO")
    @Mapping(source = "opAdmin", target = "opAdmin", qualifiedByName = "toUserSimpleVO")
    @Named("toRelatedAppkeyVO")
    AppkeyVO toRelatedAppkeyVO(AppkeyDTO appkeyDTO);

    /**
     * 批量转换
     *
     * @param dtoList dtoList
     * @return {@link List}<{@link AppkeyVO}>
     */
    @IterableMapping(qualifiedByName = "toRelatedAppkeyVO")
    List<AppkeyVO> toRelatedAppkeyVOList(List<AppkeyDTO> dtoList);

    /**
     * 转换为ElasticTipVO
     *
     * @param elasticTip 弹性提示
     * @return {@link ElasticTipVO}
     */
    ElasticTipVO toElasticTipVO(ElasticTipDTO elasticTip);

    /**
     * 转换为AppkeyRouteStrategyVO
     *
     * @param octoRouteStrategyDTO octo服务路由分组
     * @return {@link ElasticTipVO}
     */
    AppkeyRouteStrategyVO toAppkeyRouteStrategyVO(OctoRouteStrategyDTO octoRouteStrategyDTO);

    /**
     * 转换为AppkeyBillingUnitVO
     *
     * @param billingUnit 计费单位
     * @return {@link AppkeyBillingUnitVO}
     */
    AppkeyBillingUnitVO toAppkeyBillingUnitVO(AppkeyBillingUnitDTO billingUnit);

    /**
     * 转对象
     *
     * @param user mis
     * @return {@link List}<{@link AppkeyUserSimpleVO}>
     */
    default List<AppkeyUserSimpleVO> toUserSimpleVO(String user){
        if (StringUtils.isEmpty(user)) {
            return Collections.emptyList();
        }
        List<AppkeyUserSimpleVO> list = new ArrayList<>();
        for (String mis : user.split(",")) {
            list.add(AppkeyUserSimpleVO.builder().loginName(mis).build());
        }
        return list;
    }
}
