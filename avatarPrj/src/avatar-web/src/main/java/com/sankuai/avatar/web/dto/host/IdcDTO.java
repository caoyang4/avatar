package com.sankuai.avatar.web.dto.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 机房信息
 *
 * @author qinwei05
 * @date 2023/03/09
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdcDTO {

    /**
     * 机房下机器数目
     */
    private Integer count;

    /**
     * 名字
     */
    private String name;

    /**
     * 地区
     */
    private String region;

    /**
     * 机房中文名
     */
    private String cnName;

    /**
     * 描述
     */
    private String desc;
}
