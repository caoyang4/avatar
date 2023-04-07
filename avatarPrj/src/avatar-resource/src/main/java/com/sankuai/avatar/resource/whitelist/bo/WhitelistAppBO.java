package com.sankuai.avatar.resource.whitelist.bo;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-15 10:18
 */
@Data
public class WhitelistAppBO {

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
