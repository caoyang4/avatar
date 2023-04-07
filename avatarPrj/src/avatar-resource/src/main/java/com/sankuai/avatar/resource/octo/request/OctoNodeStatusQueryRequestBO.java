package com.sankuai.avatar.resource.octo.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author qinwei05
 */
@Data
@Builder
public class OctoNodeStatusQueryRequestBO {

    /**
     * appkey
     */
    private String appkey;

    /**
     * 协议
     * 1:thrift
     * 2: Http
     */
    private Integer type;

    /**
     * 环境
     * 1: dev; 2: test; 3: stage; 4: prod
     */
    private Integer env;

    /**
     * 节点状态：默认-1
     * -1 :所有
     * 0:未启动
     * 1: 启动中
     * 2:正常
     * 4:禁用
     */
    private Integer status;

    /**
     * 页码大小
     * 接口默认分页，不保证顺序，pageSize=-1取消分页
     */
    private Integer pageSize;

    /**
     * ip
     */
    private String ip;
}
