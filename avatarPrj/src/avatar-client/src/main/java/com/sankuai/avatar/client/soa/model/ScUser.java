package com.sankuai.avatar.client.soa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * SC接口返回的User模型对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/10
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScUser {
    /**
     * mis号
     */
    private String mis;

    /**
     * 中文名
     */
    private String name;

    /**
     * dx头像
     */
    private String avatarUrl;

    /**
     * 组织架构名称
     */
    private ScOrgTreeNode org;
}
