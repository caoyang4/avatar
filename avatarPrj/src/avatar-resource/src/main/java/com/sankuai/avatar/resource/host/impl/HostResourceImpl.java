package com.sankuai.avatar.resource.host.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.TypeRef;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.dayu.DayuHttpClient;
import com.sankuai.avatar.client.dayu.model.DayuGroupTag;
import com.sankuai.avatar.client.dayu.model.GroupTagQueryRequest;
import com.sankuai.avatar.client.ecs.EcsHttpClient;
import com.sankuai.avatar.client.ecs.model.EcsIdc;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.kapiserver.KApiServerHttpClient;
import com.sankuai.avatar.client.kapiserver.model.HostFeature;
import com.sankuai.avatar.client.kapiserver.model.VmHostDiskFeature;
import com.sankuai.avatar.client.nodemanager.NodeManagerHttpClient;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.client.ops.model.OpsHost;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.cache.CacheClient;
import com.sankuai.avatar.resource.host.HostResource;
import com.sankuai.avatar.resource.host.bo.*;
import com.sankuai.avatar.resource.host.core.filter.Filter;
import com.sankuai.avatar.resource.host.core.filter.Handler;
import com.sankuai.avatar.resource.host.core.filter.rule.*;
import com.sankuai.avatar.resource.host.request.GroupTagQueryRequestBO;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import com.sankuai.avatar.resource.host.request.VmHostDiskQueryRequestBO;
import com.sankuai.avatar.resource.host.transfer.HostTransfer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhaozhifan02
 */
@Service
public class HostResourceImpl implements HostResource {

    private final OpsHttpClient opsHttpClient;
    private final EcsHttpClient ecsHttpClient;
    private final CacheClient cacheClient;
    private final NodeManagerHttpClient nodeManagerHttpClient;
    private final KApiServerHttpClient kApiServerHttpClient;
    private final DayuHttpClient dayuHttpClient;

    public HostResourceImpl(OpsHttpClient opsHttpClient,
                            EcsHttpClient ecsHttpClient,
                            CacheClient cacheClient,
                            NodeManagerHttpClient nodeManagerHttpClient,
                            KApiServerHttpClient kApiServerHttpClient,
                            DayuHttpClient dayuHttpClient) {
        this.opsHttpClient = opsHttpClient;
        this.ecsHttpClient = ecsHttpClient;
        this.cacheClient = cacheClient;
        this.nodeManagerHttpClient = nodeManagerHttpClient;
        this.kApiServerHttpClient = kApiServerHttpClient;
        this.dayuHttpClient = dayuHttpClient;
    }

    private static final String CATEGORY = "avatar-web";

    /**
     * 机器类型映射
     */
    @MdpConfig("HOST_KIND_MAP:{\n" +
            "  \"server\": {\n" +
            "    \"cloud\": \"私有云物理机\",\n" +
            "    \"tx\": \"腾讯云黑石物理机\",\n" +
            "    \"maoyan_tx\": \"猫眼腾讯云物理机\"\n" +
            "  },\n" +
            "  \"vserver\": {\n" +
            "    \"cloud\": \"私有云虚拟机\",\n" +
            "    \"tx\": \"腾讯云虚拟机\",\n" +
            "    \"mos\": \"公有云虚拟机\",\n" +
            "    \"maoyan_tx\": \"猫眼腾讯云虚拟机\",\n" +
            "    \"hulk-kvm\": \"HULK虚拟机\"\n" +
            "  },\n" +
            "  \"hulk\": {\n" +
            "    \"hulk1.0\": \"HULK1.0\",\n" +
            "    \"hulk2.0\": \"HULK\",\n" +
            "    \"kata\": \"HULK安全容器\",\n" +
            "    \"tx\": \"HULK\"\n" +
            "  }\n" +
            "}")
    private String hostKindMap;

    /**
     * 机器类型映射
     */
    @MdpConfig("HOST_SOURCE_MAP:{\"policy\":\"弹性伸缩\"}")
    private String hostSourceMap;

    /**
     * 机器资源池归还映射
     */
    @MdpConfig("ORIGIN_RETURN_RESOURCE_POOL:{\"通用资源池\": \"结算单元池\"}")
    private String originReturnResourcePool;

