package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 流程心跳, 依赖心跳是互斥避免流程重复执行
 *
 * @author xk
 */
@Component
public class HeartbeatImpl implements Heartbeat {

//    @Autowired
    private final List<FlowHeartbeat> heartbeatList = new ArrayList<>();

    @Override
    public boolean setHeartbeat(FlowContext flowContext) {
        if (this.checkHeartbeat(flowContext)) {
            // 心跳已经存在
            return true;
        }

        FlowHeartbeat flowHeartbeat = this.doSetHeartbeat(new FlowHeartbeat(flowContext));
        heartbeatList.add(flowHeartbeat);
        return true;
    }

    @Override
    public boolean checkHeartbeat(FlowContext flowContext) {
        /*
        TODO
            1, 检查心跳是否存在, 在heartbeatList队列中 且 时间延迟满足
         */
        return false;
    }

    @Override
    public void cleanHeartbeat(FlowContext flowContext) {
        /*
        TODO 清理心跳
            1, 从 heartbeatList 移出流程
            2, 从心跳存储中移出, 如db、kv
         */
        for (FlowHeartbeat heartbeat : heartbeatList) {
            if (heartbeat.getFlowContext().getId().equals(flowContext.getId())) {
                this.heartbeatList.remove(heartbeat);
                break;
            }
        }
    }

    /**
     * 定时更新流程心跳, 每0.5更新一次
     */
    @Scheduled(fixedRate = 500)
    private void updateSetHeartbeat() {
        this.heartbeatList.forEach(this::doSetHeartbeat);
    }

    /**
     * 设置心跳
     *
     * @param flowHeartbeat 心跳对象
     * @return {@link FlowHeartbeat}
     */
    private synchronized FlowHeartbeat doSetHeartbeat(FlowHeartbeat flowHeartbeat) {
        /*
        TODO
            1, 更新心跳数据
            2，更新心跳时间
         */
        flowHeartbeat.setHeartbeatTime(new Date());
        return flowHeartbeat;
    }

    @Data
    private static class FlowHeartbeat {
        /**
         * 流程上下文
         */
        private FlowContext flowContext;

        /**
         * 最近一次心跳时间
         */
        private Date heartbeatTime;

        FlowHeartbeat(FlowContext flowContext) {
            this.flowContext = flowContext;
        }
    }
}
