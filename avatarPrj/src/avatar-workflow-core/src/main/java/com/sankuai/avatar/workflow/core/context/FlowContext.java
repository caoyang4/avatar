package com.sankuai.avatar.workflow.core.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 流程相关上下文参数
 * @author Jie.li.sh
 **/
@Data
@Builder
public class FlowContext {
    /**
     * id
     */
    Integer id;

    /**
     * uuid
     */
    String uuid;

    /**
     * 模板Id
     */
    Integer templateId;

    /**
     * 模版名称
     */
    String templateName;

    /**
     * 模版中文名称
     */
    String cnName;

    /**
     * 环境
     */
    String env;

    /**
     * 流程资源对象, appkey/host/domain等
     */
    FlowResource resource;

    /**
     * 创建人
     */
    String createUser;

    /**
     * 创建人中文名
     */
    String createUserName;

    /**
     * 创建人来源, user/appkey
     */
    FlowUserSource createUserSource;

    /**
     * 创建时间
     */
    Date startTime;

    /**
     * 结束时间
     */
    Date endTime;

    /**
     * 输入参数
     */
    FlowInput flowInput;

    /**
     * 配置信息
     */
    FlowConfig flowConfig;

    /**
     * 流程状态
     */
    FlowState flowState;

    /**
     * 当前执行的模块索引
     */
    Integer processIndex;

    /**
     * 当前执行的Atom索引
     */
    Integer atomIndex;

    /**
     * 引用模板的版本号
     */
    Integer version;

    /**
     * 流程审核信息
     */
    FlowAudit flowAudit;

    /**
     * 流程上报CT事件信息
     */
    FlowEvent flowEvent;

    /**
     * 当前FlowProcess
     */
    @JsonIgnore
    private ProcessContext currentProcessContext;
}
