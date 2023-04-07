package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.common.exception.SupportErrorException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.PageHelperUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.OpsSrvBO;
import com.sankuai.avatar.resource.host.HostResource;
import com.sankuai.avatar.resource.host.bo.DayuGroupTagBO;
import com.sankuai.avatar.resource.host.bo.HostAttributesBO;
import com.sankuai.avatar.resource.host.bo.HostBO;
import com.sankuai.avatar.resource.host.bo.IdcMetaDataBO;
import com.sankuai.avatar.resource.host.request.GroupTagQueryRequestBO;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import com.sankuai.avatar.web.dto.host.*;
import com.sankuai.avatar.web.request.GroupTagQueryRequest;
import com.sankuai.avatar.web.request.HostQueryRequest;
import com.sankuai.avatar.web.service.HostService;
import com.sankuai.avatar.web.transfer.host.HostTransfer;
import com.sankuai.avatar.web.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 主机Service
 * @author qinwei05
 */
@Slf4j
@Service
public class HostServiceImpl implements HostService {

    private final HostResource hostResource;
    private final AppkeyResource appkeyResource;

    public HostServiceImpl(HostResource hostResource, AppkeyResource appkeyResource) {
        this.hostResource = hostResource;
        this.appkeyResource = appkeyResource;
    }

    @Override
    public PageResponse<HostAttributesDTO> getSrvHostsByQueryRequest(HostQueryRequest request) throws SdkCallException, SdkBusinessErrorException {

        // 获取请求参数
        Integer page = request.getPage();
        Integer pageSize = request.getPageSize();
        String env = request.getEnv();
        String appkey = request.getAppkey();
        HostQueryRequestBO requestBO = reBuilderHostQueryRequest(request);

        // 获取主机信息
        List<HostAttributesBO> hostAttributesBOList = hostResource.getSrvHostsByQueryRequest(requestBO);

        // 仅处理当前页数据(补充资源池、磁盘等属性)
        PageResponse<HostAttributesBO> hostBOPageResponse = PageHelperUtils.toPageResponse(page, pageSize, hostAttributesBOList);
        List<HostAttributesBO> pageHostBOList = hostBOPageResponse.getItems();
        if (StringUtils.isBlank(request.getOriginType())){
            pageHostBOList = hostResource.patchHostTagsAndFeatures(pageHostBOList, env);
        }
        List<HostAttributesBO> patchedHostBOList = hostResource.patchVmHostDiskType(pageHostBOList, appkey, env);
        List<HostAttributesDTO> hostAttributesDTOList = HostTransfer.INSTANCE.toHostAttributesDTOList(patchedHostBOList);

        // 分页
        PageResponse<HostAttributesDTO> response = new PageResponse<>();
        response.setPage(page);
        response.setPageSize(pageSize);
        response.setTotalCount(hostBOPageResponse.getTotalCount());
        response.setTotalPage(hostBOPageResponse.getTotalPage());
        response.setItems(hostAttributesDTOList);
        return response;
    }

    private HostQueryRequestBO reBuilderHostQueryRequest(HostQueryRequest request) {
        String appkey = request.getAppkey();
        HostQueryRequestBO requestBO = HostTransfer.INSTANCE.toHostQueryRequestBO(request);
        // 获取Appkey对应的Srv
        String srv = appkeyResource.getSrvKeyByAppkey(appkey);
        if (StringUtils.isBlank(srv)) {
            throw new SupportErrorException(String.format("Appkey %s 无对应服务树节点，无法查看主机信息！！！", appkey));
        }
        requestBO.setSrv(srv);
        return requestBO;
    }

    @Override
    public HostCountDTO getHostsCountByQueryRequest(HostQueryRequest request) {
        List<HostAttributesDTO> hosts = getOriginSrvHostsByQueryRequest(request);
        // 统计各个环境的机器数目
        Map<String, Long> countsByEnv = hosts.stream()
                .filter(host -> StringUtils.isNotBlank(host.getEnv())
                        && Arrays.asList("prod", "staging", "test", "dev").contains(host.getEnv()))
                .collect(Collectors.groupingBy(HostAttributesDTO::getEnv, Collectors.counting()));
        return HostCountDTO.builder()
                .prod(countsByEnv.getOrDefault("prod", 0L).intValue())
                .staging(countsByEnv.getOrDefault("staging", 0L).intValue())
                .test(countsByEnv.getOrDefault("test", 0L).intValue())
                .dev(countsByEnv.getOrDefault("dev", 0L).intValue())
                .build();
    }

    @Override
    public List<HostAttributesDTO> getOriginSrvHostsByQueryRequest(HostQueryRequest request) {
        // 从OPS获取原始的服务树机器数据
        HostQueryRequestBO requestBO = reBuilderHostQueryRequest(request);
        List<HostAttributesBO> hostAttributesBOList = hostResource.getSrvHostsByQueryRequest(requestBO);
        return HostTransfer.INSTANCE.toHostAttributesDTOList(hostAttributesBOList);
    }

