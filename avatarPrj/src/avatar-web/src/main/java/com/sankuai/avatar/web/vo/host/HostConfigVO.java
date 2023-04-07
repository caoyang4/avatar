package com.sankuai.avatar.web.vo.host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 机器配置属性展示对象
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostConfigVO {

    /**
     * 网卡速率
     */
    private List<NicSpeed> nicSpeed;

    /**
     * idc机房属性
     */
    private List<IdcMetaDataVO> idc;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NicSpeed {
        /**
         * 属性值
         */
        private Integer value;
        /**
         * 标签
         */
        private String label;
    }

}
