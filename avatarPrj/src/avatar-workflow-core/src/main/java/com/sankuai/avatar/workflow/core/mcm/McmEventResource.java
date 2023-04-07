package com.sankuai.avatar.workflow.core.mcm;

import lombok.Data;

import java.util.List;

/**
 * MCM 事件变更资源
 *
 * @author zhaozhifan02
 */
@Data
public class McmEventResource {
    /**
     * appkey
     */
    String appkey;

    /**
     * 主机列表
     */
    List<String> host;

    /**
     * srv 服务
     */
    String srv;

    /**
     * 域名
     */
    String domain;
}
