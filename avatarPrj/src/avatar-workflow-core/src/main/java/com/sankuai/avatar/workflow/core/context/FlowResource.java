package com.sankuai.avatar.workflow.core.context;

import com.sankuai.avatar.workflow.core.context.resource.Host;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 流程中的资源对象，比如appkey/主机/域名
 *
 * @author Jie.li.sh
 * @create 2022-11-03
 **/
@Data
@Builder
public class FlowResource {
    /**
     * appkey
     */
    String appkey;

    /**
     * 主机列表
     */
    List<Host> hostList;

    /**
     * 仓库地址
     */
    String repository;

    /**
     * srv 服务
     */
    String srv;

    /**
     * 域名
     */
    String domain;
}
