package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.sankuai.avatar.capacity.service.ICapacityCollectService;
import com.sankuai.avatar.capacity.util.RocketUtil;
import com.sankuai.avatar.web.service.AppkeyCapacityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 业务容灾相关定时任务
 * @author caoyang
 * @create 2022-12-01 18:35
 */
@CraneConfiguration
@Component
@Slf4j
public class CraneAppkeyCapacity {

    private final ICapacityCollectService capacityCollect;
    private final AppkeyCapacityService appkeyCapacityService;

    @Autowired
    public CraneAppkeyCapacity(AppkeyCapacityService appkeyCapacityService,
                               ICapacityCollectService capacityCollect) {

        this.appkeyCapacityService = appkeyCapacityService;
        this.capacityCollect = capacityCollect;
    }

    /**
     * 清理已下线的 appkey 容灾信息
     */
    @Crane("com.sankuai.avatar.web.clearOfflineAppKeyForCapacity")
    public void clearOfflineAppKeyForCapacity(){
        Set<String> allOpsAppKeys = new HashSet<>(capacityCollect.getAllAppKeys());
        Set<String> currentAppKeys = appkeyCapacityService.getAllCapacityAppkey();
        log.info("ops appKeys count: {}, db appKeys count: {}", allOpsAppKeys.size(), currentAppKeys.size());
        Set<String> waitDeleteAppKey = new HashSet<>(currentAppKeys);
        waitDeleteAppKey.removeAll(allOpsAppKeys);
        waitDeleteAppKey.forEach(appKey -> {
            boolean clear = appkeyCapacityService.clearAppkeyCapacityByAppkey(appKey);
            log.info("清理appkey: {}, 返回结果 {}", appKey, clear);
        });
    }

    /**
     * 清理appkey已废弃的set，即之前有部署机器，现在无机器
     */
    @Crane("com.sankuai.avatar.web.clearInvalidAppkeySet")
    public void clearInvalidAppkeySet(){
        Set<String> appkeys = appkeyCapacityService.getAllCapacityAppkey();
        for (String appkey : appkeys) {
            Set<String> setList = RocketUtil.listSet(appkey);
            if (Objects.isNull(setList)) {continue;}
            appkeyCapacityService.clearAppkeyCapacityInvalidSet(appkey, setList);
        }
    }


}