    /**
     * WIN机器类型
     */
    @MdpConfig("WIN_TAG:[\"win\",\"yf-pay-fundstransfer-gw-fe-cmb-clone_20160603\"]")
    private String[] winTag;

    @Override
    public List<HostBO> getHostsByAppKey(String appKey) throws SdkCallException, SdkBusinessErrorException {
        List<OpsHost> opsHosts = opsHttpClient.getHostsByAppkey(appKey);
        return HostTransfer.INSTANCE.toBOList(opsHosts);
    }

    @Override
    public List<HostBO> getHostsBySrv(String srv) throws SdkCallException, SdkBusinessErrorException {
        List<OpsHost> opsHosts = opsHttpClient.getHostsBySrv(srv);
        return HostTransfer.INSTANCE.toBOList(opsHosts);
    }

    @Override
    public HostBO getHostInfo(String name) throws SdkCallException, SdkBusinessErrorException {
        OpsHost opsHost = opsHttpClient.getHostInfo(name);
        return HostTransfer.INSTANCE.toBO(opsHost);
    }

    /**
     * 根据条件筛选服务树节点主机
     * 1、环境
     * 2、cell, set, swimlane, group_tags （-代表主干道）
     * 3、ctime区间
     * 4、net_type、rs_net_type
     *
     * @param hostQueryRequestBO 主机查询请求
     * @return {@link List}<{@link HostBO}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    @Override
    public List<HostAttributesBO> getSrvHostsByQueryRequest(HostQueryRequestBO hostQueryRequestBO) throws SdkCallException, SdkBusinessErrorException {
        // 由ops获取host列表
        List<OpsHost> opsHostList = opsHttpClient.getHostsBySrv(hostQueryRequestBO.getSrv());
        List<HostAttributesBO> hostAttributesBOList = HostTransfer.INSTANCE.toHostAttributesBOList(opsHostList);
        // 补充主机核心数据
        List<HostAttributesBO> hostBOList = patchHostBOList(hostAttributesBOList);

        // 高级数据处理：检索、排序
        List<Filter<HostAttributesBO>> filters = new ArrayList<>(Arrays.asList(
                OrderedBaseFilterRule.orderedBaseFilter(),
                OrderedQueryFilterRule.orderedQueryFilter(),
                OrderedNetTypeFilterRule.orderedNetTypeFilter(),
                OrderedOriginTypeFilterRule.orderedOriginTypeFilter(),
                OrderedOctoDisabledFilterRule.orderedOctoDisabledFilter(),
                OrderedSortFilterRule.orderedSortFilter()
                ));
        if (StringUtils.isNotBlank(hostQueryRequestBO.getOriginType())){
            // 资源池信息筛选需要补充额外数据
            List<HostAttributesBO> patchedHosts = patchHostTagsAndFeatures(hostBOList, hostQueryRequestBO.getEnv());
            return Handler.init(filters).handle(patchedHosts, hostQueryRequestBO);
        }
        return Handler.init(filters).handle(hostBOList, hostQueryRequestBO);
    }

    /**
     * 补充主机标签
     *
     * @param hostList 主机列表
     * @param env      env
     * @return {@link List}<{@link HostAttributesBO}>
     */
    @Override
    public List<HostAttributesBO> patchHostTagsAndFeatures(List<HostAttributesBO> hostList, String env){
        List<HostAttributesBO> hulkHostList = hostList.stream()
                .filter(h -> "hulk".equals(h.getKind()) || ("vserver".equals(h.getKind()) && "hulk-kvm".equals(h.getVendor())))
                .collect(Collectors.toList());
        // 只检测hulk类型机器
        if (CollectionUtils.isEmpty(hulkHostList)){
            return hostList;
        }

        // 获取原始资源池类型的返回值
        Map<String, String> originReturnMap = JsonUtil.json2Map(originReturnResourcePool, String.class, String.class);
        // 批量获取hulk类型机器的特征值
        List<String> hulkHostNames = hulkHostList.stream().map(HostAttributesBO::getName).collect(Collectors.toList());
        Map<String, HostFeatureBO> hulkNodeTags = getHulkHostsFeatures(hulkHostNames, EnvEnum.valueOf(env.toUpperCase()));
        // 批量获取hulk类型机器的父机器的特征值
        List<String> hulkParentHosts = hulkHostList.stream().map(HostAttributesBO::getParent).collect(Collectors.toList());
        Map<String, List<String>> hulkParentNodeTags = getHostsParentFeatures(hulkParentHosts, EnvEnum.valueOf(env.toUpperCase()));

        // 为每个机器打标签和设置资源池类型
        setHostTagsAndFeatures(hostList, hulkNodeTags, hulkParentNodeTags, originReturnMap);
        return hostList;
    }

