package com.sankuai.avatar.client.workflow;

import com.sankuai.avatar.client.workflow.model.AppkeyFlow;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;

import java.util.List;


/**
 * 《阿凡达》工作流服务接口
 *
 * @author qinwei05
 * @date 2023/02/14
 */
public interface AvatarWorkflowHttpClient {

    /**
     * 批量查询：appkey运行中与待审核的流程列表
     *
     * @param appkeyList appkey列表
     * @param state state状态（多个状态用逗号分隔）
     * @return {@link PageResponse}<{@link AppkeyFlow}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    PageResponse<AppkeyFlow> batchGetAppkeyFlowList(List<String> appkeyList, String state) throws SdkCallException, SdkBusinessErrorException;
}
