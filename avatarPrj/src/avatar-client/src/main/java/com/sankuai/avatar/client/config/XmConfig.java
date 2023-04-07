package com.sankuai.avatar.client.config;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.xm.pub.push.Pusher;
import com.sankuai.xm.pub.push.PusherBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaozhifan02
 */
@Configuration
public class XmConfig {

    /**
     * avatar 应用号
     */
    private static final Long AVATAR_FROM_UID = 137442896082L;
    private static final String AVATAR_APP_KEY = "800ta54932309112";
    private static final String AVATAR_APP_TOKEN = "773c28fd70c23c539f7dea59f393a778";

    /**
     * avatar-审核应用号
     */
    private static final Long AVATAR_AUDIT_FROM_UID = 137444301369L;
    private static final String AVATAR_AUDIT_APP_KEY = "1w21381155907s00";
    private static final String AVATAR_AUDIT_APP_TOKEN = "9a1b9ac78bf7868b9547106f6a9e9cd5";

    /**
     * 应用号发消息配置，分线下和线上
     */
    @MdpConfig("DX_APP_UID:137442896082")
    private String FROM_UID;
    @MdpConfig("DX_APP_KEY:800ta54932309112")
    private String APP_KEY;
    @MdpConfig("DX_APP_TOKEN:773c28fd70c23c539f7dea59f393a778")
    private String APP_TOKEN;

    private static final String PUSH_MIS_URL = "https://xmapi.vip.sankuai.com/api/pub/push";

    private static final String PUSH_GROUP_URL = "https://xmapi.vip.sankuai.com/api/pub/pushToRoom";

    @Bean
    public Pusher userPusher() {
        return PusherBuilder.defaultBuilder()
                .withFromUid(Long.parseLong(FROM_UID))
                .withAppkey(APP_KEY)
                .withApptoken(APP_TOKEN)
                .withTargetUrl(PUSH_MIS_URL)
                .withConnectTimeOut(3 * 1000)
                .withSocketTimeOut(3 * 1000)
                .build();
    }

    @Bean
    public Pusher userAuditPusher() {
        return PusherBuilder.defaultBuilder()
                .withFromUid(AVATAR_AUDIT_FROM_UID)
                .withAppkey(AVATAR_AUDIT_APP_KEY)
                .withApptoken(AVATAR_AUDIT_APP_TOKEN)
                .withTargetUrl(PUSH_MIS_URL)
                .withConnectTimeOut(3 * 1000)
                .withSocketTimeOut(3 * 1000)
                .build();
    }

    @Bean
    public Pusher groupPusher() {
        return PusherBuilder.defaultBuilder()
                .withFromUid(AVATAR_FROM_UID)
                .withAppkey(AVATAR_APP_KEY)
                .withApptoken(AVATAR_APP_TOKEN)
                .withTargetUrl(PUSH_GROUP_URL)
                .withConnectTimeOut(3 * 1000)
                .withSocketTimeOut(3 * 1000)
                .build();
    }
}
