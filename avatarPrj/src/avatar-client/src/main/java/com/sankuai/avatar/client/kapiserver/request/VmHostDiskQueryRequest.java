package com.sankuai.avatar.client.kapiserver.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * VM主机磁盘查询请求
 * @author qinwei05
 */
@Data
@Builder
public class VmHostDiskQueryRequest {

    /**
     * appkey
     */
    private String appkey;

    /**
     * 主机环境
     */
    private String env;

    /**
     * 主机IP列表
     */
    private List<String> ips;
}