    @Override
    public List<HostAttributesBO> patchVmHostDiskType(List<HostAttributesBO> hostList, String appkey, String env){
        // 提取需要查询的虚拟机的 IP 列表
        List<String> vmIps = hostList.stream()
                .filter(host -> "HULK虚拟机".equals(host.getKindName())
                        && env.equals(host.getEnv())
                        && !host.getName().endsWith("-devd"))
                .map(HostAttributesBO::getIpLan)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(vmIps)) {
            return hostList;
        }

        // 调用接口查询每个分页中的HULK虚拟机磁盘类型
        VmHostDiskQueryRequestBO vmHostDiskQueryRequest = VmHostDiskQueryRequestBO.builder()
                .env(env)
                .appkey(appkey)
                .ips(vmIps)
                .build();

        List<VmHostFeatureBO> vmHostFeatureBOList = getVmHostsDiskFeatures(vmHostDiskQueryRequest);
        Map<String, List<String>> hostDiskMap = vmHostFeatureBOList.stream()
                .filter(vm -> "lvm".equals(vm.getStorageType()) || "ebs".equals(vm.getStorageType()))
                .collect(Collectors.groupingBy(VmHostFeatureBO::getIp,
                        Collectors.mapping(vm -> "lvm".equals(vm.getStorageType()) ? "lvm磁盘" : "ebs磁盘", Collectors.toList())));

