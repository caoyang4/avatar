package com.sankuai.avatar.client.soa.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务分页查询请求对象
 *
 * @author qinwei05
 * @date 2023/02/07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsoltAppkeyRequest {
    /**
     * 页码
     */
    private Integer page;

    /**
     * 页码数
     */
    private Integer pageSize;

    /**
     * 检索关键字
     */
    private String query;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * 服务状态
     */
    private Boolean stateful;

    /**
     * 服务容灾等级:支持多个以逗号分隔
     */
    private String capacity;

    /**
     * 用户MIS
     */
    private String mis;

    /**
     * 构建查询参数
     *
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public Map<String, String> toMap() {
        Map<String, String> queryMap = new HashMap<>(10);
        if (page != null){
            queryMap.put("cn", String.valueOf(page));
        } else {
            queryMap.put("cn", "1");
        }
        if (pageSize != null){
            queryMap.put("sn", String.valueOf(Math.min(pageSize, 100)));
        } else {
            queryMap.put("sn", "10");
        }
        if (StringUtils.isNotBlank(mis)){
            queryMap.put("mis", mis);
        }
        if (StringUtils.isNotBlank(query)){
            queryMap.put("q", query);
        }
        if (StringUtils.isNotBlank(rank)){
            queryMap.put("rank", rank);
        }
        if (stateful != null){
            queryMap.put("stateful", String.valueOf(stateful));
        }
        if (StringUtils.isNotBlank(capacity)){
            queryMap.put("capacity", capacity);
        }
        return queryMap;
    }
}

