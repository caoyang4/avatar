package com.sankuai.avatar.resource.appkey.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * appkey资源利用率对象
 *
 * @author qinwei05
 * @date 2022/11/07
 */
@Data
public class AppkeyResourceUtilBO {

    /**
     * 是需要优化
     */
    private Boolean isNeedOptimize;

    /**
     * 标准原因
     */
    private String standardReason;

    /**
     * CELL(SET) 资源利用率信息
     */
    private List<CellList> cellList;

    /**
     * 优化建议
     */
    private List<String> optimizeMsg;

    /**
     * 白名单
     */
    private List<Whitelist> whitelists;

    /**
     * 基线配置
     */
    @JsonProperty("msg_config")
    private MsgConfig msgConfig;

    /**
     * 利用率标准
     */
    private String utilizationStandard;

    /**
     * 资源利用率
     */
    private ResourceUtil resourceUtil;

    /**
     * appkey
     */
    private String appkey;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Whitelist {
        private String reason;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date endTime;
        private String whiteApp;
        private String cName;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResourceUtil {
        /**
         * 半年资源利用率
         */
        private double halfYPeekValue;
        /**
         * 年资源利用率
         */
        private double yearPeekValue;
        /**
         * 上周资源利用率
         */
        private double lastWeekValue;
        /**
         * 幕墙资源利用率
         */
        private double value;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MsgConfig {
        /**
         * 资源利用率基线
         */
        private double baseline;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CellList {
        private double yearPeekValue;
        private double lastWeekValue;
        private String standardReason;
        private double value;
        private Integer standardLevel;
        private Integer capacityLevel;
        private String cellName;
        private Integer isCapacityStandard;
        private Integer hostCount;
    }
}
