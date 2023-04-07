package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.dianping.cat.Cat;
import com.sankuai.avatar.web.dto.capacity.AppkeyCapacitySummaryDTO;
import com.sankuai.avatar.web.service.AppkeyCapacityService;
import com.sankuai.avatar.web.service.AppkeyPaasCapacityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * paas 容灾相关定时任务
 * @author caoyang
 * @create 2022-10-12 15:42
 */
@CraneConfiguration
@Component
@Slf4j
public class CraneAppkeyPaasCapacity {

    private final AppkeyPaasCapacityService paasCapacityService;
    private final AppkeyCapacityService appkeyCapacityService;

    @Autowired
    public CraneAppkeyPaasCapacity(AppkeyPaasCapacityService paasCapacityService,
                                   AppkeyCapacityService appkeyCapacityService) {
        this.paasCapacityService = paasCapacityService;
        this.appkeyCapacityService = appkeyCapacityService;
    }

    @Crane("com.sankuai.avatar.web.cleanAppkeyPaasCapacity")
    public void cleanAppkeyPaasCapacity() {
        LocalDate localDate = LocalDate.now().plusDays(-7);
        try {
            paasCapacityService.deleteAppkeyPaasCapacityByUpdateDate(localDate);
        } catch (Exception e){
            Cat.logError(e);
        }
    }

    @Crane("com.sankuai.avatar.web.updateOpsPaasCapacity")
    public void updateOpsPaasCapacity(){
        List<String> paasAppkeys = paasCapacityService.getValidPaasAppkeys();
        for (String appkey : paasAppkeys) {
            AppkeyCapacitySummaryDTO summary = paasCapacityService.getAppkeyPaasCapacitySummaryByPaasAppkeyNoCache(appkey);
            if (!appkeyCapacityService.updateOpsCapacity(appkey, summary.getStandardTips(), summary.getCapacityLevel())) {
                log.error("{}更新ops容灾失败", appkey);
            }
        }
    }

    /**
     * 总体容灾缓存
     */
    @Crane("com.sankuai.avatar.web.cacheCapacitySummary")
    public void cacheCapacitySummary(){
        for (String appkey : paasCapacityService.getValidPaasAppkeys()) {
            paasCapacityService.cacheAppkeyCapacitySummary(appkey, true);
        }
        for (String appkey : paasCapacityService.getValidClientAppkeys()) {
            paasCapacityService.cacheAppkeyCapacitySummary(appkey, false);
        }
    }

}
