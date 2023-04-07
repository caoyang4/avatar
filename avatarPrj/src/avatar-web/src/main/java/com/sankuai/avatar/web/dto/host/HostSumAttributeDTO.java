package com.sankuai.avatar.web.dto.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 主机列表头部统计属性展示
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostSumAttributeDTO {

    /**
     * idc名字
     */
    private List<Title> idcName;

    /**
     * 机器类型名字
     */
    private List<Title> kindName;

    /**
     * 泳道
     */
    private List<Title> swimlane;

    /**
     * 管理平台
     */
    private List<Title> managePlat;

    /**
     * cell
     */
    private List<Title> cell;

    /**
     * 网络类型
     */
    private List<Title> netType;

    /**
     * 业务分组
     */
    private List<Title> grouptags;

    /**
     * 来源类型
     */
    private List<Title> originType;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Title {
        /**
         * 文本
         */
        private String text;
        /**
         * 值
         */
        private String value;
    }
}
