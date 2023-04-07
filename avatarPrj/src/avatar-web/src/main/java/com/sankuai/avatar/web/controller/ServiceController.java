package com.sankuai.avatar.web.controller;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.web.component.rhino.RhinoLimiterComponent;
import com.sankuai.avatar.web.constant.AppkeyEnvEnum;
import com.sankuai.avatar.web.vo.appkey.AppkeyRhinoLimiterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qinwei05
 */

@RestController
@RequestMapping("/api/v2/avatar/service")
public class ServiceController {

    @Autowired
    private RhinoLimiterComponent rhinoLimiterComponent;

    @MdpConfig("SERVICE_EXPAND_REASONS:[\n" +
            "  \"发生故障，紧急扩容\",\n" +
            "  \"资源利用率较高，扩容降低风险\",\n" +
            "  \"扩容替换机器，避免节点异常\",\n" +
            "  \"不符合容灾要求，进行扩容处理\",\n" +
            "  \"新服务上线\",\n" +
            "  \"其他\"\n" +
            "]")
    private String[] serviceExpandReasons;

    /**
     * 默认服务集群限流限流策略
     * @param env 环境
     * @param appkey 服务名
     * @return appkeyRhinoLimiterVO
     * */
    @GetMapping("rhinoLimiter")
    public AppkeyRhinoLimiterVO getAppkeyRhinoLimiter(@RequestParam(value = "env", required = false, defaultValue = "prod") String env,
                                                      @RequestParam(value = "appkey") String appkey) {
        AppkeyRhinoLimiterVO appkeyRhinoLimiterVO = new AppkeyRhinoLimiterVO();
        appkeyRhinoLimiterVO.setAppKey(appkey);
        appkeyRhinoLimiterVO.setEnableLimit(false);
        if (!AppkeyEnvEnum.PROD.name().equals(env) && !rhinoLimiterComponent.limiterIsEnable(appkey)) {
            appkeyRhinoLimiterVO.setClusterLimitStrategies(new HashMap<>(16));
            return appkeyRhinoLimiterVO;
        }
        // 服务集群限流策略
        Map<String, List<String>> clusterLimitStrategies = rhinoLimiterComponent.queryClusterLimitStrategy(appkey);
        if (!clusterLimitStrategies.isEmpty()){
            appkeyRhinoLimiterVO.setClusterLimitStrategies(clusterLimitStrategies);
            appkeyRhinoLimiterVO.setEnableLimit(true);
        }
        return appkeyRhinoLimiterVO;
    }

    @GetMapping("ServiceExpandReasons")
    public List<String> getServiceExpandReasons() {
        return Arrays.asList(serviceExpandReasons);
    }
}
