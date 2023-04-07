package com.sankuai.avatar.sdk.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sankuai.avatar.sdk.entity.servicecatalog.Application;
import com.sankuai.avatar.sdk.entity.servicecatalog.Slice;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jie.li.sh
 * @create 2020-02-11
 **/

public interface ServiceCatalogApplication {

    /**
     * 根据条件返回应用列表
     * @param applicationQueryParams 查询参数
     * @return Slice<Application>
     */
    Slice<Application> listApplication(ApplicationQueryParams applicationQueryParams) throws Exception;

    /**
     * 根据应用名称获取应用详情
     * @param name 应用名称
     * @return Application
     */
    Application getApplication(String name) throws Exception;

    /**
     * 根据应用ID获取应用详情
     * @param applicationId 应用id
     * @return Application
     */
    Application getApplication(Integer applicationId) throws Exception;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class ApplicationQueryParams {
        private Integer page;
        private Integer pageSize;
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

        public Map<String, Object> buildQueryParams() {
            Map<String, Object> queryMap = new HashMap<String, Object>(20);
            queryMap.put("cn", getPage());
            queryMap.put("sn", getPageSize());
            queryMap.put("q", getQuery());
            queryMap.put("isPublic", getIsPublic());
            queryMap.put("domain", getDomain());
            queryMap.put("orgIds", getOrgIds());
            queryMap.put("admin", getAdmin());
            queryMap.put("member", getMember());
            if (getStartTime() != null) {
                queryMap.put("startTime", getStartTime().getTime());
            }
            if (getEndTime() != null) {
                queryMap.put("endTime", getEndTime().getTime());
            }
            return queryMap;
        }
    }
}
