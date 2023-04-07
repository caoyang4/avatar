package com.sankuai.avatar.web.dal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CasUser {
    /**
     * 用户mis号
     */
    private String loginName;
    /**
     * 用户中文名
     */
    private String name;
    /**
     * sso 员工号
     */
    private String code;

    /**
     * 用户类型: mis/appkey
     */
    private CasType casType;
}
