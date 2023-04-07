package com.sankuai.avatar.workflow.core.engine.process.response;

import com.sankuai.avatar.workflow.core.context.FlowState;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * FlowProcess执行结果
 *
 * @author Jie.li.sh
 * @create 2022-10-13
 **/
@AllArgsConstructor
public class Response {

    /**
     * 流程状态
     */
    @Getter
    private final FlowState flowState;

    /**
     * 执行结果
     * 每一个FlowProcess的执行结果并不一样，这里使用Object接收
     */
    private final Result result;

    /**
     * 获得flowProcess执行结果, 会转换结果类型
     *
     * @param t t
     * @return {@link T}
     */
    public <T> T getResult(Class<T> t) {
        return t.isInstance(this.result)? t.cast(this.result): null;
    }

    public static Response of(FlowState flowState, Result result) {
        return new Response(flowState, result);
    }

    public static Response of(FlowState flowState) {
        return Response.of(flowState, null);
    }
}
