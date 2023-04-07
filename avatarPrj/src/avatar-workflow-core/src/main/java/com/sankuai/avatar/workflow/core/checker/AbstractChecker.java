package com.sankuai.avatar.workflow.core.checker;

import com.sankuai.avatar.workflow.core.annotation.CheckerMeta;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowUserSource;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author xk
 */
public abstract class AbstractChecker implements Checker {

    /**
     * API 用户前缀
     */
    private static final String API_USER_PREFIX = "__";

    private final CheckerMeta checkerMeta = this.getClass().getAnnotation(CheckerMeta.class);

    @Override
    public final Integer getTimeout() {
        return this.checkerMeta==null? null: this.checkerMeta.timeout();
    }

    @Override
    public final Integer getOrder() {
        return this.checkerMeta==null? null: this.checkerMeta.order();
    }

    @Override
    public boolean matchCheck(FlowContext flowContext) {
        if (this.shouldSkipAppkeyUser(flowContext)) {
            return false;
        }
        return this.matchFlow(flowContext);
    }

    /**
     * 判断是否跳过appkey用户
     * @param flowContext 流程上下文
     * @return 是/否
     */
    protected final Boolean shouldSkipAppkeyUser(FlowContext flowContext) {
        //appkey用户跳过检查
        boolean isApiUser = flowContext.getCreateUser().startsWith(API_USER_PREFIX);
        boolean isAppKeySource = flowContext.getCreateUserSource() == FlowUserSource.APPKEY;
        return !checkerMeta.checkAppkeyUser() && isApiUser && isAppKeySource;
    }

    /**
     * 匹配流程是否预检, 遍历flowName注解
     *
     * @return boolean
     */
    protected final boolean matchFlow(FlowContext flowContext) {
        String[] flowNames  = this.checkerMeta==null? new String[0]: this.checkerMeta.value();
        return Arrays.asList(flowNames).contains(flowContext.getTemplateName());
    }

    @Override
    public int compareTo(Checker o) {
        if (!Objects.equals(this.getOrder(), o.getOrder())) {
            return this.getOrder() - o.getOrder();
        }
        return 0;
    }
}
