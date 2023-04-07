package com.sankuai.avatar.web.dto.appkey;

import lombok.Builder;
import lombok.Data;

/**
 * appkey资源利用率对象
 *
 * @author qinwei05
 * @date 2022/12/27
 */
@Data
@Builder
public class AppkeyResourceUtilDTO {

    /**
     * 标准建议
     */
    private String standardTips;

    /**
     * 基线标准数据
     */
    private String standardData;

    /**
     * 原因
     */
    private String standardReason;

    /**
     * 类型
     */
    private String standardType;

    /**
     * 类型：中文
     */
    private String standardTypeCn;

    /**
     * 资源利用率
     */
    private Double util;

    /**
     * 是否达标
     */
    private Boolean isStandard;

    /**
     * 是否加白
     */
    private Boolean isWhite;

    /**
     * 是否可以申请加白
     */
    private Boolean isApplyWhite;
}
