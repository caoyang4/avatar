package com.sankuai.avatar.web.component.rhino;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.dianping.rhino.api.RhinoAPIOneLimiterService;
import com.dianping.rhino.api.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/3/25 19:06
 * @version 1.0
 */
@Component
public class RhinoLimiterComponent {

    @Autowired
    RhinoAPIOneLimiterService.Iface rhino;

    private static final String RHINO_KEY = "rhino-one-limiter";
    private static final String SET_NAME = "default_cell";
    private static final String CAT_KEY = "rhino";
    private static final Integer RHINO_RESPONSE_CODE = 200;
    private static final Integer RHINO_ENTRANCE_LIMIT = 50;

    /**
     * 是否开启限流
     * */
    public boolean limiterIsEnable(String appKey) {
        // 获取限流主配置
        LimitMainConfig limitMainConfig = getMainConfig(appKey);
        // 限流总开关是否开启
        boolean masterSwitch = limitMainConfig.getMasterSwitch() == SwitchStatus.ON;
        // 限流debug开关是否开启
        boolean debug = limitMainConfig.isDebug();
        // 集群限流总开关是否开启
        boolean clusterSwitch = false;
        if (limitMainConfig.getStrategyMasterSwitch() != null){
            clusterSwitch = limitMainConfig.getStrategyMasterSwitch().get(LimitStrategyType.CLUSTER_QPS);
        }
        return masterSwitch && !debug && clusterSwitch;
    }

    /**
     * 返回Entrance列表，可能为空列表。空列表代表没有集群限流策略，或则没有开启的集群限流策略。
     * */
    public Map<String, List<String>> queryClusterLimitStrategy(String appKey) {
        Map<String, List<String>> clusterLimitStrategies = new HashMap<>(16);

        // 三种集群限流类型
        List<LimitStrategyType> clusterStrategyTypeList = new ArrayList<>();
        clusterStrategyTypeList.add(LimitStrategyType.CLUSTER_QPS);
        clusterStrategyTypeList.add(LimitStrategyType.CLUSTER_QPS_V2);
        clusterStrategyTypeList.add(LimitStrategyType.CLUSTER_QPS_VM);

        // 获取Entrance列表(数据量过多的特殊情况跳过计算)
        List<String> entranceList = queryEntranceListByAppKey(appKey);
        if (entranceList.size() > RHINO_ENTRANCE_LIMIT) {
            return clusterLimitStrategies;
        }
        // 查询开启状态的集群限流策略
        for (String entrance : entranceList) {
            List<String> enableClusterStrategyList = new ArrayList<>();
            List<LimitStrategy> limitStrategies = queryStrategiesByEntrance(appKey, entrance);
            for (LimitStrategy limitStrategy : limitStrategies) {
                if (limitStrategy.active && clusterStrategyTypeList.contains(limitStrategy.getType())) {
                    enableClusterStrategyList.add(limitStrategy.nickName);
                }
            }
            if (!enableClusterStrategyList.isEmpty()) {
                clusterLimitStrategies.put(entrance, enableClusterStrategyList);
            }
        }
        return clusterLimitStrategies;
    }

    private List<String> queryEntranceListByAppKey(String appKey) {
        List<String> entrances = new ArrayList<>();
        Transaction t = Cat.newTransaction(CAT_KEY, "getLimitEntrances");
        try {
            LimitEntranceResult limitEntranceResult = rhino.getLimitEntrances(appKey, RHINO_KEY);
            if (limitEntranceResult.code == RHINO_RESPONSE_CODE && !limitEntranceResult.entrances.isEmpty()) {
                entrances = limitEntranceResult.entrances.stream().map(x -> x.entrance).collect(Collectors.toList());
            }
        } catch (Exception e) {
            t.setStatus(e);
            Cat.logError(appKey, e);
        } finally {
            t.complete();
        }
        return entrances;
    }

    private List<LimitStrategy> queryStrategiesByEntrance(String appKey, String entrance) {
        List<LimitStrategy> strategies = new ArrayList<>();
        Transaction t = Cat.newTransaction(CAT_KEY, "getLimitStrategies");
        try {
            LimitStrategyResult limitStrategyResult = rhino.getLimitStrategies(appKey, RHINO_KEY, entrance, SET_NAME);
            if (limitStrategyResult != null && limitStrategyResult.strategies != null && !limitStrategyResult.strategies.isEmpty()) {
                strategies = limitStrategyResult.strategies;
            }
        } catch (Exception e) {
            t.setStatus(e);
            Cat.logError(String.format("AppKey: %s, Entrance: %s", appKey, entrance), e);
        } finally {
            t.complete();
        }
        return strategies;
    }

    private LimitMainConfig getMainConfig(String appKey) {
        LimitMainConfig limitMainConfig = new LimitMainConfig();
        limitMainConfig.setMasterSwitch(SwitchStatus.UNKNOWN);
        Transaction t = Cat.newTransaction(CAT_KEY, "getLimitEntranceConfig");
        try {
            LimitEntranceResult limitEntranceResult = rhino.getLimitEntranceConfig(appKey, RHINO_KEY, SET_NAME);
            if (limitEntranceResult.code == RHINO_RESPONSE_CODE) {
                LimitEntranceConfig limitEntranceConfig = limitEntranceResult.getMainConfig();
                limitMainConfig = parseMainConfig(limitEntranceConfig);
            }
        } catch (Exception e) {
            t.setStatus(e);
            Cat.logError(appKey, e);
        } finally {
            t.complete();
        }
        return limitMainConfig;
    }

    private LimitMainConfig parseMainConfig(LimitEntranceConfig limitEntranceConfig) {
        EnumMap<LimitStrategyType, Boolean> strategySwitches = new EnumMap<>(LimitStrategyType.class);
        strategySwitches.put(LimitStrategyType.SINGLE_VM_QPS, limitEntranceConfig.strategySwitches.singleVmQps);
        strategySwitches.put(LimitStrategyType.CLUSTER_QPS_VM, limitEntranceConfig.strategySwitches.clusterQps);
        strategySwitches.put(LimitStrategyType.CLUSTER_QPS, limitEntranceConfig.strategySwitches.clusterQps);
        strategySwitches.put(LimitStrategyType.CLUSTER_FREQUENCY, limitEntranceConfig.strategySwitches.clusterFrequency);
        strategySwitches.put(LimitStrategyType.CLUSTER_QUOTA, limitEntranceConfig.strategySwitches.clusterQuota);
        strategySwitches.put(LimitStrategyType.CENTER_CLUSTER_QPS, limitEntranceConfig.strategySwitches.centerClusterQps);
        strategySwitches.put(LimitStrategyType.CLUSTER_QPS_V2, limitEntranceConfig.strategySwitches.clusterQps);
        strategySwitches.put(LimitStrategyType.CLUSTER_FREQUENCY_V2, limitEntranceConfig.strategySwitches.clusterFrequency);

        LimitMainConfig limitMainConfig = new LimitMainConfig();
        limitMainConfig.setStrategyMasterSwitch(strategySwitches);
        limitMainConfig.setMasterSwitch(limitEntranceConfig.active ? SwitchStatus.ON : SwitchStatus.OFF);
        limitMainConfig.setDebug(limitEntranceConfig.debug);
        limitMainConfig.setHostStrategy(limitEntranceConfig.hostStrategy);
        limitMainConfig.setTestHostStrategy(limitEntranceConfig.testHostStrategy);
        limitMainConfig.setAdaptiveVmStrategy(limitEntranceConfig.adaptiveVmStrategy);

        return limitMainConfig;
    }
}
