package com.sankuai.avatar.web.vo.appkey;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/3/28 11:12
 * @version 1.0
 */
@Data
public class AppkeyRhinoLimiterVO {
    /**
     * appkey 名称，唯一标识
     */
    private String appKey;

    /**
     * enableLimit 是否开启限流
     */
    private Boolean enableLimit;

    /**
     * 限流策略
     */
    private Map<String, List<String>> clusterLimitStrategies;
}
