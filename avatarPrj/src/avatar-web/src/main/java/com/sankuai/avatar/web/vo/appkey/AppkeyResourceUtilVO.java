package com.sankuai.avatar.web.vo.appkey;

import com.sankuai.avatar.web.vo.whitelist.WhitelistAppVO;
import lombok.Data;

/**
 * appkey资源util签证官
 *
 * @author qinwei05
 * @version 1.0
 * @date 2023/1/3 21:36
 */
@Data
public class AppkeyResourceUtilVO {

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
     * 白名单
     */
    private WhitelistAppVO whiteApply;

    /**
     * 是否达标
     */
    private Boolean isStandard;

    /**
     * 达标信息
     */
    private String standardInfo;

    /**
     * 是否加白
     */
    private Boolean isWhite;

    /**
     * 一个月资源利用率云图链接
     */
    private String detailUrl;

    /**
     * 是否可以申请加白
     */
    private Boolean isApplyWhite;
}
