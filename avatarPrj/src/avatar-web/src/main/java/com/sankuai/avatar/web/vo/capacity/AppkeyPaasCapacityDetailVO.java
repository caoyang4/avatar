package com.sankuai.avatar.web.vo.capacity;

import com.sankuai.avatar.common.vo.PageResponse;
import lombok.Data;

/**
 * 服务依赖Paas容灾详情
 * @author Jie.li.sh
 * @create 2022-09-26
 **/
@Data
public class AppkeyPaasCapacityDetailVO {
    /**
     * 业务 appkey
     */
    private String appkey;

    /**
     * 总体容灾等级，剔除加白，其余取小
     */
    private Integer totalCapacityLevel;

    /**
     * 总体达标等级
     */
    private Integer totalStandardCapacityLevel;

    /**
     * paas总体容灾是否达标
     */
    private Boolean isTotalStandard;

    /**
     * 达标原因
     */
    private String standardReason;

    /**
     * 业务 appkey 所依赖的所有 paas 容灾信息
     */
    private PageResponse<AppkeyPaasCapacityVO> paasCapacityLevels;

}
