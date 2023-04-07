package com.sankuai.avatar.resource.host.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author qinwei05
 */
@Data
@Builder
public class VmHostDiskQueryRequestBO {

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
