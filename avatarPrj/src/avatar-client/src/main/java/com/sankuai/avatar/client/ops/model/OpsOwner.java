package com.sankuai.avatar.client.ops.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author xk
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpsOwner {
    /**
     * 用户id
     */
    private int id;

    /**
     * mis
     */
    private String login;

    /**
     * 中文名
     */
    private String name;
}
