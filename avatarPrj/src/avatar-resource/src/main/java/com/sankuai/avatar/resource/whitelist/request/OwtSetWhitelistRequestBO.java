package com.sankuai.avatar.resource.whitelist.request;

import com.sankuai.avatar.common.vo.PageRequest;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import lombok.*;

import java.util.Date;

/**
 * owt-set白名单 bo 请求对象
 * @author caoyang
 * @create 2022-10-27 19:11
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OwtSetWhitelistRequestBO extends PageRequest {

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
     * endTime小于某时间，即查找过期白名单
     */
    private Date endTimeLtn;

    /**
     * endTime大于某时间，即查找有效白名单
     */
    private Date endTimeGtn;
}
