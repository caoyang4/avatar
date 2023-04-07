package com.sankuai.avatar.web.dto.whitelist;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-10 14:28
 */
@Data
public class WhitelistAppDTO {

    private Integer id;

    /**
     *  白名单类型
     */
    private String app;

    /**
     *  白名单中文名称
     */
    private String cname;

    /**
     *  白名单说明
     */
    private String description;
}
