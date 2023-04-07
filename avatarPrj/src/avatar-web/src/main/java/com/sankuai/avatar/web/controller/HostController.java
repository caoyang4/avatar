package com.sankuai.avatar.web.controller;

import com.jayway.jsonpath.TypeRef;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.host.*;
import com.sankuai.avatar.web.request.GroupTagQueryRequest;
import com.sankuai.avatar.web.request.HostQueryRequest;
import com.sankuai.avatar.web.service.HostService;
import com.sankuai.avatar.web.transfer.host.HostVOTransfer;
import com.sankuai.avatar.web.vo.host.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 主机
 *
 * @author qinwei05
 */
@RestController
@RequestMapping("/api/v2/avatar/host")
public class HostController {

    private final HostService hostService;

    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    /**
     * 机器下线原因
     */
    @MdpConfig("OFFLINE_REASON:[\n" +
            "  \"资源利用率较低\",\n" +
            "  \"研发/测试环境释放资源\",\n" +
            "  \"配置不足\",\n" +
            "  \"环境/配置错误\",\n" +
            "  \"机器异常\",\n" +
            "  \"服务下线\",\n" +
            "  \"紧急资源归还\",\n" +
            "  \"活动资源归还\",\n" +
            "  \"其他\"\n" +
            "]")
    private String offlineReason;

    /**
     * 机器表头
     */
    @MdpConfig("HOST_CONFIG_TITLE:[]")
    private String hostTitleConfig;

    /**
     * 机器过滤条件
     */
    @MdpConfig("HOST_FILTER_CONDITION:[\n" +
            "  {\n" +
            "    \"key\": \"endure\",\n" +
            "    \"name\": \"仅看非弹性机器\",\n" +
            "    \"priority\": 5\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"replace_urgency\",\n" +
            "    \"name\": \"替换紧急资源的机器\",\n" +
            "    \"priority\": 10\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"exchange\",\n" +
            "    \"name\": \"仅看可赠予机器\",\n" +
            "    \"priority\": 15\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"octo_disabled\",\n" +
            "    \"name\": \"仅看被禁用节点机器\",\n" +
            "    \"priority\": 20\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"active_resource_pool\",\n" +
            "    \"name\": \"仅看活动资源池机器\",\n" +
            "    \"priority\": 25\n" +
            "  }\n" +
            "]")
    private String hostFilterConfig;

    /**
     * 网卡速率信息映射
     */
    @MdpConfig("NIC_SPEED_CONFIG:[]")
    private String nicSpeedConfig;

    /**
     * 网卡速率信息映射
     */
    @MdpConfig("EXTERNAL_IDC:[]")
    private String externalIdc;

    @GetMapping("")
    public PageResponse<HostAttributesVO> getAppkeyHost(@Valid HostQueryRequest request) {
        // OPS获取Appkey对应Srv下的主机信息
        PageResponse<HostAttributesDTO> hostAttributesDTOList = hostService.getSrvHostsByQueryRequest(request);
        return HostVOTransfer.INSTANCE.toHostAttributesVOList(hostAttributesDTOList);
    }

    @GetMapping("/offlineReason")
    public List<String> getHostOfflineReason() {
        // 文案展示: 机器下线原因
        return JsonUtil.jsonPath2NestedBean(offlineReason, new TypeRef<List<String>>(){});
    }

    @GetMapping("/titleConfig")
    public List<HostTitleConfigVO> getHostTitleConfig() {
        // 文案展示: 机器表头名
        return JsonUtil.jsonPath2NestedBean(hostTitleConfig, new TypeRef<List<HostTitleConfigVO>>(){});
    }

    @GetMapping("/filterConfig")
    public List<HostFilterConditionVO> getHostFilterConfig() {
        // 文案展示: 机器自定义高级过滤条件
        List<HostFilterConditionVO> conditionVOList = JsonUtil.jsonPath2NestedBean(
                hostFilterConfig, new TypeRef<List<HostFilterConditionVO>>(){});
        return conditionVOList.stream()
                .sorted(Comparator.comparingInt(HostFilterConditionVO::getPriority))
                .collect(Collectors.toList());
    }

