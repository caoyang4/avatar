package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.sankuai.avatar.collect.appkey.scheduer.AppkeyCollectEventScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author qinwei05
 * @date 2022/12/5 11:49
 */
@CraneConfiguration
@Component
@Slf4j
public class CraneAppkeyCollect {

    private final AppkeyCollectEventScheduler appkeyCollectEventScheduler;

    @Autowired
    public CraneAppkeyCollect(AppkeyCollectEventScheduler appkeyCollectEventScheduler) {
        this.appkeyCollectEventScheduler = appkeyCollectEventScheduler;
    }

    @Crane("com.sankuai.avatar.web.allAppkeyCollect")
    public void allAppkeyCollect() {
        log.info("begin to allAppkeyCollect, time: {}",  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        appkeyCollectEventScheduler.fullAppkeyCollect();
        log.info("end to allAppkeyCollect, time: {}",  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
