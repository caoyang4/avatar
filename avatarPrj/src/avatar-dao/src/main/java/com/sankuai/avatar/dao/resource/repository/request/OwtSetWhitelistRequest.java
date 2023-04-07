package com.sankuai.avatar.dao.resource.repository.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * owt-set白名单的条件查询对象
 * @author caoyang
 * @create 2022-10-21 15:44
 */
@Data
@Builder
public class OwtSetWhitelistRequest {

    private Integer id;

    /**
     * 白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)
     */
    private String app;

    /**
     * 加白所属 owt
     */
    private String owt;

    /**
     * 指定加白的 set
     */
    private String setName;

    /**
     * endTime小于某时间，即查找过期白名单
     */
    private Date endTimeLtn;

    /**
     * endTime大于某时间，即查找有效白名单
     */
    private Date endTimeGtn;
}
