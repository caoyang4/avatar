package com.sankuai.avatar.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MwsAuthUser {
    /**
     * sso id
     */
    private int id;
    /**
     * 用户mis号
     */
    private String login;
    /**
     * 用户中文名
     */
    private String name;
    /**
     * sso 员工号
     */
    private String code;
}
