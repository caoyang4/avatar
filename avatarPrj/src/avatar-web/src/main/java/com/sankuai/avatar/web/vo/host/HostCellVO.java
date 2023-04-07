package com.sankuai.avatar.web.vo.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Appkey下主机cell展示
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostCellVO {

    /**
     * cell标签
     */
    private String label;

    /**
     * cell值
     */
    private String value;

}
