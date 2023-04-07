package com.sankuai.avatar.client.mgw.request;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qinwei05
 * @date 2022/12/25 20:00
 */
@Data
@Builder
public class MgwVsRequest {

    /**
     * appkey
     */
    private String appkey;

    /**
     * vip
     */
    private String vip;

    /**
     * vport
     */
    private Integer vport;

    /**
     * protocol协议
     */
    private String protocol;

    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * 机房名称
     */
    private String idcName;

    /**
     * vs环境
     */
    private String vsEnv;


    /**
     * 映射成Map
     *
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public Map<String, String> toMap(){

        Map<String, String> vsQueryRequestMap = new HashMap<>();
        if (StringUtils.isNotBlank(appkey)){
            vsQueryRequestMap.put("appkey", appkey);
        }
        if (StringUtils.isNotBlank(vip)){
            vsQueryRequestMap.put("vip", vip);
        }
        if (vport != null){
            vsQueryRequestMap.put("vport", String.valueOf(vport));
        }
        if (StringUtils.isNotBlank(protocol)){
            vsQueryRequestMap.put("protocol", protocol);
        }
        if (StringUtils.isNotBlank(clusterName)){
            vsQueryRequestMap.put("cluster_name", clusterName);
        }
        if (StringUtils.isNotBlank(idcName)){
            vsQueryRequestMap.put("idc_name", idcName);
        }
        if (StringUtils.isNotBlank(vsEnv)){
            vsQueryRequestMap.put("vs_env", vsEnv);
        }
        return vsQueryRequestMap;
    }
}
