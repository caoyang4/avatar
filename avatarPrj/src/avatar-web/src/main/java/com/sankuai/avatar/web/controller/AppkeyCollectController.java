package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEvent;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventData;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.scheduer.AppkeyCollectEventScheduler;
import com.sankuai.avatar.web.transfer.appkey.AppkeyVOTransfer;
import com.sankuai.avatar.web.vo.appkey.AppkeyCollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 服务采集
 *
 * @author qinwei05
 */
@RestController
@RequestMapping("/api/v2/avatar/collect")
public class AppkeyCollectController {

    private final AppkeyCollectEventScheduler appkeyCollectEventScheduler;

    @Autowired
    public AppkeyCollectController(AppkeyCollectEventScheduler appkeyCollectEventScheduler) {
        this.appkeyCollectEventScheduler = appkeyCollectEventScheduler;
    }

    /**
     * 后端服务信息批量采集(逗号分隔)，提供API触发接口
     *
     * @param appkeys appkeys
     * @return {@link List}<{@link AppkeyCollectVO}>
     */
    @GetMapping("backend")
    public List<AppkeyCollectVO> getBackendAppkeyCollectResult(@RequestParam(value = "appkeys") String appkeys) {
        List<AppkeyCollectVO> appkeyCollectVOList = new ArrayList<>();
        String[] appkeyList = appkeys.split(",");
        for (String appkey : appkeyList) {
            AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                    .collectEventData(AppkeyCollectEventData.of(appkey))
                    .collectEventName(CollectEventName.APPKEY_REFRESH)
                    .build();
            Appkey appkeyModel = appkeyCollectEventScheduler.collect(appkeyCollectEvent);
            appkeyCollectVOList.add(AppkeyVOTransfer.INSTANCE.toVO(appkeyModel));
        }
        return appkeyCollectVOList;
    }

    /**
     * 非后端服务信息批量采集(逗号分隔)，提供API触发接口
     *
     * @param appkeys appkeys
     * @return {@link List}<{@link AppkeyCollectVO}>
     */
    @GetMapping("notBackend")
    public List<AppkeyCollectVO> getNotBackendAppkeyCollectResult(@RequestParam(value = "appkeys") String appkeys) {
        List<AppkeyCollectVO> appkeyCollectVOList = new ArrayList<>();
        String[] appkeyList = appkeys.split(",");
        for (String appkey : appkeyList) {
            AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                    .collectEventData(AppkeyCollectEventData.of(appkey))
                    .collectEventName(CollectEventName.SC_NOT_BACKEND_APPKEY_UPDATE)
                    .build();
            Appkey appkeyModel = appkeyCollectEventScheduler.collect(appkeyCollectEvent);
            appkeyCollectVOList.add(AppkeyVOTransfer.INSTANCE.toVO(appkeyModel));
        }
        return appkeyCollectVOList;
    }
}
