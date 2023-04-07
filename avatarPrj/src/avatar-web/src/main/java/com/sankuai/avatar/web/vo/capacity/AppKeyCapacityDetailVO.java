package com.sankuai.avatar.web.vo.capacity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * appkey业务容灾详细信息
 * @author caoyang
 * @create 2022-08-08 15:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppKeyCapacityDetailVO {

    /**
     * set 的容灾信息
     */
    private List<AppkeySetCapacityVO> capacityLevels;

    /**
     * 容灾白名单信息
     */
    private List<AppkeyCapacityWhiteVO> capacityWhiteList;

    /**
     * 是否 set 化
     */
    private Boolean hasSet;

    /**
     * 整体实际容灾
     */
    private Integer defaultCapacityLevel;

    /**
     * 整体标准容灾
     */
    private Integer standardCapacityLevel;

    /**
     * 整体是否达标
     */
    private Boolean isStandard;

    /**
     * 达标原因
     */
    private String standardReason;

    /**
     * 达标建议
     */
    private String standardTips;

    /**
     * 是否可延期加白
     */
    private Boolean canExpireApply;
}
