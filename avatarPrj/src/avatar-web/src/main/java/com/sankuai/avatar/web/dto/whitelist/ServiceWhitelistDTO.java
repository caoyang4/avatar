package com.sankuai.avatar.web.dto.whitelist;

import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2022-11-10 14:22
 */
@Data
public class ServiceWhitelistDTO {

    private Integer id;

    /**
     * 白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)
     */
    private WhiteType app;

    /**
     * 加白原因
     */
    private String reason;

    /**
     * 加白 appkey
     */
    private String appkey;

    /**
     * 加白 appkey 所属应用
     */
    private String application;

    /**
     * 加白 appkey 所属 org
     */
    private String orgIds;

    /**
     * 指定加白的 set
     */
    private String setName;

    /**
     * 加白申请人
     */
    private String inputUser;

    /**
     * 白名单添加时间
     */
    private Date addTime;

    /**
     * 白名单生效截止时间
     */
    private Date endTime;
}
