package com.sankuai.avatar.resource.octo.model;

import lombok.Data;

import java.util.List;

/**
 * @author qinwei05
 */
@Data
public class OctoNodeStatusResponseBO {

    /**
     * OCTO状态节点
     */
    private List<OctoNodeStatusProviderBO> providers;

    /**
     * 页面
     */
    private OctoPageResponseBO page;
}
