package com.sankuai.avatar.dao.es.request.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 审核人
 *
 * @author zhaozhifan02
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApproveUser {
    /**
     * 审核状态
     */
    private String status;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String desc;

    /**
     * 操作类型
     */
    private String op;

    /**
     * 标签
     */
    private String tag;

    /**
     * 用户
     */
    private List<String> user;
}