    @Override
    public HostDTO getHostInfo(String name) throws SdkCallException, SdkBusinessErrorException {
        HostBO hostInfo = hostResource.getHostInfo(name);
        return HostTransfer.INSTANCE.toHostDTO(hostInfo);
    }

    @Override
    public List<HostCellDTO> getHostCellByQueryRequest(HostQueryRequest request) {
        List<HostAttributesDTO> hosts = getOriginSrvHostsByQueryRequest(request);
        // Appkey下Set信息(包含主干道)
        return hosts.stream()
                .map(HostAttributesDTO::getCell)
                .distinct()
                .map(cell -> StringUtils.isBlank(cell)
                        ? HostCellDTO.builder().label("主干道").value("-").build()
                        : HostCellDTO.builder().label(cell).value(cell).build())
                .sorted(Comparator.comparing(HostCellDTO::getLabel))
                .collect(Collectors.toList());
    }

    @Override
    public HostSumAttributeDTO getHostSumAttribute(HostQueryRequest request) {
        List<HostAttributesDTO> hosts = getSrvHostsWithTagByQueryRequest(request);
        // 依次汇总机器属性
        return HostSumAttributeDTO.builder()
                .managePlat(getHostSumAttributeTitle(hosts, HostAttributesDTO::getManagePlat, null))
                .netType(getHostSumAttributeTitle(hosts, HostAttributesDTO::getNetType, null))
                .cell(getHostSumAttributeTitle(hosts, HostAttributesDTO::getCell, true))
                .idcName(getHostSumAttributeTitle(hosts, HostAttributesDTO::getIdcName, null))
                .kindName(getHostSumAttributeTitle(hosts, HostAttributesDTO::getKindName, null))
                .originType(getHostSumAttributeTitle(hosts, HostAttributesDTO::getOriginType, null))
                .swimlane(getHostSumAttributeTitle(hosts, HostAttributesDTO::getSwimlane, true))
                .grouptags(getHostSumAttributeTitle(hosts, HostAttributesDTO::getGrouptags, null))
                .build();
    }

    private List<HostAttributesDTO> getSrvHostsWithTagByQueryRequest(HostQueryRequest request) {
        // OPS机器数据（携带Tag标签与机器宿主机特性）
        String env = request.getEnv();
        HostQueryRequestBO requestBO = reBuilderHostQueryRequest(request);
        List<HostAttributesBO> hostAttributesBOList = hostResource.getSrvHostsByQueryRequest(requestBO);

        try {
            List<HostAttributesBO> patchedHostBOList = hostResource.patchHostTagsAndFeatures(hostAttributesBOList, env);
            return HostTransfer.INSTANCE.toHostAttributesDTOList(patchedHostBOList);
        } catch (SdkCallException |SdkBusinessErrorException ignored) {
            // SDK异常时忽略，不影响主逻辑
        }

        return HostTransfer.INSTANCE.toHostAttributesDTOList(hostAttributesBOList);
    }

    /**
     * 汇总主机属性
     *
     * @param hosts        主机
     * @param fieldName    字段名
     * @param defaultValue 默认项
     * @return {@link List}<{@link HostSumAttributeDTO.Title}>
     */
    private List<HostSumAttributeDTO.Title> getHostSumAttributeTitle(List<HostAttributesDTO> hosts,
                                                                     Function<HostAttributesDTO, Object> fieldName,
                                                                     Boolean defaultValue) {
        // 默认配置值
        HostSumAttributeDTO.Title defaultItem = HostSumAttributeDTO.Title.builder()
                .text("主干道")
                .value("-")
                .build();
        // 按照fieldName汇总属性信息
        List<HostSumAttributeDTO.Title> titleList = hosts.stream()
                .map(fieldName)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .distinct()
                .map(c -> {
                    int count = (int) hosts.stream().filter(dto -> fieldName.apply(dto).equals(c)).count();
                    String text = String.format("%s(%d)", c, count);
                    return HostSumAttributeDTO.Title.builder().value(c).text(text).build();
                })
                .sorted(Comparator.comparing(HostSumAttributeDTO.Title::getValue))
                .collect(Collectors.toList());
        // 默认值放在最前面
        if (!titleList.isEmpty() && Boolean.TRUE.equals(defaultValue)) {
            titleList.add(0, defaultItem);
        }
        return titleList;
    }

    @Override
    public HostIdcDistributedDTO getAppkeyHostDistributed(HostQueryRequest request) {
        List<HostAttributesDTO> hosts = getOriginSrvHostsByQueryRequest(request);

        // 统计各IDC的主机数量
        Map<String, Long> idcHostCountMap = hosts.stream()
                .filter(host -> StringUtils.isNotEmpty(host.getIdc()))
                .collect(Collectors.groupingBy(HostAttributesDTO::getIdc, Collectors.counting()));

        // 组装IDC列表
        List<IdcMetaDataBO> ecsIdcList = hostResource.getIdcList();
        List<IdcDTO> idcList = ecsIdcList.stream()
                .filter(idc -> idcHostCountMap.containsKey(idc.getIdc()))
                .map(idc -> IdcDTO.builder()
                        .name(idc.getIdc())
                        .cnName(idc.getIdcName())
                        .region(idc.getRegion())
                        .desc(idc.getDesc())
                        .count(idcHostCountMap.get(idc.getIdc()).intValue())
                        .build()
                )
                .sorted(Comparator.comparing(IdcDTO::getRegion))
                .distinct()
                .collect(Collectors.toList());

        // 组装返回结果
        HostIdcDistributedDTO hostIdcDistributedDTO = new HostIdcDistributedDTO();
        hostIdcDistributedDTO.setTotal(hosts.size());
        hostIdcDistributedDTO.setIdcList(idcList);
        hostIdcDistributedDTO.setIdcTotal(idcList.size());

        return hostIdcDistributedDTO;
    }

