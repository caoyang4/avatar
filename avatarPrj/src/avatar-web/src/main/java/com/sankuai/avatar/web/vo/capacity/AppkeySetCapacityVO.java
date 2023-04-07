package com.sankuai.avatar.web.vo.capacity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * appkey set的容灾信息
 * @author caoyang
 * @create 2022-11-04 14:30
 */
@Data
public class AppkeySetCapacityVO {

    /**
     * 链路名称
     */
    private String cellName;

    /**
     * 实际容灾等级，限定 0-5
     */
    private Integer capacityLevel;

    /**
     * 达标容灾等级，限定 0-5
     */
    private Integer standardLevel;

    /**
     * 容灾是否达标
     */
    private Boolean isCapacityStandard;

    /**
     * 达标原因
     */
    private String standardReason;

    /**
     * 达标建议
     */
    private String standardTips;

    /**
     * 链路是否加白
     */
    private Boolean isWhite;

    /**
     * 加白原因
     */
    private String whiteReason;

    /**
     * 加白起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date whiteStartTime;

    /**
     * 加白到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
