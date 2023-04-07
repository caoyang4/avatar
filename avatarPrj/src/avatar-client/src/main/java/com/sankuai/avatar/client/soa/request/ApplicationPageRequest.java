package com.sankuai.avatar.client.soa.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询请求对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/22
 **/
@Data
public class ApplicationPageRequest {
    /**
     * 页码
     */
    private Integer page;

    /**
     * 页码数
     */
    private Integer pageSize;

    /**
     * 用户MIS
     */
    private String mis;

    /**
     * 应用名/中文名
     */
    private String query;

    /**
     * 开发团队
     */
    private String orgIds;

    /**
     * 域名
     */
    private String domain;

    /**
     * 是否是公共服务
     */
    private Boolean isPublic;

    /**
     * 负责人:mis号／中文名
     */
    private String admin;

    /**
     * 成员:mis号／中文名; 还未支持
     */
    private String member;

    /**
     * 搜索创建时间>startTime
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 搜索创建时间<endTime
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 构建查询参数
     *
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public Map<String, String> buildQueryParams() {
        Map<String, String> queryMap = new HashMap<>(20);
        if (getPage() != null) {
            queryMap.put("cn", getPage().toString());
        }
        if (getPageSize() != null) {
            queryMap.put("sn", getPageSize().toString());
        }
        queryMap.put("q", getQuery());
        queryMap.put("domain", getDomain());
        queryMap.put("orgIds", getOrgIds());
        queryMap.put("admin", getAdmin());
        queryMap.put("member", getMember());
        if (getIsPublic() != null) {
            queryMap.put("isPublic", getIsPublic().toString());
        }

        if (getStartTime() != null) {
            queryMap.put("startTime", String.valueOf(getStartTime().getTime()));
        }
        if (getEndTime() != null) {
            queryMap.put("endTime", String.valueOf(getEndTime().getTime()));
        }
        if (StringUtils.isNotBlank(mis)){
            queryMap.put("mis", mis);
        }
        return queryMap;
    }
}

