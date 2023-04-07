package com.sankuai.avatar.workflow.core.notify;

import java.util.List;

/**
 * 通知内容的生成器
 *
 * @author Jie.li.sh
 * @create 2022-11-29
 **/
public interface NotifyBuilder {
    /**
     * 生成通知内容及角色等
     *
     * @param notifyRequest {@link NotifyRequest}
     * @return 通知内容
     */
    List<NotifyResult> build(NotifyRequest notifyRequest);
}
