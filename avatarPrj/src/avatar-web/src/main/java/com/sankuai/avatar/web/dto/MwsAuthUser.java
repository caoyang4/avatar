package com.sankuai.avatar.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Jie.li.sh
 * @create 2020-02-25
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MwsAuthUser {
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
