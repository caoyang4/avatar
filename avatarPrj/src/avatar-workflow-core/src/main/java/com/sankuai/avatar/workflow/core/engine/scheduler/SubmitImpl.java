package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 提交process至调度端
 * @author xk
 */
@Component
public class SubmitImpl extends SubmitAbstract {
    @Autowired
    Scheduler scheduler;

    @Override
    boolean submitDb(Integer flowId) {
        return false;

    }

    @Override
    boolean submitMq(Integer flowId) {
        return false;
    }

    @Override
    Future<Response> submitScheduler(ProcessTemplate processTemplate, SchedulerEventContext schedulerEventContext) {
        return scheduler.dispatch(processTemplate, schedulerEventContext);
    }
}
