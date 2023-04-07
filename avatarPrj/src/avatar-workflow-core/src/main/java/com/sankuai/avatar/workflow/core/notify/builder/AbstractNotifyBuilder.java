package com.sankuai.avatar.workflow.core.notify.builder;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.workflow.core.notify.NotifyBuilder;
import com.sankuai.avatar.workflow.core.notify.NotifyReceiverRole;
import com.sankuai.avatar.workflow.core.notify.NotifyRequest;
import com.sankuai.avatar.workflow.core.notify.NotifyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 预检通过后, 发起人发送通知
 * @author Jie.li.sh
 * @create 2022-12-02
 **/
@Component
public abstract class AbstractNotifyBuilder implements NotifyBuilder {
    @Autowired
    TemplateParser templateParser;

    @MdpConfig("AVATAR_DOMAIN:https://mbop.oversea.test.sankuai.com")
    String avatarDomain;

    /**
     * 渲染通知消息体
     *
     * @param notifyResult  通知结果
     * @param notifyRequest 通知请求
     * @return {@link NotifyResult}
     */
    public NotifyResult defaultBuild(NotifyResult notifyResult, NotifyRequest notifyRequest) {
        // 兜底角色
        if (notifyResult.getNotifyReceiverRole() == null) {
            notifyResult.setNotifyReceiverRole(NotifyReceiverRole.OTHER);
        }
        // 根据flow模版渲染msg
        Map<String,Object> params = new HashMap<>(16);
        params.put("title", notifyResult.getTitle());
        params.put("flowContext", notifyRequest.getFlowContext());
        params.put("avatarDomain", avatarDomain);
        params.put("errorMsgList", notifyRequest.getErrorMsgList());
        notifyResult.setMsg(templateParser.parse(getTemplateName(), params));
        return notifyResult;
    }

    /**
     * 对应模版名
     *
     * @return template name from resource file
     */

    String getTemplateName() {
        return "flow_default.ftl";
    }
}
