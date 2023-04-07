package com.sankuai.avatar.resource.appkey.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * appkey流程对象
 *
 * @author qinwei05
 * @date 2023/02/14
 */
@Data
public class AppkeyFlowBO {

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
    private String opId;

    /**
     * 环境
     */
    private String env;

    /**
     * 流程类型
     */
    private String opType;

    /**
     * 流程中文名称
     */
    private String opCnName;

}
