package com.sankuai.avatar.client.banner.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 服务接入弹性提示
 *
 * @author qinwei05
 * @version 1.0
 * @date 2023/2/8 20:25
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticTip {

    /**
     * 气泡提示
     */
    private String bubbleTip;

    /**
     * 链接提示
     */
    private String linkTip;

    /**
     * 链接
     */
    private String link;

    /**
     * 不积极接入弹性原因枚举
     */
    private List<String> refuseActiveReasonEnum;

    /**
     * 不积极接入弹性原因
     */
    private String defaultReasonEnum;

}
