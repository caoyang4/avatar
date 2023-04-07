package com.sankuai.avatar.web.vo.capacity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableMap;
import com.meituan.servicecatalog.api.annotations.FieldDoc;
import com.meituan.servicecatalog.api.annotations.Requiredness;
import com.meituan.servicecatalog.api.annotations.TypeDoc;
import com.sankuai.avatar.resource.capacity.constant.PaasCapacityType;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasClientDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2022-08-08 15:52
 */
@TypeDoc(
        description = "PaaS上报信息",
        fields = {
                @FieldDoc(name = "paasName", description = "paas名称", rule = "限定为Mafka、RDS、Lion、Eagle等paas组件，如需扩展，联系avatar团队", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "type", description = "Cluster(集群)维度判定，一个Appkey包含一个或多个集群，如 Squirrel；Topic(主题)维度判定，一个Appkey包含多个Topic，如 Mafka；Appkey(服务)维度判定，如 Oceanus", rule = "限定 CLUSTER、TOPIC、APPKEY", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "typeName", description = "容灾维度的实际标识名称，比如集群名、服务名等，后续字段以集群为例说明",  requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "clientRole", description = "容灾实体角色，例如mafka可分为producer/consumer",  requiredness = Requiredness.OPTIONAL),
                @FieldDoc(name = "typeComment", description = "容灾实体信息描述", requiredness = Requiredness.OPTIONAL),
                @FieldDoc(name = "paasAppkey", description = "容灾实体对应的PaaS appkey", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "isCore", description = "服务是否为核心", rule = "布尔值", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "capacityLevel", description = "容灾等级", rule = "限定为[0,5]", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "standardLevel", description = "容灾等级基准值", rule = "限定为[0,5]", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "isCapacityStandard", description = "容灾是否达标", rule = "布尔值", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "standardReason", description = "容灾等级判定原因", rule = "当前容灾等级判定原因描述，和容灾定义保持一致", requiredness = Requiredness.OPTIONAL),
                @FieldDoc(name = "standardTips", description = "容灾达标建议", rule = "容灾提升的建议", requiredness = Requiredness.OPTIONAL),
                @FieldDoc(name = "standardVersion", description = "影响部署容灾的客户端最低版本，分语言、分SDK返回，不涉及此项的则为空list", rule = "clientSdkVersion不为空时，此值不允许为空", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "clientSdkVersion", description = "使用该paas且不满足容灾的客户端实际版本", rule = "如果standardVersion值为空，则此项值为空list", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "clientAppkey", description = "使用该paas的客户端Appkey，不涉及此项则为空list", rule = "clientSdkVersion不为空时，clientAppkey不能为空；clientSdkVersion为clientAppkey的子集", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "clientConfig", description = "影响部署容灾的业务配置项，不涉及此项则为空list", rule = "目前只有Mafka上报，例如：可选参数：has.traffic.cluster: True/False，用于标识实体是否有流量，True为有流量，False为无流量，默认所上报全部为有流量实体", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "standardConfig", description = "业务容灾达标配置，不涉及则为空list", rule = "目前只有Mafka上报，例如：可选参数：has.traffic.cluster: True/False，用于标识实体是否有流量，True为有流量，False为无流量，默认所上报全部为有流量实体", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "isConfigStandard", description = "配置是否达标，不涉及则为true", rule = "布尔值", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "isSet", description = "是否set化，默认false；传true，setName也要传", rule = "布尔值", requiredness = Requiredness.OPTIONAL),
                @FieldDoc(name = "setName", description = "set链路名称，默认空串", rule = "isSet为true时，setName不能为空", requiredness = Requiredness.OPTIONAL),
                @FieldDoc(name = "setType", description = "set 类型（R/G/C），默认空串", rule = "限定R、G、C及其组合", requiredness = Requiredness.OPTIONAL),
                @FieldDoc(name = "owner", description = "业务负责人", rule = "多个使用逗号\",\"分隔", requiredness = Requiredness.REQUIRED),
                @FieldDoc(name = "isWhite", description = "是否是容灾白名单", rule = "是否是容灾白名单，是则传true，如果平台暂不支持白名单功能，传false即可", requiredness = Requiredness.OPTIONAL),
                @FieldDoc(name = "whiteReason", description = "加白原因", requiredness = Requiredness.OPTIONAL),
                @FieldDoc(name = "updateBy", description = "paas容灾信息上报人", requiredness = Requiredness.REQUIRED)
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppkeyPaasCapacityReportVO {

    private Integer id;

    @NotBlank(message = "PaaS名称不能为空")
    private String paasName;

    /**
     * Cluster(集群)维度判定，一个Appkey包含一个或多个集群，如 Squirrel
     * Topic(主题)维度判定，一个Appkey包含多个Topic，如 Mafka
     * Appkey(服务)维度判定，如 Oceanus
     */
    @NotNull(message = "必须指定容灾类型：APPKEY, CLUSTER, TOPIC")
    private PaasCapacityType type;

    /**
     * 容灾维度的实际标识名称，比如集群名、服务名等，后续字段以集群为例说明
     */
    @NotBlank(message = "容灾实体名称不能为空")
    private String typeName;

    /**
     * 容灾实体角色，对于mafka而言，分为producer/consumer
     */
    private String clientRole;

    /**
     * 集群信息描述
     */
    private String typeComment;

    /**
     * 容灾实体对应的PaaS appkey，和typeName对应的Appkey值，
     * 如果typeName本身即AppKey，此值保持一致即可
     */
    @NotBlank(message = "PaaS appkey不能为空")
    private String paasAppkey;

    /**
     * PaaS核心等级，核心true，非核心false，默认为true
     */
    @NotNull(message = "请指定服务是否为核心:true/false")
    private Boolean isCore;

    /**
     * 集群当前的部署容灾等级，0~5之间
     */
    @NotNull(message = "请指定容灾等级，容灾等级限定范围:[0,5]")
    @Min(value = 0,message = "容灾等级限定范围:[0,5]")
    @Max(value = 5, message = "容灾等级限定范围:[0,5]")
    private Integer capacityLevel;

    /**
     * 集群达标的部署容灾等级基准值。最多2个基准值，分别对应核心集群和非核心集群
     */
    @NotNull(message = "请指定容灾达标等级，容灾达标等级限定范围:[0,5]")
    @Min(value = 0,message = "容灾达标等级限定范围:[0,5]")
    @Max(value = 5, message = "容灾达标等级限定范围:[0,5]")
    private Integer standardLevel;

    @NotNull(message = "请指定容灾是否达标:true/false")
    private Boolean isCapacityStandard;

    /**
     * 当前容灾等级判定原因描述，和容灾定义保持一致
     */
    private String standardReason;

    /**
     * 容灾提升的建议，如果满足机房容灾，则此项值为空
     */
    private String standardTips;

    /**
     * 影响部署容灾的客户端最低版本，分语言、分SDK返回，不涉及此项的则为空
     */
    @NotNull(message = "达标客户端版本信息不允许为null，若无相关信息上报，请传空list")
    private List<ClientVersion> standardVersion;

    /**
     * 使用此集群且不满足容灾的客户端实际版本，如果standardVersion值为空，则此项值为空
     */
    @NotNull(message = "客户端信息不允许为null，若无相关信息上报，请传空list")
    private List<ClientSdkVersion> clientSdkVersion;

    /**
     * 使用此集群的客户端Appkey，不涉及此项则为空
     */
    @NotNull(message = "客户端appkey不允许为null，若无相关信息上报，请传空list")
    private List<String> clientAppkey;

    /**
     * 影响部署容灾的业务配置项，不涉及此项则为空
     */
    @NotNull(message = "容灾配置信息不允许为null，若无相关信息上报，请传空list")
    private List<ClientConfig> clientConfig;

    private Boolean isClientStandard;

    /**
     * 业务容灾达标配置；不涉及则为空
     */
    @NotNull(message = "容灾达标配置信息不允许为null，若无相关信息上报，请传空list")
    private List<ClientConfig> standardConfig;

    /**
     * 配置是否达标；不涉及则为空
     */
    private Boolean isConfigStandard;

    /**
     * 集群是否是容灾白名单，是则返回true，如果平台暂不支持白名单功能，则返回false即可
     */
    private Boolean isWhite;

    /**
     * 加白原因
     */
    private String whiteReason;

    /**
     * 是否 set 化
     */
    private Boolean isSet;

    /**
     * set名称
     */
    private String setName;

    /**
     * set类型
     */
    private String setType;

    /**
     * PaaS 更新人
     */
    private String updateBy;

    /**
     * 使用此集群的业务负责人，多个使用逗号","分隔，如果集群（容灾实体）负责人和Appkey负责人一致，可为空
     */
    private String owner;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateDate;

    public void setDefaultValue(){
        if (Objects.isNull(setName)) {setName = "";}
        if (Objects.isNull(setType)) {setType = "";}
        if (Objects.isNull(typeComment)) {typeComment = "";}
        if (Objects.isNull(standardReason)) {standardReason = "";}
        if (Objects.isNull(standardTips)) {standardTips = "";}
        if (Objects.isNull(isWhite)) {isWhite = false;}
        if (Objects.isNull(isSet)) {isSet = false;}
        if (Objects.isNull(isClientStandard)) {isClientStandard = false;}
        if (Objects.isNull(isConfigStandard)) {isConfigStandard = false;}
        if (Objects.isNull(whiteReason)) {whiteReason = "";}
        if (Objects.isNull(updateBy)) {updateBy = "";}
        if (Objects.isNull(clientSdkVersion)) {clientSdkVersion = new ArrayList<>();}
        if (Objects.isNull(standardVersion)) {standardVersion = new ArrayList<>();}
    }

    @TypeDoc(
            description = "客户端不达标的服务及相关版本信息",
            fields = {
                    @FieldDoc(name = "clientAppkey", description = "客户端appkey", requiredness = Requiredness.REQUIRED),
                    @FieldDoc(name = "items", description = "不达标客户端版本列表", requiredness = Requiredness.REQUIRED)
            }
    )
    @Data
    public static class ClientSdkVersion{
        private String clientAppkey;
        List<ClientVersion> items;
    }

    @TypeDoc(
            description = "客户端不达标版本信息",
            fields = {
                    @FieldDoc(name = "language", description = "客户端语言，如java、python、golang、c、c++等", requiredness = Requiredness.REQUIRED),
                    @FieldDoc(name = "groupId", description = "版本groupId，java客户端需上报", requiredness = Requiredness.OPTIONAL),
                    @FieldDoc(name = "artifactId", description = "版本artifactId，java客户端需上报", requiredness = Requiredness.OPTIONAL),
                    @FieldDoc(name = "version", description = "版本", requiredness = Requiredness.OPTIONAL),
            }
    )
    @Data
    public static class ClientVersion{
        private String language;
        private String groupId;
        private String artifactId;
        private String version;
    }

    @TypeDoc(
            description = "容灾配置",
            fields = {
                    @FieldDoc(name = "key", description = "配置项", requiredness = Requiredness.REQUIRED),
                    @FieldDoc(name = "value", description = "配置值", requiredness = Requiredness.REQUIRED)
            }
    )
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientConfig{
        private String key;
        private String value;
    }


    /**
     * @param clientConfigs 客户端配置
     * @return {@link List}<{@link Map}<{@link String}, {@link String}>>
     */
    public List<Map<String, String>> convertClientConfig(List<ClientConfig> clientConfigs){
        if (CollectionUtils.isEmpty(clientConfigs)) {
            return new ArrayList<>();
        }
        return clientConfigs.stream().map(
                clientConfig -> ImmutableMap.of(clientConfig.getKey(), clientConfig.getValue())
                ).collect(Collectors.toList());
    }


    /**
     * 查询paas容灾，补齐客户端信息
     * @param appkeyPaasClientDTO
     */
    public void setClientSdkVersionByClientDTO(AppkeyPaasClientDTO appkeyPaasClientDTO){
        if (Objects.isNull(appkeyPaasClientDTO)) {
            return;
        }
        ClientSdkVersion sdkVersion = new ClientSdkVersion();
        sdkVersion.setClientAppkey(appkeyPaasClientDTO.getAppkey());
        ClientVersion version = new ClientVersion();
        version.setVersion(appkeyPaasClientDTO.getVersion());
        version.setLanguage(appkeyPaasClientDTO.getLanguage());
        version.setGroupId(appkeyPaasClientDTO.getGroupId());
        version.setArtifactId(appkeyPaasClientDTO.getArtifactId());
        sdkVersion.setItems(Collections.singletonList(version));
        setClientSdkVersion(Collections.singletonList(sdkVersion));
        ClientVersion standardVersion = new ClientVersion();
        standardVersion.setVersion(appkeyPaasClientDTO.getStandardVersion());
        standardVersion.setLanguage(appkeyPaasClientDTO.getLanguage());
        standardVersion.setGroupId(appkeyPaasClientDTO.getGroupId());
        standardVersion.setArtifactId(appkeyPaasClientDTO.getArtifactId());
        setStandardVersion(Collections.singletonList(standardVersion));
    }
}
