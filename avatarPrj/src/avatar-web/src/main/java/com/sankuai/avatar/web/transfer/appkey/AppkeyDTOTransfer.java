package com.sankuai.avatar.web.transfer.appkey;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.appkey.bo.*;
import com.sankuai.avatar.resource.appkey.request.AppkeyRequestBO;
import com.sankuai.avatar.resource.appkey.request.AppkeySearchRequestBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeSrvBO;
import com.sankuai.avatar.resource.tree.request.SrvQueryRequestBO;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.web.dto.appkey.*;
import com.sankuai.avatar.web.request.AppkeyQueryPageRequest;
import com.sankuai.avatar.web.request.AppkeySearchPageRequest;
import com.sankuai.avatar.web.request.AppkeyTreeQueryRequest;
import com.sankuai.avatar.web.vo.appkey.AppkeyDetailVO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-14 14:16
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyDTOTransfer {

    AppkeyDTOTransfer INSTANCE = Mappers.getMapper(AppkeyDTOTransfer.class);

    /**
     * userBO -> AdminUserDTO
     *
     * @param userBO userBO
     * @return {@link AppkeyUserDTO}
     */
    @Named("toAdminUserDTO")
    @Mapping(source = "mis", target = "loginName")
    @Mapping(source = "organization", target = "orgName")
    @Mapping(source = "userImage", target = "avatarUrl")
    AppkeyUserDTO toAdminUserDTO(UserBO userBO);

    /**
     * batch userBO -> AdminUserDTO
     *
     * @param userBOList userBOList
     * @return {@link AppkeyUserDTO}
     */
    @IterableMapping(qualifiedByName = "toAdminUserDTO")
    List<AppkeyUserDTO> toAdminUserDTOList(List<UserBO> userBOList);

    /**
     * appkeyTreeSrvBO -> AppkeyDetailDTO
     *
     * @param appkeyTreeSrvBO appkeyTreeSrvBO
     * @return {@link AppkeyDetailDTO}
     */
    @Mapping(target = "rdAdmin", ignore = true)
    @Mapping(target = "epAdmin", ignore = true)
    @Mapping(target = "opAdmin", ignore = true)
    AppkeyDetailDTO toAppkeyDetailDTO(AppkeyTreeSrvBO appkeyTreeSrvBO);

    /**
     * scAppkeyDTO -> AppkeyDetailDTO
     *
     * @param scAppkeyDTO scAppkeyDTO
     * @return {@link AppkeyDetailDTO}
     */
    @Mapping(target = "admin", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(source = "description", target = "comment")
    @Mapping(source = "billingUnit", target = "billingUnitName")
    @Mapping(source = "isBoughtExternal", target = "isBoughtExternal", qualifiedByName = "parseBoughtExternal")
    AppkeyDetailDTO toAppkeyDetailDTO(ScAppkeyDTO scAppkeyDTO, @MappingTarget AppkeyDetailDTO appkeyDetailDTO);

    /**
     * DTO -> VO
     *
     * @param appkeyDTO appkeyDTO
     * @return {@link AppkeyDetailVO}
     */
    @Mapping(source = "rdAdmin", target = "rdAdmin", qualifiedByName = "toUserDTOList")
    @Mapping(source = "epAdmin", target = "epAdmin", qualifiedByName = "toUserDTOList")
    @Mapping(source = "opAdmin", target = "opAdmin", qualifiedByName = "toUserDTOList")
    @Mapping(source = "admin", target = "admin", qualifiedByName = "toUserDTO")
    @Mapping(source = "srv", target = "key")
    @Mapping(source = "tenant", target = "tenant")
    @Mapping(source = "description", target = "comment")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "toList")
    @Mapping(source = "frameworks", target = "frameworks", qualifiedByName = "toList")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "toList")
    @Mapping(source = "billingUnit", target = "billingUnitName")
    @Mapping(source = "createTime", target = "createAt")
    AppkeyDetailDTO toAppkeyDetailDTO(AppkeyDTO appkeyDTO);

    @Named("toTeamDTO")
    default TeamDTO toTeamDTO(AppkeyDTO appkeyDTO){
        return TeamDTO.builder().orgId(appkeyDTO.getOrgId()).orgName(appkeyDTO.getOrgName()).build();
    }

    @Named("toTree")
    default AppkeyDetailDTO.Tree toTree(AppkeyDTO appkeyDTO){
        return AppkeyDetailDTO.Tree.builder()
                .bg(appkeyDTO.getBusinessGroup())
                .owt(AppkeyDetailDTO.TreeData.builder().key(appkeyDTO.getOwt()).name(appkeyDTO.getOwtName()).build())
                .pdl(AppkeyDetailDTO.TreeData.builder().key(appkeyDTO.getPdl()).name(appkeyDTO.getPdlName()).build())
                .build();
    }

    /**
     * 转列表
     *
     * @param src src
     * @return {@link List}<{@link String}>
     */
    @Named("toList")
    default List<String> toList(String src){
        return StringUtils.isNotEmpty(src) ? Arrays.asList(src.split(",")) : Collections.emptyList();
    }

    @Named("toUserDTO")
    default AppkeyUserDTO toUserDTO(String mis){
        return StringUtils.isNotEmpty(mis) ? AppkeyUserDTO.builder().loginName(mis).build() : null;
    }

    @Named("toUserDTOList")
    default List<AppkeyUserDTO> toUserDTOList(String misList){
        if (StringUtils.isEmpty(misList)) {
            return Collections.emptyList();
        }
        List<AppkeyUserDTO> list = new ArrayList<>();
        for (String mis : misList.split(",")) {
            list.add(AppkeyUserDTO.builder().loginName(mis).build());
        }
        return list;
    }

    /**
     * scAppkeyBO -> ScAppkeyDTO
     *
     * @param scAppkeyBO scAppkeyBO
     * @return {@link ScAppkeyDTO}
     */
    ScAppkeyDTO toScAppkeyDTO(ScAppkeyBO scAppkeyBO);

    /**
     * BO -> DTO
     *
     * @param appkeyBO appkeyBO
     * @return {@link AppkeyDTO}
     */
    @Named("toDTO")
    AppkeyDTO toDTO(AppkeyBO appkeyBO);

    /**
     * 批量转换 BO -> DTO
     *
     * @param boList boList
     * @return {@link List}<{@link AppkeyDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<AppkeyDTO> toDTOList(List<AppkeyBO> boList);

    /**
     * 页码转换 BO -> DTO
     *
     * @param boList boList
     * @return {@link List}<{@link AppkeyDTO}>
     */
    @Named("toAppkeyDTOPageResponse")
    PageResponse<AppkeyDTO> toAppkeyDTOPageResponse(PageResponse<AppkeyBO> boList);

    /**
     * request转换
     *
     * @param pageRequest pageRequest
     * @return {@link AppkeyRequestBO}
     */
    @Named("toRequestBO")
    AppkeyRequestBO toRequestBO(AppkeyQueryPageRequest pageRequest);

    /**
     * request转换
     *
     * @param pageRequest pageRequest
     * @return {@link AppkeySearchRequestBO}
     */
    @Named("toSearchRequestBO")
    AppkeySearchRequestBO toSearchRequestBO(AppkeySearchPageRequest pageRequest);

    /**
     * request转换
     *
     * @param appkeyTreeQueryRequest appkeyTreeQueryRequest
     * @return {@link SrvQueryRequestBO}
     */
    @Named("toSrvQueryRequestBO")
    SrvQueryRequestBO toSrvQueryRequestBO(AppkeyTreeQueryRequest appkeyTreeQueryRequest);

    /**
     * request转换
     *
     * @param pageResponse PageResponse<ScIsoltAppkeyBO>
     * @return {@link PageResponse<IsoltAppkeyDTO>}
     */
    @Named("toScIsoltAppkeyDTOPageResponse")
    PageResponse<IsoltAppkeyDTO> toScIsoltAppkeyDTOPageResponse(PageResponse<ScIsoltAppkeyBO> pageResponse);

    /**
     * 转换为ElasticTipDTO
     *
     * @param elasticTip 弹性提示
     * @return {@link ElasticTipBO}
     */
    ElasticTipDTO toElasticTipDTO(ElasticTipBO elasticTip);

    /**
     * 转换为AppkeyFlowDTO PageResponse
     *
     * @param appkeyFlowPageResponse appkeyFlowPageResponse
     * @return {@link PageResponse}<{@link AppkeyFlowBO}>
     */
    PageResponse<AppkeyFlowDTO> toAppkeyFlowDTOPageResponse(PageResponse<AppkeyFlowBO> appkeyFlowPageResponse);

    /**
     * 外采信息展示
     *
     * @param isBoughtExternal 外采信息
     * @return {@link String}
     */
    @Named("parseBoughtExternal")
    default String parseBoughtExternal(String isBoughtExternal) {
        if (StringUtils.isBlank(isBoughtExternal)) {
            return "UNCERTAIN";
        }
        return isBoughtExternal;
    }

    /**
     * 转换为AppkeyBillingUnitDTO
     *
     * @param billingUnit 计费单位
     * @return {@link AppkeyBillingUnitDTO}
     */
    AppkeyBillingUnitDTO toAppkeyBillingUnitDTO(BillingUnitBO billingUnit);
}
