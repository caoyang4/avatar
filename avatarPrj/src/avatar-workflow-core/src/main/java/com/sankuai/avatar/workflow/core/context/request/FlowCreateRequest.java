package com.sankuai.avatar.workflow.core.context.request;

import com.sankuai.avatar.workflow.core.context.FlowUserSource;
import lombok.Builder;
import lombok.Data;

/**
 * 流程创建请求
 * @author Jie.li.sh
 **/
@Data
@Builder
public class FlowCreateRequest {
    /**
     * 输入参数
     */
    String input;

    /**
     * 创建人
     */
    String createUser;

    /**
     * 创建人中文名
     */
    String createUserName;

    /**
     * 创建人类型
     */
    FlowUserSource createUserSource;
}