        return hostList.stream()
                .map(host -> {
                    String ip = host.getIpLan();
                    if (hostDiskMap.containsKey(ip)) {
                        host.setHostTags(CollectionUtils.isNotEmpty(host.getHostTags())
                                ? Stream.concat(host.getHostTags().stream(), hostDiskMap.get(ip).stream()).collect(Collectors.toList())
                                : new ArrayList<>(hostDiskMap.get(ip)));
                    }
                    return host;
                })
                .collect(Collectors.toList());
    }

    /**
     * 设置主机标签
     *
     * @param hostList           主机列表
     * @param hulkNodeTags       HULK容器特性
     * @param hulkParentNodeTags HULK容器宿主机特性
     * @param originReturnMap    资源池映射
     */
    private void setHostTagsAndFeatures(List<HostAttributesBO> hostList, Map<String, HostFeatureBO> hulkNodeTags,
                               Map<String, List<String>> hulkParentNodeTags, Map<String, String> originReturnMap) {
        hostList.forEach(host -> {
            HostFeatureBO hulkNodeTag = hulkNodeTags.get(host.getName());
            List<String> hulkParentNodeTag = hulkParentNodeTags.getOrDefault(host.getParent(), Collections.emptyList());
            if (hulkNodeTag != null) {
                setHostTags(host, hulkNodeTag);
                setOriginType(host, hulkNodeTag);
                setReturnType(host, originReturnMap);
                setParentTags(host, hulkParentNodeTag);
            } else {
                host.setOriginType("-");
                host.setOriginGroup("-");
            }
        });
    }

    /**
     * 设置主机的标签和特性
     *
     * @param host        主机
     * @param hulkNodeTag HULK容器特性
     */
    private void setHostTags(HostAttributesBO host, HostFeatureBO hulkNodeTag) {
        List<String> tags = hulkNodeTag.getFeatures();
        host.setHostTags(CollectionUtils.isNotEmpty(host.getHostTags())
                ? Stream.concat(host.getHostTags().stream(), tags.stream()).collect(Collectors.toList())
                : new ArrayList<>(tags));
    }

    /**
     * 设置主机资源池类型
     *
     * @param host        主机
     * @param hulkNodeTag 绿巨人节点标签
     */
    private void setOriginType(HostAttributesBO host, HostFeatureBO hulkNodeTag) {
        host.setOriginGroup("-");
        if ("holiday".equals(hulkNodeTag.getOriginGroup())) {
            host.setOriginType("活动资源池");
        } else {
            host.setOriginType(hulkNodeTag.getOriginType());
            if ("专属资源池".equals(host.getOriginType())) {
                host.setOriginGroup(hulkNodeTag.getOriginGroup());
            }
        }
    }

    /**
     * 设置返回资源池信息
     *
     * @param host            宿主
     * @param originReturnMap 资源池映射
     */
    private void setReturnType(HostAttributesBO host, Map<String, String> originReturnMap) {
        host.setReturnType(originReturnMap.getOrDefault(host.getOriginType(),
                StringUtils.isNotBlank(host.getOriginType()) ? host.getOriginType() : "-"));
    }

    /**
     * 设置容器宿主机特性
     *
     * @param host               主机
     * @param hulkParentNodeTags HULK容器宿主机特性
     */
    private void setParentTags(HostAttributesBO host, List<String> hulkParentNodeTags) {
        host.setParentTags(hulkParentNodeTags);
    }

    /**
     * 补充自定义主机字段信息
     *
     * @param hostList 主机列表
     * @return {@link List}<{@link HostAttributesBO}>
     */
    private List<HostAttributesBO> patchHostBOList(List<HostAttributesBO> hostList){
        Map<String, String> idcMap = getIdcMap();
        // 格式化机器类型、网卡类型、来源类型
        Map<String, Map<String, String>> hostKind = JsonUtil.jsonPath2NestedBean(hostKindMap, new TypeRef<Map<String, Map<String, String>>>(){});
        Map<String, String> hostSource = JsonUtil.json2Map(hostSourceMap, String.class, String.class);
        Map<Integer, String> hostNicMap = ImmutableMap.<Integer, String>builder()
                .put(1000, "千兆").put(2000, "二千兆").put(10000, "万兆")
                .put(20000, "二万兆").put(50000, "五万兆").put(25000, "25000M").build();
        // 额外补充机器自定义展示属性
        for (HostAttributesBO host : hostList) {
            host.setKindName(Optional.ofNullable(hostKind.getOrDefault(host.getKind(), new HashMap<>()))
                    .map(m -> m.get(host.getVendor()))
                    .orElse(host.getKind() + "-" + host.getVendor()));
            host.setIdcName(Optional.ofNullable(idcMap.get(host.getIdc())).orElse(host.getIdc()));
            host.setCityName(Optional.ofNullable(idcMap.get(host.getIdc())).map(s -> s.split("_")[0]).orElse(""));
            host.setNicSpeedName(Optional.ofNullable(hostNicMap.get(host.getNicSpeed())).orElse(host.getNicSpeed() + "M"));
            host.setManagePlatName(Optional.ofNullable(hostSource.get(host.getManagePlat()))
                    .orElse(StringUtils.isNotBlank(host.getManagePlat()) ? host.getManagePlat() : "-"));
            host.setIsWin(!StringUtils.isBlank(host.getOs()) && Arrays.stream(winTag).anyMatch(tag -> host.getOs().toLowerCase().contains(tag)));
            host.setNetType(getIpNetType(host.getIpLan(), host.getIpLanV6()));
        }
        return hostList;
    }

    /**
     * 获取ip网络类型(双栈机器展示为IPv4+IPv6)
     *
     * @param ipv4 ipv4
     * @param ipv6 ipv6
     * @return {@link String}
     */
    private String getIpNetType(String ipv4, String ipv6) {
        if (StringUtils.isBlank(ipv4) && StringUtils.isBlank(ipv6)) {
            return "-";
        }
        if (StringUtils.isNotBlank(ipv4) && StringUtils.isNotBlank(ipv6)) {
            return "IPv4+IPv6";
        }
        return StringUtils.isNotBlank(ipv4) ? "IPv4" : "IPv6";
    }

    @Override
    public List<IdcMetaDataBO> getIdcList() throws SdkCallException, SdkBusinessErrorException {
        // 增加缓存（12小时过期）
        // 缓存异常需要降级到ECS源头获取
        try {
            String cached = cacheClient.get(CATEGORY, "ecsIdcMetaData");
            if (StringUtils.isNotBlank(cached)){
                List<IdcMetaDataBO> cachedIdcMetaData = JsonUtil.jsonPath2List(cached, IdcMetaDataBO.class, "$");
                if (CollectionUtils.isNotEmpty(cachedIdcMetaData)){
                    return cachedIdcMetaData;
                }
            }
            List<EcsIdc> idcList = ecsHttpClient.getIdcList();
            List<IdcMetaDataBO> idcMetaDataBOList = HostTransfer.INSTANCE.batchToIdcMetaDataBO(idcList);
            // 设置缓存异常不能影响正常ECS源获取
            cacheClient.set(CATEGORY, "ecsIdcMetaData", idcMetaDataBOList, 60 * 60 * 12);
            return idcMetaDataBOList;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, String> getIdcMap() throws SdkCallException, SdkBusinessErrorException {
        List<IdcMetaDataBO> idcMetaDataBOList = getIdcList();
        if (CollectionUtils.isEmpty(idcMetaDataBOList)) {
            return Collections.emptyMap();
        }
        return idcMetaDataBOList.stream()
                .filter(item -> StringUtils.isNotBlank(item.getCity()) && StringUtils.isNotBlank(item.getIdcName()))
                .collect(
                        Collectors.toMap(
                                IdcMetaDataBO::getIdc,
                                item -> item.getCity() + "_" + item.getIdcName(),
                                // 在key冲突的情况下,用新值覆盖旧值
                                (oldValue, newValue) -> StringUtils.isNotBlank(newValue) ? newValue : oldValue
                        )
                );
    }

    @Override
    public Map<String, HostFeatureBO> getHulkHostsFeatures(List<String> hostNameList, EnvEnum env) throws SdkCallException, SdkBusinessErrorException {
        Map<String, HostFeature> hulkHostsFeatures = kApiServerHttpClient.getHulkHostsFeatures(hostNameList, env);
        return HostTransfer.INSTANCE.toHostFeatureBOMap(hulkHostsFeatures);
    }

    @Override
    public List<VmHostFeatureBO> getVmHostsDiskFeatures(VmHostDiskQueryRequestBO request) throws SdkCallException, SdkBusinessErrorException {
        // 对 IP 列表进行分页（接口限定每次30个IP调用）
        int pageSize = 30;
        List<List<String>> vmPageIps = Lists.partition(request.getIps(), pageSize);
        String appkey = request.getAppkey();
        String env = request.getEnv();

        // 调用接口查询每个分页中的虚拟机磁盘类型
        List<VmHostFeatureBO> vmHostFeatureBOList = new ArrayList<>();

        for (List<String> vmPageIp : vmPageIps) {
            VmHostDiskQueryRequestBO vmHostDiskQueryRequest = VmHostDiskQueryRequestBO.builder()
                    .env(env)
                    .appkey(appkey)
                    .ips(vmPageIp)
                    .build();
            List<VmHostDiskFeature> vmHostDiskFeatureList = kApiServerHttpClient.getVmHostsDiskFeatures(
                    HostTransfer.INSTANCE.toVmHostDiskQueryRequestBO(vmHostDiskQueryRequest));
            if (CollectionUtils.isNotEmpty(vmHostDiskFeatureList)){
                vmHostFeatureBOList.addAll(HostTransfer.INSTANCE.batchToVmHostFeatureBO(vmHostDiskFeatureList));
            }
        }
        return vmHostFeatureBOList;
    }

    @Override
    public Map<String, List<String>> getHostsParentFeatures(List<String> parentHostsName, EnvEnum env) throws SdkCallException, SdkBusinessErrorException {
        return nodeManagerHttpClient.getHostsParentFeatures(parentHostsName, env);
    }

    @Override
    public List<DayuGroupTagBO> getGrouptags(GroupTagQueryRequestBO request) throws SdkCallException, SdkBusinessErrorException {
        GroupTagQueryRequest groupTagQueryRequest = HostTransfer.INSTANCE.toGroupTagQueryRequest(request);
        List<DayuGroupTag> dayuGroupTagList = dayuHttpClient.getGrouptags(groupTagQueryRequest);
        return HostTransfer.INSTANCE.batchToDayuGroupTagBO(dayuGroupTagList);
    }
}
