package com.sankuai.avatar.workflow.core.execute.listener.atomListen;

import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import org.springframework.stereotype.Component;

/**
 * atom执行失败监听器
 *
 * @author xk
 */
@Component
public class NoticeListenImpl implements AtomListener {

    /**
     * atom执行失败, 发送告警
     *
     * @param atomContext 原子上下文
     */
    @Override
    public void atomFail(AtomContext atomContext) {
        /*
        TODO
            调用大象接口，发送atom执行失败告警信息
         */
    }
}
