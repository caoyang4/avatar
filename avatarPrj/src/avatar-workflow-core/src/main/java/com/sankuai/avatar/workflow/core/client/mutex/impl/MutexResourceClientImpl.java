package com.sankuai.avatar.workflow.core.client.mutex.impl;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.AppkeyBO;
import com.sankuai.avatar.resource.host.HostResource;
import com.sankuai.avatar.resource.host.bo.HostBO;
import com.sankuai.avatar.workflow.core.client.mutex.MutexResourceClient;
import com.sankuai.avatar.workflow.core.client.mutex.MutexResourceInfo;
import com.sankuai.avatar.workflow.core.client.mutex.request.MutexResourceRequest;
import com.sankuai.avatar.workflow.core.client.mutex.response.MutexResourceResponse;
import com.sankuai.avatar.workflow.core.client.mutex.response.ResourceOperation;
import com.sankuai.avatar.workflow.core.context.FlowConfig;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.context.resource.Host;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import com.sankuai.avatar.dao.workflow.repository.FlowMutexResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Component
public class MutexResourceClientImpl implements MutexResourceClient {

    /**
     * 冲突资源提示信息拼接符
     */
    private static final String IP_RESOURCE_MSG_DELIMITER = "\n";

    /**
     * 资源缓存字段拼接符
     */
    private static final String RESOURCE_CACHE_FIELD_DELIMITER = "_";

    /**
     * 资源缓存值拼接符
     */
    private static final String RESOURCE_CACHE_DATA_DELIMITER = "@";

    /**
     * 互斥资源 jsonPath 分隔符
     */
    private static final String MUTEX_FIELD_DELIMITER = ";";

    /**
     * 互斥资源占位符
     */
    private static final String MUTEX_RESOURCE_ITEM_PLACEHOLDER = "{item}";

    /**
     * 流程链接占位符
     */
    private static final String FLOW_LINK_PLACEHOLDER = "{link}";

    /**
     * 资源冲突提示信息模板
     */
    private static final String MUTEX_RESOURCE_CONFLICT_MSG_TEMPLATE = "资源{item}被流程{link}锁定中," +
            "请查看流程链接并联系流程发起人处理。";

    /**
     * IP资源操作相关流程
     */
    private static final List<String> IP_RESOURCE_TEMPLATES = Arrays.asList("reduced_service",
            "service_transfer", "hosts_exchange", "hosts_reboot");

    /**
     * 环境校验
     */
    private static final String VALIDATE_ENV = "prod";

    /**
     * Buffer 应用
     */
    private static final String BUFFER_APPLICATION = "Buffer";

    /**
     * 流程白名单
     */
    private static final List<String> TEMPLATE_WHITE_LIST = Collections.singletonList("reduced_service");

    /**
     * srv 白名单
     */
    private static final List<String> SRV_WHITE_LIST = Collections.singletonList("meituan.data");

    @MdpConfig("AVATAR_URL:https://avatar.mws.sankuai.com")
    private String avatarUrl;

    private final FlowMutexResourceRepository flowMutexResourceRepository;

    private final AppkeyResource appKeyResource;

    private final HostResource hostResource;

    public MutexResourceClientImpl(FlowMutexResourceRepository flowMutexResourceRepository,
                                   AppkeyResource appKeyResource,
                                   HostResource hostResource) {
        this.flowMutexResourceRepository = flowMutexResourceRepository;
        this.appKeyResource = appKeyResource;
        this.hostResource = hostResource;
    }

    @Override
    public MutexResourceResponse validate(MutexResourceRequest request) {
        FlowContext flowContext = request.getFlowContext();
        // ip_lan
        MutexResourceResponse ipLanResponse = validateIPResource(flowContext);
        if (ipLanResponse != null && ipLanResponse.isLocked()) {
            return ipLanResponse;
        }
        // jsonPath 指定资源
        MutexResourceResponse mutexResourceResponse = validateCommonResource(flowContext);
        if (mutexResourceResponse != null && mutexResourceResponse.isLocked()) {
            return mutexResourceResponse;
        }
        return MutexResourceResponse.builder().locked(false).build();
    }

    @Override
    public boolean lock(MutexResourceRequest request) {
        FlowContext flowContext = request.getFlowContext();
        List<String> ipResource = getIpResource(flowContext);
        List<String> mutexResourceCacheKeyList = getMutexResourceCacheKey(flowContext);
        mutexResourceCacheKeyList.addAll(ipResource);
        return lockMutexResource(flowContext, mutexResourceCacheKeyList);
    }

