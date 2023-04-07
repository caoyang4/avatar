package com.sankuai.avatar.sdk.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sankuai.avatar.sdk.entity.servicecatalog.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jie.li.sh
 * @create 2020-02-11
 **/

public interface ServiceCatalogAppKey {
    Slice<AppKey> listAppKey(AppKeyQueryParams appKeyQueryParams) throws Exception;

    AppKey getAppKey(String name) throws Exception;

    List<String> getAppkeylistByCondition(AppKeyQueryParams appKeyQueryParams) throws Exception;

    /**
     * 获取所有标签
     * @return taglist
     * @throws Exception
     */
    List<Tag> getAllTags() throws Exception;

    /**
     * @return 获取所有端
     * @throws Exception
     */
    List<Category> getAllCategories() throws Exception;

    /**
     * 获取所有的服务框架
     * @return framework list
     * @throws Exception
     */
    List<Framework> getAllFrameworks() throws Exception;

    @Data
    class AppKeyQueryParams {
        private Integer page;
        private Integer pageSize;
        /**
         * appkey/中文名
         */
        private String query;
        /**
         * 开发团队
         */
        @JsonProperty(value = "org_ids")
        private String orgIds;
        /**
         * 应用名
         */
        private String application;
        /**
         * 容灾等级
         */
        private String capacity;
        /**
         * 服务等级
         */
        private String rank;
        /**
         * PAAS服务
         */
        private String includePaaS;

        public Map<String, Object> buildQueryParams() {
            Map<String, Object> queryMap = new HashMap<String, Object>(20);
            queryMap.put("cn", getPage());
            queryMap.put("sn", getPageSize());
            queryMap.put("q", getQuery());
            queryMap.put("orgIds", getOrgIds());
            queryMap.put("application", getApplication());
            queryMap.put("capacity", getCapacity());
            queryMap.put("includePaaS", getIncludePaaS());
            if (getRank() != null) {
                switch (getRank()) {
                    case "核心服务":
                        queryMap.put("rank", "CORE");
                        break;
                    case "非核心服务":
                        queryMap.put("rank", "NON_CORE");
                        break;
                    default:
                        break;
                }
            }
            return queryMap;
        }
    }
}
