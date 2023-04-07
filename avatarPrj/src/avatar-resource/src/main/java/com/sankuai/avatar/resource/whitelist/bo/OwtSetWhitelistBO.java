package com.sankuai.avatar.resource.whitelist.bo;

import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import lombok.Data;

import java.util.Date;

/**
 * owt-set BO对象
 * @author caoyang
 * @create 2022-10-27 18:53
 */
@Data
public class OwtSetWhitelistBO {

    private Integer id;

    /**
     * 白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)
     */
    private WhiteType app;

    /**
     * 加白所属 owt
     */
    private String owt;

    /**
     * 指定加白的 set
     */
    private String setName;

    /**
     * 加白原因
     */
    private String reason;

    /**
     * 申请人
     */
    private String applyBy;

    /**
     * 白名单生效起始时间
     */
    private Date startTime;

    /**
     * 白名单生效截止时间
     */
    private Date endTime;
}
