package com.sankuai.avatar.web.vo.appkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Appkey路由策略
 *
 * @author qinwei05
 * @date 2023/02/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyRouteStrategyVO {

    /**
     * http协议路由分组策略
     */
    private String http;

    /**
     * thrift协议路由分组策略
     */
    private String thrift;

    /**
     * 分组策略文案展示
     */
    private String context;
}