    @GetMapping("/hostConfig")
    public HostConfigVO getHostConfig() {
        // 获取网卡nic_speed与机房idc配置
        // 接口需要进行拆分
        List<IdcMetaDataDTO> idcDTOList = hostService.getIdc();
        JsonUtil.jsonPath2NestedBean(nicSpeedConfig, new TypeRef<List<HostConfigVO.NicSpeed>>(){});
        return HostConfigVO.builder()
                .idc(HostVOTransfer.INSTANCE.batchToIdcMetaDataVO(idcDTOList))
                .nicSpeed(JsonUtil.jsonPath2NestedBean(nicSpeedConfig, new TypeRef<List<HostConfigVO.NicSpeed>>(){}))
                .build();
    }

    @GetMapping("/title")
    public HostSumAttributeVO getAppkeyHostSumAttribute(@Valid HostQueryRequest request) {
        HostSumAttributeDTO hostSumAttributeDTO = hostService.getHostSumAttribute(request);
        return HostVOTransfer.INSTANCE.toHostSumAttributeVO(hostSumAttributeDTO);
    }

    @GetMapping("/count")
    public HostCountVO getAppkeyHostCount(@Valid HostQueryRequest request) {
        HostCountDTO hostCountDTO = hostService.getHostsCountByQueryRequest(request);
        return HostVOTransfer.INSTANCE.toHostCountVO(hostCountDTO);
    }

    @GetMapping("/cell")
    public List<HostCellVO> getAppkeyHostCell(@Valid HostQueryRequest request) {
        List<HostCellDTO> hostCellDTOList = hostService.getHostCellByQueryRequest(request);
        return HostVOTransfer.INSTANCE.toHostCellVOList(hostCellDTOList);
    }

    @GetMapping("/grouptags")
    public List<GroupTagVO> getAppkeyGrouptags(@Valid GroupTagQueryRequest request) {
        List<GroupTagDTO> groupTagDTOList = hostService.getGrouptags(request);
        return HostVOTransfer.INSTANCE.batchToGroupTagVO(groupTagDTOList);
    }

    @GetMapping("/idc")
    public HostIdcDistributedVO getAppkeyIdcDistributed(@Valid HostQueryRequest request) {
        HostIdcDistributedDTO hostIdcDistributedDTO = hostService.getAppkeyHostDistributed(request);
        return HostVOTransfer.INSTANCE.toHostIdcDistributedVO(hostIdcDistributedDTO);
    }

    @GetMapping("/firstHulk")
    public Boolean appkeyFirstApplyHulk(@Valid HostQueryRequest request) {
        // 不存在HULK机器时，新增机器则需要确认HULK服务协议
        String hulkTag = "hulk";
        List<HostAttributesDTO> hostAttributesDTOList = hostService.getOriginSrvHostsByQueryRequest(request);
        return hostAttributesDTOList.stream().anyMatch(i -> i.getKind().contains(hulkTag) || i.getType().contains(hulkTag));
    }

    @GetMapping("/external/{query}")
    public ExternalHostVO getExternalHost(@PathVariable("query") @Valid @NotBlank String query) {
        ExternalHostDTO externalHostDTO = hostService.getExternalHostInfo(query);
        return HostVOTransfer.INSTANCE.toExternalHostVO(externalHostDTO);
    }

    @GetMapping("/external/parent/{query}")
    public List<HostVO> getExternalParentHost(@PathVariable("query") @Valid @NotBlank String query) {
        List<HostDTO> hostDTOList = hostService.getExternalParentHostInfo(query);
        return HostVOTransfer.INSTANCE.batchToHostVO(hostDTOList);
    }

    @GetMapping("/external/idc")
    public List<IdcVO> getExternalIdc() {
        return JsonUtil.jsonPath2NestedBean(externalIdc, new TypeRef<List<IdcVO>>(){});
    }

}
