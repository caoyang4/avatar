package com.sankuai.avatar.client.workflow.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;


/**
 * appkey流程
 * <p>
 * 示例：
 * {
 *     "comment": "紧急Bug修复及服务扩容",
 *     "appkey": "com.sankuai.avatar.develop",
 *     "apply_time": "2023-02-14 10:18:12",
 *     "state": "HOLDING",
 *     "applicant": "qinwei05",
 *     "step": "待审核",
 *     "source": "avatar-workflow",
 *     "op_id": "493b2baf-5062-437a-bc4f-a1125774101a",
 *     "env": "prod",
 *     "op_type": "unlock_deploy",
 *     "op_cn_name": "高峰期解禁"
 * }
 *
 * @author qinwei05
 * @date 2023/02/14
 */
@Data
public class AppkeyFlow {

    /**
     * 描述
     */
    private String comment;

    /**
     * appkey
     */
    private String appkey;

    /**
     * 申请时间
     */
    @JsonProperty("apply_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 状态
     */
    private String state;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 执行状态
     */
    private String step;

    /**
     * 源
     */
    private String source;

    /**
     * 流程ID
     */
    @JsonProperty("op_id")
    private String opId;

    /**
     * 环境
     */
    private String env;

    /**
     * 流程类型
     */
    @JsonProperty("op_type")
    private String opType;

    /**
     * 流程中文名称
     */
    @JsonProperty("op_cn_name")
    private String opCnName;

}
