package com.sankuai.avatar.collect.appkey.collector.transfer;

import com.sankuai.avatar.collect.appkey.collector.source.*;
import com.sankuai.avatar.collect.appkey.event.consumer.ScAppkeyConsumerEventData;
import com.sankuai.avatar.resource.appkey.bo.AppkeyResourceUtilBO;
import com.sankuai.avatar.resource.appkey.bo.OpsSrvBO;
import com.sankuai.avatar.resource.appkey.bo.ScAppkeyBO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jie.li.sh
 * @create 2022-11-18
 **/
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface CollectorDataTransfer {

    /**
     * 转换器
     */
    CollectorDataTransfer INSTANCE = Mappers.getMapper(CollectorDataTransfer.class);

    /**
     * OPS数据转换为source对象
     *
     * @param opsSrvBO 源数据
     * @return {@link OpsAppkeySource}
     */
    @Mapping(source="createAt", target = "createTime")
    @Mapping(source="key", target = "srv")
    @Mapping(source = "key", target = "pdl", qualifiedByName = "fromSrvKeyParseToPdl")
    @Mapping(source = "key", target = "owt", qualifiedByName = "fromSrvKeyParseToOwt")
    OpsAppkeySource toSource(OpsSrvBO opsSrvBO);

    /**
     * SC数据转换为source对象
     *
     * @param scAppkeyBO 源数据
     * @return {@link ScAppkeySource}
     */
    @Mapping(source = "admin", target = "admin", qualifiedByName = "fromAdminParseToMis")
    @Mapping(source = "team", target = "orgId", qualifiedByName = "fromTeamParseToId")
    @Mapping(source = "team", target = "orgName", qualifiedByName = "fromTeamParseToName")
    @Mapping(target = "billingUnitId", expression = "java(String.valueOf(scAppkeyBO.getBillingUnitId()))")
    @Mapping(target = "categories", expression = "java(String.join(\",\", scAppkeyBO.getCategories()))")
    @Mapping(target = "tags", expression = "java(String.join(\",\", scAppkeyBO.getTags()))")
    @Mapping(target = "appkey", source = "appKey")
    @Mapping(target = "frameworks", expression = "java(String.join(\",\", scAppkeyBO.getFrameworks()))")
    ScAppkeySource toSource(ScAppkeyBO scAppkeyBO);

    /**
     * SC非后端类型服务数据转换为source对象
     *
     * @param scAppkeyBO 源数据
     * @return {@link ScNotBackendAppkeySource}
     */
    @Mapping(source = "admin", target = "admin", qualifiedByName = "fromAdminParseToMis")
    @Mapping(source = "team", target = "orgId", qualifiedByName = "fromTeamParseToId")
    @Mapping(source = "team", target = "orgName", qualifiedByName = "fromTeamParseToName")
    @Mapping(target = "billingUnitId", expression = "java(String.valueOf(scAppkeyBO.getBillingUnitId()))")
    @Mapping(target = "categories", expression = "java(String.join(\",\", scAppkeyBO.getCategories()))")
    @Mapping(target = "tags", expression = "java(String.join(\",\", scAppkeyBO.getTags()))")
    @Mapping(target = "appkey", source = "appKey")
    @Mapping(target = "frameworks", expression = "java(String.join(\",\", scAppkeyBO.getFrameworks()))")
    ScNotBackendAppkeySource toScNotBackendAppkeySource(ScAppkeyBO scAppkeyBO);

    /**
     * SC非后端类型服务数据转换为source对象
     *
     * @param scAppkeySource 源数据
     * @return {@link ScNotBackendAppkeySource}
     */
    ScNotBackendAppkeySource toScSource(ScAppkeySource scAppkeySource);

    /**
     * SC消费体数据转换为source对象
     *
     * @param scAppkeyConsumerEventData 源数据
     * @return {@link ScAppkeySource}
     */
    @Mapping(target = "admin", expression = "java(scAppkeyConsumerEventData.getAdmin().getMis())")
    @Mapping(target = "orgId", expression = "java(scAppkeyConsumerEventData.getTeam().getId())")
    @Mapping(target = "orgName", expression = "java(scAppkeyConsumerEventData.getTeam().getDisplayName())")
    @Mapping(target = "billingUnitId", expression = "java(String.valueOf(scAppkeyConsumerEventData.getBillingUnitId()))")
    @Mapping(target = "categories", expression = "java(String.join(\",\", scAppkeyConsumerEventData.getCategories()))")
    @Mapping(target = "tags", expression = "java(String.join(\",\", scAppkeyConsumerEventData.getTags()))")
    @Mapping(target = "appkey", source = "appKey")
    @Mapping(target = "frameworks", ignore = true)
    ScAppkeySource toSource(ScAppkeyConsumerEventData scAppkeyConsumerEventData);

    /**
     * DOM数据转换为source对象
     *
     * @param appkeyResourceUtilBO 源数据
     * @return {@link DomAppkeySource}
     */
    DomAppkeySource toSource(AppkeyResourceUtilBO appkeyResourceUtilBO);

    /**
     * 从srv解析出pdl
     *
     * @param srv srv服务树节点
     * @return {@link String}
     */
    @Named("fromSrvKeyParseToPdl")
    default String fromSrvKeyParseToPdl(String srv) {
        if (StringUtils.isBlank(srv)) {
            return "";
        }
        return Stream.of(srv.split("\\.")).limit(3).collect(Collectors.joining("."));
    }

    /**
     * 从srv解析出owt
     *
     * @param srv srv服务树节点
     * @return {@link String}
     */
    @Named("fromSrvKeyParseToOwt")
    default String fromSrvKeyParseToOwt(String srv) {
        if (StringUtils.isBlank(srv)) {
            return "";
        }
        return Stream.of(srv.split("\\.")).limit(2).collect(Collectors.joining("."));
    }

    /**
     * 解析出管理员MIS
     *
     * @param admin 管理员
     * @return {@link String}
     */
    @Named("fromAdminParseToMis")
    default String fromAdminParseToMis(ScAppkeyBO.Admin admin) {
        if (admin == null) {
            return "";
        }
        return admin.getMis();
    }

    /**
     * 从团队信息中解析id
     *
     * @param team 团队
     * @return {@link String}
     */
    @Named("fromTeamParseToId")
    default String fromTeamParseToId(ScAppkeyBO.Team team) {
        if (team == null) {
            return "";
        }
        return String.join(",", Arrays.asList(team.getOrgIdList().split("/")));
    }

    /**
     * 从团队信息中解析名称
     *
     * @param team 团队
     * @return {@link String}
     */
    @Named("fromTeamParseToName")
    default String fromTeamParseToName(ScAppkeyBO.Team team) {
        if (team == null) {
            return "";
        }
        return team.getDisplayName();
    }
}
