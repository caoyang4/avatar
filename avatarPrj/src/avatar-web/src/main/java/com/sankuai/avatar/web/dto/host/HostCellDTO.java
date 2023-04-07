package com.sankuai.avatar.web.dto.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Appkey下主机cell战士
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostCellDTO {

    /**
     * cell标签
     */
    private String label;

    /**
     * cell值
     */
    private String value;

}
