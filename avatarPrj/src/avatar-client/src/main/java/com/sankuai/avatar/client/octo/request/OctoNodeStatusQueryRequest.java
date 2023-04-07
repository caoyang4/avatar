package com.sankuai.avatar.client.octo.request;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * OCTO查询节点状态请求
 * @author qinwei05
 */
@Data
@Builder
public class OctoNodeStatusQueryRequest {

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

    /**
     * 映射成Map
     *
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public Map<String, String> toMap(){
        Map<String, String> queryRequestMap = new HashMap<>();

        if (StringUtils.isNotBlank(appkey)){
            queryRequestMap.put("appkey", appkey);
        }
        if (StringUtils.isNotBlank(ip)){
            queryRequestMap.put("ip", ip);
        }
        if (pageSize != null){
            queryRequestMap.put("pageSize", String.valueOf(pageSize));
        } else {
            queryRequestMap.put("page", "-1");
        }
        if (type != null){
            queryRequestMap.put("type", String.valueOf(type));
        }
        if (env != null){
            queryRequestMap.put("env", String.valueOf(env));
        }
        if (status != null){
            queryRequestMap.put("status", String.valueOf(status));
        }
        return queryRequestMap;
    }
}
