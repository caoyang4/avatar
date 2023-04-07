package com.sankuai.avatar.client.rocket.model;

import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qinwei05
 * @date 2022/11/30 13:41
 */
@Data
@Builder
public class HostQueryRequest {

    /**
     * appkey
     */
    private String appkey;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 返回的条数
     */
    private Integer limit;

    /**
     * 主机环境，不传则返回所有环境数据
     */
    private String env;

    /**
     * 返回的主机信息字段, 以,间隔
     */
    private String fields;

    /**
     * 映射成Map
     *
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public Map<String, String> toMap(){
        if (StringUtils.isBlank(appkey)){
            throw new SdkCallException("appkey参数不可以为空");
        }
        Map<String, String> hostQueryRequestMap = new HashMap<>();
        hostQueryRequestMap.put("appkey", appkey);
        if (offset != null){
            hostQueryRequestMap.put("offset", String.valueOf(offset));
        }
        if (limit != null){
            hostQueryRequestMap.put("limit", String.valueOf(limit));
        }
        if (StringUtils.isNotBlank(env)){
            hostQueryRequestMap.put("env", env);
        }
        if (StringUtils.isNotBlank(fields)){
            hostQueryRequestMap.put("fields", fields);
        }
        return hostQueryRequestMap;
    }
}
