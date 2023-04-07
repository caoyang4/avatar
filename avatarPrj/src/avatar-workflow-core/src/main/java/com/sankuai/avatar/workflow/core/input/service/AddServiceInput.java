package com.sankuai.avatar.workflow.core.input.service;

import com.sankuai.avatar.workflow.core.input.AbstractFlowInput;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 新增服务的输入
 * @author Jie.li.sh
 * @create 2022-11-10
 **/
public class AddServiceInput extends AbstractFlowInput {
    /**
     * bg
     */
    @NotNull
    String bg;
    /**
     * pdl节点
     */
    @NotNull
    String pdlKey;
    /**
     * 服务等级
     */
    @NotNull
    String rank;
    /**
     * 应用名
     */
    @NotNull
    String soaapp;
    /**
     * 模块名
     */
    @NotNull
    String soamod;
    /**
     * 服务名
     */
    @NotNull
    String srv;
    /**
     * 中文名
     */
    @NotNull
    String name;
    /**
     * 描述
     */
    @NotNull
    String comment;
    /**
     * 标签
     */
    List<String> tags;
    /**
     * 端
     */
    List<String> categories;
    /**
     * 框架
     */
    List<String> frameworks;
    /**
     * 服务类型 Code: Java等
     */
    String srvType;
    /**
     * 预装软件
     */
    String containerType;
    /**
     * 服务状态
     */
    Boolean stateful;
    /**
     * 服务状态原因
     */
    String statefulReason;
}