    @Override
    public boolean unLock(MutexResourceRequest request) {
        FlowContext flowContext = request.getFlowContext();
        List<String> ipResource = getIpResource(flowContext);
        List<String> mutexResourceCacheKeyList = getMutexResourceCacheKey(flowContext);
        mutexResourceCacheKeyList.addAll(ipResource);
        return unLockMutexResource(mutexResourceCacheKeyList);
    }

    @Override
    public boolean canSkip(MutexResourceRequest request) {
        FlowContext flowContext = request.getFlowContext();
        FlowConfig flowConfig = flowContext.getFlowConfig();
        FlowResource flowResource = flowContext.getResource();
        if (flowConfig == null || flowResource == null) {
            return true;
        }
        boolean mutexLockSwitch = flowConfig.getSwitches().isMutexResource();
        // 互斥锁开关
        if (mutexLockSwitch && VALIDATE_ENV.equals(flowContext.getEnv())) {
            List<String> mutexCommonFields = flowConfig.getMutexResource().getMutexFields();
            if (CollectionUtils.isEmpty(mutexCommonFields)) {
                return true;
            }
            if (validateSrv(flowContext) || validateApplication(flowContext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验IP资源互斥锁
     *
     * @param flowContext 流程上下文
     * @return MutexResourceResponse
     */
    private MutexResourceResponse validateIPResource(FlowContext flowContext) {
        FlowResource flowResource = flowContext.getResource();
        if (flowResource == null) {
            return null;
        }
        List<String> ipResource = getIpResource(flowContext);
        if (CollectionUtils.isEmpty(ipResource)) {
            return null;
        }
        // 校验 ip 资源和 appKey 从属关系,比对是否有差异主机
        String appKey = flowResource.getAppkey();
        String env = flowContext.getEnv();
        Set<String> diffHosts = getDiffHosts(ipResource, appKey, env);
        if (CollectionUtils.isNotEmpty(diffHosts)) {
            String diffResource = String.join(",", diffHosts);
            String rejectMsg = String.format("服务 %s %s环境中，找不到ip: %s", appKey, env, diffResource);
            return MutexResourceResponse.builder().locked(true).message(rejectMsg).build();
        }

        // 校验 ip 资源是否冲突
        List<String> resourceConflictMsg = getResourceConflictMsg(flowContext, ipResource);
        MutexResourceResponse response = MutexResourceResponse.builder().locked(false).build();
        if (CollectionUtils.isEmpty(resourceConflictMsg)) {
            return response;
        }
        response.setLocked(true);
        response.setMessage(String.join(IP_RESOURCE_MSG_DELIMITER, resourceConflictMsg));
        return response;
    }

    /**
     * 校验通用资源互斥锁，解析 JsonPath 获取锁定资源的值
     *
     * @param flowContext 流程上下文
     * @return MutexResourceResponse
     */
    private MutexResourceResponse validateCommonResource(FlowContext flowContext) {
        List<String> mutexResourceCacheKeyList = getMutexResourceCacheKey(flowContext);
        if (CollectionUtils.isEmpty(mutexResourceCacheKeyList)) {
            return null;
        }
        List<String> resourceConflictMsg = new ArrayList<>();
        for (String key : mutexResourceCacheKeyList) {
            // 缓存字段已经存在，则表示冲突
            if (flowMutexResourceRepository.isExist(key)) {
                resourceConflictMsg.add(formatMutexResourceConflictMsg(key));
            }
        }
        MutexResourceResponse response = MutexResourceResponse.builder().locked(false).build();
        if (CollectionUtils.isEmpty(resourceConflictMsg)) {
            return response;
        }
        response.setLocked(true);
        response.setMessage(String.join(IP_RESOURCE_MSG_DELIMITER, resourceConflictMsg));
        return response;
    }

    /**
     * 获取流程操作的IP资源
     *
     * @param flowContext 流程上下文
     * @return IP 列表
     */
    private List<String> getIpResource(FlowContext flowContext) {
        FlowResource flowResource = flowContext.getResource();
        if (flowResource == null || CollectionUtils.isEmpty(flowResource.getHostList())) {
            return Collections.emptyList();
        }
        List<String> ipLanList = flowResource.getHostList().stream()
                .map(Host::getIpLan).distinct().collect(Collectors.toList());
        return ipLanList.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 检查当前操作的IP资源是否冲突
     *
     * @param ip         IP地址
     * @param cacheValue 缓存值
     * @return 是否冲突
     */
    private boolean isConflict(String ip, String cacheValue) {
        if (!flowMutexResourceRepository.isExist(ip)) {
            return false;
        }
        String cacheData = flowMutexResourceRepository.getResourceByField(ip);
        // 缓存内容为空或者相同则可操作
        if (StringUtils.isEmpty(cacheData) || cacheValue.equals(cacheData)) {
            return false;
        }
        String[] cacheDataList = cacheData.split(RESOURCE_CACHE_DATA_DELIMITER);
        // 流程是否冲突,是IP相关流程则冲突
        if (cacheDataList.length != 0) {
            return IP_RESOURCE_TEMPLATES.contains(cacheDataList[0]);
        }
        return false;
    }

    /**
     * 获取冲突IP资源对应的流程名称
     *
     * @param ip IP地址
     * @return 流程名称
     */
    private String getIpResourceRelateFlow(String ip) {
        String cacheData = flowMutexResourceRepository.getResourceByField(ip);
        if (StringUtils.isEmpty(cacheData)) {
            return null;
        }
        List<String> cacheDataList = getCacheDataByField(ip);
        if (CollectionUtils.isEmpty(cacheDataList)) {
            return null;
        }
        return cacheDataList.get(0);
    }

    private List<String> getCacheDataByField(String field) {
        String cacheData = flowMutexResourceRepository.getResourceByField(field);
        if (StringUtils.isEmpty(cacheData)) {
            return Collections.emptyList();
        }
        return Arrays.asList(cacheData.split(RESOURCE_CACHE_DATA_DELIMITER));
    }

    /**
     * 获取 IP 冲突提示信息
     * 举例：10.1.1.1 正在(扩容、迁移、下线、重启、赠予)中
     *
     * @param ip IP地址
     * @return msg
     */
    private String getIPConflictMsg(String ip) {
        String flowTemplateName = getIpResourceRelateFlow(ip);
        if (StringUtils.isEmpty(flowTemplateName)) {
            return null;
        }
        ResourceOperation operation = ResourceOperation.getByTemplateName(flowTemplateName);
        if (operation == null) {
            return null;
        }
        return String.format("%s正在%s", ip, operation.getOperationState());
    }

    /**
     * 获取互斥资源缓存 key 列表
     *
     * @param flowContext 流程上下文
     * @return 缓存 key 列表
     */
    private List<String> getMutexResourceCacheKey(FlowContext flowContext) {
        List<String> cacheKeys = new ArrayList<>();
        List<List<String>> mutexResourceList = getMutexResource(flowContext);
        if (CollectionUtils.isEmpty(mutexResourceList)) {
            return cacheKeys;
        }
        for (List<String> resources : mutexResourceList) {
            cacheKeys.add(StringUtils.join(resources, RESOURCE_CACHE_FIELD_DELIMITER));
        }
        return cacheKeys;
    }

    /**
     * 解析互斥资源JsonPath配置列表
     *
     * @param flowContext 流程上下文
     * @return 公共资源列表
     */
    private List<List<String>> getMutexResource(FlowContext flowContext) {
        List<List<String>> result = new ArrayList<>(Collections.emptyList());
        FlowConfig flowConfig = flowContext.getFlowConfig();
        if (flowConfig == null || flowConfig.getMutexResource() == null) {
            return result;
        }
        List<String> mutexFields = flowConfig.getMutexResource().getMutexFields();
        if (CollectionUtils.isEmpty(mutexFields)) {
            return result;
        }
        String inputJson = getInputJsonStr(flowContext);
        for (String field : mutexFields) {
            String[] jsonPathKey = field.split(MUTEX_FIELD_DELIMITER);
            List<String> values = getJsonPathValue(inputJson, jsonPathKey);
            result.add(values);
        }
        return result;
    }

    /**
     * 获取流程入参 input Json 字符串
     *
     * @param flowContext 流程上下文
     * @return json string
     */
    private String getInputJsonStr(FlowContext flowContext) {
        FlowInput flowInput = flowContext.getFlowInput();
        MutexResourceInfo resourceInfo = MutexResourceInfo.builder()
                .input(flowInput)
                .appKey(flowContext.getResource().getAppkey())
                .env(flowContext.getEnv())
                .templateName(flowContext.getTemplateName())
                .build();
        return JsonUtil.bean2Json(resourceInfo);
    }

    /**
     * 解析 JsonPath 获取指定资源
     *
     * @param inputJson   流程入参
     * @param jsonPathKey jsonPath
     * @return List<String>
     */
    private List<String> getJsonPathValue(String inputJson, String[] jsonPathKey) {
        List<String> jsonPathValueList = new ArrayList<>(Collections.emptyList());
        Configuration configuration = Configuration
                .builder()
                .options(Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS)
                .build();
        for (String key : jsonPathKey) {
            List<String> jsonPathValue = JsonPath.using(configuration).parse(inputJson).read(key);
            if (jsonPathValue == null) {
                continue;
            }
            jsonPathValueList.addAll(jsonPathValue);
        }
        return jsonPathValueList;
    }

    private String formatMutexResourceConflictMsg(String cacheField) {
        List<String> cacheDataList = getCacheDataByField(cacheField);
        if (CollectionUtils.isEmpty(cacheDataList) || cacheDataList.size() < 2) {
            return null;
        }
        String uuid = cacheDataList.get(1);
        String flowUrl = String.format("%s/#/home?flow_id=%s", avatarUrl, uuid);
        return MUTEX_RESOURCE_CONFLICT_MSG_TEMPLATE.replace(MUTEX_RESOURCE_ITEM_PLACEHOLDER, cacheField)
                .replace(FLOW_LINK_PLACEHOLDER, flowUrl);
    }

    /**
     * 锁定互斥资源
     *
     * @param flowContext               流程上下文
     * @param mutexResourceCacheKeyList JsonPath 解析值缓存 Key 列表
     * @return 是否锁定成功
     */
    private boolean lockMutexResource(FlowContext flowContext, List<String> mutexResourceCacheKeyList) {
        if (CollectionUtils.isEmpty(mutexResourceCacheKeyList)) {
            return false;
        }
        List<Boolean> success = new ArrayList<>();
        for (String cacheKey : mutexResourceCacheKeyList) {
            boolean status = flowMutexResourceRepository.save(cacheKey, getCacheData(flowContext));
            success.add(status);
        }
        return success.stream().allMatch(s -> s);
    }

    private boolean unLockMutexResource(List<String> mutexResourceCacheKeyList) {
        if (CollectionUtils.isEmpty(mutexResourceCacheKeyList)) {
            return false;
        }
        List<Boolean> success = new ArrayList<>();
        for (String cacheKey : mutexResourceCacheKeyList) {
            boolean status = flowMutexResourceRepository.delete(cacheKey);
            success.add(status);
        }
        return success.stream().allMatch(s -> s);
    }

    /**
     * 生成缓存内容
     *
     * @param flowContext 流程上下文
     * @return 缓存内容
     */
    private String getCacheData(FlowContext flowContext) {
        String templateName = flowContext.getTemplateName();
        String uuid = flowContext.getUuid();
        return String.join(RESOURCE_CACHE_DATA_DELIMITER, Arrays.asList(templateName, uuid));
    }

    /**
     * 获取冲突资源提示信息
     *
     * @param flowContext 流程上下文
     * @param ipResource  IP资源列表
     * @return 冲突提示信息
     */
    private List<String> getResourceConflictMsg(FlowContext flowContext, List<String> ipResource) {
        String templateName = flowContext.getTemplateName();
        String cacheValue = templateName + RESOURCE_CACHE_DATA_DELIMITER + flowContext.getUuid();
        List<String> resourceConflictMsg = new ArrayList<>();
        for (String ip : ipResource) {
            if (isConflict(ip, cacheValue)) {
                String conflictMsg = getIPConflictMsg(ip);
                if (StringUtils.isEmpty(conflictMsg)) {
                    continue;
                }
                resourceConflictMsg.add(conflictMsg);
            }
        }
        return resourceConflictMsg;
    }


    /**
     * 获取差异主机信息
     * 比较入参 hosts 和实际 appKey 关联的资源
     *
     * @param ipResources 变更资源
     * @param appKey      服务 appKey
     * @return 差异主机列表
     */
    private Set<String> getDiffHosts(List<String> ipResources, String appKey, String env) {
        List<HostBO> hostBOList = hostResource.getHostsByAppKey(appKey);
        Set<String> currentEnvHosts = hostBOList.stream().filter(i -> env.equals(i.getEnv()))
                .map(HostBO::getIpLan).collect(Collectors.toSet());
        Set<String> ipResourceSet = new HashSet<>(ipResources);
        ipResourceSet.removeAll(currentEnvHosts);
        return ipResourceSet;
    }

    /**
     * 校验 Srv
     *
     * @param flowContext 流程上下文
     * @return 校验结果
     */
    private boolean validateSrv(FlowContext flowContext) {
        String templateName = flowContext.getTemplateName();
        // srv是否存在
        String srv = flowContext.getResource().getSrv();
        if (StringUtils.isBlank(srv)) {
            return true;
        }
        // 大数据 srv 的机器下线流程不限制
        return TEMPLATE_WHITE_LIST.contains(templateName) && SRV_WHITE_LIST.contains(srv);
    }

    private boolean validateApplication(FlowContext flowContext) {
        FlowResource flowResource = flowContext.getResource();
        // appKey 应用类型是 Buffer 不校验
        AppkeyBO appKeyDetail = appKeyResource.getByAppkey(flowResource.getAppkey());
        return appKeyDetail != null && BUFFER_APPLICATION.equals(appKeyDetail.getApplicationName());
    }
}
