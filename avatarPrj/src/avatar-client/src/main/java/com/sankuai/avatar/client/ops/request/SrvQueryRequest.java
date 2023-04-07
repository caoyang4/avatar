package com.sankuai.avatar.client.ops.request;

import com.sankuai.avatar.common.utils.DateUtils;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * SRV检索对象
 * @author qinwei05
 * @date 2022/12/30 13:41
 */
@Data
@Builder
public class SrvQueryRequest {

    /**
     * type
     */
    private String type;

    /**
     * 用户MIS
     */
    private String user;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页数量
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
     * 技术团队
     */
    private String bg;

    /**
     * 服务树：部门
     */
    private String owt;

    /**
     * 服务树：产品线
     */
    private String pdl;

    /**
     * 上线时间: 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 上线时间: 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 映射成Map
     *
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public Map<String, String> toMap(){
        Map<String, String> srvQueryRequestMap = new HashMap<>();
        if (page != null){
            srvQueryRequestMap.put("page", String.valueOf(page));
        } else {
            srvQueryRequestMap.put("page", "1");
        }
        if (pageSize != null){
            srvQueryRequestMap.put("page_size", String.valueOf(Math.min(pageSize, 200)));
        } else {
            srvQueryRequestMap.put("page", "10");
        }
        if (startTime != null){
            srvQueryRequestMap.put("create_at$gt", DateUtils.dateToString(startTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if (endTime != null){
            srvQueryRequestMap.put("create_at$lt", DateUtils.dateToString(endTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotBlank(type)){
            srvQueryRequestMap.put("type", type);
        }
        if (StringUtils.isNotBlank(user)){
            srvQueryRequestMap.put("user", user);
        }
        if (StringUtils.isNotBlank(query)){
            srvQueryRequestMap.put("query", query);
        }
        if (StringUtils.isNotBlank(rank)){
            if ("core".equals(rank)){
                srvQueryRequestMap.put("rank", "核心服务");
            } else if ("non-core".equals(rank)) {
                srvQueryRequestMap.put("rank", "非核心服务");
            } else {
                srvQueryRequestMap.put("rank", rank);
            }
        }
        if (stateful != null){
            srvQueryRequestMap.put("stateful", String.valueOf(stateful));
        }
        if (StringUtils.isNotBlank(capacity)){
            srvQueryRequestMap.put("capacity", capacity);
        }
        if (StringUtils.isNotBlank(bg)){
            srvQueryRequestMap.put("bg", bg);
        }
        if (StringUtils.isNotBlank(owt)){
            srvQueryRequestMap.put("owt", owt);
        }
        if (StringUtils.isNotBlank(pdl)){
            srvQueryRequestMap.put("pdl", pdl);
        }
        return srvQueryRequestMap;
    }
}
