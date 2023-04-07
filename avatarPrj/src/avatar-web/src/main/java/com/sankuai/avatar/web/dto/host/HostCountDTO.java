package com.sankuai.avatar.web.dto.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 各个环境主机数目展示
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostCountDTO {

    /**
     * 线上环境机器数目
     */
    private Integer prod;

    /**
     * 线上staging环境机器数目
     */
    private Integer staging;

    /**
     * 线下环境机器数目
     */
    private Integer test;

    /**
     * 线下dev环境机器数目
     */
    private Integer dev;

}
