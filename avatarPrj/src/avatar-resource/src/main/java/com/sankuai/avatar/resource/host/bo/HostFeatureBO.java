package com.sankuai.avatar.resource.host.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * HULK主机特性
 *
 * @author qinwei05
 * @date 2023/02/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HostFeatureBO {

    /**
     * 源资源池
     */
    private String originGroup;

    /**
     * 来源
     */
    private String originType;

    /**
     * 特性
     */
    private List<String> features;
}
