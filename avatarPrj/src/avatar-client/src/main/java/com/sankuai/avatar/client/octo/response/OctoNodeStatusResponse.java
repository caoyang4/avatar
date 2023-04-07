package com.sankuai.avatar.client.octo.response;

import com.sankuai.avatar.client.octo.model.OctoNodeStatusProvider;
import lombok.Data;

import java.util.List;

/**
 * @author qinwei05
 */
@Data
public class OctoNodeStatusResponse {

    /**
     * OCTO状态节点
     */
    private List<OctoNodeStatusProvider> providers;

    /**
     * 页面
     */
    private OctoPageResponse page;
}
