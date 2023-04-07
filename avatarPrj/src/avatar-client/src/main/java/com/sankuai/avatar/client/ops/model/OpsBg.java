package com.sankuai.avatar.client.ops.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

/**
 * OPS服务树BG
 * @author zhangxiaoning07
 * @create 2022-10-19
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpsBg {

    /**
     * BG的ID
     */
    private Integer id;

    /**
     * key
     */
    private String key;

    /**
     * BG的中文名称
     */
    private String name;
}
