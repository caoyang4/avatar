package com.sankuai.avatar.client.ops.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 * @create 2022-12-29 18:04
 */
@Data
public class OpsSrvDetail {

    /**
     * 主机数
     */
    @JsonProperty("host_count")
    private Integer hostCount;

    /**
     * 服务树节点信息
     */
    @JsonProperty("srv")
    private OpsSrv srv;

    /**
     * 用户
     */
    @JsonProperty("users")
    private Map<String, String> users;

    /**
     * appkeys
     */
    @JsonProperty("appkeys")
    private List<String> appkeys;
}