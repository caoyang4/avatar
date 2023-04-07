package com.sankuai.avatar.web.vo.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2021/8/18 10:15
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceSumResult {

    /**
     * 字段: cname
     * 说明: 窗口期名称
     */
    private String name;

    /**
     * 字段: cname
     */
    private String description;

    /**
     * 字段: description
     */
    private List<RegionSum> data;
}
