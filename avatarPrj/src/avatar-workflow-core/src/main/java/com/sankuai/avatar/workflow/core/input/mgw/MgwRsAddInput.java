package com.sankuai.avatar.workflow.core.input.mgw;

import com.sankuai.avatar.workflow.core.annotation.FlowMeta;
import com.sankuai.avatar.workflow.core.input.AbstractFlowInput;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FlowMeta(flowTemplateName = "mgw_rs_add")
public class MgwRsAddInput extends AbstractFlowInput {
    /**
     * 服务appKey
     */
    String appKey;

    /**
     * 环境
     */
    String env;

    /**
     * rs port
     */
    Integer rport;

    /**
     * 权重
     */
    Integer weight;

    /**
     * 健康检查重试间隔
     */
    Integer delayBeforeRetry;

    /**
     * 健康检查重试次数
     */
    Integer nbGetRetry;

    /**
     * 健康检查阈值
     */
    Integer htGetRetry;

    /**
     * 健康检查超时时间
     */
    Integer connectTimeout;

    /**
     * sorry rs
     */
    Boolean isSorryRs;

    /**
     * 协议
     */
    String protocol;

    /**
     * 健康检查方式
     */
    String checkType;

    /**
     * rs ip列表
     */
    List<String> rips;

    /**
     * vs 列表
     */
    List<Vs> vsList;

    /**
     * 健康检查端口
     */
    Integer connectPort;
}
