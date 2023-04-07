package com.sankuai.avatar.web.vo.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 主机表头配置
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostTitleConfigVO {

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * name
     */
    private String name;

    /**
     * key
     */
    private String key;
}
