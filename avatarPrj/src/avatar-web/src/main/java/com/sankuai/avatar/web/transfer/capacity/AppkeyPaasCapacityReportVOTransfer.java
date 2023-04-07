package com.sankuai.avatar.web.transfer.capacity;

import com.sankuai.avatar.web.dto.capacity.AppkeyPaasCapacityDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasClientDTO;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasStandardClientDTO;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityReportVO;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityReportVO.ClientVersion;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityReportVO.ClientSdkVersion;
import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.*;

/**
 * @author caoyang
 * @create 2022-10-28 19:29
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public abstract class AppkeyPaasCapacityReportVOTransfer {

    public static AppkeyPaasCapacityReportVOTransfer INSTANCE = Mappers.getMapper(AppkeyPaasCapacityReportVOTransfer.class);

    /**
     * reportVO -> AppkeyPaasCapacityDTOList
     *
     * @param reportVO 容灾上报对象
     * @return {@link List}<{@link AppkeyPaasCapacityDTO}>
     */
    public List<AppkeyPaasCapacityDTO> toAppkeyPaasCapacityDTOList(AppkeyPaasCapacityReportVO reportVO){
        List<String> clientAppkeys = reportVO.getClientAppkey();
        if (CollectionUtils.isEmpty(clientAppkeys)) {
            clientAppkeys = Collections.singletonList("");
        }
        return toAppkeyPaasCapacityDTOList(clientAppkeys, reportVO);
    }

    /**
     * reportVO -> AppkeyPaasClientDTOList
     *
     * @param reportVO 容灾上报对象
     * @return {@link List}<{@link AppkeyPaasClientDTO}>
     */
    public List<AppkeyPaasClientDTO> toAppkeyPaasClientDTOList(AppkeyPaasCapacityReportVO reportVO){
        List<ClientSdkVersion> clientSdkVersionList = reportVO.getClientSdkVersion();
        if (CollectionUtils.isEmpty(clientSdkVersionList)) {
            return Collections.emptyList();
        }
        List<AppkeyPaasStandardClientDTO> standardClientDTOList = toAppkeyPaasStandardClientDTOList(reportVO);
        List<AppkeyPaasClientDTO> appkeyPaasClientDTOList = new ArrayList<>();
        for (ClientSdkVersion clientSdkVersion : clientSdkVersionList) {
            List<AppkeyPaasClientDTO> paasClientDTOList = toAppkeyPaasClientDTOList(clientSdkVersion.getItems(), reportVO);
            paasClientDTOList.forEach(appkeyPaasClientDTO -> appkeyPaasClientDTO.setAppkey(clientSdkVersion.getClientAppkey()));
            appkeyPaasClientDTOList.addAll(paasClientDTOList);
        }
        polishAppkeyPaasClientDTO(standardClientDTOList, appkeyPaasClientDTOList);
        return appkeyPaasClientDTOList;
    }

    /**
     * reportVO -> AppkeyPaasStandardClientDTOList
     *
     * @param reportVO 容灾上报对象
     * @return {@link List}<{@link AppkeyPaasStandardClientDTO}>
     */
    public List<AppkeyPaasStandardClientDTO> toAppkeyPaasStandardClientDTOList(AppkeyPaasCapacityReportVO reportVO){
        List<ClientVersion> standardVersionList = reportVO.getStandardVersion();
        if (CollectionUtils.isEmpty(standardVersionList)) {
            return Collections.emptyList();
        }
        return toAppkeyPaasStandardClientDTOList(standardVersionList, reportVO);
    }

    /**
     * report -> appkeyPaasCapacityDTOList
     * @param clientAppkeyList 上报的业务 appkey列表
     * @param reportVO reportVO
     * @return List<AppkeyPaasCapacityDTO>
     */
    protected abstract List<AppkeyPaasCapacityDTO> toAppkeyPaasCapacityDTOList(List<String> clientAppkeyList,
                                                                               @Context AppkeyPaasCapacityReportVO reportVO);

    public AppkeyPaasCapacityDTO toAppkeyPaasCapacityDTO(String appkey, @Context AppkeyPaasCapacityReportVO reportVO){
        AppkeyPaasCapacityDTO appkeyPaasCapacityDTO = toAppkeyPaasCapacityDTO(reportVO);
        appkeyPaasCapacityDTO.setAppkey(appkey);
        return appkeyPaasCapacityDTO;
    }

    @Mapping(target = "clientConfig", expression = "java(reportVO.convertClientConfig(reportVO.getClientConfig()))")
    @Mapping(target = "standardConfig", expression = "java(reportVO.convertClientConfig(reportVO.getStandardConfig()))")
    protected abstract AppkeyPaasCapacityDTO toAppkeyPaasCapacityDTO(AppkeyPaasCapacityReportVO reportVO);

    /**
     * report -> AppkeyPaasClientDTOList
     *
     * @param clientVersionList 客户端sdk版本列表
     * @param reportVO             reportVO
     * @return {@link List}<{@link AppkeyPaasClientDTO}>
     */
    protected abstract List<AppkeyPaasClientDTO> toAppkeyPaasClientDTOList(List<ClientVersion> clientVersionList,
                                                                           @Context AppkeyPaasCapacityReportVO reportVO);

    public AppkeyPaasClientDTO toAppkeyPaasClientDTO(ClientVersion clientVersion, @Context AppkeyPaasCapacityReportVO reportVO){
        AppkeyPaasClientDTO appkeyPaasClientDTO = toAppkeyPaasClientDTO(reportVO);
        appkeyPaasClientDTO.setLanguage(clientVersion.getLanguage());
        appkeyPaasClientDTO.setGroupId(clientVersion.getGroupId());
        appkeyPaasClientDTO.setArtifactId(clientVersion.getArtifactId());
        appkeyPaasClientDTO.setVersion(clientVersion.getVersion());
        appkeyPaasClientDTO.setIsCapacityStandard(false);
        return appkeyPaasClientDTO;
    }

    @Mapping(target = "language", ignore = true)
    @Mapping(target = "groupId", ignore = true)
    @Mapping(target = "artifactId", ignore = true)
    @Mapping(target = "standardVersion", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "appkey", ignore = true)
    @Mapping(target = "isCapacityStandard", ignore = true)
    protected abstract AppkeyPaasClientDTO toAppkeyPaasClientDTO(AppkeyPaasCapacityReportVO reportVO);

    /**
     * report -> AppkeyPaasStandardClientDTOList
     *
     * @param standardVersionList 标准版本列表
     * @param reportVO            容灾上报
     * @return {@link List}<{@link AppkeyPaasStandardClientDTO}>
     */
    public abstract List<AppkeyPaasStandardClientDTO> toAppkeyPaasStandardClientDTOList(List<ClientVersion> standardVersionList ,
                                                                                        @Context AppkeyPaasCapacityReportVO reportVO);

    public AppkeyPaasStandardClientDTO toAppkeyPaasStandardClientDTO(ClientVersion clientVersion, @Context AppkeyPaasCapacityReportVO reportVO){
        AppkeyPaasStandardClientDTO standardClientDTO = toAppkeyPaasStandardClientDTO(reportVO);
        standardClientDTO.setLanguage(clientVersion.getLanguage());
        standardClientDTO.setGroupId(clientVersion.getGroupId());
        standardClientDTO.setArtifactId(clientVersion.getArtifactId());
        standardClientDTO.setStandardVersion(clientVersion.getVersion());
        return standardClientDTO;
    }

    /**
     * reportVO -> AppkeyPaasStandardClientDTO
     *
     * @param reportVO 容灾上报对象
     * @return {@link AppkeyPaasStandardClientDTO}
     */
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "groupId", ignore = true)
    @Mapping(target = "artifactId", ignore = true)
    @Mapping(target = "standardVersion", ignore = true)
    protected abstract AppkeyPaasStandardClientDTO toAppkeyPaasStandardClientDTO(AppkeyPaasCapacityReportVO reportVO);

    /**
     * appkey客户端信息补齐达标版本
     *
     * @param standardClientDTOList   标准客户端dtolist
     * @param appkeyPaasClientDTOList appkey paas客户端dtolist
     */
    private void polishAppkeyPaasClientDTO(List<AppkeyPaasStandardClientDTO> standardClientDTOList, List<AppkeyPaasClientDTO> appkeyPaasClientDTOList){
        if (CollectionUtils.isEmpty(appkeyPaasClientDTOList)) {
            return;
        }
        Map<String, String> map = new HashMap<>(16);
        for (AppkeyPaasStandardClientDTO standardClientDTO : standardClientDTOList) {
            String key = standardClientDTO.getLanguage() + "_" + standardClientDTO.getGroupId() + "_" + standardClientDTO.getArtifactId();
            map.put(key, standardClientDTO.getStandardVersion());
        }
        for (AppkeyPaasClientDTO appkeyPaasClientDTO : appkeyPaasClientDTOList) {
            String key = appkeyPaasClientDTO.getLanguage() + "_" + appkeyPaasClientDTO.getGroupId() + "_" + appkeyPaasClientDTO.getArtifactId();
            if (map.containsKey(key)) {
                appkeyPaasClientDTO.setStandardVersion(map.get(key));
            }
        }
    }
}