    @Override
    public List<IdcMetaDataDTO> getIdc() {
        List<IdcMetaDataBO> ecsIdcList = hostResource.getIdcList();
        return HostTransfer.INSTANCE.batchToIdcDTO(ecsIdcList);
    }

    @Override
    public ExternalHostDTO getExternalHostInfo(String name) throws SdkCallException, SdkBusinessErrorException {
        HostDTO hostDTO;
        String mis = UserUtils.getCurrentCasUser().getLoginName();
        try {
            hostDTO = getHostInfo(name);
        } catch (SdkCallException |SdkBusinessErrorException ignored) {
            // 此处业务逻辑异常允许直接降级，检索不到即为空
            return ExternalHostDTO.builder().host(null).canDelete(false).canUpdate(false).build();
        }
        if (Arrays.asList("it", "manual").contains(hostDTO.getVendor())) {
            String hostName = hostDTO.getName();
            String appkey = appkeyResource.getByHost(hostName);
            hostDTO.setAppkey(appkey);
            if (StringUtils.isNotBlank(appkey)) {
                OpsSrvBO opsSrvBO = appkeyResource.getAppkeyByOps(appkey);
                Boolean permission = opsSrvBO.getRdAdmin().contains(mis) || opsSrvBO.getOpAdmin().contains(mis);
                return ExternalHostDTO.builder().host(hostDTO).canDelete(permission).canUpdate(permission).build();
            }
        }
        return ExternalHostDTO.builder().host(null).canDelete(false).canUpdate(false).build();
    }

    @Override
    public List<HostDTO> getExternalParentHostInfo(String name) throws SdkCallException, SdkBusinessErrorException {
        try {
            HostDTO hostDTO = getHostInfo(name);
            if (Arrays.asList("it", "manual").contains(hostDTO.getVendor())) {
                return Collections.singletonList(hostDTO);
            }
        } catch (SdkCallException |SdkBusinessErrorException ignored) {
            // 此处业务逻辑异常允许直接降级，检索不到即为空
        }
        return Collections.emptyList();
    }

    @Override
    public List<GroupTagDTO> getGrouptags(GroupTagQueryRequest request) {
        String appkey = request.getAppkey();
        String env = request.getEnv();
        // appkey与owt同时存在时，以appkey所在owt为准
        if (StringUtils.isNotBlank(appkey)) {
            String srv = appkeyResource.getSrvKeyByAppkey(appkey);
            String appkeyOwt = Stream.of(srv.split("\\.")).limit(2).collect(Collectors.joining("."));
            request.setOwt(appkeyOwt);
        }
        if (StringUtils.isBlank(request.getOwt())) {
            throw new SupportErrorException("获取业务分组信息时，owt信息不能为空！");
        }
        // 获取Dayu业务分组信息
        GroupTagQueryRequestBO groupTagQueryRequestBO = HostTransfer.INSTANCE.toGroupTagQueryRequestBO(request);
        List<DayuGroupTagBO> dayuGroupTagBOList = hostResource.getGrouptags(groupTagQueryRequestBO);
        if (CollectionUtils.isEmpty(dayuGroupTagBOList)){
            return Collections.emptyList();
        }
        // 统计各个Grouptags的机器数目
        List<HostAttributesDTO> hosts = Optional.ofNullable(appkey)
                .map(key -> {
                    HostQueryRequest hostQueryRequest = new HostQueryRequest();
                    hostQueryRequest.setAppkey(key);
                    hostQueryRequest.setEnv(env);
                    return getOriginSrvHostsByQueryRequest(hostQueryRequest);
                })
                .orElse(Collections.emptyList());

        Map<String, Long> countsByGrouptagsMap = hosts.stream()
                .filter(host -> StringUtils.isNotBlank(host.getGrouptags()))
                .collect(Collectors.groupingBy(HostAttributesDTO::getGrouptags, Collectors.counting()));

        return dayuGroupTagBOList.stream()
                .map(dayuGroupTagBO -> {
                    GroupTagDTO groupTagDTO = new GroupTagDTO();
                    groupTagDTO.setHostCount(countsByGrouptagsMap.getOrDefault(dayuGroupTagBO.getGroupTags(), 0L));
                    groupTagDTO.setGroupTagsName(String.format("%s__%s", dayuGroupTagBO.getGroupTagsName(), dayuGroupTagBO.getGroupTags()));
                    return groupTagDTO;
                })
                .collect(Collectors.toList());
    }
}
